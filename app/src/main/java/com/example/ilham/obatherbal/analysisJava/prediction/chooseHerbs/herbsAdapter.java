package com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.obatherbal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





public class herbsAdapter extends RecyclerView.Adapter<herbsAdapter.herbsPredictViewHolder> {
    private Context mCtx;
    List<herbsModel> herbsModelList;
    List<herbsModel> checkedHerbs = new ArrayList<>();
    private int checked=0;



    public herbsAdapter(Context mCtx, List<herbsModel> herbsModelList) {
        this.mCtx = mCtx;
        this.herbsModelList = herbsModelList;
    }

    @NonNull
    @Override
    public herbsPredictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_predictherb, parent,false);
        herbsPredictViewHolder holder = new herbsPredictViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final herbsPredictViewHolder herbsPredictViewHolder, int i) {
        final herbsModel detailherbs = herbsModelList.get(i);
        herbsPredictViewHolder.name.setText(detailherbs.getNameHerbs());
        herbsPredictViewHolder.checkBox.setChecked(false);
        if(checkedHerbs.size() > 0)
        {
            for (herbsModel h : checkedHerbs)
            {
                Log.d("adapter","id = "+h.getIdHerbs());
                if(h.getIdHerbs().contains(detailherbs.getIdHerbs())){
                    herbsPredictViewHolder.checkBox.setChecked(true);
                }

            }

        }
        herbsPredictViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    CheckBox checkBox = (CheckBox) v;
                    if(checkBox.isChecked()&&checked==10)
                    {
                        herbsPredictViewHolder.checkBox.setChecked(false);
                    }
                    else
                    {
                        if (checkBox.isChecked())
                        {
                            checkedHerbs.add(herbsModelList.get(pos));
                            checked++;

                        }
                        else
                        {
                            checkedHerbs.remove(herbsModelList.get(pos));
                            checked--;
                        }
                    }


                }
            });

        }



    @Override
    public int getItemCount() {
        return herbsModelList.size();
    }

    public void filterlist (ArrayList<herbsModel> filteredList)
    {
        herbsModelList =filteredList;
        notifyDataSetChanged();

    }

    class herbsPredictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        CheckBox checkBox;
        ItemClickListener itemClickListener;

        public herbsPredictViewHolder(@NonNull View itemView) {
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
