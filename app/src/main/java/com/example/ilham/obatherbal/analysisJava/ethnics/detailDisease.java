package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class detailDisease extends AppCompatActivity {
    List<detailDiseaseModel> detailDiseaseModels;
    detailDiseaseAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loading;
    String daerah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_disease);
        detailDiseaseModels = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this).getRequestQueue();
        daerah = getIntent().getStringExtra("daerahPenyakit");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_detailDiseaseEthnic);
        loading = (ProgressBar) findViewById(R.id.loadingtanamanpenyakit);
        loading.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new detailDiseaseAdapter(this,detailDiseaseModels);
        recyclerView.setAdapter(adapter);
        getDataDetailDisease();
    }

    private void getDataDetailDisease() {
        String url = "https://my-json-server.typicode.com/ilham31/jsonobatherbal/db";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("tes tes", "OnresponseHerbal" + response.toString());
                        try {
                            JSONArray disease = response.getJSONArray("disease");
                            for(int i =0;i<disease.length();i++) {
                                JSONObject jsonObject = disease.getJSONObject(i);
                                if (jsonObject.getString("Penyakit").toLowerCase().equals(daerah.toLowerCase())){
                                    JSONArray tanaman = jsonObject.getJSONArray("Tanaman");
                                    Log.d("ethnic detail disease", "size tanaman =" + tanaman.length());

                                        JSONObject detail = tanaman.getJSONObject(1);
                                        detailDiseaseModels.add(
                                                new detailDiseaseModel(
                                                        detail.getString("Tanaman"),
                                                        detail.getString("spesies"),
                                                        detail.getString("Bagian")

                                                )
                                        );


                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("ethnic", "Onerror" + error.toString());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
