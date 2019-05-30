package com.example.ilham.obatherbal.knowledge.tacit;

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

public class adapterTacitSearch extends  RecyclerView.Adapter<adapterTacitSearch.tacitViewHolder>{
    private Context mCtx;
    List<tacitModelSearch>tacitModelSearches;

    public adapterTacitSearch(Context mCtx, List<tacitModelSearch> tacitModelSearches) {
        this.mCtx = mCtx;
        this.tacitModelSearches = tacitModelSearches;
    }

    @NonNull
    @Override
    public tacitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutherbal, parent,false);
        tacitViewHolder holder = new tacitViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull tacitViewHolder tacitViewHolder, int i) {
        final tacitModelSearch detailTacit = (tacitModelSearch) tacitModelSearches.get(i);
        tacitViewHolder.namaHerbal.setText(detailTacit.getTitle());
        tacitViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailTacit.class);
                i.putExtra("idTacit", detailTacit.get_id());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tacitModelSearches.size();
    }
    public void filterlist (ArrayList<tacitModelSearch> filteredList)
    {
        tacitModelSearches =filteredList;
        notifyDataSetChanged();
    }

    class tacitViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal;
        public tacitViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal_search);
        }
    }
}
