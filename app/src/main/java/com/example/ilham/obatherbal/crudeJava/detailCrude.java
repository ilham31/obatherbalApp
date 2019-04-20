package com.example.ilham.obatherbal.crudeJava;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crude);
         RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
         String idPlant = getIntent().getStringExtra("idPlant");
         Log.d(TAG,"idplant = "+idPlant);
         detailPic = (ImageView) findViewById(R.id.detailCrudePic);
         tabLayout = (TabLayout) findViewById(R.id.tablayoutdetailcrude);
         appBarLayout = (AppBarLayout) findViewById(R.id.appbarCrudeDetail);
         viewPager = (ViewPager) findViewById(R.id.viewPagerDetailCrude);

         getPicDetailPlant(idPlant);

         viewPagerDetailCrude adapter = new viewPagerDetailCrude(getSupportFragmentManager());
         tabDetailPlant detailPlant = new tabDetailPlant();
         tabCrudeDetailPlant detailCrude = new tabCrudeDetailPlant();
         Bundle bundle = new Bundle();
         bundle.putString("idplant",idPlant);

         detailPlant.setArguments(bundle);
         detailCrude.setArguments(bundle);

         adapter.addFragment(detailPlant,"Detail");
         adapter.addFragment(detailCrude,"Crude");
         viewPager.setAdapter(adapter);
         tabLayout.setupWithViewPager(viewPager);
    }

    private void getPicDetailPlant(String idPlant) {
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
