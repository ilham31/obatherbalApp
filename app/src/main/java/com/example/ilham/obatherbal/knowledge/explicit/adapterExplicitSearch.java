package com.example.ilham.obatherbal.knowledge.explicit;

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

public class adapterExplicitSearch extends RecyclerView.Adapter<adapterExplicitSearch.explicitViewHolder> {
    private Context mCtx;
    List<explicitModelSearch> explicitModelSearches;

    public adapterExplicitSearch(Context mCtx, List<explicitModelSearch> explicitModelSearches) {
        this.mCtx = mCtx;
        this.explicitModelSearches = explicitModelSearches;
    }

    @NonNull
    @Override
    public explicitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutherbal, parent,false);
        explicitViewHolder holder = new explicitViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull explicitViewHolder explicitViewHolder, int i) {
        final explicitModelSearch detailExplicit = (explicitModelSearch) explicitModelSearches.get(i);
        explicitViewHolder.namaHerbal.setText(detailExplicit.getTitle());
        explicitViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailExplicit.class);
                i.putExtra("idExplicit", detailExplicit.get_id());
                mCtx.startActivity(i);
            }
        });
    }

    public void filterlist (ArrayList<explicitModelSearch> filteredList)
    {
        explicitModelSearches =filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return explicitModelSearches.size();
    }

    class explicitViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal;
        public explicitViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal_search);
        }
    }
}
