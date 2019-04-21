package com.example.ilham.obatherbal.analysisJava.prediction.confirmPage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class resultPrediction extends Fragment {
    private View view;
    private TextView method;
    private RecyclerView recyclerView;
    private adapterConfirm adapterConfirm;

    public resultPrediction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_result_prediction, container, false);
        final ArrayList<herbsModel> idPlant= (ArrayList<herbsModel>)getArguments().getSerializable("idPlant");
        final String idCategories= getArguments().getString("idCategories");
        method = (TextView) view.findViewById(R.id.chosenMethod);
        method.setText("Method :"+idCategories);
        recyclerView = (RecyclerView) view.findViewById(R.id.plantPredict);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterConfirm = new adapterConfirm(getActivity(),idPlant);
        recyclerView.setAdapter(adapterConfirm);


        for (herbsModel h : idPlant)
        {
            Log.d("confirm","id plant = "+h.getIdPlant()+" name = "+h.getNameHerbs());
        }
        Log.d("confirm","idcategories" + idCategories);
        return view;
    }

}
