package com.example.ilham.obatherbal.search.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
                .inflate(R.layout.searchlayoutherbal, parent,false);
        kampoViewHolder holder = new kampoViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull kampoViewHolder kampoViewHolder, int i) {
        kampoModel detailkampo = (kampoModel) kampoModelList.get(i);
        kampoViewHolder.namaHerbal.setText(detailkampo.getNamaKampo());

        final String idHerbal =detailkampo.getIdKampo();
        kampoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return kampoModelList.size();
    }

    public void filterlist (ArrayList<kampoModel> filteredList)
    {
        kampoModelList =filteredList;
        notifyDataSetChanged();
    }

    class kampoViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal;
        ImageView thumbnailKampo;
        public kampoViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal_search);
        }
    }
}
