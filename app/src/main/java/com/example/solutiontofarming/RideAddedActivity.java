package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.TransportDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RideAddedActivity extends AppCompatActivity {
    private Button btnShowRide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_added);
        bindComponents();
        addListeners();
        generateNextRideId();


    }

    private void generateNextRideId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID id = snapshot.getValue(ID.class);

                String rId = id.getRideId().substring(1);
                int newRId = Integer.parseInt(rId) + 1;
                id.setRideId("R"+Integer.toString(newRId));
                Log.d("", "onDataChange: temp  "+ newRId);
                FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").setValue(id);


                Log.d("", "onDataChange: ride ID : "+"R"+Integer.toString(newRId));
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

    public void bindComponents(){
        btnShowRide = findViewById(R.id.btn_show_ride);
    }
    public void addListeners(){
        btnShowRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TransportServiceActivity.class));
            }
        });
    }
}