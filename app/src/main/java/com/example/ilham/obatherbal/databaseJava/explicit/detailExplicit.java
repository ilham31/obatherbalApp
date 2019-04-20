package com.example.ilham.obatherbal.databaseJava.explicit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONException;
import org.json.JSONObject;

public class detailExplicit extends AppCompatActivity {
    TextView title,uploader,datePublish,abstractExplicitDetail,description;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_explicit);
        title = (TextView) findViewById(R.id.titleExplicitDetail);
        uploader = (TextView) findViewById(R.id.uploaderExplicitDetail);
        datePublish = (TextView) findViewById(R.id.datePublishExplicitDetail);
        abstractExplicitDetail= (TextView) findViewById(R.id.abstractExplicitDetail);
        description = (TextView) findViewById(R.id.desctiptionExplicitDetail);
        loading = (ProgressBar) findViewById(R.id.loadDetailExplicit);
        loading.setVisibility(View.VISIBLE);
        String idExplicit = getIntent().getStringExtra("idExplicit");
        getDataExplicit(idExplicit);

    }

    private void getDataExplicit(String idExplicit) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/explicit/get/"+idExplicit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject explicit = response.getJSONObject("data");

                            SpannableStringBuilder strTitle = new SpannableStringBuilder("Title : \n" + explicit.getString("title"));
                            strTitle.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setText(strTitle);

                            SpannableStringBuilder strUploader = new SpannableStringBuilder("Uploader : \n" + explicit.getString("firstName")+" "+explicit.getString("lastName"));
                            strUploader.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            uploader.setText(strUploader);

                            SpannableStringBuilder strDatePublish = new SpannableStringBuilder("Date publish : \n" + explicit.getString("datePublish"));
                            strDatePublish.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            datePublish.setText(strDatePublish);

                            SpannableStringBuilder strAbstract = new SpannableStringBuilder("Abstract : \n" + explicit.getString("abstract"));
                            strAbstract.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            abstractExplicitDetail.setText(strAbstract);

                            SpannableStringBuilder strDesc = new SpannableStringBuilder("Description : \n" + explicit.getString("description"));
                            strDesc.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            description.setText(strDesc);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("diseaseTab", "Onerrordisease" + error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
