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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabDetailPlant extends Fragment {
    View rootview;
    TextView describe,notFound,plantNames;
    String idplant;
    ProgressBar loading;

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
        notFound = (TextView) rootview.findViewById(R.id.notFoundWiki);
        notFound.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getDataPlant(idplant);
        return rootview;
    }

    private void getDataPlant(String idplant) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/plant/get/"+idplant;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject plant = response.getJSONObject("data");
                            String plantName = plant.getString("sname");
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
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void getDescribeWiki(final String plantName) {
        String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="+plantName;
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
                            }
                            else
                            {
                                JSONObject value = pages.getJSONObject(key);
                                String extract = value.getString("extract");

                                SpannableStringBuilder strDetailWiki = new SpannableStringBuilder("Detail : \n"+ extract);
                                strDetailWiki.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                describe.setText(strDetailWiki);

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

                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}