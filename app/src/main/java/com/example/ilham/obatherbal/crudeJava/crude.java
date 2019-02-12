package com.example.ilham.obatherbal.crudeJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.herbalJava.herbalAdapter;
import com.example.ilham.obatherbal.herbalJava.herbalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class crude extends Fragment {

    RecyclerView recyclerView;
    crudeAdapter adapter;
    List<crudeModel> crudeModels;
    EditText search;
    private static final String TAG = "crude";
    public crude() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_crude, container, false);
        crudeModels = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        getData();
//        searchData(rootView);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_crude);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new crudeAdapter(getActivity(),crudeModels);
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    private void searchData(View rootView) {
        search = (EditText) rootView.findViewById(R.id.search_crude);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });
    }

    private void filter(String s) {
        ArrayList<crudeModel> filteredList = new ArrayList<>();
        for (crudeModel item : crudeModels){
            if (item.getNama().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }

    private void getData() {

        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, "Onresponsecrude" + jsonArray.toString());
                        Log.d(TAG, "lengthonresponse" + jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.d(TAG,"jsonobject"+jsonObject);
                                crudeModels.add(
                                        new crudeModel(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("title")

                                        )
                                );
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "Onerror" + volleyError.toString());
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }
//        crudeModels.add(
//                new crudeModel(
//                        "crude1",
//                        "crude 1"
//                )
//        );
//        crudeModels.add(
//                new crudeModel(
//                        "crude2",
//                        "crude 2"
//                )
//        );
//        crudeModels.add(
//                new crudeModel(
//                        "crude3",
//                        "crude 3"
//                )
//        );
//        crudeModels.add(
//                new crudeModel(
//                        "crude4",
//                        "crude 4"
//                )
//        );
//        crudeModels.add(
//                new crudeModel(
//                        "crude5",
//                        "crude 5"
//                )
//        );
//    }

}
