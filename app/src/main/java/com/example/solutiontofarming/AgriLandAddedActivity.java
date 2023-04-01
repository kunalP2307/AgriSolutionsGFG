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

public class AgriLandAddedActivity extends AppCompatActivity {

    Button btnLandAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agri_land_added);
        this.btnLandAdded = findViewById(R.id.btn_show_agri_land_added);

        generateNextFarmId();

        btnLandAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandRentServiceActivity.class));
            }
        });
    }


    public void generateNextFarmId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID id = snapshot.getValue(ID.class);

                String fId = id.getFieldId().substring(1);
                int newFId = Integer.parseInt(fId) + 1;
                id.setFieldId("F"+Integer.toString(newFId));
                Log.d("", "onDataChange: temp  "+ newFId);
                FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").setValue(id);

                Log.d("", "onDataChange: Field Id : "+"F"+Integer.toString(newFId));
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
        startActivity(new Intent(getApplicationContext(),LandRentServiceActivity.class));
    }
}