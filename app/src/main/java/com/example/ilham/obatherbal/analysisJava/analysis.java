package com.example.ilham.obatherbal.analysisJava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPrediction;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPredictionCompound;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;
import com.example.ilham.obatherbal.analysisJava.ethnics.MapsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class analysis extends Fragment {
    private CardView analysis,comparison,ethnics;
    private View rootview;
    private String selection;
    String[] type;

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
                type = new String[]{"Jamu","Compound"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose ");
                builder.setSingleChoiceItems(type, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = type[which];
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selection.equals("Jamu")){
                            Intent intent = new Intent(getActivity(),steppersPrediction.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getActivity(),steppersPredictionCompound.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
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
        ethnics = (CardView) rootview.findViewById(R.id.cardGIS);
        ethnics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ethnic = new Intent(getActivity(),MapsActivity.class);
                ethnic.putExtra("daerah","Sumatra utara");
                startActivity(ethnic);
            }
        });
        return rootview;
    }

}
