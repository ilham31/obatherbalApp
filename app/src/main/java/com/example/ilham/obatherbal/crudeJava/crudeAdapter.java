package com.example.ilham.obatherbal.crudeJava;

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
import com.example.ilham.obatherbal.herbalJava.herbalModel;

import java.util.ArrayList;
import java.util.List;

public class crudeAdapter extends RecyclerView.Adapter<crudeAdapter.crudeViewHolder> {
    private Context mCtx;
    List<crudeModel> crudeModelList;

    public crudeAdapter(Context mCtx, List<crudeModel> crudeModelList) {
        this.mCtx = mCtx;
        this.crudeModelList = crudeModelList;
    }

    @NonNull
    @Override
    public crudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_crude, parent,false);
        crudeViewHolder holder= new crudeViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull crudeViewHolder crudeViewHolder, int i) {
        crudeModel detailCrude = crudeModelList.get(i);
        crudeViewHolder.namaCrude.setText(detailCrude.getNama());
        crudeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailCrude.class);
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return crudeModelList.size();
    }

    public void filterlist (ArrayList<crudeModel> filteredList)
    {
        crudeModelList =filteredList;
        notifyDataSetChanged();
    }

    class crudeViewHolder extends RecyclerView.ViewHolder{

        TextView namaCrude;
        public crudeViewHolder(@NonNull View itemView) {
            super(itemView);
            namaCrude=(TextView)itemView.findViewById(R.id.nama_crude);
        }
    }
}
