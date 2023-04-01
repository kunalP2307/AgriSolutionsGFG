package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NoRidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_rides);

    }

    @Override
    public void onBackPressed(){
        this.startActivity(new Intent(NoRidesActivity.this, ServicesActivity.class));
    }
}