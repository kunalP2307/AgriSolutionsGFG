package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Transport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class ScheduleTransportActivity extends AppCompatActivity {
    private Button btnAddRide;
    private TextView textViewAddRide , textViewAddVehicle, textViewAddDriver;
    private ImageView imageButtonAlertRide, imageButtonAlertVehicle, imageButtonAlertDriver;
    private Transport currentTransport;
    String rideId,userId;
    private boolean rideDetailsStatus[] = {false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_transport);
        generateRideId();
        bindComponents();
        addListeners();
        getSupportActionBar().setTitle("Add Ride");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        currentTransport = (Transport) intent.getSerializableExtra("currentTransport");
        if(currentTransport != null) {


            if(currentTransport.getSourceAddress() !=""){
                imageButtonAlertRide = findViewById(R.id.img_status_ride);
                imageButtonAlertRide.setBackgroundColor(Color.GREEN);
                imageButtonAlertRide.setImageResource(R.drawable.check);
                textViewAddRide.setTextColor(Color.GREEN);
                rideDetailsStatus[0] = true;
            }
            if(currentTransport.getDriverName()!=null){
                imageButtonAlertDriver = findViewById(R.id.img_status_driver);
                imageButtonAlertDriver.setBackgroundColor(Color.GREEN);
                imageButtonAlertDriver.setImageResource(R.drawable.check);
                textViewAddDriver.setTextColor(Color.GREEN);
                rideDetailsStatus[1] = true;
            }
            if(currentTransport.getVehicleNo()!=null){
                imageButtonAlertVehicle = findViewById(R.id.img_status_vehicle);
                imageButtonAlertVehicle.setBackgroundColor(Color.GREEN);
                imageButtonAlertVehicle.setImageResource(R.drawable.check);
                textViewAddVehicle.setTextColor(Color.GREEN);
                rideDetailsStatus[2] = true;
            }

        }

    }

    public void bindComponents(){
        this.currentTransport = (Transport) getIntent().getSerializableExtra("currentTransport");
        this.btnAddRide = findViewById(R.id.btn_add_ride);
        this.imageButtonAlertRide = findViewById(R.id.img_status_ride);
        this.imageButtonAlertVehicle = findViewById(R.id.img_status_ride);
        this.imageButtonAlertDriver = findViewById(R.id.img_status_driver);
        this.textViewAddRide = findViewById(R.id.text_ride_details);
        this.textViewAddDriver = findViewById(R.id.text_driver_details);
        this.textViewAddVehicle = findViewById(R.id.text_vehicle_details);
    }

    public void addListeners(){
        this.btnAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRide(currentTransport);
            }
        });
        this.textViewAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTransport == null)
                    currentTransport = new Transport();

                Intent intent = new Intent(getApplicationContext(),AddRideDetailsActivity.class);
                intent.putExtra("currentTransport",currentTransport);
                startActivity(intent);
            }
        });
        this.textViewAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddVehicleDetailsActivity.class);
                intent.putExtra("currentTransport",currentTransport);
                startActivity(intent);
            }
        });
        this.textViewAddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddDriverDetailsActivity.class);
                intent.putExtra("currentTransport",currentTransport);
                startActivity(intent);
            }
        });
    }

    public void addRide(Transport transport){
        if(rideDetailsStatus[0]||rideDetailsStatus[1]||rideDetailsStatus[2]){
            FirebaseDatabase.getInstance().getReference("Transport-Rides").child(rideId).setValue(currentTransport);
            startActivity(new Intent(getApplicationContext(),RideAddedActivity.class));
        }
        Log.d("ad", "addRide: "+"ride added");
    }
    private void generateRideId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID id = snapshot.getValue(ID.class);
                rideId = id.getRideId();
                Log.d("", "onDataChange: ride ID"+rideId);
                //getId(userId);
                /*if(id != null){
                   textViewUid.setText(id.getUserId());
                   Log.d("RegAct", "onDataChange: "+ textViewUid.getText().toString());
               }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  Log.d("RegAct", "onDataChange2: "+ textViewUid.getText().toString());

    }

}