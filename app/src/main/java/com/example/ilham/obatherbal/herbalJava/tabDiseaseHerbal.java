package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.detailCrude;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabDiseaseHerbal extends Fragment {
    String idHerbal;
    View rootview;
    TextView nameDetailHerbal,efficacyDetailHerbal,refDetailHerbal,descriptionDclassHerbal,diseaseDclassHerbal,refDiseaseDclassHerbal;
    ProgressBar loading;

    public tabDiseaseHerbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab_disease_herbal, container, false);
        Bundle bundle = this.getArguments();
        idHerbal = bundle.getString("idHerbal");
        Log.d("diseaseTab","idherbal = "+ idHerbal);
        RequestQueue queue = MySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        nameDetailHerbal = (TextView) rootview.findViewById(R.id.detailNameHerbal);
        efficacyDetailHerbal = (TextView) rootview.findViewById(R.id.detailEfficacy);
        refDetailHerbal = (TextView) rootview.findViewById(R.id.detailRefHerbal);
        descriptionDclassHerbal = (TextView) rootview.findViewById(R.id.descriptionDclassHerbal);
        diseaseDclassHerbal = (TextView) rootview.findViewById(R.id.diseaseDclassHerbal);
        refDiseaseDclassHerbal = (TextView) rootview.findViewById(R.id.refDiseaseDclassHerbal);
        loading = (ProgressBar) rootview.findViewById(R.id.loadDiseaseDetailJamu);
        loading.setVisibility(View.VISIBLE);
        getDetailHerbal(idHerbal);
        return rootview;
    }

    private void getDetailHerbal(String idHerbal) {
        Log.d("disease","Call data detail and disease" + idHerbal);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/detail/"+idHerbal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("herbsmed");
                            nameDetailHerbal.setText("Name : " + herbsmed.getString("name"));

                            efficacyDetailHerbal.setText("Efficacy : " + herbsmed.getString("efficacy"));
                            String idDclass = herbsmed.getString("idclass");
                            getDetailDisease(idDclass);
                            refDetailHerbal.setText( "Reference Herbal : "+ herbsmed.getString("ref"));
                            Linkify.addLinks(refDetailHerbal,Linkify.WEB_URLS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("diseaseTab", "Onerrordisease" + error.toString());
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

    private void getDetailDisease(String idDclass) {
        Log.d("disease","Call data disease and disease" + idDclass);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/dclass/detailDclass/"+idDclass;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponse" + response.toString());
                        try {
                            JSONObject Dclass = response.getJSONObject("dclass");
                            descriptionDclassHerbal.setText("Description : " + Dclass.getString("description"));
                            diseaseDclassHerbal.setText("Disease : " + Dclass.getString("diseases"));
                            refDiseaseDclassHerbal.setText("Reference disease : "+ Dclass.getString("ref"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("diseaseTab", "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

}
