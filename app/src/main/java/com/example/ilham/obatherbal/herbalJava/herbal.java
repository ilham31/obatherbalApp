package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ilham.obatherbal.R;

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
    public herbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_herbal, container, false);
        herbalModels = new ArrayList<>();

        getData();

        sortData(rootView);

        return rootView ;

    }

    private void sortData(final View rootView) {
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.filter_herbal);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filter_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    starRecyclerView(rootView);
                    searchData(rootView);

                }
                else
                {
                    String selectedValue = (String) parent.getItemAtPosition(position);
                    categories(selectedValue);
                    searchOnCategories(rootView,selectedValue);

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


    private void starRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_herbal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new herbalAdapter(getActivity(),herbalModels);
        recyclerView.setAdapter(adapter);

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
        herbalModels.add(
                new herbalModel(

                        "jamu 1",
                        "batuk",
                        "jamu",
                        "jamu1"
                )
        );
        herbalModels.add(
                new herbalModel(
                        "jamu 2 enak",
                        "flu",
                        "jamu",
                        "jamu2"
                )
        );
        herbalModels.add(
                new herbalModel(
                        "jamu 3 pahit",
                        "jerawat",
                        "jamu",
                        "jamu3"
                )
        );
        herbalModels.add(
                new herbalModel(
                        "kampo 1 mantap",
                        "enak",
                        "kampo",
                        "kampo1"
                )
        );
        herbalModels.add(
                new herbalModel(
                        "kampo 2 pahit",
                        "batuk",
                        "kampo",
                        "kampo2"
                )
        );
        herbalModels.add(
                new herbalModel(
                        "kampo 3 enak",
                        "flu",
                        "kampo",
                        "kampo3"
                )
        );
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
