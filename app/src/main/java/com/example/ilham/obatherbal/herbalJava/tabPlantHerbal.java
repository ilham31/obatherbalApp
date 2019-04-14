package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabPlantHerbal extends Fragment {
    View rootview;
    String idHerbal;
    crudeAdapter adapter;
    List<crudeModel> crudeModels;
    RecyclerView recyclerView;
    ProgressBar loading;
    List<String> idCrudeResponse;

    public tabPlantHerbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab_plant_herbal, container, false);
        crudeModels = new ArrayList<>();
        idCrudeResponse = new ArrayList<>();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview_plant_in_herbal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loading = (ProgressBar) rootview.findViewById(R.id.loadPlantDetailJamu);
        loading.setVisibility(View.VISIBLE);
        adapter = new crudeAdapter(recyclerView,getActivity(),crudeModels);
        recyclerView.setAdapter(adapter);
        Bundle bundle = this.getArguments();
        idHerbal = bundle.getString("idHerbal");
        Log.d("plantTab","idherbal = "+ idHerbal);
        getIdCrude(idHerbal);
        return rootview;
    }

    private void getIdCrude(String idHerbal) {
        Log.d("disease","Call data detail and disease" + idHerbal);
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
                                idCrudeResponse.add(idCrude);
//                                getDetailCrude(idCrude);
                            }
                            checkSameItem(idCrudeResponse);

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

    private void checkSameItem(List<String> idCrudeResponse) {
        HashSet<String> hashet = new HashSet<String>();
        hashet.addAll(idCrudeResponse);
        idCrudeResponse.clear();
        idCrudeResponse.addAll(hashet);
        for (int counter = 0; counter < idCrudeResponse.size(); counter++) {
            getDetailCrude(idCrudeResponse.get(counter));
        }
    }

    private void getDetailCrude(String idCrude) {
        Log.d("getCrude","masuk sini" +idCrude);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/crudedrug/detail/"+idCrude;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
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