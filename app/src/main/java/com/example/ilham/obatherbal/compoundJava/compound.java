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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
//        ft.addToBackStack(null);
        ft.commit();
    }


    private void getData() {
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        loadCompound.setVisibility(View.GONE);
                        Log.d(TAG, "Onresponsecompound" + jsonArray.toString());
                        Log.d(TAG, "lengthonresponse" + jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.d(TAG,"jsonobject"+jsonObject);
                                compoundModels.add(
                                        new compoundModel(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("title")

                                        )
                                );
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "Onerror" + volleyError.toString());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

}
