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

public class adapterSearchJamu extends RecyclerView.Adapter<adapterSearchJamu.herbalViewHolder>{

    private Context mCtx;
    List<herbalModel> herbalModelList;

    public adapterSearchJamu(Context mCtx, List<herbalModel> herbalModelList) {
        this.mCtx = mCtx;
        this.herbalModelList = herbalModelList;
    }

    @NonNull
    @Override
    public herbalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutherbal, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
        herbalViewHolder holder = new herbalViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull herbalViewHolder herbalViewHolder, int i) {
        herbalModel detailherbal =herbalModelList.get(i);

        herbalViewHolder.namaHerbal.setText(detailherbal.getNama());
        final String idHerbal = detailherbal.getId();
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
        public herbalViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal_search);
           }
    }
}
