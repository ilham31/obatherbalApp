package com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;
import com.example.ilham.obatherbal.analysisJava.comparison.chooseMethod.chooseMethodComparison;

import com.example.ilham.obatherbal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class chooseJamu extends Fragment {

    private Button buttonNext;
    private View view;
    private KeyListener listener1,listener2;
    private jamuModelComparison selected;
    private String idJamu1,idJamu2;
    List<jamuModelComparison> chosenJamu;
    AutoCompleteTextView jamu1,jamu2;


    List<jamuModelComparison> jamuModelComparisonList;
    public chooseJamu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        jamuModelComparisonList = new ArrayList<>();
        chosenJamu = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();
        getDataJamuComparison();


        view =  inflater.inflate(R.layout.fragment_choose_jamu, container, false);
        jamu1 = view.findViewById(R.id.jamu1);
        ArrayAdapter<jamuModelComparison> jamuSuggest = new ArrayAdapter<jamuModelComparison>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, jamuModelComparisonList);
        jamu1.setAdapter(jamuSuggest);
        jamu2 = view.findViewById(R.id.jamu2);
        jamu2.setAdapter(jamuSuggest);

        listener1 = jamu1.getKeyListener();
        jamu1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    jamu1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selected = (jamuModelComparison) parent.getAdapter().getItem(position);
                            idJamu1 = selected.getId();
                            jamu1.setKeyListener(null);
                            chosenJamu.add(selected);
                        }
                    });
                }
                else {
                    Log.d("choose jamu","focus"+hasFocus+ "idJamu = "+idJamu1);
                    if (idJamu1==null)
                    {
                        jamu1.setError("invalid jamu");
                    }
                }
            }
        });

        listener2 = jamu2.getKeyListener();
        jamu2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    jamu2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selected = (jamuModelComparison) parent.getAdapter().getItem(position);
                            idJamu2 = selected.getId();
                            jamu2.setKeyListener(null);
                            chosenJamu.add(selected);
                        }
                    });
                }
                else {
                    Log.d("choose jamu","focus"+hasFocus+ "idJamu = "+idJamu2);
                    if (idJamu2==null)
                    {
                        jamu2.setError("invalid jamu");
                    }
                }
            }
        });




        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_1_comparison);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("choose jamu","length arraylist "+chosenJamu.size());
                    Log.d("choose jamu","size  = "+chosenJamu.size());


                    if (chosenJamu.size() == 2 )
                    {
                        if (idJamu1==idJamu2)
                        {
                            Toast.makeText(getActivity(),"Please Select 2 different Jamu",Toast.LENGTH_SHORT).show();
                            jamu1.setKeyListener(listener1);
                            jamu2.setKeyListener(listener2);
                            chosenJamu.clear();
                            jamu1.setError("choose different jamu");
                            jamu2.setError("choose different jamu");
                        }
                        else
                        {
                            jamu1.setText("");
                            jamu2.setText("");
                            steppersComparison.goToStepMethodComparison();
                            chooseMethodComparison step2fragment = new chooseMethodComparison();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("idJamu", (Serializable) chosenJamu);
                            step2fragment.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                                    .replace(R.id.frame_layoutstepperComparison, step2fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please Select 2 Jamu",Toast.LENGTH_SHORT).show();
                    }

            }
        });
        return view;
    }

    private void getDataJamuComparison() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Jamu comparison", "Onresponsecrude" + jsonArray.toString());
                        Log.d("Jamu comparison", "lengthonresponse" + jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.d(TAG,"jsonobject"+jsonObject);
                                jamuModelComparisonList.add(
                                        new jamuModelComparison(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("title")

                                        )
                                );
                                ArrayAdapter<jamuModelComparison> jamuSuggest = new ArrayAdapter<jamuModelComparison>(
                                        getActivity(), android.R.layout.simple_dropdown_item_1line, jamuModelComparisonList);
                                jamu1.setAdapter(jamuSuggest);
                                jamu2.setAdapter(jamuSuggest);
                            } catch (JSONException e) {

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Jamu comparison", "Onerror" + volleyError.toString());
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);


    }

}