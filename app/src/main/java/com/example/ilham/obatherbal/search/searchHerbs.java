package com.example.ilham.obatherbal.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.herbalModel;
import com.example.ilham.obatherbal.herbalJava.kampoModel;
import com.example.ilham.obatherbal.search.Adapter.adapterKampo;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchJamu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class searchHerbs extends Fragment {

    private static final String TAG = "searchherbs";
    private EditText searchHerbs;
    View rootView;
    List<herbalModel> herbalModels;
    List<kampoModel> kampoModels;
    adapterSearchJamu adapterJamu;
    adapterKampo adapterKampo;
    RecyclerView recyclerView;

    public searchHerbs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String idCategories = arguments.getString("categories");
        Log.d(TAG,"kategori yang dipilih :"+idCategories );
        rootView = inflater.inflate(R.layout.fragment_search_herbs, container, false);
        searchHerbs = (EditText) rootView.findViewById(R.id.search_herbs);
        switch (idCategories)
        {
            case "jamu":
                searchHerbs.setHint("Search Jamu");
                herbalModels = new ArrayList<>();
                getDataJamu();
                searhJamu();
                startRecyclerViewJamu();
                break;
            case "kampo":
                searchHerbs.setHint("Search Kampo");
                kampoModels = new ArrayList<>();
                getDataKampo();
                searchKampo();
                startRecyclerViewKampo();
                break;
            case "crude":
                searchHerbs.setHint("Search Crude");
                getDataCrude();
                searchCrude();
                break;
            case "compound":
                searchHerbs.setHint("Search Compound");
                getDataCompound();
                searchCompound();
                break;
        }
          return rootView;
    }




    private void searchCompound() {
    }

    private void getDataCompound() {
    }

    private void searchCrude() {
        
    }

    private void getDataCrude() {
    }

    private void searchKampo() {
        searchHerbs.addTextChangedListener(new TextWatcher() {
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
                filterKampo(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void filterKampo(String s) {
        ArrayList<kampoModel> filteredList = new ArrayList<>();
        for (kampoModel item : kampoModels){
            if (item.getNamaKampo().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterKampo.filterlist(filteredList);
    }


    private void getDataKampo() {
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, "Onresponsekampo" + jsonArray.toString());
                        Log.d(TAG, "lengthresponse" + jsonArray.length());
                        for (int i = 0; i < 20; i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.d(TAG,"jsonobject"+jsonObject);
                                kampoModels.add(
                                        new kampoModel(
                                                jsonObject.getString("title"),
                                                "Khasiat",
                                                jsonObject.getString("albumId"),
                                                jsonObject.getString("id"),
                                                jsonObject.getString("thumbnailUrl")
                                        )
                                );
                            } catch (JSONException e) {

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "Onerrorkampo" + volleyError.toString());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    private void startRecyclerViewKampo() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterKampo = new adapterKampo(getActivity(),kampoModels);
        recyclerView.setAdapter(adapterKampo);
        recyclerView.setVisibility(View.GONE);
    }

    private void getDataJamu() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, "OnresponseSearch" + jsonArray.toString());
                        Log.d(TAG, "lengthonresponse" + jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.d(TAG,"jsonobject"+jsonObject);
                                herbalModels.add(
                                        new herbalModel(
                                                jsonObject.getString("title"),
                                                "Khasiat",
                                                jsonObject.getString("userId"),
                                                jsonObject.getString("id"),
                                                jsonObject.getString("body")
                                        )
                                );
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

    private void searhJamu() {
        searchHerbs.addTextChangedListener(new TextWatcher() {
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
                filterJamu(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void startRecyclerViewJamu()
    {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterJamu = new adapterSearchJamu(getActivity(),herbalModels);
        recyclerView.setAdapter(adapterJamu);
        recyclerView.setVisibility(View.GONE);
    }

    private void filterJamu(String s) {
        ArrayList<herbalModel> filteredList = new ArrayList<>();
        for (herbalModel item : herbalModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterJamu.filterlist(filteredList);
    }

}
