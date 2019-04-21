package com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.ItemClickListener;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.herbsAdapter;

import java.util.ArrayList;
import java.util.List;

public class compoundAdapterPredict extends RecyclerView.Adapter<compoundAdapterPredict.compoundPredictViewHolder> {
    private Context mCtx;
    List<compoundPredictModel> compoundModelList;
    List<compoundPredictModel> checkedCompound = new ArrayList<>();
    private int checked=0;

    public compoundAdapterPredict(Context mCtx, List<compoundPredictModel> compoundModelList) {
        this.mCtx = mCtx;
        this.compoundModelList = compoundModelList;
    }

    @NonNull
    @Override
    public compoundPredictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_predictherb, parent,false);
        compoundPredictViewHolder holder = new compoundPredictViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final compoundPredictViewHolder compoundPredictViewHolder, int i) {
        final compoundPredictModel detailCompound = compoundModelList.get(i);
        compoundPredictViewHolder.name.setText(detailCompound.getNameCompound());
        compoundPredictViewHolder.checkBox.setChecked(false);
        if (checkedCompound.size() > 0)
        {
            for (compoundPredictModel h : checkedCompound)
            {
                Log.d("adapter","id = "+h.getIdCompound());
                if(h.getIdData().contains(detailCompound.getIdData())){
                    compoundPredictViewHolder.checkBox.setChecked(true);
                }

            }
        }
        compoundPredictViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox checkBox = (CheckBox) v;
                if(checkBox.isChecked()&&checked==10)
                {
                    compoundPredictViewHolder.checkBox.setChecked(false);
                }
                else
                {
                    if (checkBox.isChecked())
                    {
                        checkedCompound.add(compoundModelList.get(pos));
                        checked++;

                    }
                    else
                    {
                        checkedCompound.remove(compoundModelList.get(pos));
                        checked--;
                    }
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return compoundModelList.size();
    }

    public void filterlist (ArrayList<compoundPredictModel> filteredList)
    {
        compoundModelList =filteredList;
        notifyDataSetChanged();

    }

    class compoundPredictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        CheckBox checkBox;
        ItemClickListener itemClickListener;

        public compoundPredictViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.herbsPredictName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkHerbsPredict);
            checkBox.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }

    }
