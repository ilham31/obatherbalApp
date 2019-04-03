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
import com.example.ilham.obatherbal.analysisJava.ethnics.MapsActivity;
import java.util.ArrayList;
import java.util.List;

public class adapterEthnic extends RecyclerView.Adapter<adapterEthnic.ethnicViewHolder> {

    private Context mCtx;
    List<ethnicModel> ethnicModelList;

    public adapterEthnic(Context mCtx, List<ethnicModel> ethnicModelList) {
        this.mCtx = mCtx;
        this.ethnicModelList = ethnicModelList;
    }

    @NonNull
    @Override
    public ethnicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_ethnic, parent,false);
        ethnicViewHolder holder = new ethnicViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ethnicViewHolder ethnicViewHolder, int i) {
        ethnicModel detailEthnic = ethnicModelList.get(i);
        ethnicViewHolder.ethnicName.setText(detailEthnic.getEthnicName());
        ethnicViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ethnic = new Intent(mCtx,MapsActivity.class);
                ethnic.putExtra("daerah","Sumatra utara");
                mCtx.startActivity(ethnic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ethnicModelList.size();
    }

    public void filterlist (ArrayList<ethnicModel> filteredList)
    {
        ethnicModelList =filteredList;
        notifyDataSetChanged();
    }

    class ethnicViewHolder extends RecyclerView.ViewHolder{
        TextView ethnicName;
        public ethnicViewHolder(@NonNull View itemView) {
            super(itemView);
            ethnicName = (TextView) itemView.findViewById(R.id.ethnicName);
        }
    }
}
