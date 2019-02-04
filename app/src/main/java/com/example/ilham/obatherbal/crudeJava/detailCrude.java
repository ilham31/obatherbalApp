package com.example.ilham.obatherbal.crudeJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;


public class detailCrude extends AppCompatActivity {
    ImageView gambarDetailCrude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crude);
        gambarDetailCrude = (ImageView) findViewById(R.id.detailCrudePic);
        String url = "https://images.wisegeek.com/chinese-herbs.jpg";
        Glide.with(this)
                .load(url)
                .into(gambarDetailCrude);

    }
}
