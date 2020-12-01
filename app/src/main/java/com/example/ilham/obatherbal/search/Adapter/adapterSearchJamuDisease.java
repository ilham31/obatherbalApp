package com.example.ilham.obatherbal.search.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.detailHerbal;
import com.example.ilham.obatherbal.herbalJava.herbalModel;

import java.util.ArrayList;
import java.util.List;

public class adapterSearchJamuDisease extends RecyclerView.Adapter<adapterSearchJamuDisease.herbalViewHolderDisease>{

    private Context mCtx;
    List<herbalModel> herbalModelListDisease;

    public adapterSearchJamuDisease(Context mCtx, List<herbalModel> herbalModelListDisease) {
        this.mCtx = mCtx;
        this.herbalModelListDisease = herbalModelListDisease;
    }

    @NonNull
    @Override
    public herbalViewHolderDisease onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutherbal, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
        herbalViewHolderDisease holder = new herbalViewHolderDisease(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull herbalViewHolderDisease herbalViewHolderDisease, int i) {
        herbalModel detailherbal =herbalModelListDisease.get(i);
        herbalViewHolderDisease.khasiatHerbal.setText(detailherbal.getKhasiat());
        final String idHerbal = detailherbal.getId();
        herbalViewHolderDisease.itemView.setOnClickListener(new View.OnClickListener() {
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
        return herbalModelListDisease.size();
    }

    public void filterlist (ArrayList<herbalModel> filteredList)
    {
        herbalModelListDisease =filteredList;
        notifyDataSetChanged();
    }

    class herbalViewHolderDisease extends RecyclerView.ViewHolder{
        TextView khasiatHerbal;
        public herbalViewHolderDisease(@NonNull View itemView) {
            super(itemView);
            khasiatHerbal = itemView.findViewById(R.id.nama_herbal_search);
        }
    }
}
