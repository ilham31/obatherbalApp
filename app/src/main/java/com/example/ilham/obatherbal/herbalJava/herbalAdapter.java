package com.example.ilham.obatherbal.herbalJava;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.OnLoadMoreListener;

import java.util.List;




public class herbalAdapter extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mCtx;
    List<herbalModel> herbalModelList;

    public herbalAdapter(RecyclerView recyclerView,Context mCtx, List<herbalModel> herbalModelList) {
        this.mCtx = mCtx;
        this.herbalModelList = herbalModelList;
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
        return herbalModelList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_herbal, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
           vh = new herbalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof herbalViewHolder) {

            herbalModel detailherbal= (herbalModel) herbalModelList.get(position);

            ((herbalViewHolder) holder).namaHerbal.setText(detailherbal.getNama());
            ((herbalViewHolder) holder).khasiatHerbal.setText(detailherbal.getKhasiat());
            Glide.with(mCtx)
                .load(mCtx.getString(R.string.url)+"/jamu/api/herbsmed/image/"+detailherbal.getThumbnail())
                    .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(((herbalViewHolder) holder).thumbnail);
            final String idHerbal =detailherbal.getId();
            ((herbalViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, detailHerbal.class);
                i.putExtra("idHerbal", idHerbal);
                mCtx.startActivity(i);

            }
        });



        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return herbalModelList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }




    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public static class herbalViewHolder extends RecyclerView.ViewHolder{
        TextView namaHerbal,khasiatHerbal;
        ImageView thumbnail;
        public herbalViewHolder(@NonNull View itemView) {
            super(itemView);
            namaHerbal = itemView.findViewById(R.id.nama_herbal);
            khasiatHerbal = itemView.findViewById(R.id.khasiat_herbal);
            thumbnail = itemView.findViewById(R.id.gambar_herbal);
        }
    }


}
