package com.example.ilham.obatherbal.compoundJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ilham.obatherbal.R;


public class detailCompound extends AppCompatActivity {
    ImageView detailCompoundPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_compound);
        detailCompoundPic = (ImageView) findViewById(R.id.detailCompoundPic);
        String url= "https://pubchem.ncbi.nlm.nih.gov/image/imagefly.cgi?cid=14259051&width=500&height=500";
        Glide.with(this)
                .load(url)
                .into(detailCompoundPic);
    }
}
