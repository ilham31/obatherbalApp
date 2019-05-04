package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Address> addresses;
    Bundle extras;
    RecyclerView recyclerView;
    List<diseaseModel>diseaseModels;
    diseaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        diseaseModels = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        getDataApi();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_disease);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText searchDisease = (EditText) findViewById(R.id.search_disease);
        adapter = new diseaseAdapter(this,diseaseModels);
        recyclerView.setAdapter(adapter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        extras = getIntent().getExtras();
        mapFragment.getMapAsync(this);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);

        searchDisease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(recyclerView.getVisibility()!= View.VISIBLE)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                filterDisease(s.toString());

            }
        });

    }

    private void filterDisease(String s) {
        Log.d("disease","string filter" + s);
        ArrayList<diseaseModel> filteredList = new ArrayList<>();
        for (diseaseModel item : diseaseModels){
            if (item.getDiseaseName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);

    }

    private void getDataApi() {
        String url = "https://my-json-server.typicode.com/ilham31/jsonobatherbal/db";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ethnic", "OnresponseHerbal" + response.toString());
                        try {
                            JSONArray disease = response.getJSONArray("disease");
                            for(int i =0;i<disease.length();i++) {
                                JSONObject jsonObject = disease.getJSONObject(i);
                                diseaseModels.add(
                                        new diseaseModel(
                                                "0",
                                                jsonObject.getString("Penyakit")

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
//        JsonArrayRequest request = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray jsonArray) {
////                        loadCrude.setVisibility(View.GONE);
//                        Log.d("maps", "Onresponsecrude" + jsonArray.toString());
//                        Log.d("maps", "lengthonresponse" + jsonArray.length());
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            try {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
////                                Log.d(TAG,"jsonobject"+jsonObject);
//                                diseaseModels.add(
//                                        new diseaseModel(
//                                                jsonObject.getString("id"),
//                                                jsonObject.getString("title")
//
//                                        )
//                                );
//                                adapter.notifyDataSetChanged();
//                            } catch (JSONException e) {
//
//                            }
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Log.d("maps", "Onerror" + volleyError.toString());
//                    }
//                });
//
//        MySingleton.getInstance(this).addToRequestQueue(request);





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String address;
        address = extras.getString("daerah");
        // Add a marker in Sydney and move the camera

        Geocoder geocoder = new Geocoder(this);
        MarkerOptions userMarkerOptions = new MarkerOptions();
        try {
            addresses = geocoder.getFromLocationName(address, 2);
            if (addresses.size()>0)
            {
                Double lat = (double) (addresses.get(0).getLatitude());
                Double lon = (double) (addresses.get(0).getLongitude());
                final LatLng user = new LatLng(lat, lon);
                /*used marker for show the location */
                mMap.addMarker(new MarkerOptions().position(user));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user,6));
//                try {
//                    GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.indonesia_province, getApplicationContext());
//
//                    GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
//                    style.setFillColor(Color.MAGENTA);
//                    style.setStrokeColor(Color.MAGENTA);
//                    style.setStrokeWidth(1F);
//
//                    layer.addLayerToMap();
//
//                } catch (IOException ex) {
//                    Log.e("IOException", ex.getLocalizedMessage());
//                } catch (JSONException ex) {
//                    Log.e("JSONException", ex.getLocalizedMessage());
//                }
            }
            else
            {
                Toast.makeText(this, "Cant find city", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
