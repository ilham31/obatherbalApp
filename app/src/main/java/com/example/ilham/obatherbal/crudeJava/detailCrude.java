package com.example.ilham.obatherbal.crudeJava;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class detailCrude extends AppCompatActivity  {
    private static final String TAG = "detailCrude";
    ImageView detailPic;
    List<detailCrudeModel> detailCrudeModels;
    detailCrudeAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loading;
    List<String> idCrudeResponse;
    TextView describe;
    String plantName;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crude);
        detailCrudeModels = new ArrayList<>();
        idCrudeResponse = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_detailCrude);
        loading = (ProgressBar) findViewById(R.id.loadDetailPlant);
        loading.setVisibility(View.VISIBLE);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         adapter = new detailCrudeAdapter(this,detailCrudeModels);
         recyclerView.setAdapter(adapter);
        detailPic = (ImageView) findViewById(R.id.detailCrudePic);
        describe = (TextView) findViewById(R.id.descriptivePlant);

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String idPlant = getIntent().getStringExtra("idPlant");
        Log.d(TAG,"idplant = "+idPlant);
        getIntent().removeExtra("idPlant");
        getDataPlant(idPlant);


    }


    private void getDataPlant(String idPlant) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/plant/get/"+idPlant;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONObject plant = response.getJSONObject("data");
                            Glide.with(detailCrude.this)
                                    .load(plant.getString("refimg"))
                                    .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .into(detailPic);
                            plantName = plant.getString("sname");
                            getDescribeWiki(plantName);
                            JSONArray refCrude = plant.getJSONArray("refCrude");
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
                        Log.d(TAG, "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private void getDescribeWiki(String plantName) {
       String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="+plantName;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Onresponsegetdetailcrude" + response.toString());
                        try {
                            JSONObject query = response.getJSONObject("query");
                            JSONObject pages = query.getJSONObject("pages");
                            Iterator keys = pages.keys();
                            String key = (String)keys.next();
                            JSONObject value = pages.getJSONObject(key);
                            String extract = value.getString("extract");
                            describe.setText(extract);
                            Log.d("detail","isi respon"+extract);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
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
         Log.d(TAG,"masuk sini" +idCrude);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/crudedrug/get/"+idCrude;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Onresponsegetdetailcrude" + response.toString());
                        loading.setVisibility(View.GONE);
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
                        Log.d(TAG, "Onerror" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}
