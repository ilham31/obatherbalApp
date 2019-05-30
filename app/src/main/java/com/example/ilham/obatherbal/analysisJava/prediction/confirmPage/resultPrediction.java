package com.example.ilham.obatherbal.analysisJava.prediction.confirmPage;


import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class resultPrediction extends Fragment {
    private View view;
    private TextView method,infoLoad;
    private RecyclerView recyclerView;
    private adapterConfirm adapterConfirm;
    ArrayList<herbsModel> idPlant;
    ArrayList<String>postIdPlant;
    ArrayList<String>id;
    ArrayList<String>plantName;
    Button submitPredictPlant;
    String Categories;
    ProgressBar load;

    public resultPrediction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_result_prediction, container, false);
        idPlant= (ArrayList<herbsModel>)getArguments().getSerializable("idPlant");
        plantName = new ArrayList<String>();

        final String idCategories= getArguments().getString("idCategories");
        Categories= getArguments().getString("categories");
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
                    for (herbsModel h : idPlant)
                    {
                        Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
                        plantName.add(h.getNameHerbs());
                    }
                    postData();
//                    Intent i = new Intent(getActivity(),resultPredictionPlant.class);
//                    Bundle args = new Bundle();
//                    args.putString("methodPredict",Categories);
//                    args.putStringArrayList("plantName", plantName);
//                    i.putExtra("bundle",args);
//                    startActivity(i);
//                    getActivity().finish();
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

    private void postData() {
        postIdPlant = new ArrayList<>();
        id = new ArrayList<>();
        for (herbsModel h : idPlant)
        {
            Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
            postIdPlant.add(h.getIdPlant());
        }
        Log.d("size post","data size post ="+postIdPlant.size());
        for (int i = 0; i<postIdPlant.size(); i++)
        {
            Log.d("size post","data id ="+postIdPlant.get(i).toString());
        }
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/user/signin";

        JSONArray jArry=new JSONArray();
        for (int i=0;i<postIdPlant.size();i++)
        {
            JSONObject jObjd=new JSONObject();
            try {
                jObjd.put("idplant", postIdPlant.get(i));
                jArry.put(jObjd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("data",jArry);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("confirm","jsonobject = "+jsonObject.toString());
        JsonObjectRequest predictRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(predictRequest);
    }



}
