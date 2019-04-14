package com.example.ilham.obatherbal.databaseJava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
    CardView knowledge;
    String[] type;
    String selection;

    public database() {
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
        Glide.with(this)
                .load("https://image.freepik.com/free-photo/book-library-with-open-textbook_1150-5921.jpg")
                .into(imageView);
        knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new String[]{"Tacit","Explicit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose ");
                builder.setSingleChoiceItems(type, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = type[which];
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selection.equals("Tacit")){
                            Intent intent = new Intent(getActivity(),tacit.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getActivity(),explicit.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
        return rootview;
    }




}
