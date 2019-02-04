package com.example.ilham.obatherbal.compoundJava;


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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class compound extends Fragment {
    RecyclerView recyclerView;
    compoundAdapter adapter;
    List<compoundModel> compoundModels;
    EditText search;

    public compound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_compound, container, false);
        compoundModels = new ArrayList<>();
        getData();
        searchData(rootView);
        startRecyclerView(rootView);
        return rootView;
    }

    private void startRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_compound);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new compoundAdapter(getActivity(),compoundModels);
        recyclerView.setAdapter(adapter);
    }

    private void searchData(View rootView) {
        search = (EditText) rootView.findViewById(R.id.search_compound);
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
        ArrayList<compoundModel> filteredList = new ArrayList<>();
        for (compoundModel item : compoundModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }

    private void getData() {
        compoundModels.add(
                new compoundModel(
                        "compound1",
                        "compound 1"
                )
        );
        compoundModels.add(
                new compoundModel(
                        "compound2",
                        "compound 2"
                )
        );
        compoundModels.add(
                new compoundModel(
                        "compound3",
                        "compound 3"
                )
        );
        compoundModels.add(
                new compoundModel(
                        "compound4",
                        "compound 4"
                )
        );
        compoundModels.add(
                new compoundModel(
                        "compound5",
                        "compound 5"
                )
        );
    }

}
