package com.example.ilham.obatherbal.crudeJava;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;

import java.util.List;

public class detailCrudeAdapter extends RecyclerView.Adapter<detailCrudeAdapter.crudeDrugDetailViewHolder> {

    private Context mCtx;
    List<detailCrudeModel> detailCrudeModels;

    public detailCrudeAdapter(Context mCtx, List<detailCrudeModel> detailCrudeModels) {
        this.mCtx = mCtx;
        this.detailCrudeModels = detailCrudeModels;
    }

    @NonNull
    @Override
    public crudeDrugDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cadview_detailplant, parent,false);
        crudeDrugDetailViewHolder holder = new crudeDrugDetailViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull crudeDrugDetailViewHolder crudeDrugDetailViewHolder, int i) {
        detailCrudeModel detailCrudeModel = detailCrudeModels.get(i);
        crudeDrugDetailViewHolder.sname.setText(detailCrudeModel.getSname());
        crudeDrugDetailViewHolder.name_eng.setText(detailCrudeModel.getName_en() + " / ");
        crudeDrugDetailViewHolder.name_loc.setText(detailCrudeModel.getName_loc());
        crudeDrugDetailViewHolder.gname.setText("gname : "+detailCrudeModel.getGnameCrude());
        crudeDrugDetailViewHolder.position.setText("Position : " + detailCrudeModel.getPositionCrude());
        crudeDrugDetailViewHolder.effect.setText("Effect : "+detailCrudeModel.getEffect());
        crudeDrugDetailViewHolder.refCrude.setText("Reference : "+detailCrudeModel.getRef());
    }


    @Override
    public int getItemCount() {
        return detailCrudeModels.size();
    }

    class crudeDrugDetailViewHolder extends RecyclerView.ViewHolder{

        TextView sname,name_eng,name_loc,gname,position,effect,refCrude;
        public crudeDrugDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            sname = itemView.findViewById(R.id.namaCrude);
            name_eng = itemView.findViewById(R.id.namaLatin);
            name_loc = itemView.findViewById(R.id.namaLocal);
            gname = itemView.findViewById(R.id.gName);
            position = itemView.findViewById(R.id.positionCrude);
            effect = itemView.findViewById(R.id.effectCrude);
            refCrude = itemView.findViewById(R.id.refCrude);
        }
    }
}
