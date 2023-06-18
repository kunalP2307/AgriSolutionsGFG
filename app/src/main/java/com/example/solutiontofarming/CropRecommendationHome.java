package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CropRecommendationHome extends AppCompatActivity {

    Button btn_get_recommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_recommend_home);

        bindComponents();
        addListeners();
    }

    void bindComponents(){
        btn_get_recommend = findViewById(R.id.btn_get_recommend);
    }

    void addListeners(){
        btn_get_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(CropRecommendationHome.this,CropRecommendation.class);
                 startActivity(intent);
            }
        });
    }
}