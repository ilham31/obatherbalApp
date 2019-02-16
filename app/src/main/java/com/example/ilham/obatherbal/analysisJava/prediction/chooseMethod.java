package com.example.ilham.obatherbal.analysisJava.prediction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethod extends Fragment {
    private static final String TAG = "choose";
    private View view;
    private Button buttonNext;
    private TextView chosen;


    public chooseMethod() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_choose_method, container, false);
        chosen = (TextView) view.findViewById(R.id.chosenPlant);
        Bundle bundle = getArguments();
//        Log.d(TAG,"checked Item"+c)

        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_2);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steppersPrediction.goToStepResult();
                resultPrediction step3Fragment = new resultPrediction();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layoutstepper, step3Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;

    }

}

