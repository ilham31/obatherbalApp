package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.categoriesHerbal;
import com.example.ilham.obatherbal.search.searchHerbs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class kampo extends Fragment {

    kampoAdapter kampoAdapter;
    RecyclerView kampoRecycleriew;
    List<kampoModel> kampoModels;
    ProgressBar loadKampo;
    View rootView;
    EditText searchKampo;
    private LinearLayoutManager kampoLayoutManager;
    private Handler handler;

    private static final String TAG = "kampo";
    public kampo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kampo, container, false);
        kampoModels = new ArrayList<>();
//        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        searchKampo = (EditText) rootView.findViewById(R.id.search_kampo);
        kampoRecycleriew = (RecyclerView) rootView.findViewById(R.id.recyclerview_kampo);
        loadKampo = (ProgressBar) rootView.findViewById(R.id.loadKampo);
        loadKampo.setVisibility(View.VISIBLE);
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        sortData();
        return rootView;
    }

    private void sortData() {
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.filter_kampo);
// Create an ArrayAdapter using the string array and a default spinner layout
        List<categoriesHerbal> itemList = new ArrayList<categoriesHerbal>();
        itemList.add(
                new categoriesHerbal(
                        "kampo",
                        "Kampo"
                )
        );
        itemList.add(
                new categoriesHerbal(
                        "Jamu",
                        "Jamu"
                )
        );
        ArrayAdapter<categoriesHerbal> spinnerAdapter = new ArrayAdapter<categoriesHerbal>(getActivity(), android.R.layout.simple_spinner_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears

// Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    categoriesHerbal selectedValue = (categoriesHerbal) parent.getItemAtPosition(position);
                    String categories = (String) selectedValue.getCategories();
                    final String idCategories = (String) selectedValue.getIdCategories();
                    StartRecyclerViewKampo(rootView);
                    get10DataKampo();
                    searchKampo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchHerb(idCategories);
                        }
                    });
                }
                else {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    herbal herbalFragment = new herbal();

                    ft.replace(R.id.main_frame, herbalFragment);
//        ft.addToBackStack(null);
                    ft.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void StartRecyclerViewKampo(View rootView) {

        kampoRecycleriew.setHasFixedSize(true);

        kampoLayoutManager = new LinearLayoutManager(getActivity());

        // use a linear layout manager
        kampoRecycleriew.setLayoutManager(kampoLayoutManager);
        kampoAdapter = new kampoAdapter(kampoRecycleriew, getActivity(), kampoModels);
        kampoRecycleriew.setAdapter(kampoAdapter);
        kampoAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                kampoModels.add(null);
                kampoAdapter.notifyItemInserted(kampoModels.size() - 1);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        kampoModels.remove(kampoModels.size() - 1);
                        kampoAdapter.notifyItemRemoved(kampoModels.size());
                        //add items one by one
                        int start = kampoModels.size();
                        int end = start + 20;
                        loadMoreKampo(start, end);
                    }
                }, 5000);
            }
        });
        kampoRecycleriew.setVisibility(View.VISIBLE);
    }

    private void loadMoreKampo(final int start, final int end) {
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, "Onresponse" + jsonArray.toString());
                        for (int i = start; i < end; i++) {
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
                                kampoAdapter.notifyItemInserted(kampoModels.size());

                            } catch (JSONException e) {

                            }
                        }
                        kampoAdapter.setLoaded();

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

    private void get10DataKampo() {
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        loadKampo.setVisibility(View.GONE);
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
                                kampoAdapter.notifyDataSetChanged();
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

    private void searchHerb(String idCategories) {
        Bundle arguments = new Bundle();
        arguments.putString( "categories" , idCategories);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        searchHerbs searchHerbs = new searchHerbs();
        searchHerbs.setArguments(arguments);
        ft.replace(R.id.main_frame, searchHerbs);
//        ft.addToBackStack(null);
        ft.commit();
    }

}
