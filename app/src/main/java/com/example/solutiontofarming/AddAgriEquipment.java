package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAgriEquipment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final String TAG = "AddAgriEquipment";
    EditText editTextEquipmentOwner,getEditTextEquipmentOwnerMobile,editTextEquipmentLocation,editTextEquipmentName,editTextEquipmentRentHour,editTextEquipmentDetails;
    Spinner spinEquipmentCategory;
    Button btnAddEquipment;
    String[] agriEquipmentCategory = {"Tractor","Sprayer","Field Cultivator","Shredders And Cutters","Seeders And Planters","Soil Cultivation","Irrigation","Other"};
    AgriculturalEquipment agriculturalEquipment;
    String equipmentCategory;
    String userId,equipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agri_equipment);

        getSupportActionBar().setTitle("Add Equipment");

        bindComponents();
        addListeners();
        getUserId();
        getEquipmentId();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,agriEquipmentCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEquipmentCategory.setAdapter(arrayAdapter);

    }

    public void bindComponents(){
        this.editTextEquipmentOwner = findViewById(R.id.edit_equip_owner_name);
        this.getEditTextEquipmentOwnerMobile = findViewById(R.id.edit_equip_owner_mobile_no);
        this.editTextEquipmentLocation = findViewById(R.id.edit_agri_equip_location);
        this.editTextEquipmentName = findViewById(R.id.edit_agri_equip_name);
        this.editTextEquipmentRentHour = findViewById(R.id.edit_agri_equip_rent);
        this.editTextEquipmentDetails = findViewById(R.id.edit_agri_euip_details);
        this.btnAddEquipment = findViewById(R.id.btn_add_agri_equip);
        this.spinEquipmentCategory = findViewById(R.id.spin_agri_equip_cat);
        spinEquipmentCategory.setOnItemSelectedListener(this);

    }


    public void addListeners(){
        this.btnAddEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validDetails()) {
                    setEquipmentDetails();
                    addEquipment();
                }
            }
        });
    }

    public void setEquipmentDetails(){

        agriculturalEquipment = new AgriculturalEquipment("U1F"
                ,editTextEquipmentOwner.getText().toString()
                ,getEditTextEquipmentOwnerMobile.getText().toString()
                ,equipmentCategory
                ,editTextEquipmentName.getText().toString()
                ,editTextEquipmentRentHour.getText().toString()
                ,editTextEquipmentDetails.getText().toString()
                ,editTextEquipmentLocation.getText().toString());

    }

    public void addEquipment(){
        FirebaseDatabase.getInstance().getReference("Agricultural-Equipment").child(equipmentId).setValue(agriculturalEquipment);
        Log.d("ad", "addRide: "+"Equipment added");
        startActivity(new Intent(getApplicationContext(),EquipmentAddedActivity.class));
    }

    public boolean validDetails(){
        if(editTextEquipmentOwner.getText().toString().isEmpty()){
            editTextEquipmentOwner.setError("Please Enter Owner Name");
            editTextEquipmentOwner.requestFocus();
            return false;
        }
        if(getEditTextEquipmentOwnerMobile.getText().toString().isEmpty()){
            getEditTextEquipmentOwnerMobile.setError("Please Enter Mobile Number");
            getEditTextEquipmentOwnerMobile.requestFocus();
            return false;
        }
        if(getEditTextEquipmentOwnerMobile.getText().toString().length() != 10){
            getEditTextEquipmentOwnerMobile.setError("Please Enter Valid Mobile Number");
            getEditTextEquipmentOwnerMobile.requestFocus();
            return false;
        }
        if(editTextEquipmentLocation.getText().toString().isEmpty()){
            editTextEquipmentLocation.setError("Please Enter Location");
            editTextEquipmentLocation.requestFocus();
            return false;
        }
        if(editTextEquipmentName.getText().toString().isEmpty()){
            editTextEquipmentName.setError("Please Enter Equipment Name");
            editTextEquipmentName.requestFocus();
            return false;
        }
        if(editTextEquipmentRentHour.getText().toString().isEmpty()){
            editTextEquipmentRentHour.setError("Please Enter Rent Per Hour");
            editTextEquipmentRentHour.requestFocus();
            return false;
        }
        return true;
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

    public void getEquipmentId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ID id = snapshot.getValue(ID.class);
                equipmentId = id.getWareHouseId();

                Log.d(TAG, "onDataChange: Equipment ID :"+equipmentId);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(),
                agriEquipmentCategory[position],
                Toast.LENGTH_LONG)
                .show();
        equipmentCategory = agriEquipmentCategory[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}