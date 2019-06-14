package com.example.ilham.obatherbal.analysisJava.comparison.resultComparison;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class resultComparison extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<String> idCrudeResponse1,idCrudeResponse2;

    public resultComparison() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentt
        view = inflater.inflate(R.layout.fragment_confirm_comparison, container, false);
        final String idjamu1 = getArguments().getString("idjamu1");
        final String idjamu2 = getArguments().getString("idjamu2");
        Log.d("result","getdata idjamu 1 = "+idjamu1+", idjamu2 = "+idjamu2);

        idCrudeResponse1 = new ArrayList<>();
        idCrudeResponse2 = new ArrayList<>();

        tabLayout = (TabLayout) view.findViewById(R.id.tablayoutComparisonJamu);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerComparisonJamu);

        getDataJamuCrude1(idjamu1);
        getDataJamuCrude2(idjamu2);
        viewPagerComparisonJamu adapter = new viewPagerComparisonJamu(getChildFragmentManager());

        //inisialisasi 3 tab

        tabPlantComparison1 comparisonJamu1 = new tabPlantComparison1();
        tabPlantComparisonJamu2 comparisonJamu2 = new tabPlantComparisonJamu2();
        tabComparisonBoth comparisonBoth = new tabComparisonBoth();

        Bundle bundle = new Bundle();
        bundle.putString("idjamu1",idjamu1);
        bundle.putString("idjamu2",idjamu2);

        Bundle bundleBoth = new Bundle();
        bundleBoth.putStringArrayList("idCrude1", (ArrayList<String>) idCrudeResponse1);
        bundleBoth.putStringArrayList("idCrude2", (ArrayList<String>)idCrudeResponse2);

        comparisonJamu1.setArguments(bundle);
        comparisonJamu2.setArguments(bundle);
        comparisonBoth.setArguments(bundle);

        adapter.addFragment(comparisonJamu1,"Jamu 1");
        adapter.addFragment(comparisonJamu2,"Jamu 2");
        adapter.addFragment(comparisonBoth,"Both");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void getDataJamuCrude2(String idHerbal2) {
        Log.d("tab 1 comparison","id crude = " + idHerbal2);
        String url = getString(R.string.url)+"/jamu/api/herbsmed/detail/"+idHerbal2;
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
                                idCrudeResponse2.add(idCrude);
//                                getDetailCrude(idCrude);
                            }
                            checkSameItem2(idCrudeResponse2);
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
//cek crude untuk menghindari id yang sama
    private void checkSameItem2(List<String> idCrudeResponse2) {
        HashSet<String> hashet2 = new HashSet<String>();
        hashet2.addAll(idCrudeResponse2);
        idCrudeResponse2.clear();
        idCrudeResponse2.addAll(hashet2);
    }

    private void getDataJamuCrude1(String idHerbal1) {
        Log.d("tab 1 comparison","id crude = " + idHerbal1);
        String url = "getString(R.string.url)+/jamu/api/herbsmed/detail/"+idHerbal1;
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
                                idCrudeResponse1.add(idCrude);
//                                getDetailCrude(idCrude);
                            }
                            checkSameItem1(idCrudeResponse1);
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
    //cek crude untuk menghindari id yang sama
    private void checkSameItem1(List<String> idCrudeResponse1) {
        HashSet<String> hashet1 = new HashSet<String>();
        hashet1.addAll(idCrudeResponse1);
        idCrudeResponse1.clear();
        idCrudeResponse1.addAll(hashet1);
        }

}
