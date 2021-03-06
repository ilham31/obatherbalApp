package com.example.ilham.obatherbal.knowledge.explicit;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class explicit extends AppCompatActivity {
    RecyclerView mRecyclerView;
    explicitAdapter mAdapter;
    List<explicitModel> explicitModels;
    ProgressBar loadExplicit;
    EditText searchExplicit;
    TextView notfounddoc;
    private LinearLayoutManager mLayoutManager;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        //deklarasi komponen
        explicitModels = new ArrayList<>();
        searchExplicit = (EditText) findViewById(R.id.search_explicit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_explicit);
        loadExplicit = (ProgressBar) findViewById(R.id.loadExplicit);
        notfounddoc = (TextView) findViewById(R.id.notfoundDocumentExplicit);
        notfounddoc.setVisibility(View.GONE);
        loadExplicit.setVisibility(View.VISIBLE);
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        get10DataExplicit();
        startRecyclerviewExplicit();
        searchExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(explicit.this,searchExplicit.class);
                startActivity(intent);

            }
        });
    }


    //deklarasi recyclerview
    private void startRecyclerviewExplicit() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new explicitAdapter(mRecyclerView,this,explicitModels);
        mRecyclerView.setAdapter(mAdapter);

        //loadmore recyclerview
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                explicitModels.add(null);
                mAdapter.notifyItemInserted(explicitModels.size()-1);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        explicitModels.remove(explicitModels.size()-1);
                        mAdapter.notifyItemRemoved(explicitModels.size());
                        
                        int page = (explicitModels.size()/10)+1;
                        loadMoreDataExplicit(page);
                    }
                },2000);
            }
        });
    }
    //mengambil data selanjutnya di halaman berikutnya
    private void loadMoreDataExplicit(int page) {
        String url = getString(R.string.url)+"/jamu/api/explicit/"+page;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadExplicit.setVisibility(View.GONE);
                        Log.d("explicit", "Onresponse" + response.toString());
                        try {
                            JSONArray explicit = response.getJSONArray("data");
                            Log.d("explicit","explicit"+explicit.toString());
                            for (int i = 0; i < explicit.length() ; i++)
                            {

                                JSONObject jsonObject = explicit.getJSONObject(i);
                                explicitModels.add(
                                        new explicitModel(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("firstname"),
                                                jsonObject.getString("lastname"),
                                                jsonObject.getString("title"),
                                                jsonObject.getString("datePublish"),
                                                jsonObject.getString("citation"),
                                                jsonObject.getString("language"),
                                                jsonObject.getString("abstract"),
                                                jsonObject.getString("description"),
                                                jsonObject.getString("file")
                                        )
                                );
                                mAdapter.notifyItemInserted(explicitModels.size());
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
    //mendapatkan 10 data awal
    private void get10DataExplicit() {
        String url = getString(R.string.url)+"/jamu/api/explicit";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadExplicit.setVisibility(View.GONE);
                        Log.d("explicit", "Onresponse" + response.toString());
                        try {
                            if (Integer.valueOf(response.getString("lenght")) ==  0)
                            {
                                notfounddoc.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                JSONArray explicit = response.getJSONArray("data");
                                Log.d("explicit","explicit"+explicit.toString());
                                for (int i = 0; i < explicit.length() ; i++)
                                {
                                    JSONObject jsonObject = explicit.getJSONObject(i);
                                    explicitModels.add(
                                            new explicitModel(
                                                    jsonObject.getString("_id"),
                                                    jsonObject.getString("firstName"),
                                                    jsonObject.getString("lastName"),
                                                    jsonObject.getString("title"),
                                                    jsonObject.getString("datePublish"),
                                                    jsonObject.getString("citation"),
                                                    jsonObject.getString("language"),
                                                    jsonObject.getString("abstract"),
                                                    jsonObject.getString("description"),
                                                    jsonObject.getString("file")
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
}
