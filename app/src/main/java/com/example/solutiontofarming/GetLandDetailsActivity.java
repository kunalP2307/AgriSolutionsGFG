package com.example.solutiontofarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solutiontofarming.data.Crop;

import java.util.ArrayList;
import java.util.List;


public class GetLandDetailsActivity extends AppCompatActivity {
    Button btn_select_location, btn_get_current_location, btn_add_details, btn_add_crop_details;
    EditText edit_land_id, edit_land_area, edit_soil_type, edit_location;

    RecyclerView recycler_list_crop;

    CropAdapter adapter;

    ArrayList<Crop> dataList;

    final String TAG = "GetLandDetailsActivity";


    // intent string details of getCropActivity
    String cropName, cropPlantingSeason, cropGrowingDuration, harvestingStorage, marketValue, yieldCrop, pesticideUsed, fertilizerUsed, irrigationPractice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_land_details);

        bindComponents();
        addListener();
    }

    void bindComponents() {
        btn_add_details = findViewById(R.id.btn_add_details);
        btn_select_location = findViewById(R.id.btn_select_location);
        btn_get_current_location = findViewById(R.id.btn_get_current_location);
        edit_land_id = findViewById(R.id.edit_land_id);
        edit_land_area = findViewById(R.id.edit_land_area);
        edit_soil_type = findViewById(R.id.edit_soil_type);
        edit_location = findViewById(R.id.edit_location);
        btn_add_crop_details = findViewById(R.id.btn_add_crop_details);
        recycler_list_crop = findViewById(R.id.recycler_list_crop);


        cropName = getIntent().getStringExtra("cropName");
        cropPlantingSeason = getIntent().getStringExtra("cropPlantingSeason");
        cropGrowingDuration = getIntent().getStringExtra("cropGrowingDuration");
        harvestingStorage = getIntent().getStringExtra("harvestingStorage");
        marketValue = getIntent().getStringExtra("marketValue");
        yieldCrop = getIntent().getStringExtra("yieldCrop");
        pesticideUsed = getIntent().getStringExtra("pesticideUsed");
        fertilizerUsed = getIntent().getStringExtra("fertilizerUsed");
        irrigationPractice = getIntent().getStringExtra("irrigationPractice");

        Log.d(TAG ,"crop name "+cropName);

        cropName = "wheat";
        cropPlantingSeason = "rainy";
        marketValue = "25";

        dataList = new ArrayList();

//        if(!cropName.isEmpty() && !cropPlantingSeason.isEmpty() && !cropGrowingDuration.isEmpty()){
//             dataList.clear();
//             dataList.add(new Crop(cropName,cropPlantingSeason,marketValue));
//        }

        dataList.add(new Crop(cropName, cropPlantingSeason, marketValue));
        adapter = new CropAdapter(this, dataList);
        recycler_list_crop.setAdapter(adapter);
        recycler_list_crop.setLayoutManager(new LinearLayoutManager(this));


    }

    void addListener() {

        btn_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String landId = edit_land_id.getText().toString();
                String landArea = edit_land_area.getText().toString();
                String soilType = edit_soil_type.getText().toString();
                String location = edit_location.getText().toString();


                Intent intent = new Intent(GetLandDetailsActivity.this, CropRecommendation.class);

                // crop details
                intent.putExtra("cropName", cropName);
                intent.putExtra("cropPlantingSeason", cropPlantingSeason);
                intent.putExtra("cropGrowingDuration", cropGrowingDuration);
                intent.putExtra("harvestingStorage", harvestingStorage);
                intent.putExtra("marketValue", marketValue);
                intent.putExtra("yieldCrop", yieldCrop);
                intent.putExtra("pesticideUsed", pesticideUsed);
                intent.putExtra("fertilizerUsed", fertilizerUsed);
                intent.putExtra("irrigationPractice", irrigationPractice);

                // land details
                intent.putExtra("landId", landId);
                intent.putExtra("landArea", landArea);
                intent.putExtra("soilType", soilType);
                intent.putExtra("location", location);


                if (validate()) {
                    Toast.makeText(GetLandDetailsActivity.this, "Land " +landId+ " added Successfully" , Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }


            }
        });

        btn_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_get_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_add_crop_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetLandDetailsActivity.this, GetCropDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean validate() {
        // validation

        if (edit_land_id.getText().toString().isEmpty()) {
            edit_land_id.setError("Please enter land ID");
            edit_land_id.requestFocus();
            return false;
        }
        if (edit_land_area.getText().toString().isEmpty()) {
            edit_land_area.setError("Please enter land area");
            edit_land_area.requestFocus();
            return false;

        }
        if (edit_soil_type.getText().toString().isEmpty()) {
            edit_soil_type.setError("Please enter soil type");
            edit_soil_type.requestFocus();
            return false;
        }
        if (edit_location.getText().toString().isEmpty()) {
            edit_location.setError("Please enter land ID");
            edit_location.requestFocus();
            return false;
        }
        return true;
    }


}