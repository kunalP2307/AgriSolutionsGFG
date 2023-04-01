package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.solutiontofarming.data.TransportDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowTransportActivity extends AppCompatActivity {

    private TextView editTextVehicleNo;
    private TextView editTextSource;
    private TextView editTextDest;
    private Button btnCall;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transport);
        bindComponents();
        getSupportActionBar().setTitle("Bulletins");

        Intent intent = getIntent();

        /*FirebaseDatabase.getInstance().getReference("Transportation").child("ride1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TransportDetails transportDetails = snapshot.getValue(TransportDetails.class);

                if(transportDetails != null){
                    //final String vehicleNo = transportDetails.getVehicleNo();
                    //final String source = transportDetails.getSource();
                    //final String destination = transportDetails.getDestination();
                    ConstraintLayout constraintLayout = findViewById(R.id.show_rides);
                    constraintLayout.setVisibility(View.VISIBLE);
                    editTextVehicleNo.setText(transportDetails.getTransportDate());
                    editTextSource.setText(transportDetails.getSource());
                    editTextDest.setText(transportDetails.getDestination());
                    phone = transportDetails.getDriverPhoneNo();
                }
                else{
                    startActivity(new Intent(ShowTransportActivity.this, NoRidesActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + phone);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                startActivity(i);
            }
        });

    }
    public void bindComponents(){
        btnCall = findViewById(R.id.btn_contact);
        editTextVehicleNo = findViewById(R.id.edit_vehicle_no);
        editTextSource = findViewById(R.id.edit_source);
        editTextDest = findViewById(R.id.edit_dest);
    }

}