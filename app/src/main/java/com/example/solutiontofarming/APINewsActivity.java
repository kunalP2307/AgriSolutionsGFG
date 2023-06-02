package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class APINewsActivity extends AppCompatActivity implements AsyncDDsCallBack{

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apinews);
        progressBar = findViewById(R.id.progrss);
        Toast.makeText(this, "called activity", Toast.LENGTH_SHORT).show();
        new GetNews().setInstance(APINewsActivity.this, progressBar)
                .execute();
    }

    @Override
    public void setResult(String csvString) {
        Toast.makeText(this, "callback", Toast.LENGTH_SHORT).show();
        Log.d("TAG", " API NEWS setResult: "+csvString);
    }
}