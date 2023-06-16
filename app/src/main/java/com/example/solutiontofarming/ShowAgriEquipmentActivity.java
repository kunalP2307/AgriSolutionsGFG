package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.Transport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowAgriEquipmentActivity extends AppCompatActivity {

    TextView textViewEquipCategory,textViewEquipName,textViewEquipmentRent,textViewEquipmentAdditionalDetails,textViewEquipmentLocation,textViewEquipmentOwner,
                textViewYearUsed,textViewAvailability;
    Button btnCallEquipmentOwner;
    ImageView imageViewEquipImage;
    AgriEquipment agriculturalEquipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_agri_equip);

        agriculturalEquipment = (AgriEquipment) getIntent().getSerializableExtra("selectedEquipment");
        bindComponents();
        addListeners();
        showEquipmentDetails();
        getSupportActionBar().setTitle("Equipment Details");

    }


    public void bindComponents(){
        this.textViewEquipCategory = findViewById(R.id.text_equip_category);
        this.textViewEquipName = findViewById(R.id.text_equip_name);
        this.textViewEquipmentRent = findViewById(R.id.text_equip_rent_day);
//        this.textViewEquipmentAdditionalDetails = findViewById(R.id.text_equip_details);
        textViewAvailability = findViewById(R.id.text_equip_avail_dates);
        this.textViewEquipmentLocation = findViewById(R.id.text_equip_location);
        textViewYearUsed = findViewById(R.id.textViewYearUsed);
        this.textViewEquipmentOwner = findViewById(R.id.text_equip_owner_name);
        imageViewEquipImage = findViewById(R.id.text_equip_Image);
        this.btnCallEquipmentOwner = findViewById(R.id.btn_call_equi_owner);

    }

    public void addListeners(){
        this.btnCallEquipmentOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+agriculturalEquipment.getOwner().getContact()));
                startActivity(intent);
            }
        });
    }

    public void showEquipmentDetails(){
        textViewEquipCategory.setText(agriculturalEquipment.getCategory());
        textViewEquipName.setText(agriculturalEquipment.getName());
        textViewYearUsed.setText(agriculturalEquipment.getYearsUsed()+" Years");
        textViewEquipmentRent.setText(agriculturalEquipment.getRentPerDay());
        if(!agriculturalEquipment.getImageUrl().equals("")){
            Glide.with(getApplicationContext()).load(agriculturalEquipment.getImageUrl()).into(imageViewEquipImage);
        }else{
            imageViewEquipImage.setVisibility(View.GONE);
        }
        for(int i=0; i<agriculturalEquipment.getAvailabilities().size(); i++){
            textViewAvailability.setText(textViewAvailability.getText().toString() + agriculturalEquipment.getAvailabilities().get(i).getStartDate() +" -> "+agriculturalEquipment.getAvailabilities().get(i).getEndDate()+ " ");
        }

        textViewEquipmentLocation.setText(agriculturalEquipment.getAddress().getAddress());
        textViewEquipmentOwner.setText(agriculturalEquipment.getOwner().getName());

    }
}