package com.example.ilham.obatherbal.compoundJava;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class compoundAdapter extends RecyclerView.Adapter<compoundAdapter.compoundViewHolder> {

    private Context mCtx;
    List<compoundModel> compoundModels;

    public compoundAdapter(Context mCtx, List<compoundModel> compoundModels) {
        this.mCtx = mCtx;
        this.compoundModels = compoundModels;
    }

    @NonNull
    @Override
    public compoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_compound, parent,false);
        compoundViewHolder holder = new compoundViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull compoundViewHolder compoundViewHolder, int i) {
        compoundModel detailCompound = compoundModels.get(i);
        compoundViewHolder.namaCompound.setText(detailCompound.getNama());
        compoundViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailCompound.class);
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return compoundModels.size();
    }

    public void filterlist (ArrayList<compoundModel> filteredList)
    {
        compoundModels =filteredList;
        notifyDataSetChanged();
    }

    class compoundViewHolder extends RecyclerView.ViewHolder{
        TextView namaCompound;
        public compoundViewHolder(@NonNull View itemView) {
            super(itemView);
            namaCompound=(TextView) itemView.findViewById(R.id.nama_compound);
        }
    }
}
