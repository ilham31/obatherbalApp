package com.example.ilham.obatherbal.knowledge.tacit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class detailTacit extends AppCompatActivity {
    TextView title,uploaderTacit,datePublishTacit,refTacit,contentTacit,downloadFile;
    ProgressBar loading;
    String idTacit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tacit);
        title = (TextView) findViewById(R.id.titleTacitDetail);
        uploaderTacit = (TextView) findViewById(R.id.uploaderTacitDetail);
        datePublishTacit = (TextView) findViewById(R.id.datePublishTacitDetail);
//        refTacit= (TextView) findViewById(R.id.refTacitDetail);
        contentTacit = (TextView) findViewById(R.id.contentTacitDetail);
        loading = (ProgressBar) findViewById(R.id.loadDetailTacit);
        loading.setVisibility(View.VISIBLE);
        idTacit = getIntent().getStringExtra("idTacit");
        Log.d("idtacit","idtacit = "+idTacit);
        getDataTacit(idTacit);

    }
    //mengambil data tacit
    private void getDataTacit(String idTacit) {
        String url = getString(R.string.url)+"/jamu/api/tacit/get/"+idTacit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject tacit = response.getJSONObject("data");

                            title.setText(tacit.getString("title"));

                            SpannableStringBuilder strUploader = new SpannableStringBuilder("By : " + tacit.getString("createdby"));
                            strUploader.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            uploaderTacit.setText(strUploader);

                            datePublishTacit.setText(tacit.getString("datePublish").substring(0,10));


                            SpannableStringBuilder strDesc = new SpannableStringBuilder("Content : \n" + tacit.getString("content"));
                            strDesc.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            contentTacit.setText(strDesc);



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
