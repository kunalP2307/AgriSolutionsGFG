package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowRideDetailsActivity extends AppCompatActivity {

    TextView textViewDate,textViewTime,textViewSource,textViewDestination,textViewRoute,textViewVehicleType,textViewAvailableLoad,textViewPricePerKm,textViewDriverName;
    Button btnCall,btnGetRide;
    Transport transport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ride_details);
        transport = (Transport) getIntent().getSerializableExtra("selectedRide");
        getSupportActionBar().setTitle("Ride Details");

        bindComponents();
        showRideDetails();
        addListeners();

    }
    public void bindComponents(){
        this.textViewSource = findViewById(R.id.text_ride_details_source);
        this.textViewDestination = findViewById(R.id.text_ride_details_destination);
        this.textViewDate = findViewById(R.id.text_ride_details_date);
        this.textViewTime = findViewById(R.id.text_ride_details_time);
        this.textViewPricePerKm = findViewById(R.id.text_ride_details_price_per_km);
        this.textViewAvailableLoad = findViewById(R.id.text_view_ride_details_available_load);
        this.textViewDriverName = findViewById(R.id.text_ride_details_driver_name);
        this.textViewVehicleType = findViewById(R.id.text_view_ride_details_vehicle_type);
        this.btnCall = findViewById(R.id.btn_view_ride_details_call);
        this.textViewRoute = findViewById(R.id.text_ride_details_view_route);
        this.btnGetRide = findViewById(R.id.btn_view_ride_details_get_this_ride);
    }
    public void showRideDetails(){
        textViewSource.setText(transport.getSourceAddress());
        textViewDestination.setText(transport.getDestinationAddress());
        textViewDate.setText(transport.getRideDate()+"  "+transport.getRideTime());
        //textViewTime.setText(transport.getRideTime());
        textViewPricePerKm.setText(transport.getPricePerKm());
        textViewVehicleType.setText(transport.getVehicleType());
        textViewAvailableLoad.setText(transport.getAvailableLoad());
        textViewDriverName.setText(transport.getDriverName());
    }

    public void addListeners(){
        this.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + transport.getDrivePhone());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        this.btnGetRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestRideIntent = new Intent(getApplicationContext(),GetRideActivity.class);
                requestRideIntent.putExtra("phone",transport.getDrivePhone());
                requestRideIntent.putExtra("name",transport.getDriverName());
                requestRideIntent.putExtra("date",transport.getRideDate());
                startActivity(requestRideIntent);
            }
        });

        this.textViewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String geoPoints = transport.getGeoPoints();
                //Log.d("", "onClick: "+geoPoints);
                Intent intent;
                if(transport.getVehicleNo().equals("MH12 AB3221")) {
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=18.603009,73.786869&daddr=20.896545,76.202581"));
                }
                else if(transport.getVehicleNo().equals("MH14 AC4324")){
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=18.6577012,73.8469152&daddr=19.6846007,73.56266738"));
                }
                else{
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=18.604173,73.788809&daddr=21.133324,79.083752"));
                }
                startActivity(intent);
            }
        });
        
    }
}