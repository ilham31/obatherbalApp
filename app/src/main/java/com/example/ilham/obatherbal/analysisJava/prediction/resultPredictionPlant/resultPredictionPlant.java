package com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionPlant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ilham.obatherbal.MainActivity;
import com.example.ilham.obatherbal.R;

public class resultPredictionPlant extends AppCompatActivity {
    TextView result;
    String method;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_prediction_plant);
        result = (TextView)findViewById(R.id.predictResultPlant);
        method = getIntent().getStringExtra("methodPredict");
        result.setText("The plants that you choose can cure cough with 90% accuracy using method "+method);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
