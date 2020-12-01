package com.example.ilham.obatherbal.crudeJava;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

import org.json.JSONException;
import org.json.JSONObject;


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

         //deklarasi komponen
         Log.d(TAG,"idplant = "+idPlant);
         detailPic = (ImageView) findViewById(R.id.detailCrudePic);
         tabLayout = (TabLayout) findViewById(R.id.tablayoutdetailcrude);
         appBarLayout = (AppBarLayout) findViewById(R.id.appbarCrudeDetail);
         viewPager = (ViewPager) findViewById(R.id.viewPagerDetailCrude);

         //mengambil gambar tanaman
         getPicDetailPlant(idPlant);

         //deklarasi adapter untuk detail tanaman
         viewPagerDetailCrude adapter = new viewPagerDetailCrude(getSupportFragmentManager());
         tabDetailPlant detailPlant = new tabDetailPlant();
         tabCrudeDetailPlant detailCrude = new tabCrudeDetailPlant();
         tabCompoundDetailPlant detailCompound = new tabCompoundDetailPlant();

         //mengisi bundle menggunakan id plant
         Bundle bundle = new Bundle();
         bundle.putString("idplant",idPlant);

         //menyimpan bundle pada tab
         detailPlant.setArguments(bundle);
         detailCrude.setArguments(bundle);
         detailCompound.setArguments(bundle);

         adapter.addFragment(detailPlant,"Detail");
         adapter.addFragment(detailCrude,"Crude");
         adapter.addFragment(detailCompound,"Compound");
         viewPager.setAdapter(adapter);
         tabLayout.setupWithViewPager(viewPager);
    }

    //mengambil data tanaman
    private void getPicDetailPlant(String idPlant) {
        String url = getString(R.string.url)+"/jamu/api/plant/get/"+idPlant;
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
