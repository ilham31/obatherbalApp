package com.example.ilham.obatherbal.herbalJava;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;

import java.util.ArrayList;
import java.util.List;

public class kampoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mCtx;
    List<kampoModel> kampoModelList;

    public kampoAdapter(RecyclerView recyclerView,Context mCtx, List<kampoModel> kampoModelList) {
        this.mCtx = mCtx;
        this.kampoModelList = kampoModelList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return kampoModelList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerView.ViewHolder vh;
        if (i == VIEW_ITEM) {
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_herbal, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
            vh = new kampoAdapter.kampoViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new kampoAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  kampoViewHolder)
        {
            kampoModel detailkampo = (kampoModel) kampoModelList.get(i);
            ((kampoViewHolder) holder).namaHerbalKampo.setText(detailkampo.getNamaKampo());
            ((kampoViewHolder) holder).khasiatHerbalKampo.setText(detailkampo.getKhasiatKampo());
            Glide.with(mCtx)
                .load(detailkampo.getThumbnailKampo())
                .into(((kampoViewHolder) holder).thumbnailKampo);
            final String idHerbal =detailkampo.getIdKampo();
            ((kampoAdapter.kampoViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, detailHerbal.class);
                    i.putExtra("idHerbal", idHerbal);
                    mCtx.startActivity(i);

                }
            });


        }
        else {
            ((kampoAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return kampoModelList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void filterlist(ArrayList<kampoModel> filteredList) {
        kampoModelList =filteredList;
        notifyDataSetChanged();
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public static class kampoViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbalKampo,khasiatHerbalKampo;
        ImageView thumbnailKampo;
        public kampoViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbalKampo = itemView.findViewById(R.id.nama_herbal);
            khasiatHerbalKampo = itemView.findViewById(R.id.khasiat_herbal);
            thumbnailKampo = itemView.findViewById(R.id.gambar_herbal);
        }
    }

}
