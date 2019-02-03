package com.example.ilham.obatherbal.herbalJava;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilham.obatherbal.R;
import com.squareup.picasso.Picasso;

public class detailHerbal extends AppCompatActivity {
    ImageView gambarDetailHerbal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herbal);
        gambarDetailHerbal = (ImageView) findViewById(R.id.detailHerbalPic);
        String url = "https://cdn.images.express.co.uk/img/dynamic/11/590x/herbal-medicines-remedies-lavender-allergy-tea-oil-health-824416.jpg";
        Picasso.get().load(url).into(gambarDetailHerbal);
        Picasso.get().setLoggingEnabled(true);
    }


}
