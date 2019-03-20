package com.example.ilham.obatherbal.analysisJava.prediction.chooseMethodCompound;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPredictionCompound;
import com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound.confirmPageCompound;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethodCompound extends Fragment {
    private Button buttonNext;
    View view;

    public chooseMethodCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_method_compound, container, false);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_2_compound);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steppersPredictionCompound.goToStepResult();
                confirmPageCompound step3Fragment = new confirmPageCompound();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layoutstepperCompound, step3Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}
