package com.example.ilham.obatherbal.crudeJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.ilham.obatherbal.R;
import com.squareup.picasso.Picasso;

public class detailCrude extends AppCompatActivity {
    ImageView gambarDetailCrude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crude);
        gambarDetailCrude = (ImageView) findViewById(R.id.detailCrudePic);
        String url = "https://images.wisegeek.com/chinese-herbs.jpg";
        Picasso.get().load(url).into(gambarDetailCrude);
        Picasso.get().setLoggingEnabled(true);

    }
}
