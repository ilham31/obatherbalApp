package com.example.ilham.obatherbal.analysisJava.prediction.chooseMethodCompound;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseMethod.methodModel;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPredictionCompound;
import com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound.confirmPageCompound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound.compoundPredictModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethodCompound extends Fragment {
    private static final String TAG = "choose";
    private Button buttonNext;
    View view;
    private String categories;
    private String idCategories;
    private Integer optimizationValue;

    public chooseMethodCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_method_compound, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.pilihMetodeCompound);
        final Switch optimization = (Switch) view.findViewById(R.id.optimization_compound);
        final ArrayList<compoundPredictModel> idCompound= (ArrayList<compoundPredictModel>)getArguments().getSerializable("idCompound");
        for (compoundPredictModel h : idCompound)
        {
            Log.d("method compound","id compound = "+h.getIdCompound() +" name = "+h.getNameCompound());
        }
        selectMethod();

        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_2_compound);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optimization.isChecked()) {
                    optimizationValue = 1;
                } else {
                    optimizationValue = 0;
                }
                Log.d("optimization","optimization = " + optimizationValue);
                steppersPredictionCompound.goToStepResult();
                confirmPageCompound step3Fragment = new confirmPageCompound();
                Bundle bundle = new Bundle();
                bundle.putSerializable("idCompound", (Serializable) idCompound);
                bundle.putString("idCategories",idCategories);
                bundle.putString("categories",categories);
                bundle.putInt("Optimization", optimizationValue);
                step3Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layoutstepperCompound, step3Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void selectMethod() {
        Spinner spinner = (Spinner) view.findViewById(R.id.pilihMetodeCompound);
        List<methodModel> methodModels = new ArrayList<>();
        methodModels.add(
                new methodModel(
                        "dnn",
                        "Deep Neural Network"
                )
        );
        methodModels.add(
                new methodModel(
                        "rf",
                        "Random Forest"
                )
        );

        methodModels.add(
                new methodModel(
                        "lgbm",
                        "Light GBM"
                )
        );
        ArrayAdapter<methodModel> spinnerAdapter = new ArrayAdapter<methodModel>(getActivity(), android.R.layout.simple_spinner_item, methodModels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    methodModel selectedValue = (methodModel) parent.getItemAtPosition(position);
                    categories = (String) selectedValue.getCategories();
                    idCategories = (String) selectedValue.getIdCategories();
                    Log.d("method compound","selected item"+idCategories);
                }
                else
                {
                    methodModel selectedValue = (methodModel) parent.getItemAtPosition(position);
                    categories = (String) selectedValue.getCategories();
                    idCategories = (String) selectedValue.getIdCategories();
                    Log.d("method compound","selected item"+idCategories);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
