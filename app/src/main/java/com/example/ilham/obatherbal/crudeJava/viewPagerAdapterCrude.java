package com.example.ilham.obatherbal.crudeJava;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;

import java.util.List;

public class viewPagerAdapterCrude extends PagerAdapter {
    Activity activity;
    List <String> imageDetailCrude;
    LayoutInflater inflater;

    public viewPagerAdapterCrude(Activity activity, List<String> imageDetailCrude) {
        this.activity = activity;
        this.imageDetailCrude = imageDetailCrude;
    }

    @Override
    public int getCount() {
        return imageDetailCrude.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpage_item_detailcrude,container,false);

        ImageView images = (ImageView) itemView.findViewById(R.id.detailImageCrude);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        images.setMinimumHeight(height);
        images.setMinimumWidth(width);
        Glide.with(activity.getApplicationContext())
                .load(imageDetailCrude.get(position))
                .into(images);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View) object);
    }
}
