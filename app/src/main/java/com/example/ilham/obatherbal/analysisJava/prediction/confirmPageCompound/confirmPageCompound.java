package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;


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
import com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound.compoundPredictModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmPageCompound extends Fragment {

    View view;
    private TextView method;
    private RecyclerView recyclerView;
    private adapterConfirmCompound adapterConfirmCompound;
    public confirmPageCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_page_compound, container, false);
        final ArrayList<compoundPredictModel> idCompound= (ArrayList<compoundPredictModel>)getArguments().getSerializable("idCompound");
        final String idCategories= getArguments().getString("idCategories");
        final String Categories= getArguments().getString("categories");
        method = (TextView) view.findViewById(R.id.chosenMethodCompound);
        method.setText("Method :"+Categories);
        recyclerView = (RecyclerView) view.findViewById(R.id.compoundPredict);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterConfirmCompound = new adapterConfirmCompound(getActivity(),idCompound);
        recyclerView.setAdapter(adapterConfirmCompound);


        for (compoundPredictModel h : idCompound)
        {
            Log.d("confirm","id plant = "+h.getIdCompound()+" name = "+h.getNameCompound());
        }
        Log.d("confirm","idcategories" + idCategories);
        return view;
    }

}
