package com.example.ilham.obatherbal.analysisJava;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPrediction;

import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;


/**
 * A simple {@link Fragment} subclass.
 */
public class analysis extends Fragment {
    private CardView analysis,comparison;
    private View rootview;

    public analysis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_analysis, container, false);
        analysis = (CardView) rootview.findViewById(R.id.cardAnalysis);
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),steppersPrediction.class);
                startActivity(intent);
            }
        });
        comparison = (CardView) rootview.findViewById(R.id.cardComparison);
        comparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComparison = new Intent(getActivity(),steppersComparison.class);
                startActivity(intentComparison);
            }
        });
        return rootview;
    }

}
