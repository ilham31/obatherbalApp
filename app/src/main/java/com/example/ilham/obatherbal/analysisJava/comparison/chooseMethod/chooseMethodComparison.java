package com.example.ilham.obatherbal.analysisJava.comparison.chooseMethod;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;
import com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison.confirmComparison;

import com.example.ilham.obatherbal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethodComparison extends Fragment {

    private View view;
    private Button buttonNext;
    public chooseMethodComparison() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_method2, container, false);
        buttonNext =(Button) view.findViewById(R.id.button_next_fragment_step_2_comparison);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steppersComparison.goToStepConfirm();
                confirmComparison step3fragment = new confirmComparison();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layoutstepperComparison, step3fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        return view;
    }

}
