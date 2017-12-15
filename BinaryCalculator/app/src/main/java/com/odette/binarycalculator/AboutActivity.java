package com.odette.binarycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onClickBack(View view) {
        // open Main Activity again
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
