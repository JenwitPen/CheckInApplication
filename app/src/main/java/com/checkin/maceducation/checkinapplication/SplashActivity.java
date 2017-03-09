package com.checkin.maceducation.checkinapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };}

    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }}