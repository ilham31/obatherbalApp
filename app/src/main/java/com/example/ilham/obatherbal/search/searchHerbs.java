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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.example.ilham.obatherbal.compoundJava.compoundModel;
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
import com.example.ilham.obatherbal.search.Adapter.adapterSearchCompound;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchJamu;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchJamuDisease;
import com.example.ilham.obatherbal.search.Adapter.adapterSearchPlant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

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
    List <compoundModel> compoundModelList;
    adapterSearchJamu adapterJamu;
    adapterKampo adapterKampo;
    adapterSearchPlant adapterSearchPlant;
    adapterSearchCompound adapterSearchCompound;
    RecyclerView recyclerView;
    TextView notfoundsearch,notfoundfilter;
    LinearLayout categoriesSearch;
    adapterSearchJamuDisease adapterSearchJamuDisease;

    public searchHerbs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String idCategories = arguments.getString("categories");
        Log.d(TAG,"kategori yang dipilih :"+idCategories );
        rootView = inflater.inflate(R.layout.fragment_search_herbs, parent, false);
        notfoundsearch = (TextView) rootView.findViewById(R.id.startSearch);
        notfoundfilter =(TextView)rootView.findViewById(R.id.notfoundfilter);
        categoriesSearch = (LinearLayout) rootView.findViewById(R.id.categoriesSearchHerbal);
        categoriesSearch.setVisibility(GONE);
        notfoundfilter.setVisibility(GONE);
        notfoundsearch.setVisibility(View.VISIBLE);
        searchHerbs = (EditText) rootView.findViewById(R.id.search_herbs);
        switch (idCategories)
        {
            case "jamu":
                searchHerbs.setHint("Search Jamu");
                herbalModels = new ArrayList<>();
                getDataJamu();
                searhJamubyName();
                startRecyclerViewJamubyName();
//                filterSearchTypeHerbal();
//                categoriesSearch.setVisibility(View.VISIBLE);
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
                compoundModelList = new ArrayList<>();
                getDataCompound();
                searchCompound();
                startRecyclerViewCompound();
                break;
        }
          return rootView;
    }

    private void filterSearchTypeHerbal() {
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.filter_herbalSearch);
// Create an ArrayAdapter using the string array and a default spinner layout
        List<String> itemList = new ArrayList<String>();
        itemList.add(0,"Name");
        itemList.add(1,"Disease");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears

// Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    searhJamubyName();
                    startRecyclerViewJamubyName();
                }
                else if (position == 1)
                {
                    searchJamubyDisease();
                    startRecyclerViewJamubyDisease();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startRecyclerViewJamubyDisease() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterSearchJamuDisease = new adapterSearchJamuDisease(getActivity(),herbalModels);

        recyclerView.setAdapter(adapterSearchJamuDisease);
        recyclerView.setVisibility(GONE);
    }

    private void searchJamubyDisease() {
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
                    notfoundsearch.setVisibility(GONE);
                }
                filterJamubyDisease(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(GONE);
                }
            }
        });
    }

    private void filterJamubyDisease(String s) {
        ArrayList<herbalModel> filteredList = new ArrayList<>();
        for (herbalModel item : herbalModels){
            if (item.getKhasiat().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterSearchJamuDisease.filterlist(filteredList);
        Log.d("searchJamu","size filter ="+filteredList.size());
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(GONE);
        }

    }

    private void startRecyclerViewCompound() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterSearchCompound= new adapterSearchCompound(getActivity(),compoundModelList);
        recyclerView.setAdapter(adapterSearchCompound);
        recyclerView.setVisibility(GONE);
    }

    private void startRecyclerViewPlant() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterSearchPlant = new adapterSearchPlant(getActivity(),crudeModels);
        recyclerView.setAdapter(adapterSearchPlant);
        recyclerView.setVisibility(GONE);
    }


    private void searchCompound() {
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
                    notfoundsearch.setVisibility(GONE);
                }
                filterCompound(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(GONE);
                }

            }
        });
    }

    private void filterCompound(String s) {
        Log.d(TAG,"string filter" + s);
        ArrayList<compoundModel> filteredList = new ArrayList<>();
        for (compoundModel item : compoundModelList){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterSearchCompound.filterlist(filteredList);
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(GONE);
        }

    }

    private void getDataCompound() {
        String url = "http://www.mocky.io/v2/5cce4f3f300000d30d52c2d4";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length() ; i++)
                            {
                                JSONObject jsonObject = data.getJSONObject(i);
                                compoundModelList.add(
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
                    notfoundsearch.setVisibility(GONE);
                }
                filterPlant(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(GONE);
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
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(GONE);
        }
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
                    notfoundsearch.setVisibility(GONE);
                }
                filterKampo(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(GONE);
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
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(GONE);
        }
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

    private void startRecyclerViewKampo() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterKampo = new adapterKampo(getActivity(),kampoModels);
        recyclerView.setAdapter(adapterKampo);
        recyclerView.setVisibility(GONE);
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

    private void searhJamubyName() {
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
                    notfoundsearch.setVisibility(GONE);
                }
                filterJamubyName(s.toString());
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(GONE);
                }
            }
        });
    }

    private void startRecyclerViewJamubyName()
    {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterJamu = new adapterSearchJamu(getActivity(),herbalModels);
        recyclerView.setAdapter(adapterJamu);
        recyclerView.setVisibility(GONE);
    }

    private void filterJamubyName(String s) {
        ArrayList<herbalModel> filteredList = new ArrayList<>();
        for (herbalModel item : herbalModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterJamu.filterlist(filteredList);
        Log.d("searchJamu","size filter ="+filteredList.size());
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(GONE);
        }
    }

}
