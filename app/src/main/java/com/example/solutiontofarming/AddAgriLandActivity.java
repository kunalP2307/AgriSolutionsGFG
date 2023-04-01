package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AddAgriLandActivity extends AppCompatActivity {

    final String TAG = "AddAgriLandActivity";
    EditText textViewName,textViewPhone,textViewArea,textViewLocation,textViewType,textViewRent;
    Button btnAddLand , btnCallOwner, btnViewLocation;
    AgriculturalLand agriculturalLand;
    String userId,farmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agri_land);
        getFarmId();
        getUserId();
        bindComponents();
        addListeners();
        getSupportActionBar().setTitle("Add Farm");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null) {
            boolean useThisActivityToDisplayLand = getIntent().getExtras().getBoolean("display");
            agriculturalLand = (AgriculturalLand)intent.getSerializableExtra("displayAgriLand");

            if (useThisActivityToDisplayLand) {
                if(agriculturalLand != null){
                    textViewName.setText(agriculturalLand.getProviderName());
                    textViewPhone.setText(agriculturalLand.getProviderPhone());
                    textViewLocation.setText(agriculturalLand.getLandAddress());
                    textViewArea.setText(agriculturalLand.getLandArea());
                    textViewRent.setText(agriculturalLand.getRentPerYear());
                    textViewType.setText(agriculturalLand.getLandType());
                    btnAddLand.setVisibility(View.INVISIBLE);
                    btnViewLocation.setVisibility(View.VISIBLE);
                    btnCallOwner.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void bindComponents(){
        this.btnAddLand = findViewById(R.id.btn_add_land);
        this.btnCallOwner = findViewById(R.id.btn_contact_land_owner);
        this.btnViewLocation = findViewById(R.id.btn_view_location_on_map);
        this.textViewName = findViewById(R.id.text_add_land_owner_name);
        this.textViewPhone = findViewById(R.id.text_land_owner_mobile);
        this.textViewArea = findViewById(R.id.text_add_land_area);
        this.textViewLocation = findViewById(R.id.text_add_land_field_address);
        this.textViewType = findViewById(R.id.text_add_land_field_type);
        this.textViewRent = findViewById(R.id.text_add_land_rent_year);
    }

    public void addListeners(){
        btnCallOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f",agriculturalLand.getLandLatitude() , agriculturalLand.getLandLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        btnViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + ""+agriculturalLand.getLandLatitude() + "," + ""+agriculturalLand.getLandLongitude() + " (" + agriculturalLand.getLandAddress() + ")";
                Intent intent  =new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

        btnAddLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLandDetails();
                addLand();
            }
        });
    }

    public void setLandDetails(){
        agriculturalLand = new AgriculturalLand();
        agriculturalLand.setProviderId(userId);
        agriculturalLand.setProviderName(textViewName.getText().toString());
        agriculturalLand.setProviderPhone(textViewPhone.getText().toString());
        agriculturalLand.setLandAddress(textViewLocation.getText().toString());
        agriculturalLand.setLandType(textViewType.getText().toString());
        agriculturalLand.setRentPerYear(textViewRent.getText().toString());
        agriculturalLand.setLandArea(textViewArea.getText().toString());
        agriculturalLand.setLandLatitude("18.677225");
        agriculturalLand.setLandLongitude("73.844797");
        agriculturalLand.setLandStatus("Visible to All");
    }


    public void getFarmId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ID id = snapshot.getValue(ID.class);
                farmId = id.getFieldId();
                Log.d(TAG, "onDataChange: Farm Id "+farmId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  Log.d("RegAct", "onDataChange2: "+ textViewUid.getText().toString());

    }

    public void getUserId(){
        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userId = user.getUserId();
                Log.d("", "onDataChange: User ID"+userId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addLand(){
        FirebaseDatabase.getInstance().getReference("Land-Renting").child(farmId).setValue(agriculturalLand);
        startActivity(new Intent(getApplicationContext(),AgriLandAddedActivity.class));
    }

}