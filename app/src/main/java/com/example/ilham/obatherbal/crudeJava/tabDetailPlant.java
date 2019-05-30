package com.example.ilham.obatherbal.crudeJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabDetailPlant extends Fragment {
    View rootview;
    TextView describe,notFound,plantNames,refWiki;
    String idplant;
    ProgressBar loading;
    String plantName;

    public tabDetailPlant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab_detail_plant, container, false);
        RequestQueue queue = MySingleton.getInstance(this.getActivity()).getRequestQueue();
        Bundle bundle = this.getArguments();
        idplant = bundle.getString("idplant");
        describe = (TextView) rootview.findViewById(R.id.descriptivePlant);
        loading = (ProgressBar) rootview.findViewById(R.id.loadWikiData);
        plantNames = (TextView) rootview.findViewById(R.id.detailNamePlant);
        refWiki = (TextView) rootview.findViewById(R.id.referenceWiki);
        notFound = (TextView) rootview.findViewById(R.id.notFoundWiki);
        notFound.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getDataPlant(idplant);
        return rootview;
    }

    private void getDataPlant(String idplant) {
        String url = getString(R.string.url)+"/jamu/api/plant/get/"+idplant;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject plant = response.getJSONObject("data");
                            plantName = plant.getString("sname");
                            SpannableStringBuilder strDetailName = new SpannableStringBuilder("Name : \n"+ plantName);
                            strDetailName.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            plantNames.setText(strDetailName);
                            getDescribeWiki(plantName);
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
                        Toast.makeText(getActivity(), message,
                                Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void getDescribeWiki(final String plantName) {
        String url = null;
        try {
            url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="+ URLEncoder.encode(plantName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        try {
                            JSONObject query = response.getJSONObject("query");
                            JSONObject pages = query.getJSONObject("pages");
                            Iterator keys = pages.keys();
                            String key = (String)keys.next();
                            Log.d("keyStringIterator","key ="+key);
                            if (key.equals("-1"))
                            {
                                Log.d("keyStringIterator","key ="+"masuk log");
                                notFound.setVisibility(View.VISIBLE);
                                refWiki.setText(View.VISIBLE);
                            }
                            else
                            {
                                JSONObject value = pages.getJSONObject(key);
                                String extract = value.getString("extract");

                                SpannableStringBuilder strDetailWiki = new SpannableStringBuilder("Detail : \n"+ extract);
                                strDetailWiki.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                describe.setText(strDetailWiki);

                                SpannableStringBuilder strRefWiki = null;
                                try {
                                    String refPlantName = plantName.replaceAll(" ", "_");
                                    strRefWiki = new SpannableStringBuilder("Reference : \n"+ "https://en.wikipedia.org/wiki/"+ URLEncoder.encode(refPlantName, "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                strDetailWiki.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                refWiki.setText(strRefWiki);
                                Linkify.addLinks(refWiki,Linkify.WEB_URLS);
                                Log.d("detail","isi respon"+extract);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
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
                        Toast.makeText(getActivity(), message,
                                Toast.LENGTH_LONG).show();

                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}
