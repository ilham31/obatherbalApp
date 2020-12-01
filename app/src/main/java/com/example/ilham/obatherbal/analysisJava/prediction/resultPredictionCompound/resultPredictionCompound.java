package com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionCompound;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilham.obatherbal.MainActivity;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionPlant.adapterResultPredic;

import java.util.ArrayList;

public class resultPredictionCompound extends AppCompatActivity {
    TextView result,textCompound, diseaseDescriptionTextView;
    String method,diseaseDesc, disease;
    ImageView back,successPredictCompound;
    RecyclerView recyclerView;
    ArrayList<String> idCompound;
    Boolean success = true;
    adapterResultPredic mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_prediction_compound);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        result = (TextView)findViewById(R.id.predictResultCompound);
        method = args.getString("methodPredict");
        idCompound = (ArrayList<String>) args.getStringArrayList("compoundName");
        disease = args.getString("disease");
        diseaseDesc = args.getString("descDisease");
        back = (ImageView) findViewById(R.id.backpresspredictresultCompound);
        successPredictCompound = (ImageView) findViewById(R.id.successPredictCompound);
        diseaseDescriptionTextView = (TextView) findViewById(R.id.diseaseDesctiptionCompound);
        textCompound = (TextView)findViewById(R.id.texttextCompound);
        if (success)
        {
            successPredictCompound.setImageResource(R.drawable.compoundpredict);
            Log.d("result", "plantttt"+String.valueOf(idCompound));
            result.setText("The compounds that you choose can cure " + disease +"using method "+method);
            diseaseDescriptionTextView.setText(diseaseDesc);
            recyclerView = (RecyclerView)findViewById(R.id.compoundPredictResult);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mAdapter = new adapterResultPredic(this,idCompound);
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            successPredictCompound.setImageResource(R.drawable.error);
            result.setText("Error when predict");
            textCompound.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
