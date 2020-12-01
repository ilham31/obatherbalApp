package com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.example.ilham.obatherbal.MySingleton;
import com.example.ilham.obatherbal.analysisJava.comparison.steppersComparison;
import com.example.ilham.obatherbal.analysisJava.comparison.confirmJamu.confirmComparison;

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
public class chooseJamu extends Fragment implements Serializable{

    private Button buttonNext;
    private View view;
    private KeyListener listener1,listener2;
    private jamuModelComparison selected;
    private String idJamu1,idJamu2;
    List<jamuModelComparison> chosenJamu;
    AutoCompleteTextView jamu1,jamu2;
    public int match1 = 0,match2 = 0;
    List<jamuModelComparison> jamuModelComparisonList;
    public chooseJamu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //membuat array list
        jamuModelComparisonList = new ArrayList<>();
        chosenJamu = new ArrayList<>();
        RequestQueue queue = MySingleton.getInstance(this.getActivity().getApplicationContext()).getRequestQueue();

        //get data jamu untuk auto complete
        getDataJamuComparison();

        view =  inflater.inflate(R.layout.fragment_choose_jamu, container, false);

        //inisiasi komponen
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
                            //menyimpan jamu yang dipilih ke arraylist chosenjamu
                            chosenJamu.add(selected);
                        }
                    });
                }
                //jika keluar dari focus di form
                else {
                    if (idJamu1 == null)
                    {
                        Log.d("choose jamu","focus"+hasFocus+ "idJamu = "+idJamu1);
                        for (jamuModelComparison item : jamuModelComparisonList)
                        {
                            String jamu1Database = item.getNama();
                            if (jamu1.getText().toString().equals(jamu1Database))
                            {
                                //jika var jamu1 sama dengan di database
                                jamu1.setKeyListener(null);
                                match1 = 1;
                                idJamu1=item.getId();
                                chosenJamu.add(item);
                            }
                        }

                        if (match1 != 1)
                        {
                            jamu1.setError("invalid jamu");
                        }
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
                    if (idJamu2 == null)
                    {
                        Log.d("choose jamu","focus"+hasFocus+ "idJamu = "+idJamu2);
                        for (jamuModelComparison item : jamuModelComparisonList)
                        {
                            String jamu2Database = item.getNama();
                            if (jamu2.getText().toString().equals(jamu2Database))
                            {
                                jamu2.setKeyListener(null);
                                match2 = 1;
                                idJamu2=item.getId();
                                chosenJamu.add(item);
                            }
                        }
                        if (match2 != 1)
                        {
                            jamu2.setError("invalid jamu");
                        }
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
                        //kalau kedua jamu sama
                        if (idJamu1.equals(idJamu2))
                        {
                            Toast.makeText(getActivity(),"Please Select 2 different Jamu",Toast.LENGTH_SHORT).show();
                            chooseJamu refreshFragment = new chooseJamu();
                            getFragmentManager().beginTransaction().replace(R.id.frame_layoutstepperComparison,refreshFragment).commit();
                        }
                        else
                        {
                            jamu1.setText("");
                            jamu2.setText("");
                            steppersComparison.goToStepMethodComparison();
                            confirmComparison step2fragment = new confirmComparison();
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
                        chooseJamu refreshFragment = new chooseJamu();
                        getFragmentManager().beginTransaction().replace(R.id.frame_layoutstepperComparison,refreshFragment).commit();
                    }

            }
        });
        return view;
    }

    private void getDataJamuComparison() {
        String url = getString(R.string.url)+"/jamu/api/herbsmed/getlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean responseStatus = response.getBoolean("success");
                            if(responseStatus) {
                                JSONArray herbsmeds = response.getJSONArray("data");
                                Log.d("comparison", "herbsmeds" + herbsmeds.toString());
                                for (int i = 0; i < herbsmeds.length(); i++) {
                                    JSONObject jsonObject = herbsmeds.getJSONObject(i);
                                    String check = jsonObject.getString("idherbsmed");
                                    Character id = check.charAt(0);
                                    Log.d("comparison", "huruf pertama" + id);
                                    Log.d("comparison", "masuk if" + id);
                                    jamuModelComparisonList.add(
                                            new jamuModelComparison(
                                                    jsonObject.getString("idherbsmed"),
                                                    jsonObject.getString("name")

                                            )
                                    );
                                    //menyimpan data yang di dapatkan dari server ke arrayadapter untuk ditampilkan sebagai saran
                                    ArrayAdapter<jamuModelComparison> jamuSuggest = new ArrayAdapter<jamuModelComparison>(
                                            getActivity(), android.R.layout.simple_dropdown_item_1line, jamuModelComparisonList);
                                    jamu1.setAdapter(jamuSuggest);
                                    jamu2.setAdapter(jamuSuggest);
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "The server could not be found. Please try again after some time!",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("comparison", "Onerror" + error.toString());
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
