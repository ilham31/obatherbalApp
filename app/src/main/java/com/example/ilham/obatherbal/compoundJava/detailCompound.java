package com.example.ilham.obatherbal.compoundJava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class detailCompound extends AppCompatActivity {
    ImageView detailCompoundPic;
    TextView compoundName,partofplants,plantSp,effectPart,refComp;
    String name,partPlant,plantSpecies,effectPartOfSpecies,refCompound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_compound);
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String idCompound = getIntent().getStringExtra("idCompound");
        detailCompoundPic = (ImageView) findViewById(R.id.detailCompoundPic);
        compoundName = (TextView) findViewById(R.id.compoundName);
        partofplants = (TextView) findViewById(R.id.partOfSpecies);
        plantSp = (TextView) findViewById(R.id.plantSpecies);
        effectPart = (TextView) findViewById(R.id.effectPart);
        refComp = (TextView) findViewById(R.id.refCompound);
        getDataCompound(idCompound);
    }

    private void getDataCompound(String idCompound) {
        String url = getString(R.string.url)+"/jamu/api/compound/get/"+idCompound;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject compound = response.getJSONObject("data");
                            name = compound.getString("cname");

                            SpannableStringBuilder strCompoundName = new SpannableStringBuilder("Compound Name : \n"+ name);
                            strCompoundName.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            compoundName.setText(strCompoundName);

                            Glide.with(detailCompound.this)
                                    .load("https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/"+name +"/PNG")
                                    .apply(new RequestOptions().error(R.drawable.placehold).diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .into(detailCompoundPic);

                            JSONArray refCrudeCompound = compound.getJSONArray("refCrudeCompound");
                            JSONObject ref = refCrudeCompound.getJSONObject(0);
                            Log.d("detail_compound", "Onresponse refcompound" + ref);

                            partPlant = ref.getString("part_of_plant");
                            SpannableStringBuilder strPartOfPlant = new SpannableStringBuilder("Part of Plant : \n"+ partPlant);
                            strPartOfPlant.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            partofplants.setText(strPartOfPlant);

                            plantSpecies = ref.getString("plant_species");
                            SpannableStringBuilder strPlantSpecies = new SpannableStringBuilder("Plant Species : \n"+ plantSpecies);
                            strPlantSpecies.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            plantSp.setText(strPlantSpecies);

                            effectPartOfSpecies = ref.getString("effect_part");
                            SpannableStringBuilder strEffectPart = new SpannableStringBuilder("Effect: \n"+ effectPartOfSpecies);
                            strEffectPart.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            effectPart.setText(strEffectPart);

                            refCompound = ref.getString("ref_metabolites");
                            SpannableStringBuilder strRefCompound = new SpannableStringBuilder("Reference Compound : \n"+ refCompound);
                            strRefCompound.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            refComp.setText(strRefCompound);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Handle error
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(detailCompound.this, message,
                                Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

