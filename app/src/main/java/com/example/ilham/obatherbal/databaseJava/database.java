package com.example.ilham.obatherbal.databaseJava;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class database extends Fragment {
    View rootview;
    ImageView imageView;


    public database() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_database, container, false);
        imageView = (ImageView)rootview.findViewById(R.id.thumbnailKnowledge);
        Glide.with(this)
                .load("https://image.freepik.com/free-photo/book-library-with-open-textbook_1150-5921.jpg")
                .into(imageView);
        return rootview;
    }




}
