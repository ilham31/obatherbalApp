package com.example.ilham.obatherbal.crudeJava;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabCrudeDetailPlant extends Fragment {
    View rootview;
    List<detailCrudeModel> detailCrudeModels;
    List<String> idCrudeResponse;
    detailCrudeAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loading;
    String idplant;

    public tabCrudeDetailPlant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //deklarasi komponen
        rootview = inflater.inflate(R.layout.fragment_tab_crude_detail_plant, container, false);
        RequestQueue queue = MySingleton.getInstance(this.getActivity()).getRequestQueue();
        Bundle bundle = this.getArguments();
        idplant = bundle.getString("idplant");
        detailCrudeModels = new ArrayList<>();
        idCrudeResponse = new ArrayList<>();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview_detailCrude);
        loading = (ProgressBar) rootview.findViewById(R.id.loadCrudePlantRecyclerViewDetail);
        loading.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new detailCrudeAdapter(getActivity(),detailCrudeModels);
        recyclerView.setAdapter(adapter);
        getDataPlant(idplant);
        return rootview;
    }

    //mengambil data tanaman
    private void getDataPlant(String idplant) {
            String url = getString(R.string.url)+"/jamu/api/plant/get/"+idplant;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("crudePlant", "Onresponse" + response.toString());
                            try {
                                JSONObject plant = response.getJSONObject("data");
                                JSONArray refCrude = plant.getJSONArray("refCrude");
                                for (int i = 0; i < refCrude.length() ; i++)
                                {
                                    JSONObject jsonObject = refCrude.getJSONObject(i);
                                    String idCrude = jsonObject.getString("idcrude");
                                    idCrudeResponse.add(idCrude);
//                                getDetailCrude(idCrude);
                                }
                                //cek duplikasi id crude pada tanaman
                                checkSameItem(idCrudeResponse);
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

    private void checkSameItem(List<String> idCrudeResponse) {
        HashSet<String> hashet = new HashSet<String>();
        hashet.addAll(idCrudeResponse);
        idCrudeResponse.clear();
        idCrudeResponse.addAll(hashet);
        for (int counter = 0; counter < idCrudeResponse.size(); counter++) {

            //melihat detail crude
            getDetailCrude(idCrudeResponse.get(counter));
        }
    }

    private void getDetailCrude(String s) {
        Log.d("masuk crude recy","masuk sini" +s);
        String url = getString(R.string.url)+"/jamu/api/crudedrug/get/"+s;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("masuk crude recy", "Onresponsegetdetailcrude" + response.toString());

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

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);


    }


}
