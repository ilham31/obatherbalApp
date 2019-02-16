package com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseMethod;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPrediction;
import com.example.ilham.obatherbal.crudeJava.crudeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseHerbs extends Fragment {
    private static final String TAG = "herbsPredict" ;
    private Button buttonNext;
    RecyclerView recyclerView;
    private View view;
    herbsAdapter adapter;
    List<herbsModel> herbsModels;
    private List<herbsModel> currentSelectedItems ;
    StringBuffer sb = null;
    EditText search;

    public chooseHerbs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_herbs, container, false);
        herbsModels = new ArrayList<>();
        currentSelectedItems = new ArrayList<>();
//        search=(EditText) view.findViewById(R.id.searchPredictHerb);
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        getDataHerbs();
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_predictPlant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new herbsAdapter(getActivity(),herbsModels);
        recyclerView.setAdapter(adapter);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_1);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                steppersPrediction.goToStepMethod();
//                chooseMethod step2Fragment = new chooseMethod();
////            step2Fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
//                        .replace(R.id.frame_layoutstepper, step2Fragment)
//                        .addToBackStack(null)
//                        .commit();
                    sb = new StringBuffer();
                    for (herbsModel h : adapter.checkedHerbs)
                    {
                        sb.append(h.getNameHerbs());
                        sb.append("\n");

                    }

                    if (adapter.checkedHerbs.size()>0)
                    {
                        Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please Check Plants",Toast.LENGTH_SHORT).show();
                    }

            }
        });

        return view;
    }

    private void filter(String s) {
        ArrayList<herbsModel> filteredlist =  new ArrayList<>();
        for (herbsModel item : herbsModels)
        {
            if(item.getNameHerbs().toLowerCase().contains(s.toLowerCase()))
            {
                filteredlist.add(item);

            }
        }
        adapter.filterlist(filteredlist);
    }


    private void getDataHerbs() {
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
                                herbsModels.add(
                                        new herbsModel(
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


}

