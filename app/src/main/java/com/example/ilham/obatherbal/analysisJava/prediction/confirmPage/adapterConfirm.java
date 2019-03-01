package com.example.ilham.obatherbal.analysisJava.prediction.confirmPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsModel;

import java.util.List;


public class adapterConfirm extends RecyclerView.Adapter<adapterConfirm.adapterConfirmViewHolder>{
private Context mCtx;
List<herbsModel> herbsModelList;

    public adapterConfirm(Context mCtx, List<herbsModel> herbsModelList) {
        this.mCtx = mCtx;
        this.herbsModelList = herbsModelList;
    }

    @NonNull
    @Override
    public adapterConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_confirm, parent,false);
        adapterConfirmViewHolder holder = new adapterConfirmViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterConfirmViewHolder adapterConfirmViewHolder, final int i) {
        herbsModel detailherb = herbsModelList.get(i);
        adapterConfirmViewHolder.plantName.setText(detailherb.getNameHerbs());
        adapterConfirmViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(i);
            }
        });

    }
    public void removeAt(int position) {
        herbsModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, herbsModelList.size());
    }
    @Override
    public int getItemCount() {
        return herbsModelList.size();
    }


    class adapterConfirmViewHolder extends RecyclerView.ViewHolder{

        TextView plantName;
        Button remove;
        public adapterConfirmViewHolder(@NonNull View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.confirmHerbsPredictName);
            remove = (Button) itemView.findViewById(R.id.removePredictHerb);
        }
    }
}

