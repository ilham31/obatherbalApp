package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchEthnic extends AppCompatActivity {
    EditText searchEthnic;
    ProgressBar loading;
    RecyclerView recyclerView;
    List<ethnicModel> ethnicModelList;
    adapterEthnic adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ethnic);
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        ethnicModelList = new ArrayList<>();
        searchEthnic = (EditText) findViewById(R.id.search_ethnic);
        loading = (ProgressBar) findViewById(R.id.loadEthnic);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_ethnic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new adapterEthnic(this,ethnicModelList);
        recyclerView.setAdapter(adapter);
        loading.setVisibility(View.VISIBLE);
        getDataEthnic();
        searchEthnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                filterEthnic(s.toString());

            }
        });
    }

    private void filterEthnic(String s) {
        ArrayList<ethnicModel> filteredList = new ArrayList<>();
        for (ethnicModel item : ethnicModelList){
            if (item.getEthnicName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }

    private void getDataEthnic() {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/ethnic/index";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("ethnic", "OnresponseHerbal" + response.toString());
                        try {

                            JSONArray ethnic = response.getJSONArray("ethnic");
                            Log.d("ethnic","ethnic"+ethnic.toString());
                            for (int i = 0; i < ethnic.length() ; i++)
                            {
                                JSONObject jsonObject = ethnic.getJSONObject(i);

                                ethnicModelList.add(
                                        new ethnicModel(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("name"),
                                                jsonObject.getString("province")
                                        )
                                );
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
                        Log.d("ethnic", "Onerror" + error.toString());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}