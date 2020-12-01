package com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionPlant;

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

import java.util.ArrayList;

public class resultPredictionPlant extends AppCompatActivity  {
    TextView result,textPlant, diseaseDescTextView;
    String method,diseaseDesc, disease;
    ImageView back,successPredict;
    RecyclerView recyclerView;
    ArrayList<String> idPlant;
    adapterResultPredic mAdapter;
    Boolean success = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_prediction_plant);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        result = findViewById(R.id.predictResultPlant);
        diseaseDescTextView = findViewById(R.id.diseaseDesctiptionCrude);
        method = args.getString("methodPredict");
        idPlant = (ArrayList<String>) args.getStringArrayList("plantName");
//        args.putString("classDisease",classDisease);
//        args.putString("descDisease",descDisease);
//        args.putString("refDisease",refDisease);
        disease = args.getString("disease");
        diseaseDesc = args.getString("descDisease");

        back = (ImageView) findViewById(R.id.backpresspredictresult);
        successPredict = (ImageView) findViewById(R.id.successPredict);
        textPlant = (TextView)findViewById(R.id.texttext);
        if (success)
        {
            successPredict.setImageResource(R.drawable.predictplant);
            Log.d("result", "plantttt"+String.valueOf(idPlant));
            result.setText("The plants that you choose can cure "+disease);
            diseaseDescTextView.setText(diseaseDesc);
            recyclerView = (RecyclerView)findViewById(R.id.plantPredictResult);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mAdapter = new adapterResultPredic(this,idPlant);
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            successPredict.setImageResource(R.drawable.error);
            result.setText("Error when predict");
            textPlant.setVisibility(View.GONE);
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
