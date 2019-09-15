package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
    ProgressBar predictProgress;
    TextView progressNotif,phasePredict;
    Handler handler = new Handler();
    private int progressStatus = 0;
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

        final String idCategories= getArguments().getString("idCategories");
        final String Categories= getArguments().getString("categories");
        method = (TextView) view.findViewById(R.id.chosenMethodCompound);
        method.setText("Method :"+Categories);
        recyclerView = (RecyclerView) view.findViewById(R.id.compoundPredict);
        submitCompound = (Button) view.findViewById(R.id.submitPredictCompound);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        View v = inflater.inflate(R.layout.layout_loading_dialog, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();

        
        submitCompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCompound.size()>0)
                {
                    for (compoundPredictModel h : idCompound)
                    {
                        Log.d("confirm","id plant = "+h.getIdCompound()+" name = "+h.getNameCompound());
                        compoundName.add(h.getNameCompound());
                    }
                    AlertDialog.Builder myDialogBuilder = new AlertDialog.Builder(getActivity());
                    View ProgressView = getLayoutInflater().inflate(R.layout.layout_loading_dialog,
                            null);
                    predictProgress=(ProgressBar)ProgressView.findViewById(R.id.progressbarPredict);
                    progressNotif = (TextView) ProgressView.findViewById(R.id.progressNotif);
                    phasePredict = (TextView) ProgressView.findViewById(R.id.phasePredict);

                    myDialogBuilder.setView(ProgressView);
                    myDialogBuilder.create().show();

                    new Thread(new Runnable() {
                        public void run() {
                            while (progressStatus < 100) {
                                progressStatus += 1;
                                // Update the progress bar and display the
                                //current value in the text view
                                handler.post(new Runnable() {
                                    public void run() {
                                        predictProgress.setProgress(progressStatus);
                                        progressNotif.setText(progressStatus+"/"+predictProgress.getMax());
                                        if (progressStatus <= 25)
                                        {
                                            phasePredict.setText("Phase :"+"Phase 1");
                                        }
                                        else if (progressStatus <= 50){
                                            phasePredict.setText("Phase :"+"Phase 2");
                                        }
                                        else if (progressStatus <= 75){
                                            phasePredict.setText("Phase :"+"Phase 3");
                                        }
                                        else if (progressStatus == 100)
                                        {
                                            Intent i = new Intent(getActivity(), resultPredictionCompound.class);
                                            Bundle args = new Bundle();
                                            args.putString("methodPredict",Categories);
                                            args.putStringArrayList("compoundName", compoundName);
                                            i.putExtra("bundle",args);
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                        else{
                                            phasePredict.setText("Phase :"+"Phase 4");
                                        }


                                    }
                                });
                                try {
                                    // Sleep for 200 milliseconds.
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
//
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
