package com.example.ilham.obatherbal.herbalJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;


public class detailHerbal extends AppCompatActivity {
    ImageView gambarDetailHerbal;
    TextView reference;
    String urlRef = "www.google.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herbal);
        gambarDetailHerbal = (ImageView) findViewById(R.id.detailHerbalPic);
        String url = "https://cdn.images.express.co.uk/img/dynamic/11/590x/herbal-medicines-remedies-lavender-allergy-tea-oil-health-824416.jpg";
        Glide.with(this)
                .load(url)
                .into(gambarDetailHerbal);

        reference = (TextView) findViewById(R.id.reference);
        reference.setText(urlRef);
        Linkify.addLinks(reference,Linkify.WEB_URLS);
    }


}
