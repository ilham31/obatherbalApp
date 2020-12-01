package com.example.ilham.obatherbal.analysisJava.prediction.resultPredictionPlant;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;

import java.util.List;

public class adapterResultPredic extends RecyclerView.Adapter<adapterResultPredic.resultPredictViewHolder> {
    private Context mCtx;
    List<String>plantName;

    public adapterResultPredic(Context mCtx, List<String> plantName) {
        this.mCtx = mCtx;
        this.plantName = plantName;
    }

    @NonNull
    @Override
    public resultPredictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.predictresultplant, parent, false);
        resultPredictViewHolder holder = new resultPredictViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull resultPredictViewHolder resultPredictViewHolder, int i) {
            resultPredictViewHolder.plantName.setText(plantName.get(i));
    }

    @Override
    public int getItemCount() {
        return plantName.size();
    }

    class resultPredictViewHolder extends RecyclerView.ViewHolder{
        TextView plantName;
        public resultPredictViewHolder(@NonNull View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.predictResultPlantList);
        }
    }
}
