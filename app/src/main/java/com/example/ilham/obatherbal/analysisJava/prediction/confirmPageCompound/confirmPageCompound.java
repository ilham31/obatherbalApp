package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound.compoundPredictModel;
import com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionCompound.resultPredictionCompound;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmPageCompound extends Fragment {

    View view;
    private TextView method;
    private RecyclerView recyclerView;
    private adapterConfirmCompound adapterConfirmCompound;
    ArrayList<compoundPredictModel> idCompound;
    Button submitCompound;
    ArrayList<String>compoundName;
    ProgressBar predictProgress;
    TextView progressNotif,phasePredict;
    Handler handler = new Handler();
    private int progressStatus = 0;
    int optimization;
    String category;
    String idCategories;
    ArrayList<String>postIdCompound;
    String classDisease;
    String descDisease;
    String disease;

    public confirmPageCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_page_compound, container, false);
        compoundName = new ArrayList<>();
        idCompound= (ArrayList<compoundPredictModel>)getArguments().getSerializable("idCompound");
        idCategories= getArguments().getString("idCategories");
        category = getArguments().getString("categories");
        optimization = getArguments().getInt("Optimization");
        method = (TextView) view.findViewById(R.id.chosenMethodCompound);
        method.setText("Method :"+category);
        recyclerView = (RecyclerView) view.findViewById(R.id.compoundPredict);
        submitCompound = (Button) view.findViewById(R.id.submitPredictCompound);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        View v = inflater.inflate(R.layout.layout_loading_dialog, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();

        
        submitCompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCompound.size()>0)
                {
                    for (compoundPredictModel h : idCompound)
                    {
                        Log.d("confirm","id plant = "+h.getIdCompound()+" name = "+h.getNameCompound());
                        compoundName.add(h.getNameCompound());
                    }
                    AlertDialog.Builder myDialogBuilder = new AlertDialog.Builder(getActivity());
                    View ProgressView = getLayoutInflater().inflate(R.layout.layout_loading_dialog,
                            null);
                    predictProgress=(ProgressBar)ProgressView.findViewById(R.id.progressbarPredict);
                    progressNotif = (TextView) ProgressView.findViewById(R.id.progressNotif);
                    phasePredict = (TextView) ProgressView.findViewById(R.id.phasePredict);

                    myDialogBuilder.setView(ProgressView);
                    myDialogBuilder.create().show();
                    getPredictionResult();
                }
                else
                {
                    Toast.makeText(getActivity(),"please choose at least 1 compound",Toast.LENGTH_LONG).show();
                }

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterConfirmCompound = new adapterConfirmCompound(getActivity(),idCompound);
        recyclerView.setAdapter(adapterConfirmCompound);



        return view;
    }

    private void getPredictionResult() {
        postIdCompound = new ArrayList<>();
        for (compoundPredictModel h : idCompound)
        {
            Log.d("confirm","id compound = "+h.getIdCompound()+" name = "+h.getNameCompound());
            postIdCompound.add(h.getIdCompound());
        }
        String param = "";
        for (int i = 0 ; i < postIdCompound.size();i++){
            param=param+"&id[]="+postIdCompound.get(i);
        }

        String url = getString(R.string.url)+"/jamu/api/user/secret?type=compound&optimization="+optimization+"&model="+idCategories+param;
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
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
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
                                Intent i = new Intent(getActivity(), resultPredictionCompound.class);
                                Bundle args = new Bundle();
                                args.putString("methodPredict",category);
                                args.putStringArrayList("compoundName", compoundName);
                                args.putString("classDisease",classDisease);
                                args.putString("descDisease",descDisease);
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
