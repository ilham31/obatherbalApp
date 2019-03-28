package com.example.ilham.obatherbal.herbalJava;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herbal);
        String idHerbal = getIntent().getStringExtra("idHerbal");
        tabLayout = (TabLayout) findViewById(R.id.tablayoutdetailJamu);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbaridDetailJamu);
        viewPager = (ViewPager) findViewById(R.id.viewPagerDetailJamu);
        gambarDetailHerbal = (ImageView) findViewById(R.id.detailHerbalPic);
        viewPagerDetailHerbalAdapter adapter = new viewPagerDetailHerbalAdapter(getSupportFragmentManager());

        //adding Fragment

        tabDiseaseHerbal diseaseHerbal = new tabDiseaseHerbal();
        tabCrudeHerbal crudeHerbal = new tabCrudeHerbal();
        tabPlantHerbal plantHerbal = new tabPlantHerbal();
        Bundle bundle = new Bundle();
        bundle.putString("idHerbal",idHerbal);

        diseaseHerbal.setArguments(bundle);
        crudeHerbal.setArguments(bundle);
        plantHerbal.setArguments(bundle);

        adapter.addFragment(diseaseHerbal,"Detail");
        adapter.addFragment(crudeHerbal,"Crude");
        adapter.addFragment(plantHerbal,"Plant");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        String url = "https://cdn.images.express.co.uk/img/dynamic/11/590x/herbal-medicines-remedies-lavender-allergy-tea-oil-health-824416.jpg";
        Glide.with(this)
                .load(url)
                .into(gambarDetailHerbal);

//        reference = (TextView) findViewById(R.id.reference);
//        reference.setText(urlRef);
//        Linkify.addLinks(reference,Linkify.WEB_URLS);
    }


}
