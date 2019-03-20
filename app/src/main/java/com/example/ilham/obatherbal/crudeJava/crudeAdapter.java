package com.example.ilham.obatherbal.crudeJava;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.herbalAdapter;
import java.util.List;

public class crudeAdapter extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mCtx;
    List<crudeModel> crudeModelList;

    public crudeAdapter(RecyclerView recyclerView, Context mCtx, List<crudeModel> crudeModelList) {
        this.mCtx = mCtx;
        this.crudeModelList = crudeModelList;
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
        return crudeModelList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            LayoutInflater inflater =LayoutInflater.from(mCtx);
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_crude, parent,false);
            vh = new crudeViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof crudeViewHolder) {

            crudeModel detailCrude= (crudeModel) crudeModelList.get(position);

            ((crudeViewHolder) holder).namaCrude.setText(detailCrude.getNama());
            Glide.with(mCtx)
                    .load(detailCrude.getRefimgplant())
                    .apply(new RequestOptions().error(R.drawable.imageplaceholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(((crudeViewHolder)holder).crudePic);
            final String idPlant = detailCrude.getId();
            ((crudeViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, detailCrude.class);
                    i.putExtra("idPlant",idPlant);
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
        return crudeModelList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class crudeViewHolder extends RecyclerView.ViewHolder{

        TextView namaCrude;
        ImageView crudePic;
        public crudeViewHolder(@NonNull View itemView) {
            super(itemView);
            namaCrude=(TextView)itemView.findViewById(R.id.nama_crude);
            crudePic = (ImageView) itemView.findViewById(R.id.gambar_crude);
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
