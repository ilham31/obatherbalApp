package com.example.ilham.obatherbal.compoundJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.crudeModel;
import com.example.ilham.obatherbal.search.searchHerbs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class compound extends Fragment {
    RecyclerView recyclerView;
    compoundAdapter adapter;
    List<compoundModel> compoundModels;
    EditText search;
    ProgressBar loadCompound;
    private static final String TAG = "compound";

    public compound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_compound, container, false);
        compoundModels = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        getData();
        search = (EditText) rootView.findViewById(R.id.search_compound);
        loadCompound = (ProgressBar) rootView.findViewById(R.id.loadCompound);
        loadCompound.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCompound();
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_compound);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new compoundAdapter(getActivity(),compoundModels);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void searchCompound() {
        Bundle arguments = new Bundle();
        arguments.putString( "categories" , "compound");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        searchHerbs searchHerbs = new searchHerbs();
        searchHerbs.setArguments(arguments);
        ft.replace(R.id.main_frame, searchHerbs);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void getData() {
        String url = "http://www.mocky.io/v2/5cce4f3f300000d30d52c2d4";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadCompound.setVisibility(View.GONE);
                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length() ; i++)
                            {
                                JSONObject jsonObject = data.getJSONObject(i);
                                compoundModels.add(
                                        new compoundModel(
                                                "0",
                                                jsonObject.getString("Compounds"),
                                                jsonObject.getString("Part of Plant"),
                                                jsonObject.getString("Plant Species"),
                                                jsonObject.getString("Molecular Formula"),
                                                jsonObject.getString("References")
                                               )
                                );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "Onerror" + error.toString());
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
