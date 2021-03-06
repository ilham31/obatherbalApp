package com.example.ilham.obatherbal.knowledge.tacit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchTacit extends AppCompatActivity {
    List<tacitModelSearch> tacitModelSearches;
    RecyclerView recyclerView;
    TextView notfoundsearch,notfoundfilter;
    EditText searchTacit;
    adapterTacitSearch adapterTacitSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tacit);
        notfoundsearch = (TextView) findViewById(R.id.startSearchTacit);
        notfoundfilter =(TextView)findViewById(R.id.notfoundfilterTacit);
        searchTacit = (EditText) findViewById(R.id.searchTacit);
        notfoundfilter.setVisibility(View.GONE);
        notfoundsearch.setVisibility(View.VISIBLE);
        tacitModelSearches = new ArrayList<>();
        getDataTacit();
        searhTacit();
        startRecyclerViewTacit();
    }
    // deklarasi komponen recyclerview tacit
    private void startRecyclerViewTacit() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_search_tacit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterTacitSearch = new adapterTacitSearch(this,tacitModelSearches);
        recyclerView.setAdapter(adapterTacitSearch);
        recyclerView.setVisibility(View.GONE);
    }


    private void searhTacit() {
        searchTacit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //cek keadaan recyclerview
                if(recyclerView.getVisibility()!= View.VISIBLE)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    notfoundsearch.setVisibility(View.GONE);
                }
                //memanggilfungsi filterTacit dengan parameter s
                filterTacit(s.toString());
                //cek search box, jika kosong recyclerview di hilangkan dan di munculkan info
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(View.GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(View.GONE);
                }

            }
        });
    }

    private void filterTacit(String s) {
        Log.d("search explicit","string filter" + s);
        ArrayList<tacitModelSearch> filteredList = new ArrayList<>();
        for (tacitModelSearch item : tacitModelSearches){
            //jika ada vari item.getTitle di string pencarian
            if (item.getTitle().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterTacitSearch.filterlist(filteredList);
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(View.GONE);
        }
    }

    //mengambil data tacit untuk pencarian dengan menggunakan model tacitModelSearches
    private void getDataTacit() {
        String url = getString(R.string.url)+"/jamu/api/tacit/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("search explicit", "Onresponse" + response.toString());
                        try {
                            JSONArray tacit = response.getJSONArray("data");
                            Log.d("search explicit","plant"+tacit.toString());
                            for (int i = 0; i < tacit.length() ; i++)
                            {
                                JSONObject jsonObject = tacit.getJSONObject(i);
                                tacitModelSearches.add(
                                        new tacitModelSearch(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("title")
                                        )
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("search explicit", "Onerrorplant" + error.toString());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
