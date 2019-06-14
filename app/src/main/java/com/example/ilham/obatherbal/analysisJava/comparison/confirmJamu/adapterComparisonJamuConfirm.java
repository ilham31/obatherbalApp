package com.example.ilham.obatherbal.analysisJava.comparison.confirmJamu;

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

import com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu.jamuModelComparison;
import java.util.List;

public class adapterComparisonJamuConfirm extends RecyclerView.Adapter<adapterComparisonJamuConfirm.adapterComparisonJamuConfirmViewHolder>{

    private Context mCtx;
    List<jamuModelComparison> jamuModelComparisonList;

    public adapterComparisonJamuConfirm(Context mCtx, List<jamuModelComparison> jamuModelComparisonList) {
        this.mCtx = mCtx;
        this.jamuModelComparisonList = jamuModelComparisonList;
    }

    @NonNull
    @Override
    public adapterComparisonJamuConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_confirm, parent,false);
        adapterComparisonJamuConfirmViewHolder holder = new adapterComparisonJamuConfirmViewHolder(v);
        return holder;
        }

    @Override
    public void onBindViewHolder(@NonNull adapterComparisonJamuConfirmViewHolder adapterComparisonJamuConfirmViewHolder, final int i) {
        jamuModelComparison detailjamu = jamuModelComparisonList.get(i);
        adapterComparisonJamuConfirmViewHolder.plantName.setText(detailjamu.getNama());
        adapterComparisonJamuConfirmViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jamuModelComparisonList.size();
    }

    public void removeAt(int position) {
        jamuModelComparisonList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, jamuModelComparisonList.size());
    }

    class adapterComparisonJamuConfirmViewHolder extends RecyclerView.ViewHolder{

        TextView plantName;
        Button remove;
        public adapterComparisonJamuConfirmViewHolder(@NonNull View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.confirmHerbsPredictName);
            remove = (Button) itemView.findViewById(R.id.removePredictHerb);
        }
    }
}
