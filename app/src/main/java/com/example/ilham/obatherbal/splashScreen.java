package com.example.ilham.obatherbal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class splashScreen extends AppCompatActivity {
    private static final long SPLASH_SCREEN_MS = 2500;

    private long mTimeBeforeDelay;
    private Handler mSplashHandler;
    AVLoadingIndicatorView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        load = findViewById(R.id.loadSplashScren);
        load.setVisibility(View.VISIBLE);
        mSplashHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The first time mTimeBeforeDelay will be 0.
        long gapTime = System.currentTimeMillis() - mTimeBeforeDelay;
        if (gapTime > SPLASH_SCREEN_MS) {
            gapTime = SPLASH_SCREEN_MS;
        }
        mSplashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, MainActivity.class);
                startActivity(intent);
                splashScreen.this.finish();
                load.setVisibility(View.GONE);
            }
        }, gapTime);
        // Save the time before the delay.
        mTimeBeforeDelay = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSplashHandler.removeCallbacksAndMessages(null);
    }

}
