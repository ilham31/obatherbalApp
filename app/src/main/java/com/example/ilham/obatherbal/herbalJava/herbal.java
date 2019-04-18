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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
    RecyclerView mRecyclerView;
    herbalAdapter mAdapter;
    List<herbalModel> herbalModels;
    ProgressBar loadHerb;
    View rootView;
    EditText searchHerbal;
    private LinearLayoutManager mLayoutManager;
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
//        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        searchHerbal = (EditText) rootView.findViewById(R.id.search_herbal);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_herbal);
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
//        ft.addToBackStack(null);
        ft.commit();
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
                        int page = (herbalModels.size()/10)+1;
                        Log.d(TAG, "pagejamu" + page +"size = " +herbalModels.size());
                        loadMoreJamu(page);

                    }
                }, 2000);

            }
        });

    }


    //AIzaSyDx99g6q4WjOWoiNb06l0I7mhVs8zAc-VQ
    //https://www.googleapis.com/customsearch/v1?key=AIzaSyDx99g6q4WjOWoiNb06l0I7mhVs8zAc-VQ&cx=008499053186876385122:rpuxtfhy9bq&q=jamu gadung klingsir
    private void get10DataJamu() {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/pages";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
               (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                   @Override
                   public void onResponse(JSONObject response) {
                       loadHerb.setVisibility(View.GONE);
                       Log.d(TAG, "OnresponseHerbal" + response.toString());
                       try {

                           JSONArray herbsmeds = response.getJSONArray("data");
                           Log.d(TAG,"herbsmeds"+herbsmeds.toString());
                           for (int i = 0; i < herbsmeds.length() ; i++)
                           {
                               JSONObject jsonObject = herbsmeds.getJSONObject(i);
                               if (jsonObject.getString("idtype").equals("2"))
                               {
                                   herbalModels.add(
                                           new herbalModel(
                                                   jsonObject.getString("name"),
                                                   jsonObject.getString("efficacy"),
                                                   jsonObject.getString("idherb"),
                                                   jsonObject.getString("idherbsmed"),
                                                   jsonObject.getString("img")

                                           )
                                   );
                                   mAdapter.notifyDataSetChanged();
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

private void loadMoreJamu(final int page) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/pages/"+page;
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
                                if (jsonObject.getString("idtype").equals("2"))
                                {
                                    herbalModels.add(
                                            new herbalModel(
                                                    jsonObject.getString("name"),
                                                    jsonObject.getString("efficacy"),
                                                    jsonObject.getString("idherb"),
                                                    jsonObject.getString("idherbsmed"),
                                                    jsonObject.getString("img")
                                            )
                                    );
                                    mAdapter.notifyItemInserted(herbalModels.size());
                                }

                            }
                            mAdapter.setLoaded();
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
                    get10DataJamu();
                    searchHerbal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchHerb(idCategories);
                        }
                    });
                }
                    else {
                    categoriesHerbal selectedValue = (categoriesHerbal) parent.getItemAtPosition(position);
                    String categories = (String) selectedValue.getCategories();
                    final String idCategories = (String) selectedValue.getIdCategories();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    kampo kampoFragment = new kampo();

                    ft.replace(R.id.main_frame, kampoFragment);
//        ft.addToBackStack(null);
                    ft.commit();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}



