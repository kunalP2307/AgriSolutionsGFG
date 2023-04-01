package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.Transport;

public class ShowAgriEquipmentActivity extends AppCompatActivity {

    TextView textViewEquipCategory,textViewEquipName,textViewEquipmentRent,textViewEquipmentAdditionalDetails,textViewEquipmentLocation,textViewEquipmentOwner;
    Button btnCallEquipmentOwner;
    AgriculturalEquipment agriculturalEquipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_agri_equip);

        agriculturalEquipment = (AgriculturalEquipment) getIntent().getSerializableExtra("selectedEquipment");
        bindComponents();
        addListeners();
        showEquipmentDetails();
        getSupportActionBar().setTitle("Equipment Details");

    }

    public void bindComponents(){
        this.textViewEquipCategory = findViewById(R.id.text_equip_category);
        this.textViewEquipName = findViewById(R.id.text_equip_name);
        this.textViewEquipmentRent = findViewById(R.id.text_equip_rent_hour);
        this.textViewEquipmentAdditionalDetails = findViewById(R.id.text_equip_details);
        this.textViewEquipmentLocation = findViewById(R.id.text_equip_location);
        this.textViewEquipmentOwner = findViewById(R.id.text_equip_owner_name);
        this.btnCallEquipmentOwner = findViewById(R.id.btn_call_equi_owner);
    }

    public void addListeners(){
        this.btnCallEquipmentOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+agriculturalEquipment.getProviderContact()));
                startActivity(intent);
            }
        });
    }

    public void showEquipmentDetails(){
        textViewEquipmentOwner.setText(agriculturalEquipment.getProviderName());
        textViewEquipmentLocation.setText(agriculturalEquipment.getEquipmentLocation());
        textViewEquipmentAdditionalDetails.setText(agriculturalEquipment.getAdditionalDetails());
        textViewEquipName.setText(agriculturalEquipment.getEquipmentName());
        textViewEquipCategory.setText(agriculturalEquipment.getEquipmentCategory());
        textViewEquipmentRent.setText(agriculturalEquipment.getRentPerHour());
    }
}