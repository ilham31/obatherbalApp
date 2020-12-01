package com.example.ilham.obatherbal.knowledge.explicit;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONException;
import org.json.JSONObject;

public class detailExplicit extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE = 1000;
    TextView title,uploader,datePublish,abstractExplicitDetail,description,downloadFile,refExplicit;
    ProgressBar loading;
    String fileName,urlDownload;
    String idExplicit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deklarasi komponen
        setContentView(R.layout.activity_detail_explicit);
        title = (TextView) findViewById(R.id.titleExplicitDetail);
        uploader = (TextView) findViewById(R.id.uploaderExplicitDetail);
        datePublish = (TextView) findViewById(R.id.datePublishExplicitDetail);
        abstractExplicitDetail= (TextView) findViewById(R.id.abstractExplicitDetail);
        description = (TextView) findViewById(R.id.desctiptionExplicitDetail);
        loading = (ProgressBar) findViewById(R.id.loadDetailExplicit);
        refExplicit = (TextView) findViewById(R.id.refExplicitDetail);
        downloadFile = (TextView) findViewById(R.id.downloadFile);
        downloadFile.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        idExplicit = getIntent().getStringExtra("idExplicit");
        getDataExplicit(idExplicit);
        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    //cek permission untuk mengakses external storage
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }

            }
        });

    }
    //mendownload file dengan menggunakan idexplicit
    private void downloadExplicit(String idExplicit) {
        String url = getString(R.string.url)+"/jamu/api/explicit/get/"+idExplicit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject explicit = response.getJSONObject("data");

                            fileName = explicit.getString("file");
                            urlDownload = getString(R.string.url)+"/jamu/api/explicit/file/"+fileName;
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle("Download");
                            request.setDescription("Downloading file...");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());

                            DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);

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

    private void startDownloading() {
        downloadExplicit(idExplicit);
    }

    // cek request permission dari user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
            {
                //jika di perbolehkan oleh user
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startDownloading();
                }
                else
                {
                    Toast.makeText(this,"Permission denied !",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //menampilkan data explicit
    private void getDataExplicit(String idExplicit) {
        String url = getString(R.string.url)+"/jamu/api/explicit/get/"+idExplicit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        downloadFile.setVisibility(View.VISIBLE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject explicit = response.getJSONObject("data");

                            String titleFile = explicit.getString("title");
                            title.setText(titleFile);

                            SpannableStringBuilder strUploader = new SpannableStringBuilder("By : " + explicit.getString("firstName")+" "+explicit.getString("lastName"));
                            strUploader.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            uploader.setText(strUploader);

                            datePublish.setText(explicit.getString("datePublish").substring(0,10));

                            SpannableStringBuilder strAbstract = new SpannableStringBuilder("Abstract : \n" + explicit.getString("abstract"));
                            strAbstract.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            abstractExplicitDetail.setText(strAbstract);

                            SpannableStringBuilder strDesc = new SpannableStringBuilder("Description : \n" + explicit.getString("description"));
                            strDesc.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            description.setText(strDesc);

                            SpannableStringBuilder strRef = new SpannableStringBuilder("Reference : \n" + "researchgate.net");
                            strAbstract.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            refExplicit.setText(strRef);
                            Linkify.addLinks(refExplicit,Linkify.WEB_URLS);



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
