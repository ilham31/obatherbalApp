package com.example.ilham.obatherbal.herbalJava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.loadMore;

import java.util.ArrayList;
import java.util.List;






public class herbalAdapter extends RecyclerView.Adapter<herbalAdapter.herbalViewHolder>{

     private Context mCtx;
    List<herbalModel> herbalModelList;

    public herbalAdapter(Context mCtx, List<herbalModel> herbalModelList) {

        this.herbalModelList = herbalModelList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public herbalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_herbal, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
        herbalViewHolder holder = new herbalViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull herbalViewHolder herbalViewHolder, int i) {

        herbalModel detailherbal =herbalModelList.get(i);

        herbalViewHolder.namaHerbal.setText(detailherbal.getNama());
        herbalViewHolder.khasiatHerbal.setText(detailherbal.getKhasiat());
        Glide.with(mCtx)
                    .load(detailherbal.getThumbnail())
                    .into(herbalViewHolder.thumbnail);
        final String idHerbal =detailherbal.getId();
        herbalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailHerbal.class);
                i.putExtra("idHerbal", idHerbal);
                mCtx.startActivity(i);

            }
                });
    }






    @Override
    public int getItemCount() {
        return herbalModelList.size();
    }

    public void filterlist (ArrayList<herbalModel> filteredList)
    {
        herbalModelList =filteredList;
        notifyDataSetChanged();
    }


    class herbalViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal,khasiatHerbal;
        ImageView thumbnail;
        public herbalViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal);
            khasiatHerbal = itemView.findViewById(R.id.khasiat_herbal);
            thumbnail = itemView.findViewById(R.id.gambar_herbal);
        }
    }


}
