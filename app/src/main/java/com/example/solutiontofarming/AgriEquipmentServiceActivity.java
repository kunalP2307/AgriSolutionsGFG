package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgriEquipmentServiceActivity extends AppCompatActivity {

    final String TAG = "AgriEquipmentSeomrviceActivity";
    List<AgriculturalEquipment> agriculturalEquipmentsList = new ArrayList<>();
    String userId;
    CardView imageViewFindEquip,imageViewAddEquip,imageViewMyEquipments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agri_equipment_service);
        intiEquipments();
        bindComponents();
        addLisnteners();
        getUserDetails();
        getSupportActionBar().setTitle("Rent In/Out Equipment");

    }
    public void bindComponents(){
        this.imageViewAddEquip = findViewById(R.id.card_add_agri_equip);
        this.imageViewFindEquip = findViewById(R.id.card_borrow_ware_house);
        this.imageViewMyEquipments = findViewById(R.id.card_my_warehouses);
    }

    public void addLisnteners(){
        this.imageViewAddEquip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddAgriEquipDetailsActivity.class));
            }
        });
        this.imageViewFindEquip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowAvailableAgriEquipmentsActivity.class);

                intent.putExtra("availableEquipment", (Serializable) agriculturalEquipmentsList);
                startActivity(intent);

            }
        });
        this.imageViewMyEquipments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowAvailableAgriEquipmentsActivity.class);
                intent.putExtra("availableEquipment", (Serializable) agriculturalEquipmentsList);

                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }

    public void intiEquipments(){

        FirebaseDatabase.getInstance().getReference("Agricultural-Equipment").addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                agriculturalEquipmentsList.clear();
                Log.d(TAG, "onDataChange: inside Function");
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    AgriculturalEquipment agriculturalEquipment = postSnapshot.getValue(AgriculturalEquipment.class);
                    agriculturalEquipmentsList.add(agriculturalEquipment);
                    Log.d("", "onDataChange: equipmentList Size "+agriculturalEquipmentsList.size());
                    //Log.d("", "onDataChange: "+transport.getVehicleNo());
                }
                //Log.d(TAG, "onDataChange : Size "+transportList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserDetails(){

        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user  = snapshot.getValue(User.class);
                userId = user.getUserId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}