package com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.crudeAdapter;
import com.example.ilham.obatherbal.crudeJava.crudeModel;
import com.example.ilham.obatherbal.crudeJava.detailCrudeAdapter;
import com.example.ilham.obatherbal.crudeJava.detailCrudeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabPlantComparison1 extends Fragment {
    View view;
    String idHerbal;
    List<detailCrudeModel> detailCrudeModels;
    detailCrudeAdapter adapter;
    RecyclerView recyclerView;
    TextView namaJamu,khasiatJamu;
    ProgressBar loading;
    List<String> idCrudeResponse;

    public tabPlantComparison1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_plant_comparison_jamu1, container, false);
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        detailCrudeModels = new ArrayList<>();
        idCrudeResponse = new ArrayList<>();
        Bundle bundle = this.getArguments();
        idHerbal = bundle.getString("idjamu1");
        Log.d("plantTab","idplant = "+ idHerbal);
        Log.d("tab 1 comparison","tab called = " + idHerbal);
        namaJamu = (TextView) view.findViewById(R.id.namaJamuComparison1);
        khasiatJamu = (TextView) view.findViewById(R.id.khasiatJamuComparison1);
        loading = (ProgressBar) view.findViewById(R.id.loadtabComparisonJamu1);
        loading.setVisibility(View.VISIBLE);
        getIdCrude(idHerbal);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_plant_comparison_jamu1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new detailCrudeAdapter(getActivity(),detailCrudeModels);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getIdCrude(String idHerbal) {
        Log.d("tab 1 comparison","id crude = " + idHerbal);
        String url = getString(R.string.url)+"/jamu/api/herbsmed/get/"+idHerbal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("data");
                            JSONArray refCrude = herbsmed.getJSONArray("refCrude");
                            namaJamu.setText(herbsmed.getString("name"));
                            khasiatJamu.setText(herbsmed.getString("efficacy"));
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
        Log.d("tab 1 comparison","id crude masuk sini" +idCrude);
        String url = getString(R.string.url)+"/jamu/api/crudedrug/get/"+idCrude;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("getCrude", "Onresponsegetdetailcrude" + response.toString());
                        try {
                            JSONObject crudeDrug = response.getJSONObject("data");
                            detailCrudeModels.add(
                                    new detailCrudeModel(
                                            crudeDrug.getString("sname"),
                                            crudeDrug.getString("name_en"),
                                            crudeDrug.getString("name_loc1"),
                                            crudeDrug.getString("gname"),
                                            crudeDrug.getString("position"),
                                            crudeDrug.getString("effect"),
                                            crudeDrug.getString("ref")
                                    )
                            );
                            adapter.notifyDataSetChanged();
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
