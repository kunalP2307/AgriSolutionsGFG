package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.solutiontofarming.data.ID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EquipmentAddedActivity extends AppCompatActivity {

    Button btnShowEquipAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_added);
        this.btnShowEquipAdded = findViewById(R.id.btn_show_agri_land_added);

        generateNextEquipmentId();

        btnShowEquipAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AgriEquipmentServiceActivity.class));
            }
        });
    }


    public void generateNextEquipmentId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID id = snapshot.getValue(ID.class);

                String eId = id.getWareHouseId().substring(1);
                int newEId = Integer.parseInt(eId) + 1;
                id.setWareHouseId("W"+Integer.toString(newEId));
                Log.d("", "onDataChange: temp  "+ newEId);
                FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").setValue(id);

                Log.d("", "onDataChange: ride ID : "+"W"+Integer.toString(newEId));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),AgriEquipmentServiceActivity.class));
    }
}