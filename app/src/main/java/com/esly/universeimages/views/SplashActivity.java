package com.esly.universeimages.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.esly.universeimages.R;
import com.esly.universeimages.circleprogress.CircleProgress;

public class SplashActivity extends ActionBarActivity {
    private CircleProgress mProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressView = (CircleProgress) findViewById(R.id.progress);
        mProgressView.startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                newActivity();
                finish();

            }
        },2500);

    }


    public void newActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
