package com.example.ilham.obatherbal.search;


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

import com.example.ilham.obatherbal.crudeJava.crudeModel;
import com.example.ilham.obatherbal.crudeJava.detailCrude;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.herbalModel;
import com.example.ilham.obatherbal.herbalJava.kampoModel;
import com.example.ilham.obatherbal.search.Adapter.adapterKampo;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchJamu;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchPlant;

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
    List<crudeModel> crudeModels;
    adapterSearchJamu adapterJamu;
    adapterKampo adapterKampo;
    adapterSearchPlant adapterSearchPlant;
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
                searchHerbs.setHint("Search Plant");
                crudeModels = new ArrayList<>();
                getDataCrude();
                searchCrude();
                startRecyclerViewPlant();
                break;
            case "compound":
                searchHerbs.setHint("Search Compound");
                getDataCompound();
                searchCompound();
                break;
        }
          return rootView;
    }

    private void startRecyclerViewPlant() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterSearchPlant = new adapterSearchPlant(getActivity(),crudeModels);
        recyclerView.setAdapter(adapterSearchPlant);
        recyclerView.setVisibility(View.GONE);
    }


    private void searchCompound() {
    }

    private void getDataCompound() {
    }

    private void searchCrude() {
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
                filterPlant(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void filterPlant(String s) {
        Log.d(TAG,"string filter" + s);
        ArrayList<crudeModel> filteredList = new ArrayList<>();
        for (crudeModel item : crudeModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterSearchPlant.filterlist(filteredList);
    }

    private void getDataCrude() {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/plant/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray plant = response.getJSONArray("data");
                            Log.d(TAG,"plant"+plant.toString());
                            for (int i = 0; i < plant.length() ; i++)
                            {
                                JSONObject jsonObject = plant.getJSONObject(i);
                                crudeModels.add(
                                        new crudeModel(
                                                jsonObject.getString("idplant"),
                                                jsonObject.getString("sname"),
                                                ""

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
                        Log.d(TAG, "Onerrorplant" + error.toString());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
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
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray herbsmeds = response.getJSONArray("data");
                            Log.d(TAG,"herbsmeds"+herbsmeds.toString());
                            for (int i = 0; i < herbsmeds.length() ; i++)
                            {
                                JSONObject jsonObject = herbsmeds.getJSONObject(i);
                                String check = jsonObject.getString("idherbsmed");
                                Character id = check.charAt(0);
                                Log.d(TAG,"huruf pertama"+id);
                                if (id == 'K')
                                {
                                    Log.d(TAG,"masuk if"+id);
                                    kampoModels.add(
                                            new kampoModel(
                                                    jsonObject.getString("name"),
                                                    "",
                                                    "",
                                                    jsonObject.getString("idherbsmed"),
                                                    ""
                                            )
                                    );
                                }

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
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

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
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray herbsmeds = response.getJSONArray("data");
                            Log.d(TAG,"herbsmeds"+herbsmeds.toString());
                            for (int i = 0; i < herbsmeds.length() ; i++)
                            {
                                JSONObject jsonObject = herbsmeds.getJSONObject(i);
                                String check = jsonObject.getString("idherbsmed");
                                Character id = check.charAt(0);
                                Log.d(TAG,"huruf pertama"+id);
                                if (id == 'J')
                                {
                                    Log.d(TAG,"masuk if"+id);
                                    herbalModels.add(
                                            new herbalModel(
                                                    jsonObject.getString("name"),
                                                    "",
                                                    "",
                                                    jsonObject.getString("idherbsmed"),
                                                    ""
                                            )
                                    );
                                }

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
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

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
