package com.example.ilham.obatherbal.analysisJava.comparison.confirmComparison;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.obatherbal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class confirmComparison extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public confirmComparison() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_comparison, container, false);
        final String idjamu1 = getArguments().getString("idjamu1");
        final String idjamu2 = getArguments().getString("idjamu2");
        Log.d("result","getdata idjamu 1 = "+idjamu1+", idjamu2 = "+idjamu2);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayoutComparisonJamu);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerComparisonJamu);

        viewPagerComparisonJamu adapter = new viewPagerComparisonJamu(getChildFragmentManager());

        tabPlantComparison1 comparisonJamu1 = new tabPlantComparison1();
        tabPlantComparisonJamu2 comparisonJamu2 = new tabPlantComparisonJamu2();

        Bundle bundle = new Bundle();
        bundle.putString("idjamu1",idjamu1);
        bundle.putString("idjamu2",idjamu2);



        comparisonJamu1.setArguments(bundle);
        comparisonJamu2.setArguments(bundle);

        adapter.addFragment(comparisonJamu1,"Jamu 1");
        adapter.addFragment(comparisonJamu2,"Jamu 2");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


}
