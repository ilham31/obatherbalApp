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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkStatusAvialable (getApplicationContext())) {
            bottomNavigationView=(BottomNavigationView)findViewById(R.id.main_nav);
            frameLayout=(FrameLayout) findViewById(R.id.main_frame);
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
