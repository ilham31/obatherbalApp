package com.example.ilham.obatherbal.databaseJava.tacit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tacit extends AppCompatActivity {
    RecyclerView mRecyclerView;
    tacitAdapter mAdapter;
    List<tacitModel>tacitModels;
    ProgressBar loadTacit;
    EditText searchTacit;
    TextView notfounddoc;
    private LinearLayoutManager mLayoutManager;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacit);
        tacitModels = new ArrayList<>();
        searchTacit = (EditText) findViewById(R.id.search_tacit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_tacit);
        loadTacit = (ProgressBar) findViewById(R.id.loadTacit);
        notfounddoc = (TextView) findViewById(R.id.notfoundDocumentTacit);
        notfounddoc.setVisibility(View.GONE);
        loadTacit.setVisibility(View.VISIBLE);
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        get10DataTacit();
        startRecyclerviewTacit();
        searchTacit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tacit.this,searchTacit.class);
                startActivity(intent);

            }
        });
    }

    private void get10DataTacit() {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/tacit";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadTacit.setVisibility(View.GONE);
                        Log.d("explicit", "Onresponse" + response.toString());
                        try {
                            if (Integer.valueOf(response.getString("lenght")) ==  0)
                            {
                                notfounddoc.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                JSONArray tacit = response.getJSONArray("data");
                                Log.d("explicit","explicit"+tacit.toString());
                                for (int i = 0; i < tacit.length() ; i++)
                                {
                                    JSONObject jsonObject = tacit.getJSONObject(i);
                                    tacitModels.add(
                                            new tacitModel(
                                                    jsonObject.getString("_id"),
                                                    jsonObject.getString("datePublish"),
                                                    jsonObject.getString("title"),
                                                    jsonObject.getString("content"),
                                                    jsonObject.getString("reference"),
                                                    jsonObject.getString("file"),
                                                    jsonObject.getString("createdby")
                                            )
                                    );
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("explicit", "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void startRecyclerviewTacit() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new tacitAdapter(mRecyclerView,this,tacitModels);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                tacitModels.add(null);
                mAdapter.notifyItemInserted(tacitModels.size()-1);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tacitModels.remove(tacitModels.size()-1);
                        mAdapter.notifyItemRemoved(tacitModels.size());

                        int page = (tacitModels.size()/10)+1;
                        loadMoreDataTacit(page);
                    }
                },2000);
            }
        });
    }

    private void loadMoreDataTacit(int page) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/tacit/"+page;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadTacit.setVisibility(View.GONE);
                        Log.d("explicit", "Onresponse" + response.toString());
                        try {
                            JSONArray tacit = response.getJSONArray("data");
                            Log.d("explicit","explicit"+tacit.toString());
                            for (int i = 0; i < tacit.length() ; i++)
                            {

                                JSONObject jsonObject = tacit.getJSONObject(i);
                                tacitModels.add(
                                        new tacitModel(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("datePublish"),
                                                jsonObject.getString("title"),
                                                jsonObject.getString("content"),
                                                jsonObject.getString("reference"),
                                                jsonObject.getString("file"),
                                                jsonObject.getString("createdby")
                                        )
                                );
                                mAdapter.notifyItemInserted(tacitModels.size());
                            }
                            mAdapter.setLoaded();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("explicit", "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
