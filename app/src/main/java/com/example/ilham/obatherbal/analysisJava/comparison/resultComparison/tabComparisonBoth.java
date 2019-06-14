package com.example.ilham.obatherbal.analysisJava.comparison.resultComparison;


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
public class tabComparisonBoth extends Fragment {
    View view;
    String idHerbal1,idHerbal2;
    List<detailCrudeModel> detailCrudeModels;
    detailCrudeAdapter adapter;
    RecyclerView recyclerView;
    TextView nothingBoth;
    ProgressBar loading;
    List<String> idCrudeResponse1,idCrudeResponse2;

    public tabComparisonBoth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_comparison_both, container, false);
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        detailCrudeModels = new ArrayList<>();
        idCrudeResponse1 = new ArrayList<>();
        idCrudeResponse2 = new ArrayList<>();

        Bundle bundle = this.getArguments();
        idHerbal1 = bundle.getString("idjamu1");
        idHerbal2 = bundle.getString("idjamu2");
        Log.d("intent","masuk sini = " +idHerbal1 +idHerbal2);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_plant_comparison_jamu_both);
        nothingBoth = (TextView) view.findViewById(R.id.nothingBoth);
        loading = (ProgressBar) view.findViewById(R.id.loadtabComparisonJamuBoth);

        nothingBoth.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        getDataJamuCrude1(idHerbal1);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new detailCrudeAdapter(getActivity(),detailCrudeModels);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void checkSimilarity(List<String> idCrudeResponse1, List<String> idCrudeResponse2) {
        Log.d("tabBoth","masuk sini");
        List<String> common = new ArrayList<String>(idCrudeResponse1);
        common.retainAll(idCrudeResponse2);
        Log.d("tabBoth","size common = "+common.size());
        if (common.size() == 0)
        {
            nothingBoth.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
        else
        {
            for (int counter = 0; counter < common.size(); counter++) {
                getDetailCrude(common.get(counter));
            }
        }
    }

    private void getDetailCrude(String s) {
        Log.d("tab 1 comparison","id crude masuk sini" +s);
        String url = getString(R.string.url)+"/jamu/api/crudedrug/get/"+s;
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

    private void getDataJamuCrude2(String idHerbal2) {
        Log.d("getdatajamu 2","masuk sini = " + idHerbal2);
        String url = getString(R.string.url)+"/jamu/api/herbsmed/get/"+idHerbal2;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("data");
                            JSONArray refCrude = herbsmed.getJSONArray("refCrude");
                            for (int i = 0; i < refCrude.length() ; i++)
                            {
                                JSONObject jsonObject = refCrude.getJSONObject(i);
                                String idCrude = jsonObject.getString("idcrude");
                                idCrudeResponse2.add(idCrude);
//                                getDetailCrude(idCrude);
                            }
                            checkSameItem2(idCrudeResponse2);
                            checkSimilarity(idCrudeResponse1,idCrudeResponse2);
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

    private void checkSameItem2(List<String> idCrudeResponse2) {
        HashSet<String> hashet2 = new HashSet<String>();
        hashet2.addAll(idCrudeResponse2);
        idCrudeResponse2.clear();
        idCrudeResponse2.addAll(hashet2);
    }

    private void getDataJamuCrude1(String idHerbal1) {
        Log.d("getDatajamu 1","masuk sini = " + idHerbal1);
        String url = getString(R.string.url)+"/jamu/api/herbsmed/get/"+idHerbal1;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("data");
                            JSONArray refCrude = herbsmed.getJSONArray("refCrude");
                             for (int i = 0; i < refCrude.length() ; i++)
                            {
                                JSONObject jsonObject = refCrude.getJSONObject(i);
                                String idCrude = jsonObject.getString("idcrude");
                                idCrudeResponse1.add(idCrude);
//                                getDetailCrude(idCrude);
                            }
                            checkSameItem1(idCrudeResponse1);
                            getDataJamuCrude2(idHerbal2);
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

    private void checkSameItem1(List<String> idCrudeResponse1) {
        HashSet<String> hashet1 = new HashSet<String>();
        hashet1.addAll(idCrudeResponse1);
        idCrudeResponse1.clear();
        idCrudeResponse1.addAll(hashet1);

    }

}
