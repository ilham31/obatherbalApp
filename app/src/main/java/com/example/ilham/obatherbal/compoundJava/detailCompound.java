package com.example.ilham.obatherbal.compoundJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.R;


public class detailCompound extends AppCompatActivity {
    ImageView detailCompoundPic;
    TextView compoundName,partofplants,plantSp,molecularInf,refComp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_compound);
        detailCompoundPic = (ImageView) findViewById(R.id.detailCompoundPic);
        compoundName = (TextView) findViewById(R.id.compoundName);
        partofplants = (TextView) findViewById(R.id.partOfSpecies);
        plantSp = (TextView) findViewById(R.id.plantSpecies);
        molecularInf = (TextView) findViewById(R.id.molecularInfo);
        refComp = (TextView) findViewById(R.id.refCompound);

        Bundle bundle = getIntent().getExtras();
        String nameCompound = bundle.getString("compoundName");
        String partOfPlant = bundle.getString("partOfPlantCompound");
        String plantSpecies = bundle.getString("plantSpeciesCompound");
        String molecularInfo = bundle.getString("molecularFormula");
        String refCompound = bundle.getString("refCompound");

        Glide.with(this)
                .load("https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/"+nameCompound +"/PNG")
                .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(detailCompoundPic);

        SpannableStringBuilder strCompoundName = new SpannableStringBuilder("Compound Name : \n"+ nameCompound);
        strCompoundName.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        compoundName.setText(strCompoundName);

        SpannableStringBuilder strMolecul = new SpannableStringBuilder("Molecular Info : \n"+ molecularInfo);
        strMolecul.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        molecularInf.setText(strMolecul);

        SpannableStringBuilder strPlantSpecies = new SpannableStringBuilder("Plant Species : \n"+ plantSpecies);
        strPlantSpecies.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        plantSp.setText(strPlantSpecies);

        SpannableStringBuilder strPartOfPlant = new SpannableStringBuilder("Part of Plant : \n"+ partOfPlant);
        strPartOfPlant.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        partofplants.setText(strPartOfPlant);

        SpannableStringBuilder strRefCompound = new SpannableStringBuilder("Reference Compound : \n"+ refCompound);
        strRefCompound.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        refComp.setText(strRefCompound);
    }
}
