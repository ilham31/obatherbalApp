package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.categoriesHerbal;

import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.search.searchHerbs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class herbal extends Fragment {

    private static final String JAMU = "datajamu";
    RecyclerView mRecyclerView,kampoRecycleriew;
    herbalAdapter mAdapter;
    kampoAdapter kampoAdapter;
    List<herbalModel> herbalModels;
    List<kampoModel> kampoModels;
    ProgressBar loadHerb;
    View rootView;
    EditText searchHerbal;
    private LinearLayoutManager mLayoutManager,kampoLayoutManager;
    private Handler handler;

    private static final String TAG = "herbal";

    public herbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_herbal, container, false);
        herbalModels = new ArrayList<>();
        kampoModels = new ArrayList<>();
        searchHerbal = (EditText) rootView.findViewById(R.id.search_herbal);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_herbal);

        kampoRecycleriew = (RecyclerView) rootView.findViewById(R.id.recyclerview_kampo);
        loadHerb = (ProgressBar) rootView.findViewById(R.id.loadHerb);
        loadHerb.setVisibility(View.VISIBLE);
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        sortData(rootView);

        return rootView;

    }



    private void searchHerb(String idCategories) {
        Bundle arguments = new Bundle();
        arguments.putString( "categories" , idCategories);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        searchHerbs searchHerbs = new searchHerbs();
        searchHerbs.setArguments(arguments);
        ft.replace(R.id.main_frame, searchHerbs);
        ft.addToBackStack(null);
        ft.commit();
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

    private void get20DataKampo() {
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        loadHerb.setVisibility(View.GONE);
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


    private void StartRecyclerViewJamu(final View rootView) {

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new herbalAdapter(mRecyclerView, getActivity(), herbalModels);
        mRecyclerView.setAdapter(mAdapter);
          mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                herbalModels.add(null);
                mAdapter.notifyItemInserted(herbalModels.size() - 1);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        herbalModels.remove(herbalModels.size() - 1);
                        mAdapter.notifyItemRemoved(herbalModels.size());
                        //add items one by one
                        int start = herbalModels.size();
                        int end = start + 20;
                        loadMoreJamu(start, end);

                    }
                }, 5000);

            }
        });
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void get20DataJamu() {

        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        loadHerb.setVisibility(View.GONE);
                        Log.d(TAG, "Onresponse" + jsonArray.toString());
                        Log.d(TAG, "lengthonresponse" + jsonArray.length());

                        for (int i = 0; i < 20; i++) {
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
                                mAdapter.notifyDataSetChanged();
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


    private void loadMoreJamu(final int start, final int end) {
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, "Onresponse" + jsonArray.toString());
                        for (int i = start; i < end; i++) {
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
                                mAdapter.notifyItemInserted(herbalModels.size());
                            } catch (JSONException e) {

                            }
                        }
                        mAdapter.setLoaded();

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

    private void sortData(final View rootView) {

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.filter_herbal);
// Create an ArrayAdapter using the string array and a default spinner layout
        List<categoriesHerbal> itemList = new ArrayList<categoriesHerbal>();
        itemList.add(
                new categoriesHerbal(
                        "jamu",
                        "Jamu"
                )
        );
        itemList.add(
                new categoriesHerbal(
                        "kampo",
                        "Kampo"
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
                    StartRecyclerViewJamu(rootView);
                    get20DataJamu();
                    searchHerbal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchHerb(idCategories);
                        }
                    });
                    kampoRecycleriew.setVisibility(View.GONE);


                } else {
                    categoriesHerbal selectedValue = (categoriesHerbal) parent.getItemAtPosition(position);
                    String categories = (String) selectedValue.getCategories();
                    final String idCategories = (String) selectedValue.getIdCategories();
                    StartRecyclerViewKampo(rootView);
                    get20DataKampo();
                    searchHerbal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchHerb(idCategories);
                        }
                    });
                    mRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}



