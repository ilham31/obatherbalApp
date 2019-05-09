package com.example.ilham.obatherbal.databaseJava.tacit;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class detailTacit extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE = 1000;
    TextView title,uploaderTacit,datePublishTacit,refTacit,contentTacit;
    Button downloadFile;
    ProgressBar loading;
    String fileName,urlDownload;
    String idTacit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tacit);
        title = (TextView) findViewById(R.id.titleTacitDetail);
        uploaderTacit = (TextView) findViewById(R.id.uploaderTacitDetail);
        datePublishTacit = (TextView) findViewById(R.id.datePublishTacitDetail);
        refTacit= (TextView) findViewById(R.id.refTacitDetail);
        contentTacit = (TextView) findViewById(R.id.contentTacitDetail);
        loading = (ProgressBar) findViewById(R.id.loadDetailTacit);
        downloadFile = (Button) findViewById(R.id.downloadFileTacit);
        downloadFile.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        idTacit = getIntent().getStringExtra("idTacit");
        Log.d("idtacit","idtacit = "+idTacit);
        getDataTacit(idTacit);
        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
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

    private void getDataTacit(String idTacit) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/tacit/get/"+idTacit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        downloadFile.setVisibility(View.VISIBLE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject tacit = response.getJSONObject("data");

                            title.setText(tacit.getString("title"));

                            SpannableStringBuilder strUploader = new SpannableStringBuilder("By : " + tacit.getString("createdby"));
                            strUploader.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            uploaderTacit.setText(strUploader);

                            SpannableStringBuilder strDatePublish = new SpannableStringBuilder("Date publish : \n" + tacit.getString("datePublish").substring(0,10));
                            strDatePublish.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            datePublishTacit.setText(strDatePublish);

                            SpannableStringBuilder strAbstract = new SpannableStringBuilder("Reference : \n" + tacit.getString("reference"));
                            strAbstract.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            refTacit.setText(strAbstract);
                            Linkify.addLinks(refTacit,Linkify.WEB_URLS);

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

    private void startDownloading() {
        downloadTacit(idTacit);
    }

    private void downloadTacit(String idTacit) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/tacit/get/"+idTacit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject tacit = response.getJSONObject("data");

                            fileName = tacit.getString("file");
                            urlDownload = "http://ci.apps.cs.ipb.ac.id/jamu/api/tacit/file/"+fileName;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
            {
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

}
