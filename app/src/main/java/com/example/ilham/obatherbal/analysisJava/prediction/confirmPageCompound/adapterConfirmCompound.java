package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound.compoundPredictModel;

import java.util.List;

public class adapterConfirmCompound extends RecyclerView.Adapter<adapterConfirmCompound.adapterConfirmCompoundViewHolder> {
    private Context mCtx;
    List<compoundPredictModel> compoundPredictModels;

    public adapterConfirmCompound(Context mCtx, List<compoundPredictModel> compoundPredictModels) {
        this.mCtx = mCtx;
        this.compoundPredictModels = compoundPredictModels;
    }

    @NonNull
    @Override
    public adapterConfirmCompoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_confirm, parent,false);
        adapterConfirmCompoundViewHolder holder = new adapterConfirmCompoundViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterConfirmCompoundViewHolder adapterConfirmCompoundViewHolder, final int i) {
        compoundPredictModel detailCompound = compoundPredictModels.get(i);
        adapterConfirmCompoundViewHolder.plantName.setText(detailCompound.getNameCompound());
        adapterConfirmCompoundViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return compoundPredictModels.size();
    }

    public void removeAt(int position) {
        compoundPredictModels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, compoundPredictModels.size());
    }

    class adapterConfirmCompoundViewHolder extends RecyclerView.ViewHolder{

        TextView plantName;
        Button remove;
        public adapterConfirmCompoundViewHolder(@NonNull View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.confirmHerbsPredictName);
            remove = (Button) itemView.findViewById(R.id.removePredictHerb);
        }
    }
}
