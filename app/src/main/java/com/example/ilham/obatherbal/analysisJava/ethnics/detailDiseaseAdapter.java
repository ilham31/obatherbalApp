package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;

import java.util.List;

public class detailDiseaseAdapter extends RecyclerView.Adapter<detailDiseaseAdapter.detailDiseaseEthniclViewHolder>{

    private Context mCtx;
    List<detailDiseaseModel> detailDiseaseModels;

    public detailDiseaseAdapter(Context mCtx, List<detailDiseaseModel> detailDiseaseModels) {
        this.mCtx = mCtx;
        this.detailDiseaseModels = detailDiseaseModels;
    }

    @NonNull
    @Override
    public detailDiseaseEthniclViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_detail_disease, parent,false);
        detailDiseaseEthniclViewHolder holder = new detailDiseaseEthniclViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull detailDiseaseEthniclViewHolder detailDiseaseEthniclViewHolder, int i) {
        detailDiseaseModel detailDiseaseModel =detailDiseaseModels.get(i);
        detailDiseaseEthniclViewHolder.tanaman.setText("Tanaman : " + detailDiseaseModel.getTanaman());
        detailDiseaseEthniclViewHolder.spesies.setText("Spesies :" + detailDiseaseModel.getSpesies());
        detailDiseaseEthniclViewHolder.bagian.setText("Bagian : "+detailDiseaseModel.getBagian());
    }

    @Override
    public int getItemCount() {
        return detailDiseaseModels.size();
    }

    class detailDiseaseEthniclViewHolder extends RecyclerView.ViewHolder{

        TextView tanaman,spesies,bagian;
        public detailDiseaseEthniclViewHolder(@NonNull View itemView) {
            super(itemView);
            tanaman = itemView.findViewById(R.id.tanamanDetailDisease);
            spesies = itemView.findViewById(R.id.spesiesDetailDisease);
            bagian = itemView.findViewById(R.id.bagianDetailDisease);
        }
    }
}
