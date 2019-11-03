package com.example.ilham.obatherbal.knowledge;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.AboutKMSJamu;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.knowledge.explicit.explicit;
import com.example.ilham.obatherbal.knowledge.tacit.tacit;


/**
 * A simple {@link Fragment} subclass.
 */
public class knowldege extends Fragment {
    View rootview;
    ImageView imageView;
    CardView knowledge,aboutKMSJamu;
    String[] type;
    String selection;

    public knowldege() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_database, container, false);

        //knowledge card
        imageView = (ImageView)rootview.findViewById(R.id.thumbnailKnowledge);
        knowledge = (CardView) rootview.findViewById(R.id.knowledgeCard);
        aboutKMSJamu = rootview.findViewById(R.id.aboutKMSJamu);
        Glide.with(this)
                .load("https://image.freepik.com/free-photo/book-library-with-open-textbook_1150-5921.jpg")
                .into(imageView);
        knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), explicit.class);
                startActivity(intent);
            }
        });

        aboutKMSJamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AboutKMSJamu.class);
                startActivity(intent);
            }
        });
        return rootview;
    }




}
