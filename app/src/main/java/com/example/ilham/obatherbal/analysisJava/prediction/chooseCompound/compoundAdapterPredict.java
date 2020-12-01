package com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class compoundAdapterPredict extends RecyclerView.Adapter<compoundAdapterPredict.compoundPredictViewHolder> {
    private Context mCtx;
    List<compoundPredictModel> compoundModelList;
    List<compoundPredictModel> checkedCompound = new ArrayList<>();
    private int checked=0;
    //konstruktor untuk adapter
    public compoundAdapterPredict(Context mCtx, List<compoundPredictModel> compoundModelList) {
        this.mCtx = mCtx;
        this.compoundModelList = compoundModelList;
    }
    //deklarasi viewholder untuk recyclerview
    @NonNull
    @Override
    public compoundPredictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_predictherb, parent,false);
        compoundPredictViewHolder holder = new compoundPredictViewHolder(v);
        return holder;
    }
    //deklarasi isi untuk viewholder
    @Override
    public void onBindViewHolder(@NonNull final compoundPredictViewHolder compoundPredictViewHolder, int i) {
        final compoundPredictModel detailCompound = compoundModelList.get(i);
        compoundPredictViewHolder.name.setText(detailCompound.getNameCompound());
        //semua checkbox di set false
        compoundPredictViewHolder.checkBox.setChecked(false);
        //kalau arraylist terdapat isi
        if (checkedCompound.size() > 0)
        {
            //menmerikasi array list dengan model JSON
            for (compoundPredictModel h : checkedCompound)
            {
                //jika id array list ada yang sama dengan data yang diambil dari server maka checkbox di set true
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
                //kalau checkbox sudah di ceklis sebanyak 10
                if(checkBox.isChecked()&&checked==10)
                {
                    compoundPredictViewHolder.checkBox.setChecked(false);
                    Toast.makeText(mCtx,"Maximum compounds are 10",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //jika tidak dan belum di centang
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

    //untuk menghitung jumlah array list
    @Override
    public int getItemCount() {
        return compoundModelList.size();
    }

    //filter recyclerview
    public void filterlist (ArrayList<compoundPredictModel> filteredList)
    {
        compoundModelList =filteredList;
        notifyDataSetChanged();

    }

    //deklarasi komponen komponen untuk recyclerview
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
