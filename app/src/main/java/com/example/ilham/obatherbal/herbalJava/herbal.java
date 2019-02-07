package com.example.ilham.obatherbal.herbalJava;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.categoriesHerbal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class herbal extends Fragment {

    RecyclerView recyclerView;
    herbalAdapter adapter;
    List <herbalModel> herbalModels;
    EditText search;
    private static final String TAG = "herbal";
    public herbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_herbal, container, false);
        herbalModels = new ArrayList<>();

        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        getData();

        sortData(rootView);
        StartRecyclerView(rootView);

        return rootView ;

    }

    private void StartRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_herbal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapter = new herbalAdapter(getActivity(),herbalModels);
        recyclerView.setAdapter(adapter);
    }

    private void sortData(final View rootView) {

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.filter_herbal);
// Create an ArrayAdapter using the string array and a default spinner layout
        List<categoriesHerbal> itemList = new ArrayList<categoriesHerbal>();
        itemList.add(
                new categoriesHerbal(
                        "1",
                        "All"
                )
        );itemList.add(
                new categoriesHerbal(
                        "2",
                        "Jamu"
                )
        );itemList.add(
                new categoriesHerbal(
                        "3",
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
                if (position == 0)
                {
                    StartRecyclerView(rootView);
                    searchData(rootView);

                }
                else
                {
                    categoriesHerbal selectedValue = (categoriesHerbal) parent.getItemAtPosition(position);
                    String categories = (String)selectedValue.getCategories();
                    String idCategories = (String )selectedValue.getIdCategories();
                    Log.d(TAG,"selected"+categories+id);
                    categories(idCategories);
                    searchOnCategories(rootView,idCategories);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void searchOnCategories(View rootView, final String selectedValue) {
        search = (EditText) rootView.findViewById(R.id.search_herbal);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterOnCategories(s.toString(),selectedValue);
            }
        });

    }

    private void filterOnCategories(String s,String selectedValue) {
        ArrayList<herbalModel> searchCategories = new ArrayList<>();
        for (herbalModel item : herbalModels)
        {
            if((item.getTipe().toLowerCase().contains(selectedValue.toLowerCase()))&&(item.getNama().toLowerCase().contains(s.toLowerCase())))
            {
                searchCategories.add(item);
            }
        }
        adapter.filterlist(searchCategories);
    }




    private void searchData(View rootView) {
        search = (EditText) rootView.findViewById(R.id.search_herbal);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }

    private void getData() {

        String url ="https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG,"Onresponse"+jsonArray.toString());
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.d(TAG,"jsonobject"+jsonObject);
                                herbalModels.add(
                                    new herbalModel(
                                            jsonObject.getString("title"),
                                            "Khasiat",
                                            jsonObject.getString("albumId"),
                                            jsonObject.getString("id"),
                                            jsonObject.getString("thumbnailUrl")
                                    )
                            );
                            }
                            catch(JSONException e) {

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG,"Onerror"+volleyError.toString());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void filter(String s) {
        ArrayList<herbalModel> filteredList = new ArrayList<>();
        for (herbalModel item : herbalModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }
    private void categories(String selectedValue) {
        ArrayList<herbalModel> categoriesList = new ArrayList<>();
        for (herbalModel item : herbalModels)
        {
            if(item.getTipe().toLowerCase().contains(selectedValue.toLowerCase()))
            {
                categoriesList.add(item);
            }
        }
        adapter.filterlist(categoriesList);
    }



}
