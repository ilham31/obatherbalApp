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
import com.example.ilham.obatherbal.crudeJava.crudeModel;
import com.example.ilham.obatherbal.crudeJava.detailCrude;
import java.util.ArrayList;
import java.util.List;

public class adapterSearchPlant extends RecyclerView.Adapter<adapterSearchPlant.plantSearchViewHolder> {
    private Context mCtx;
    List<crudeModel> crudeModelList;

    public adapterSearchPlant(Context mCtx, List<crudeModel> crudeModelList) {
        this.mCtx = mCtx;
        this.crudeModelList = crudeModelList;
    }

    @NonNull
    @Override
    public plantSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchlayoutplant, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
        plantSearchViewHolder holder = new plantSearchViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull plantSearchViewHolder plantSearchViewHolder, int i) {
        crudeModel detailPlant = crudeModelList.get(i);
        plantSearchViewHolder.namaPlant.setText(detailPlant.getNama());
        final String idPlant = detailPlant.getId();
        plantSearchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailCrude.class);
                i.putExtra("idPlant", idPlant);
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

    class plantSearchViewHolder extends RecyclerView.ViewHolder{
        TextView namaPlant;
        public plantSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPlant = (TextView) itemView.findViewById(R.id.nama_plant_search);
        }
    }
}
