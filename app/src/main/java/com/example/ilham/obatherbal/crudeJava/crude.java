package com.example.ilham.obatherbal.crudeJava;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.OnLoadMoreListener;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.search.searchHerbs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class crude extends Fragment {
    View rootView;
    RecyclerView recyclerView;
    crudeAdapter adapter;
    List<crudeModel> crudeModels;
    EditText search;
    ProgressBar loadCrude;
    private static final String TAG = "crude";
    private LinearLayoutManager mLayoutManager;
    private Handler handler;
    public crude() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //dekalrasi variabel
        rootView = inflater.inflate(R.layout.fragment_crude, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_crude);
        crudeModels = new ArrayList<>();
        String link = getString(R.string.url);
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        search=(EditText) rootView.findViewById(R.id.search_crude);
        loadCrude = (ProgressBar) rootView.findViewById(R.id.loadCrude);
        loadCrude.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCrude();
            }
        });
        get10DataCrude(link);
        startRecyclerviewCrude(link);



        return rootView;
    }
    //mengambil data pada halaman selanjutnya
    private void loadMoreDataCrude(int page, String link) {
        String url = link+"/jamu/api/plant/pages/"+page;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray plant = response.getJSONArray("data");
                            Log.d(TAG,"plant"+plant.toString());
                            for (int i = 0; i < plant.length() ; i++)
                            {
                                JSONObject jsonObject = plant.getJSONObject(i);
                                crudeModels.add(
                                        new crudeModel(
                                                jsonObject.getString("idplant"),
                                                jsonObject.getString("sname"),
                                                jsonObject.getString("refimg")
                                        )
                                );
                                adapter.notifyItemInserted(crudeModels.size());
                            }
                            adapter.setLoaded();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Activity activity = getActivity();
                        if(activity != null && isAdded())
                        {
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
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }
    //deklarasi recyclerview
    private void startRecyclerviewCrude(final String link) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new crudeAdapter(recyclerView,getActivity(),crudeModels);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                crudeModels.add(null);
                adapter.notifyItemInserted(crudeModels.size() - 1);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        crudeModels.remove(crudeModels.size() - 1);
                        adapter.notifyItemRemoved(crudeModels.size());
                        //add items one by one
                        int page = (crudeModels.size()/10)+1;
                        Log.d(TAG, "pagecrude" + page +"size = " +crudeModels.size());
                        loadMoreDataCrude(page,link);

                    }
                }, 2000);

            }
        });
    }
    //mencari crud emenggunakan categories "crude"
    private void searchCrude() {
        Bundle arguments = new Bundle();
        arguments.putString( "categories" , "crude");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        searchHerbs searchHerbs = new searchHerbs();
        searchHerbs.setArguments(arguments);
        ft.replace(R.id.main_frame, searchHerbs);
        ft.addToBackStack(null);
        ft.commit();
    }

    //mengambil 10 data awal crude
    private void get10DataCrude(String link) {
        String url = link+"/jamu/api/plant/pages/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadCrude.setVisibility(View.GONE);
                        Log.d(TAG, "Onresponse" + response.toString());
                        try {
                            JSONArray plant = response.getJSONArray("data");
                            Log.d(TAG,"plant"+plant.toString());
                            for (int i = 0; i < plant.length() ; i++)
                            {

                                JSONObject jsonObject = plant.getJSONObject(i);
                                crudeModels.add(
                                        new crudeModel(
                                                jsonObject.getString("idplant"),
                                                jsonObject.getString("sname"),
                                                jsonObject.getString("refimg")
                                        )
                                );
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Activity activity = getActivity();
                        if(activity != null && isAdded())
                        {
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
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }


}
