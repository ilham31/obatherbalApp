package com.example.ilham.obatherbal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ilham.obatherbal.analysisJava.analysis;
import com.example.ilham.obatherbal.compoundJava.compound;
import com.example.ilham.obatherbal.crudeJava.crude;
import com.example.ilham.obatherbal.knowledge.knowldege;
import com.example.ilham.obatherbal.herbalJava.herbal;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private com.example.ilham.obatherbal.herbalJava.herbal herbal;
    private com.example.ilham.obatherbal.crudeJava.crude crude;
    private knowldege database;
    private com.example.ilham.obatherbal.analysisJava.analysis analysis;
    private com.example.ilham.obatherbal.compoundJava.compound compound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // deklarasi bottom navigation view
         bottomNavigationView=(BottomNavigationView)findViewById(R.id.main_nav);
            frameLayout=(FrameLayout) findViewById(R.id.main_frame);
            herbal= new herbal();
            crude = new crude();
            database=new knowldege();
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

                        case R.id.nav_database:;
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



    }
    //setting fragment
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
