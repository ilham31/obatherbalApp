package com.example.ilham.obatherbal.analysisJava.prediction.confirmPage;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsModel;
import com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionPlant.resultPredictionPlant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmPrediction extends Fragment {
    private View view;
    private TextView method;
    private RecyclerView recyclerView;
    private adapterConfirm adapterConfirm;
    ArrayList<herbsModel> idPlant;
    ArrayList<String>postIdPlant;
    ArrayList<String>id;
    ArrayList<String>plantName;
    Button submitPredictPlant;
    String Categories;
    ProgressBar predictProgress;
    TextView progressNotif,phasePredict;
    Handler handler = new Handler();
    String idCategories;
    private int progressStatus = 0;
    int optimization;
    String classDisease;
    String descDisease;
    String disease;
    public confirmPrediction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_result_prediction, container, false);
        idPlant= (ArrayList<herbsModel>)getArguments().getSerializable("idPlant");
        plantName = new ArrayList<String>();
        idCategories= getArguments().getString("idCategories");
        Categories= getArguments().getString("categories");
        optimization = getArguments().getInt("Optimization");
        method = (TextView) view.findViewById(R.id.chosenMethod);
        method.setText("Method :"+Categories);
        submitPredictPlant = (Button) view.findViewById(R.id.submitPredictPlant);
        recyclerView = (RecyclerView) view.findViewById(R.id.plantPredict);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterConfirm = new adapterConfirm(getActivity(),idPlant);
        recyclerView.setAdapter(adapterConfirm);
        submitPredictPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idPlant.size()>0)
                {
                    AlertDialog.Builder myDialogBuilder = new AlertDialog.Builder(getActivity());
                    View ProgressView = getLayoutInflater().inflate(R.layout.layout_loading_dialog,
                            null);
                    predictProgress=(ProgressBar)ProgressView.findViewById(R.id.progressbarPredict);
                    progressNotif = (TextView) ProgressView.findViewById(R.id.progressNotif);
                    phasePredict = (TextView) ProgressView.findViewById(R.id.phasePredict);
                    phasePredict.setText("Please wait a minute");
                    myDialogBuilder.setView(ProgressView);
                    myDialogBuilder.create().show();
                    getPredictionResult();

                }
                else
                {
                    Toast.makeText(getActivity(),"please choose at least 1 plant",Toast.LENGTH_LONG).show();
                }


            }
        });

        for (herbsModel h : idPlant)
        {
            Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
        }
        Log.d("confirm","idcategories" + idCategories);
        return view;
    }

    private void getPredictionResult() {
        postIdPlant = new ArrayList<>();
        for (herbsModel h : idPlant)
        {
            Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
            postIdPlant.add(h.getIdPlant());
        }
        String param = "";
        for (int i = 0 ; i < postIdPlant.size();i++){
            param=param+"&id[]="+postIdPlant.get(i);
        }

        String url = "https://api.jamumedicine.com/jamu/api/user/secret?type=crude&optimization="+optimization+"&model="+idCategories+param;
        Log.d("confirm","url = "+url);
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("confirm","url = "+response.toString());
                    JSONObject data = response.getJSONObject("data");
                    classDisease = data.getString("class");
                    descDisease = data.getString("description");
                    disease = data.getString("diseases");
                    loadData();
//                    final String refDisease = data.getString("ref");
                    Log.d("confirm","dataresponse = "+classDisease + descDisease + disease);
                    for (herbsModel h : idPlant)
                    {
                        Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
                        plantName.add(h.getNameHerbs());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                 String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getActivity(), message,
                        Toast.LENGTH_LONG).show();
            }
        });
        myReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(myReq);
    }

    private void loadData() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    // Update the progress bar and display the
                    //current value in the text view
                    if (idPlant.size() < 4) progressStatus+= 1;
                    else progressStatus+=0.5;
                    handler.post(new Runnable() {
                        public void run() {
                            predictProgress.setProgress(progressStatus);
                            progressNotif.setText(progressStatus+"/"+predictProgress.getMax());
                            if (progressStatus <= 25)
                            {
                                phasePredict.setText("Phase :"+"Phase 1");
                            }
                            else if (progressStatus <= 50){
                                phasePredict.setText("Phase :"+"Phase 2");
                            }
                            else if (progressStatus <= 75){
                                phasePredict.setText("Phase :"+"Phase 3");
                            }
                            else if (progressStatus == 100)
                            {
                                Intent i = new Intent(getActivity(),resultPredictionPlant.class);
                                Bundle args = new Bundle();
                                args.putString("methodPredict",Categories);
                                args.putStringArrayList("plantName", plantName);
                                args.putString("classDisease",classDisease);
                                args.putString("descDisease",descDisease);
//                                args.putString("refDisease",refDisease);
                                args.putString("disease",disease);
                                i.putExtra("bundle",args);
                                startActivity(i);
                                getActivity().finish();
                            }
                            else{
                                phasePredict.setText("Phase :"+"Phase 4");
                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
