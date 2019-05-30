package com.example.ilham.obatherbal.knowledge.explicit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;

import java.util.List;

public class explicitAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mCtx;
    List<explicitModel> explicitModelsList;

    public explicitAdapter(RecyclerView recyclerView,Context mCtx, List<explicitModel> explicitModelsList) {
        this.mCtx = mCtx;
        this.explicitModelsList = explicitModelsList;
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
        return explicitModelsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerView.ViewHolder vh;
        if (i == VIEW_ITEM) {
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_displayexplicit, parent,false);
//        View view =inflater.inflate(R.layout.cardview_herbal,null);
            vh = new exxplicitViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof exxplicitViewHolder) {

            explicitModel detailExplicit= (explicitModel) explicitModelsList.get(position);

            ((exxplicitViewHolder) holder).title.setText(detailExplicit.getTitle());
            ((exxplicitViewHolder) holder).uploader.setText("By :"+detailExplicit.getFirstname()+" "+detailExplicit.getLastname());
            final String idExplicit =detailExplicit.get_id();
            ((exxplicitViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, detailExplicit.class);
                    i.putExtra("idExplicit", idExplicit);
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
        return explicitModelsList.size();
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

    public static class exxplicitViewHolder extends RecyclerView.ViewHolder{

        TextView title,uploader;
        public exxplicitViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleExplicit);
            uploader = itemView.findViewById(R.id.uploaderExplicit);
        }
    }
}
