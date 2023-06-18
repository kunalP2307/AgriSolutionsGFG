package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetCropDetailsActivity extends AppCompatActivity {

    EditText edit_crop_name, edit_crop_planting_season, edit_crop_growing_duration, edit_crop_market_value, edit_crop_harvesting_storage, edit_yield_of_crops, edit_pesticide_used, edit_fertilizer_nutrient_application, edit_irrigation_practices;
    Button btn_crop_add_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_crop_details);

        bindComponents();

        addListener();
    }

    private void bindComponents() {
        edit_crop_name = findViewById(R.id.edit_crop_name);
        edit_crop_planting_season = findViewById(R.id.edit_crop_planting_season);
        edit_crop_growing_duration = findViewById(R.id.edit_crop_growing_duration);
        edit_crop_market_value = findViewById(R.id.edit_crop_market_value);
        edit_crop_harvesting_storage = findViewById(R.id.edit_crop_harvesting_storage);
        edit_yield_of_crops = findViewById(R.id.edit_yield_of_crops);
        btn_crop_add_details = findViewById(R.id.btn_crop_add_details);
        edit_pesticide_used = findViewById(R.id.edit_pesticide_used);
        edit_fertilizer_nutrient_application = findViewById(R.id.edit_fertilizer_nutrient_application);
        edit_irrigation_practices = findViewById(R.id.edit_irrigation_practices);


    }

    private void addListener() {
        btn_crop_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // add the new crops
                String cropName = edit_crop_name.getText().toString();
                String cropPlantingSeason = edit_crop_planting_season.getText().toString();
                String cropGrowingDuration = edit_crop_growing_duration.getText().toString();
                String harvestingStorage = edit_crop_harvesting_storage.getText().toString();
                String marketValue = edit_crop_market_value.getText().toString();
                String yieldCrop = edit_yield_of_crops.getText().toString();
                String pesticideUsed = edit_pesticide_used.getText().toString();
                String fertilizerUsed = edit_fertilizer_nutrient_application.getText().toString();
                String irrigationPractice = edit_irrigation_practices.getText().toString();


                Intent intent = new Intent(GetCropDetailsActivity.this, GetLandDetailsActivity.class);
                intent.putExtra("cropName", cropName);
                intent.putExtra("cropPlantingSeason", cropPlantingSeason);
                intent.putExtra("cropGrowingDuration", cropGrowingDuration);
                intent.putExtra("harvestingStorage", harvestingStorage);
                intent.putExtra("marketValue", marketValue);
                intent.putExtra("yieldCrop", yieldCrop);
                intent.putExtra("pesticideUsed", pesticideUsed);
                intent.putExtra("fertilizerUsed", fertilizerUsed);
                intent.putExtra("irrigationPractice", irrigationPractice);
                if (validate()) {
                    Toast.makeText(GetCropDetailsActivity.this, "Crop "+cropName+" Added Successfully ", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                // passing data to getLandActivity

            }
        });
    }

    private boolean validate() {
        // validation
        if (edit_crop_name.getText().toString().isEmpty()) {
            edit_crop_name.setError("Please Enter Crop name");
            edit_crop_name.requestFocus();
            return false;
        } else if (edit_crop_planting_season.getText().toString().isEmpty()) {
            edit_crop_planting_season.setError("Please Enter Avg RainFall in mm ");
            edit_crop_planting_season.requestFocus();
            return true;
        } else if (edit_crop_growing_duration.getText().toString().isEmpty()) {
            edit_crop_growing_duration.setError("Please Enter Growing Duration ");
            edit_crop_growing_duration.requestFocus();
            return false;
        } else if (edit_crop_market_value.getText().toString().isEmpty()) {
            edit_crop_market_value.setError("Please Enter Crop Market Value ");
            edit_crop_market_value.requestFocus();
            return false;
        } else if (edit_crop_harvesting_storage.getText().toString().isEmpty()) {
            edit_crop_harvesting_storage.setError("Please Enter harvesting Storage ");
            edit_crop_harvesting_storage.requestFocus();
            return false;
        } else if (edit_yield_of_crops.getText().toString().isEmpty()) {
            edit_yield_of_crops.setError("Please Enter yields of crop");
            edit_yield_of_crops.requestFocus();
            return false;
        } else if (edit_pesticide_used.getText().toString().isEmpty()) {
            edit_pesticide_used.setError("Please Enter pesticide used");
            edit_pesticide_used.requestFocus();
            return false;
        } else if (edit_fertilizer_nutrient_application.getText().toString().isEmpty()) {
            edit_fertilizer_nutrient_application.setError("Please Enter fertilizer used");
            edit_fertilizer_nutrient_application.requestFocus();
            return false;
        } else if (edit_irrigation_practices.getText().toString().isEmpty()) {
            edit_irrigation_practices.setError("Please Enter irrigation practices used");
            edit_irrigation_practices.requestFocus();
            return false;
        }

        return true;

    }

}