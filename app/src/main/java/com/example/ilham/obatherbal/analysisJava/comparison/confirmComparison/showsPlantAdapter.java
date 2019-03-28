package com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.crudeAdapter;
import com.example.ilham.obatherbal.crudeJava.crudeModel;
import com.example.ilham.obatherbal.crudeJava.detailCrude;

import java.util.List;

public class showsPlantAdapter extends RecyclerView.Adapter<showsPlantAdapter.crudeViewHolderComparison>{
    List<crudeModel> crudeModelList;
    private Context mCtx;

    public showsPlantAdapter(List<crudeModel> crudeModelList, Context mCtx) {
        this.crudeModelList = crudeModelList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public crudeViewHolderComparison onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_crude, parent,false);
        crudeViewHolderComparison vh = new crudeViewHolderComparison(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull crudeViewHolderComparison crudeViewHolderComparison, int i) {
        crudeModel detailCrude= (crudeModel) crudeModelList.get(i);

        crudeViewHolderComparison.namaCrude.setText(detailCrude.getNama());
        Glide.with(mCtx)
                .load(detailCrude.getRefimgplant())
                .apply(new RequestOptions().error(R.drawable.imageplaceholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(crudeViewHolderComparison.crudePic);
    }

    @Override
    public int getItemCount() {
        return crudeModelList.size();
    }

    public static class crudeViewHolderComparison extends RecyclerView.ViewHolder{

        TextView namaCrude;
        ImageView crudePic;
        public crudeViewHolderComparison(@NonNull View itemView) {
            super(itemView);
            namaCrude=(TextView)itemView.findViewById(R.id.nama_crude);
            crudePic = (ImageView) itemView.findViewById(R.id.gambar_crude);
        }
    }
}
