package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound.compoundPredictModel;
import com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionCompound.resultPredictionCompound;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmPageCompound extends Fragment {

    View view;
    private TextView method;
    private RecyclerView recyclerView;
    private adapterConfirmCompound adapterConfirmCompound;
    Button submitCompound;
    ArrayList<String>compoundName;
    public confirmPageCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_page_compound, container, false);
        compoundName = new ArrayList<>();
        final ArrayList<compoundPredictModel> idCompound= (ArrayList<compoundPredictModel>)getArguments().getSerializable("idCompound");
        for (compoundPredictModel h : idCompound)
        {
            Log.d("confirm","id plant = "+h.getIdCompound()+" name = "+h.getNameCompound());
            compoundName.add(h.getNameCompound());
        }
        final String idCategories= getArguments().getString("idCategories");
        final String Categories= getArguments().getString("categories");
        method = (TextView) view.findViewById(R.id.chosenMethodCompound);
        method.setText("Method :"+Categories);
        recyclerView = (RecyclerView) view.findViewById(R.id.compoundPredict);
        submitCompound = (Button) view.findViewById(R.id.submitPredictCompound);
        submitCompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCompound.size()>0)
                {
                    Intent i = new Intent(getActivity(), resultPredictionCompound.class);
                    Bundle args = new Bundle();
                    args.putString("methodPredict",Categories);
                    args.putStringArrayList("compoundName", compoundName);
                    i.putExtra("bundle",args);
                    startActivity(i);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(),"please choose at least 1 compound",Toast.LENGTH_LONG).show();
                }

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterConfirmCompound = new adapterConfirmCompound(getActivity(),idCompound);
        recyclerView.setAdapter(adapterConfirmCompound);



        return view;
    }

}
