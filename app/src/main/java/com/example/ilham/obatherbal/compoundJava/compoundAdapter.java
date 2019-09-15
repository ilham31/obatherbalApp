package com.example.ilham.obatherbal.compoundJava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.crudeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class compoundAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mCtx;
    List<compoundModel> compoundModels;

    public compoundAdapter(RecyclerView recyclerView, Context mCtx, List<compoundModel> compoundModels) {
        this.mCtx = mCtx;
        this.compoundModels = compoundModels;
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
        return compoundModels.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            LayoutInflater inflater =LayoutInflater.from(mCtx);
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_compound, parent,false);
            vh = new compoundViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  compoundViewHolder) {
            compoundModel detailCompound= (compoundModel) compoundModels.get(i);

            ((compoundViewHolder) holder).namaCompound.setText(detailCompound.getNama());
            Glide.with(mCtx)
                    .load("https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/" + detailCompound.getNama() + "/PNG")
                    .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(((compoundViewHolder) holder).compoundPic);
            final String idCompound = detailCompound.getId();
            ((compoundViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, detailCompound.class);
                    i.putExtra("idCompound",idCompound);
                    mCtx.startActivity(i);
                }
            });
        } else {
            ((compoundAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return compoundModels.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void filterlist (ArrayList<compoundModel> filteredList)
    {
        compoundModels =filteredList;
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
    }

    public static class compoundViewHolder extends RecyclerView.ViewHolder{
        TextView namaCompound;
        ImageView compoundPic;
        public compoundViewHolder(@NonNull View itemView) {
            super(itemView);
            namaCompound=(TextView) itemView.findViewById(R.id.nama_compound);
            compoundPic = (ImageView) itemView.findViewById(R.id.gambar_compound);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
