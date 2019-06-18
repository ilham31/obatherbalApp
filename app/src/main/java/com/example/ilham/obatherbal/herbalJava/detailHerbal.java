package com.example.ilham.obatherbal.herbalJava;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONException;
import org.json.JSONObject;


public class detailHerbal extends AppCompatActivity {
    ImageView gambarDetailHerbal;
    TextView reference;
    String urlRef = "www.google.com";
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herbal);
        String idHerbal = getIntent().getStringExtra("idHerbal");

        //deklarasi komponen
        tabLayout = (TabLayout) findViewById(R.id.tablayoutdetailJamu);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbaridDetailJamu);
        viewPager = (ViewPager) findViewById(R.id.viewPagerDetailJamu);
        gambarDetailHerbal = (ImageView) findViewById(R.id.detailHerbalPic);
        getPicHerbal(idHerbal);
        viewPagerDetailHerbalAdapter adapter = new viewPagerDetailHerbalAdapter(getSupportFragmentManager());

        //adding Fragment

        tabDiseaseHerbal diseaseHerbal = new tabDiseaseHerbal();
        tabCrudeHerbal crudeHerbal = new tabCrudeHerbal();
        tabPlantHerbal plantHerbal = new tabPlantHerbal();
        Bundle bundle = new Bundle();
        bundle.putString("idHerbal",idHerbal);

        //menyimpan bundle berisi idherbal pada tiap fragment
        diseaseHerbal.setArguments(bundle);
        crudeHerbal.setArguments(bundle);
        plantHerbal.setArguments(bundle);

        //memasukkan fragment ke adapter
        adapter.addFragment(diseaseHerbal,"Detail");
        adapter.addFragment(plantHerbal,"Plant");
        adapter.addFragment(crudeHerbal,"Crude");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //mengambil gambar jamu
    private void getPicHerbal(String idHerbal) {
        String url = getString(R.string.url)+"/jamu/api/herbsmed/get/"+idHerbal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("data");
                            Glide.with(detailHerbal.this)
                                    .load(getString(R.string.url)+"/jamu/api/herbsmed/image/"+herbsmed.getString("img"))
                                    .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .into(gambarDetailHerbal);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
//                        Toast.makeText(getActivity(), message,
//                                Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


}
