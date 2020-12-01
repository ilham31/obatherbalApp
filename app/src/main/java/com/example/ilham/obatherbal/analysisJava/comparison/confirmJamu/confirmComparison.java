package com.example.ilham.obatherbal.analysisJava.comparison.confirmJamu;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ilham.obatherbal.analysisJava.comparison.resultComparison.resultComparison;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu.jamuModelComparison;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmComparison extends Fragment implements Serializable {

    private View view;
    private Button buttonNext;
    private RecyclerView recyclerView;
    private adapterComparisonJamuConfirm adapterComparisonJamuConfirm;
    List<jamuModelComparison> detailjamu;
    public confirmComparison() {
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
                //kalau jamu yang terpilih 2
                if (idJamu.size() == 2)
                {
                    List<String> idjamu = new ArrayList<String>();
                    for (jamuModelComparison h : idJamu)
                    {
                        idjamu.add(h.getId());
                    }
                    Log.d("confirm","idjamu 1 = "+idjamu.get(0)+", idjamu2 = "+idjamu.get(1));
                    steppersComparison.goToStepConfirm();
                    resultComparison step3fragment = new resultComparison();
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
