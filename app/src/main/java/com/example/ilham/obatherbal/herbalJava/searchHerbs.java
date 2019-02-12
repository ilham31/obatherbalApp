package com.example.ilham.obatherbal.herbalJava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.obatherbal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class searchHerbs extends Fragment {


    public searchHerbs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_herbs, container, false);
    }

}
