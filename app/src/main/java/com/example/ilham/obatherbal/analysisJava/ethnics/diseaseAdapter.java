package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;

import java.util.ArrayList;
import java.util.List;

public class diseaseAdapter extends RecyclerView.Adapter<diseaseAdapter.diseaseViewHolder>{
    private Context mCtx;
    List<diseaseModel> diseaseModelList;

    public diseaseAdapter(Context mCtx, List<diseaseModel> diseaseModelList) {
        this.mCtx = mCtx;
        this.diseaseModelList = diseaseModelList;
    }

    @NonNull
    @Override
    public diseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_disease, parent,false);
        diseaseViewHolder holder = new diseaseViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull diseaseViewHolder diseaseViewHolder, int i) {
        final diseaseModel detailDisease = diseaseModelList.get(i);
        diseaseViewHolder.diseaseName.setText(detailDisease.getDiseaseENG()+" ("+detailDisease.getDiseaseINA()+")");
        diseaseViewHolder.plantEthnics.setText("can cured using "+detailDisease.getSpecies()+" ("+detailDisease.getNameINA()+") using its "+detailDisease.getSectionENG()+" /"+detailDisease.getSectionINA());

    }

    @Override
    public int getItemCount() {
        return diseaseModelList.size();
    }

    public void filterlist (ArrayList<diseaseModel> filteredList)
    {
        diseaseModelList =filteredList;
        notifyDataSetChanged();
    }

    class diseaseViewHolder extends RecyclerView.ViewHolder{

        TextView diseaseName,plantEthnics;
        public diseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseName = (TextView) itemView.findViewById(R.id.diseaseEthnic);
            plantEthnics = (TextView) itemView.findViewById(R.id.plantEthnic);
        }
    }
}
