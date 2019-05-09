package com.example.ilham.obatherbal.search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.compoundJava.compoundModel;
import com.example.ilham.obatherbal.compoundJava.detailCompound;

import java.util.ArrayList;
import java.util.List;

public class adapterSearchCompound extends RecyclerView.Adapter<adapterSearchCompound.compoundViewHolder> {

    private Context mCtx;
    List<compoundModel> compoundModelList;

    public adapterSearchCompound(Context mCtx, List<compoundModel> compoundModelList) {
        this.mCtx = mCtx;
        this.compoundModelList = compoundModelList;
    }

    @NonNull
    @Override
    public compoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutherbal, parent,false);
        compoundViewHolder holder = new compoundViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull compoundViewHolder compoundViewHolder, int i) {
        final compoundModel detailCompound = (compoundModel) compoundModelList.get(i);
        compoundViewHolder.namaHerbal.setText(detailCompound.getNama());
        compoundViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailCompound.class);
                Bundle b = new Bundle();
                b.putString("compoundName",detailCompound.getNama());
                b.putString("partOfPlantCompound",detailCompound.getPartOfplant());
                b.putString("plantSpeciesCompound",detailCompound.getPlantspecies());
                b.putString("molecularFormula",detailCompound.getMolecular_formula());
                b.putString("refCompound",detailCompound.getRef());
                i.putExtras(b);
                mCtx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return compoundModelList.size();
    }

    public void filterlist (ArrayList<compoundModel> filteredList)
    {
        compoundModelList =filteredList;
        notifyDataSetChanged();
    }

    class compoundViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal;
        ImageView thumbnailKampo;
        public compoundViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal_search);
        }
    }
}