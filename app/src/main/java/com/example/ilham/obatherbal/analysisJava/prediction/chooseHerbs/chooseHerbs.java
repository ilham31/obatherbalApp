package com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.prediction.chooseMethod.chooseMethod;
import com.example.ilham.obatherbal.analysisJava.prediction.steppersPrediction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
    List<herbsModel> idPlant;

    public chooseHerbs() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_herbs, container, false);
        herbsModels = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_predictPlant);
        startRecyclerView();
        getDataHerbs();
        idPlant = new ArrayList<>();
        currentSelectedItems = new ArrayList<>();

        search=(EditText) view.findViewById(R.id.searchPredictHerb);

        search.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = search.getRight()
                            - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    // when EditBox has padding, adjust leftEdge like
                    // leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.edittext_padding_left_right);
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        // clicked on clear icon
                        search.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
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




        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_1);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                    sb = new StringBuffer();
                    for (herbsModel h : adapter.checkedHerbs)
                    {
                        idPlant.add(
                                new herbsModel(
                                        h.getIdHerbs(),
                                        h.getNameHerbs(),
                                        h.getIdPlant()
                                )
                        );

                    }

                    if (adapter.checkedHerbs.size()>0)
                    {
                        steppersPrediction.goToStepMethod();
                        chooseMethod step2Fragment = new chooseMethod();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("idPlant", (Serializable) idPlant);
                        step2Fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                                .replace(R.id.frame_layoutstepper, step2Fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please Check Plants",Toast.LENGTH_SHORT).show();
                    }

            }
        });

        return view;
    }

    private void startRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new herbsAdapter(getActivity(),herbsModels);
        recyclerView.setAdapter(adapter);

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
        String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/plant/getlist";
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
                                herbsModels.add(
                                        new herbsModel(
                                                jsonObject.getString("_id"),
                                                jsonObject.getString("sname"),
                                                jsonObject.getString("idplant")

                                        )

                                );
                                adapter = new herbsAdapter(getActivity(),herbsModels);
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "Onerrorplant" + error.toString());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }


}

