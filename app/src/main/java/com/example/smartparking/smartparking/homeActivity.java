package com.example.smartparking.smartparking;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class homeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Shared shared=new Shared(homeActivity.this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                if(shared.getName()!=""&&shared.getPassword()!=""){

                    Intent homeIntent = new Intent(homeActivity.this, HomeMainActivity.class);
                    homeIntent.putExtra("name",shared.getName());
                    startActivity(homeIntent);
                    finish();
                }
                else {
                    Intent homeIntent = new Intent(homeActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();

                }
            }
        },SPLASH_TIME_OUT);
    }
}
