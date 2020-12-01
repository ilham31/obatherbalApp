package com.example.ilham.obatherbal.analysisJava.prediction.chooseMethod;


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
import com.example.ilham.obatherbal.analysisJava.prediction.confirmPage.confirmPrediction;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPrediction;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseMethod extends Fragment {
    private static final String TAG = "choose";
    private View view;
    private Button buttonNext;
    private String categories;
    private String idCategories;
    private Integer optimizationValue;



    public chooseMethod() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_choose_method, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.pilihMetode);
        final Switch optimization = (Switch) view.findViewById(R.id.optimization);
        final ArrayList<herbsModel> idPlant= (ArrayList<herbsModel>)getArguments().getSerializable("idPlant");
        for (herbsModel h : idPlant)
        {
            Log.d("method","id plant = "+h.getIdPlant() +" name = "+h.getNameHerbs());
        }
        selectMethod();

        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_2);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optimization.isChecked()) {
                    optimizationValue = 1;
                } else {
                    optimizationValue = 0;
                }
                Log.d("method","selected item on button"+idCategories);
                steppersPrediction.goToStepResult();
                confirmPrediction step3Fragment = new confirmPrediction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("idPlant", (Serializable) idPlant);
                bundle.putString("idCategories",idCategories);
                bundle.putString("categories",categories);
                bundle.putInt("Optimization", optimizationValue);
                step3Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layoutstepper, step3Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;

    }

    private void selectMethod() {
        Spinner spinner = (Spinner) view.findViewById(R.id.pilihMetode);
        List<methodModel> methodModels = new ArrayList<>();
        methodModels.add(
                new methodModel(
                        "dl",
                        "Deep Learning"
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
                        "svm",
                        "Support Vector Machine"
                )
        );
        ArrayAdapter<methodModel>  spinnerAdapter = new ArrayAdapter<methodModel>(getActivity(), android.R.layout.simple_spinner_item, methodModels);
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
                    Log.d("method","selected item"+idCategories);
                }
                else
                {
                    methodModel selectedValue = (methodModel) parent.getItemAtPosition(position);
                    categories = (String) selectedValue.getCategories();
                    idCategories = (String) selectedValue.getIdCategories();
                    Log.d("method","selected item"+idCategories);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

