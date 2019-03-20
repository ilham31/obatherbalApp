package com.example.ilham.obatherbal.analysisJava.prediction.confirmPageCompound;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.obatherbal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmPageCompound extends Fragment {

    View view;
    public confirmPageCompound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_page_compound, container, false);
        return view;
    }

}
