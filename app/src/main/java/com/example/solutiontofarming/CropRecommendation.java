package com.example.solutiontofarming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solutiontofarming.data.CropData;
import com.example.solutiontofarming.data.Land;
import com.example.solutiontofarming.data.LandData;

import java.util.ArrayList;

public class CropRecommendation extends AppCompatActivity {

    private EditText editAvgRainfall, editAvgTemperature, editGrowingSeasonDuration, editWaterSupply;
    private Button btnGetCropRecommend, btnAddLandDetails, btnEditLandDetails;
    private Spinner spinWaterSource;
    String[] waterSources = {"River", "Lake", "Pond", "Well", "Canal", "Rainwater Harvesting"};
    String cropName, cropPlantingSeason, cropGrowingDuration, harvestingStorage, marketValue, yieldCrop, pesticideUsed, fertilizerUsed, irrigationPractice, landId, landArea, soilType, location;
    RecyclerView recycler_list_land;
    LandAdapter adapter;
    ArrayList<Land> dataList;
    CropData cropData;
    LandData landData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_recommend_form);

        bindComponents();

//water source
        Spinner spinWaterSource = findViewById(R.id.spin_water_source);
        ArrayAdapter<String> waterSourceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, waterSources);
        waterSourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWaterSource.setAdapter(waterSourceAdapter);


//water supply through out yr

        addListener();


    }

    private void bindComponents() {

        // editText

        editAvgRainfall = findViewById(R.id.edit_avg_rainfall);
        editAvgTemperature = findViewById(R.id.edit_avg_temperature);
        editGrowingSeasonDuration = findViewById(R.id.edit_growing_season_duration);
        spinWaterSource = findViewById(R.id.spin_water_source);
        editWaterSupply = findViewById(R.id.edit_water_supply);

        // button
        btnGetCropRecommend = findViewById(R.id.btn_get_crop_recommend);
        btnAddLandDetails = findViewById(R.id.btn_add_land_details);

        recycler_list_land = findViewById(R.id.recycler_list_land);


        cropName = getIntent().getStringExtra("cropName");
//        cropPlantingSeason = getIntent().getStringExtra("cropPlantingSeason");
        cropGrowingDuration = getIntent().getStringExtra("cropGrowingDuration");
        harvestingStorage = getIntent().getStringExtra("harvestingStorage");
        marketValue = getIntent().getStringExtra("marketValue");
        yieldCrop = getIntent().getStringExtra("yieldCrop");
        pesticideUsed = getIntent().getStringExtra("pesticideUsed");
        fertilizerUsed = getIntent().getStringExtra("fertilizerUsed");
        irrigationPractice = getIntent().getStringExtra("irrigationPractice");


        landId = getIntent().getStringExtra("landId");
        landArea = getIntent().getStringExtra("landArea");
        soilType = getIntent().getStringExtra("soilType");
        location = getIntent().getStringExtra("location");


        dataList = new ArrayList();
        dataList.add(new Land(landId, landArea, cropName));
        adapter = new LandAdapter(this, dataList);
        recycler_list_land.setAdapter(adapter);
        recycler_list_land.setLayoutManager(new LinearLayoutManager(this));


    }
    // Add any necessary listeners or logic for the components

    private void addListener() {
        btnAddLandDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CropRecommendation.this, GetLandDetailsActivity.class);           // add a complete new land activity

                startActivity(intent);

            }
        });


        btnGetCropRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String avgRainFall = editAvgRainfall.getText().toString();
                String avgTemperature = editAvgTemperature.getText().toString();
                String growingDurationSeason = editGrowingSeasonDuration.getText().toString();
                String waterSupply = editWaterSupply.getText().toString();


                Intent intent = new Intent(CropRecommendation.this, FinalCropMatchActivity.class);

                intent.putExtra("cropName", cropName);
                intent.putExtra("cropPlantingSeason", cropPlantingSeason);
                intent.putExtra("cropGrowingDuration", cropGrowingDuration);
                intent.putExtra("harvestingStorage", harvestingStorage);
                intent.putExtra("marketValue", marketValue);
                intent.putExtra("yieldCrop", yieldCrop);
                intent.putExtra("pesticideUsed", pesticideUsed);
                intent.putExtra("fertilizerUsed", fertilizerUsed);
                intent.putExtra("irrigationPractice", irrigationPractice);
                intent.putExtra("cropName", cropName);
                intent.putExtra("cropPlantingSeason", cropPlantingSeason);
                intent.putExtra("cropGrowingDuration", cropGrowingDuration);
                intent.putExtra("harvestingStorage", harvestingStorage);
                intent.putExtra("marketValue", marketValue);
                intent.putExtra("yieldCrop", yieldCrop);
                intent.putExtra("pesticideUsed", pesticideUsed);
                intent.putExtra("fertilizerUsed", fertilizerUsed);
                intent.putExtra("irrigationPractice", irrigationPractice);
                intent.putExtra("avgRainFall", avgRainFall);
                intent.putExtra("growingDurationSeason", growingDurationSeason);
                intent.putExtra("avgTemperature", avgTemperature);
                intent.putExtra("waterSupply", waterSupply);
//
//                spinWaterSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String selectedSpinner =  parent.getItemAtPosition(position).toString();
//                    }
//                });


                // intent.putExtra("selectedSpinner",selectedSpinner);
                setCropRecommendData();
                if (validate()) {
                    startActivity(intent);
                }

            }
        });
    }

    public boolean validate() {
        if (editAvgRainfall.getText().toString().equals("")) {
            editAvgRainfall.setError("Please Enter Avg RainFall in mm ");
            editAvgRainfall.requestFocus();
            return false;
        }
        if (editAvgTemperature.getText().toString().equals("")) {
            editAvgTemperature.setError("Please Enter Avg Temperature celcius");
            editAvgTemperature.requestFocus();
            return false;
        }
        if (editGrowingSeasonDuration.getText().toString().isEmpty()) {
            editGrowingSeasonDuration.setError("Please Enter Growing Season");
            editGrowingSeasonDuration.requestFocus();
            return false;
        }
        if (editWaterSupply.getText().toString().isEmpty()) {
            editWaterSupply.setError("Please Enter Water Supply");
            editWaterSupply.requestFocus();
            return false;
        }

        return true;

    }

    public void setCropRecommendData() {
        cropData = new CropData(
                cropName,
                cropGrowingDuration,
                marketValue,
                harvestingStorage,
                yieldCrop,
                pesticideUsed,
                irrigationPractice,
                fertilizerUsed
        );

        landData = new LandData(
                landId,
                landArea,
                soilType,
                location,
                editAvgRainfall.getText().toString(),
                editAvgTemperature.getText().toString(),
                editGrowingSeasonDuration.getText().toString(),
                editWaterSupply.getText().toString(),
                spinWaterSource.getSelectedItem().toString()
        );

    }


}