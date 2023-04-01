package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;
import java.util.List;

public class ShowAvailableAgriLandsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textViewLandAddress, textViewLandArea, textViewLandRent, textViewLandType;
    ListView listViewAgriLand;
    AgriculturalLandAdapter agriculturalLandAdapter;
    List<AgriculturalLand> availableRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_agri_lands);
        bindComponents();


        availableRides = (List<AgriculturalLand>) getIntent().getSerializableExtra("availableLand");

        listViewAgriLand = findViewById(R.id.list_available_agri_lands);
        agriculturalLandAdapter = new AgriculturalLandAdapter(this, (ArrayList<AgriculturalLand>) availableRides);
        listViewAgriLand.setAdapter(agriculturalLandAdapter);
        listViewAgriLand.setOnItemClickListener(this);
        getSupportActionBar().setTitle("All Farms");

    }

    public void bindComponents(){
        this.listViewAgriLand = findViewById(R.id.list_available_agri_lands);
        this.textViewLandAddress = findViewById(R.id.text_field_location);
        this.textViewLandArea = findViewById(R.id.text_field_area);
        this.textViewLandRent = findViewById(R.id.text_field_rent);
        this.textViewLandType = findViewById(R.id.text_field_type);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AgriculturalLand agriculturalLand = (AgriculturalLand) agriculturalLandAdapter.getItem(position);

        Intent intent = new Intent(getApplicationContext(),AddAgriLandActivity.class);
        intent.putExtra("display",true);
        intent.putExtra("displayAgriLand",agriculturalLand);
        startActivity(intent);
    }
}