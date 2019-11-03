package com.example.ilham.obatherbal.crudeJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.compoundJava.compoundAdapter;
import com.example.ilham.obatherbal.compoundJava.compoundModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabCompoundDetailPlant extends Fragment {
    View rootview;
    compoundAdapter adapter;
    List<compoundModel> compoundModels;
    RecyclerView recyclerView;
    ProgressBar loading;
    String idplant;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab_compound_detail_plant, container, false);
        RequestQueue queue = MySingleton.getInstance(this.getActivity()).getRequestQueue();
        Bundle bundle = this.getArguments();
        idplant = bundle.getString("idplant");
        compoundModels =new ArrayList<>();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview_detailCompoundinCrude);
        loading = (ProgressBar) rootview.findViewById(R.id.loadCompoundPlantRecyclerViewDetail);
        loading.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new compoundAdapter(recyclerView,getActivity(),compoundModels);
        recyclerView.setAdapter(adapter);
        getDataPlant(idplant);
        return rootview;
    }

    //mengambil data compound
    private void getDataPlant(String idplant) {
        String url = getString(R.string.url)+"/jamu/api/plant/get/"+idplant;
        loading.setVisibility(View.GONE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("crudePlant", "Onresponse" + response.toString());
                        try {
                            JSONObject plant = response.getJSONObject("data");
                            JSONArray refCompound = plant.getJSONArray("refCompound");
                            Log.d("compound detail plant", "length compound" + refCompound.length());
                            for (int i = 0; i < refCompound.length() ; i++)
                            {
                                JSONObject jsonObject = refCompound.getJSONObject(i);
                                compoundModels.add(new compoundModel(
                                        jsonObject.getString("_id"),
                                        jsonObject.getString("cname")
                                ));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Handle error
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
                        Toast.makeText(getActivity(), message,
                                Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

}
