package com.example.ilham.obatherbal.search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.detailHerbal;
import com.example.ilham.obatherbal.herbalJava.kampoModel;

import java.util.ArrayList;
import java.util.List;

public class adapterKampo extends RecyclerView.Adapter<adapterKampo.kampoViewHolder> {
    private Context mCtx;
    List<kampoModel> kampoModelList;

    public adapterKampo(Context mCtx, List<kampoModel> kampoModelList) {
        this.mCtx = mCtx;
        this.kampoModelList = kampoModelList;
    }



    @NonNull
    @Override
    public kampoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_herbal, parent,false);
        kampoViewHolder holder = new kampoViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull kampoViewHolder kampoViewHolder, int i) {
        kampoModel detailkampo = (kampoModel) kampoModelList.get(i);
        kampoViewHolder.namaHerbalKampo.setText(detailkampo.getNamaKampo());
        kampoViewHolder.khasiatHerbalKampo.setText(detailkampo.getNamaKampo());
        Glide.with(mCtx)
                .load(detailkampo.getThumbnailKampo())
                .into(kampoViewHolder.thumbnailKampo);
        kampoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailHerbal.class);
                mCtx.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return kampoModelList.size();
    }

    public void filterlist (ArrayList<kampoModel> filteredList)
    {
        kampoModelList =filteredList;
        notifyDataSetChanged();
    }

    class kampoViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbalKampo,khasiatHerbalKampo;
        ImageView thumbnailKampo;
        public kampoViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbalKampo = itemView.findViewById(R.id.nama_herbal);
            khasiatHerbalKampo = itemView.findViewById(R.id.khasiat_herbal);
            thumbnailKampo = itemView.findViewById(R.id.gambar_herbal);
        }
    }
}
