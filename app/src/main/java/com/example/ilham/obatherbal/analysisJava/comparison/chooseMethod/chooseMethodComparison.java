package com.example.ilham.obatherbal.analysisJava.comparison.chooseMethod;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;
import com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison.confirmComparison;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu.jamuModelComparison;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethodComparison extends Fragment implements Serializable {

    private View view;
    private Button buttonNext;
    private RecyclerView recyclerView;
    private adapterComparisonJamuConfirm adapterComparisonJamuConfirm;
    List<jamuModelComparison> detailjamu;
    public chooseMethodComparison() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ArrayList<jamuModelComparison> idJamu =  (ArrayList<jamuModelComparison>)getArguments().getSerializable("idJamu");
        for (jamuModelComparison h : idJamu)
        {
            Log.d("choose methodcomp","nama jamu = "+h.getId());

        }
        detailjamu = new ArrayList<>();
        view = inflater.inflate(R.layout.fragment_choose_method2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.jamuComparison);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterComparisonJamuConfirm = new adapterComparisonJamuConfirm(getActivity(),idJamu);
        recyclerView.setAdapter(adapterComparisonJamuConfirm);
        buttonNext =(Button) view.findViewById(R.id.button_next_fragment_step_2_comparison);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idJamu.size() == 2)
                {
                    List<String> idjamu = new ArrayList<String>();
                    for (jamuModelComparison h : idJamu)
                    {
                        idjamu.add(h.getId());
                    }
                    Log.d("confirm","idjamu 1 = "+idjamu.get(0)+", idjamu2 = "+idjamu.get(1));
                    steppersComparison.goToStepConfirm();
                    confirmComparison step3fragment = new confirmComparison();
                    Bundle bundle = new Bundle();
                    bundle.putString("idjamu1",idjamu.get(0));
                    bundle.putString("idjamu2",idjamu.get(1));
                    step3fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                            .replace(R.id.frame_layoutstepperComparison, step3fragment)
                            .addToBackStack(null)
                            .commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"You need 2 jamu's to compare",Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }

}
