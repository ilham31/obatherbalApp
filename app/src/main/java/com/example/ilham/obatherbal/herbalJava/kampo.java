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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
        kampoRecycleriew.setVisibility(View.VISIBLE);
    }

    private void get10DataKampo() {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadKampo.setVisibility(View.GONE);
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
