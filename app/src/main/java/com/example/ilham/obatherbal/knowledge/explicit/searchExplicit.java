package com.example.ilham.obatherbal.knowledge.explicit;

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

public class searchExplicit extends AppCompatActivity {
    List<explicitModelSearch> explicitModels;
    RecyclerView recyclerView;
    TextView notfoundsearch,notfoundfilter;
    EditText searchExplicit;
    adapterExplicitSearch adapterExplicitSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_explicit);
        //deklarasi komponen halaman
        notfoundsearch = (TextView) findViewById(R.id.startSearchExplicit);
        notfoundfilter =(TextView)findViewById(R.id.notfoundfilterExplicit);
        searchExplicit = (EditText) findViewById(R.id.searchExplicit);
        notfoundfilter.setVisibility(View.GONE);
        notfoundsearch.setVisibility(View.VISIBLE);
        explicitModels = new ArrayList<>();
        getDataExplicit();
        searhExplicit();
        startRecyclerViewExplicit();
    }
    //deklarasi recyclerview
    private void startRecyclerViewExplicit() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_search_explicit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterExplicitSearch = new adapterExplicitSearch(this,explicitModels);
        recyclerView.setAdapter(adapterExplicitSearch);
        recyclerView.setVisibility(View.GONE);
    }

    private void searhExplicit() {
        searchExplicit.addTextChangedListener(new TextWatcher() {
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
                //panggil fungsi filter dengan parameter kata-kata yang berada di search bar
                filterExplicit(s.toString());
                //jika kata-katanya kosong, recyclerview dihilangkan dan info di tampilkan
                if(s.toString().length() == 0)
                {
                    recyclerView.setVisibility(View.GONE);
                    notfoundsearch.setVisibility(View.VISIBLE);
                    notfoundfilter.setVisibility(View.GONE);
                }

            }
        });
    }

    //filter explicit
    private void filterExplicit(String s) {
        Log.d("search explicit","string filter" + s);
        ArrayList<explicitModelSearch> filteredList = new ArrayList<>();
        for (explicitModelSearch item : explicitModels){
            //jika kata-kata di search box sama dengan di database
            if (item.getTitle().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapterExplicitSearch.filterlist(filteredList);
        if (filteredList.size() == 0)
        {
            notfoundfilter.setVisibility(View.VISIBLE);
        }
        else
        {
            notfoundfilter.setVisibility(View.GONE);
        }
    }
    //mendapatkan data explicit
    private void getDataExplicit() {
        String url = getString(R.string.url)+"/jamu/api/explicit/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("search explicit", "Onresponse" + response.toString());
                        try {
                            JSONArray explicit = response.getJSONArray("data");
                            Log.d("search explicit","plant"+explicit.toString());
                            for (int i = 0; i < explicit.length() ; i++)
                            {
                                JSONObject jsonObject = explicit.getJSONObject(i);
                                explicitModels.add(
                                        new explicitModelSearch(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("title"),
                                                jsonObject.getString("description")
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
