package com.example.ilham.obatherbal.herbalJava;


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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.crudeJava.detailCrude;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabDiseaseHerbal extends Fragment {
    String idHerbal;
    View rootview;
    TextView nameDetailHerbal,efficacyDetailHerbal,refDetailHerbal,descriptionDclassHerbal,diseaseDclassHerbal,refDiseaseDclassHerbal,companyHerbal;
    ProgressBar loading;

    public tabDiseaseHerbal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab_disease_herbal, container, false);
        Bundle bundle = this.getArguments();
        idHerbal = bundle.getString("idHerbal");
        Log.d("diseaseTab","idherbal = "+ idHerbal);
        RequestQueue queue = MySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        nameDetailHerbal = (TextView) rootview.findViewById(R.id.detailNameHerbal);
        efficacyDetailHerbal = (TextView) rootview.findViewById(R.id.detailEfficacy);
        refDetailHerbal = (TextView) rootview.findViewById(R.id.detailRefHerbal);
        descriptionDclassHerbal = (TextView) rootview.findViewById(R.id.descriptionDclassHerbal);
        diseaseDclassHerbal = (TextView) rootview.findViewById(R.id.diseaseDclassHerbal);
        refDiseaseDclassHerbal = (TextView) rootview.findViewById(R.id.refDiseaseDclassHerbal);
        companyHerbal = (TextView) rootview.findViewById(R.id.companyHerbal);
        loading = (ProgressBar) rootview.findViewById(R.id.loadDiseaseDetailJamu);
        loading.setVisibility(View.VISIBLE);
        getDetailHerbal(idHerbal);
        return rootview;
    }

    private void getDetailHerbal(String idHerbal) {
        Log.d("disease","Call data detail and disease" + idHerbal);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/herbsmed/get/"+idHerbal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        Log.d("diseaseTab", "Onresponsedetail" + response.toString());
                        try {
                            JSONObject herbsmed = response.getJSONObject("data");

                            SpannableStringBuilder strNameDetailHerb = new SpannableStringBuilder("Name : \n" + herbsmed.getString("name"));
                            strNameDetailHerb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            nameDetailHerbal.setText(strNameDetailHerb);

                            SpannableStringBuilder strEfficacyDetailHerb = new SpannableStringBuilder("Efficacy : \n" + herbsmed.getString("efficacy"));
                            strEfficacyDetailHerb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            efficacyDetailHerbal.setText(strEfficacyDetailHerb);
                            String idDclass = herbsmed.getString("idclass");

                            getCompany(herbsmed.getString("idcompany"));
                            getDetailDisease(idDclass);

                            SpannableStringBuilder strRefDetailHerb = new SpannableStringBuilder("Reference Herbal : \n"+ herbsmed.getString("ref"));
                            strRefDetailHerb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            refDetailHerbal.setText(strRefDetailHerb);
                            Linkify.addLinks(refDetailHerbal,Linkify.WEB_URLS);

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

    private void getCompany(String idcompany) {
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/company/get/"+idcompany;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponse" + response.toString());
                        try {
                            JSONObject company = response.getJSONObject("data");
                            SpannableStringBuilder strcompHerbal = new SpannableStringBuilder("Company : \n" + company.getString("cname"));
                            strcompHerbal.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            companyHerbal.setText(strcompHerbal);


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

    private void getDetailDisease(String idDclass) {
        Log.d("disease","Call data disease and disease" + idDclass);
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/dclass/get/"+idDclass;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("diseaseTab", "Onresponse" + response.toString());
                        try {
                            JSONObject Dclass = response.getJSONObject("data");
                            SpannableStringBuilder strDesDclass = new SpannableStringBuilder("Description : \n" + Dclass.getString("description"));
                            strDesDclass.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            descriptionDclassHerbal.setText(strDesDclass);

                            SpannableStringBuilder strdisease = new SpannableStringBuilder("Disease : \n" + Dclass.getString("diseases"));
                            strdisease.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            diseaseDclassHerbal.setText(strdisease);

                            SpannableStringBuilder strRefDisease = new SpannableStringBuilder("Reference disease : \n"+ Dclass.getString("ref"));
                            strRefDisease.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            refDiseaseDclassHerbal.setText(strRefDisease);

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
