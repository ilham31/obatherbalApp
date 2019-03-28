package com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.crudeAdapter;
import com.example.ilham.obatherbal.crudeJava.crudeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabPlantComparisonJamu1 extends Fragment {
    View view;
    String idHerbal;
    showsPlantAdapter adapter;
    List<crudeModel> crudeModels;
    RecyclerView recyclerView;

    public tabPlantComparisonJamu1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_plant_comparison_jamu1, container, false);
        crudeModels = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_plant_comparison_jamu1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new showsPlantAdapter(crudeModels,getActivity());
        recyclerView.setAdapter(adapter);
        Bundle bundle = this.getArguments();
        idHerbal = bundle.getString("idjamu");
        Log.d("plantTab","idherbal = "+ idHerbal);
        getArguments().remove("idjamu");
        Log.d("tab 1 comparison","tab called = " + idHerbal);
        getIdCrude(idHerbal);
        return view;
    }

    private void getIdCrude(String idHerbal) {
        Log.d("tab 1 comparison","id crude = " + idHerbal);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/detail/"+idHerbal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("herbsmed");
                            JSONArray refCrude = herbsmed.getJSONArray("refCrude");
                            for (int i = 0; i < refCrude.length() ; i++)
                            {
                                JSONObject jsonObject = refCrude.getJSONObject(i);
                                String idCrude = jsonObject.getString("idcrude");
                                getDetailCrude(idCrude);
                            }


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

    private void getDetailCrude(String idCrude) {
        Log.d("tab 1 comparison","id crude masuk sini" +idCrude);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/crudedrug/detail/"+idCrude;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getCrude", "Onresponsegetdetailcrude" + response.toString());
                        try {
                            JSONObject crudeDrug = response.getJSONObject("crudedrug");
                            JSONArray refPlant = crudeDrug.getJSONArray("refPlant");
                            for(int i = 0 ; i <refPlant.length();i++ )
                            {
                                JSONObject jsonObject = refPlant.getJSONObject(i);
                                crudeModels.add(
                                        new crudeModel(
                                                jsonObject.getString("idplant"),
                                                jsonObject.getString("sname"),
                                                jsonObject.getString("refimg")
                                        )
                                );
                                Log.d("tabplantHerbal","datanih : "+jsonObject.getString("idplant")+jsonObject.getString("sname"));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("getCrude", "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }


}
