package com.example.ilham.obatherbal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ilham.obatherbal.analysisJava.analysis;
import com.example.ilham.obatherbal.compoundJava.compound;
import com.example.ilham.obatherbal.crudeJava.crude;
import com.example.ilham.obatherbal.databaseJava.database;
import com.example.ilham.obatherbal.herbalJava.herbal;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private com.example.ilham.obatherbal.herbalJava.herbal herbal;
    private com.example.ilham.obatherbal.crudeJava.crude crude;
    private com.example.ilham.obatherbal.databaseJava.database database;
    private com.example.ilham.obatherbal.analysisJava.analysis analysis;
    private com.example.ilham.obatherbal.compoundJava.compound compound;

//    final Fragment fragmentHerbal = new herbal();
//    final Fragment fragmentCrude = new crude();
//    final Fragment fragmentCompound = new compound();
//    final Fragment fragmentAnalysis = new analysis();
//    final Fragment fragmentDataBase = new database();
//    final FragmentManager fm = getSupportFragmentManager();
//    Fragment active = fragmentHerbal;
//final Fragment fragmentherbal = new herbal();
//    final Fragment fragmentCrude = new crude();
//    final Fragment fragmentCompound = new compound();
//    final Fragment fragmentAnalysis = new analysis();
//    final Fragment fragmentDataBase = new database();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(isNetworkStatusAvialable (getApplicationContext())) {
            bottomNavigationView=(BottomNavigationView)findViewById(R.id.main_nav);
            frameLayout=(FrameLayout) findViewById(R.id.main_frame);
//            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//            fm.beginTransaction().add(R.id.main_frame,fragmentHerbal, "1").commit();
//            fm.beginTransaction().add(R.id.main_frame, fragmentCrude, "2").hide(fragmentCrude).commit();
//            fm.beginTransaction().add(R.id.main_frame, fragmentCompound, "3").hide(fragmentCompound).commit();
//            fm.beginTransaction().add(R.id.main_frame, fragmentAnalysis, "4").hide(fragmentAnalysis).commit();
//            fm.beginTransaction().add(R.id.main_frame, fragmentDataBase, "5").hide(fragmentDataBase).commit();

            herbal= new herbal();
            crude = new crude();
            database=new database();
            analysis=new analysis();
            compound=new compound();

            setFragment(herbal);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_herbal:
                            setFragment(herbal);
                            return true;

                        case R.id.nav_crude:
                            setFragment(crude);
                            return true;

                        case R.id.nav_database:
                            setFragment(database);
                            return true;

                        case R.id.nav_analysis:
                            setFragment(analysis);
                            return true;

                        case R.id.nav_compound:
                            setFragment(compound);
                            return true;

                        default:
                            return false;

                    }
                }


            });
        } else {
            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

        }


    }

    private boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.nav_herbal:
//                    fm.beginTransaction().hide(active).show(fragmentHerbal).commit();
//                    active = fragmentHerbal;
//                    return true;
//
//                case R.id.nav_crude:
//                    fm.beginTransaction().hide(active).show(fragmentCrude).commit();
//                    active = fragmentCrude;
//                    return true;
//
//                case R.id.nav_compound:
//                    fm.beginTransaction().hide(active).show(fragmentCompound).commit();
//                    active = fragmentCompound;
//                    return true;
//
//                case R.id.nav_analysis:
//                    fm.beginTransaction().hide(active).show(fragmentAnalysis).commit();
//                    active = fragmentAnalysis;
//                    return true;
//
//                case R.id.nav_database:
//                    fm.beginTransaction().hide(active).show(fragmentDataBase).commit();
//                    active = fragmentDataBase;
//                    return true;
//            }
//            return false;
//        }
//    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);

        fragmentTransaction.commit();

    }
}
