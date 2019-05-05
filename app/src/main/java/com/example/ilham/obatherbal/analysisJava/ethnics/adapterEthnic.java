package com.example.ilham.obatherbal.analysisJava.ethnics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.ethnics.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class adapterEthnic extends RecyclerView.Adapter<adapterEthnic.ethnicViewHolder> {

    private Context mCtx;
    List<ethnicModel> ethnicModelList;

    public adapterEthnic(Context mCtx, List<ethnicModel> ethnicModelList) {
        this.mCtx = mCtx;
        this.ethnicModelList = ethnicModelList;
    }

    @NonNull
    @Override
    public ethnicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_ethnic, parent,false);
        ethnicViewHolder holder = new ethnicViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ethnicViewHolder ethnicViewHolder, int i) {
        final ethnicModel detailEthnic = ethnicModelList.get(i);
        ethnicViewHolder.ethnicName.setText(detailEthnic.getEthnicName());
        ethnicViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getDataProvince(detailEthnic.getProvinceId());
                Intent ethnic = new Intent(mCtx,MapsActivity.class);
                ethnic.putExtra("daerah","sumatera utara");
                mCtx.startActivity(ethnic);

            }
        });
    }

    private void getDataProvince(final String provinceId) {
        RequestQueue queue = MySingleton.getInstance(mCtx.getApplicationContext()).getRequestQueue();
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/province/get/"+provinceId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject province = response.getJSONObject("data");
                            String address = province.getString("province_name");
                            Intent ethnic = new Intent(mCtx,MapsActivity.class);
                            ethnic.putExtra("daerah","Sumatera utara");
                            mCtx.startActivity(ethnic);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("ethnic", "Onerror" + error.toString());
                    }
                });
        MySingleton.getInstance(mCtx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public int getItemCount() {
        return ethnicModelList.size();
    }

    public void filterlist (ArrayList<ethnicModel> filteredList)
    {
        ethnicModelList =filteredList;
        notifyDataSetChanged();
    }

    class ethnicViewHolder extends RecyclerView.ViewHolder{
        TextView ethnicName;
        public ethnicViewHolder(@NonNull View itemView) {
            super(itemView);
            ethnicName = (TextView) itemView.findViewById(R.id.ethnicName);
        }
    }
}
