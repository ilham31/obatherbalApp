package com.example.ilham.obatherbal.crudeJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.herbalAdapter;
import com.example.ilham.obatherbal.herbalJava.herbalModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class crude extends Fragment {

    RecyclerView recyclerView;
    crudeAdapter adapter;
    List<crudeModel> crudeModels;
    EditText search;
    public crude() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_crude, container, false);
        crudeModels = new ArrayList<>();
        getData();
        searchData(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_crude);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new crudeAdapter(getActivity(),crudeModels);
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    private void searchData(View rootView) {
        search = (EditText) rootView.findViewById(R.id.search_crude);
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

    private void filter(String s) {
        ArrayList<crudeModel> filteredList = new ArrayList<>();
        for (crudeModel item : crudeModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }

    private void getData() {
        crudeModels.add(
                new crudeModel(
                        "crude1",
                        "crude 1"
                )
        );
        crudeModels.add(
                new crudeModel(
                        "crude2",
                        "crude 2"
                )
        );
        crudeModels.add(
                new crudeModel(
                        "crude3",
                        "crude 3"
                )
        );
        crudeModels.add(
                new crudeModel(
                        "crude4",
                        "crude 4"
                )
        );
        crudeModels.add(
                new crudeModel(
                        "crude5",
                        "crude 5"
                )
        );
    }

}
