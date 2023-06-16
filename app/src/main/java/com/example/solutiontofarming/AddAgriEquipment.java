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
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.Availability;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Owner;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.data.User;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAgriEquipment extends AppCompatActivity{

    final String TAG = "AddAgriEquipment";
    EditText editTextEquipmentOwner,getEditTextEquipmentOwnerContact,editTextEquipmentLocation,editTextEquipmentName, editTextEquipmentRent,editTextEquipmentDetails,
                editTextYearsUsed,editTextAvailableFrom,editTextAvailableTill;
    Spinner spinEquipmentCategory;
    TextView textView;
    Button btnAddEquipment,buttonAddImage, buttonAddAvailability;
    String[] agriEquipmentCategory = {"Select","Tractor","Sprayer","Field Cultivator","Shredders And Cutters","Seeders And Planters","Soil Cultivation","Irrigation","Other"};
    //AgriculturalEquipment agriculturalEquipment;
    AgriEquipment agriEquipment;
    String equipmentCategory;
    String userId,equipmentId;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agri_equipment);

        getSupportActionBar().setTitle("Add Equipment");

        bindComponents();
        addListeners();
        initSpinner();
        getUserId();

    }

    public void bindComponents(){
        editTextEquipmentName = findViewById(R.id.edit_agri_equip_name);
        editTextYearsUsed = findViewById(R.id.edit_agri_equip_year_used);
        editTextEquipmentOwner = findViewById(R.id.edit_equip_owner_name);
        getEditTextEquipmentOwnerContact = findViewById(R.id.edit_equip_owner_contact);
        editTextEquipmentName = findViewById(R.id.edit_agri_equip_name);
        editTextEquipmentRent = findViewById(R.id.edit_agri_equip_rent);
        spinEquipmentCategory = findViewById(R.id.spin_agri_equip_cat);
        editTextAvailableFrom = findViewById(R.id.edit_agri_euip_avialable_from);
        editTextAvailableTill = findViewById(R.id.edit_agri_euip_avialable_to);
        buttonAddAvailability = findViewById(R.id.btn_add_new_availability);
        buttonAddImage = findViewById(R.id.btn_add_img_equip);
        editTextEquipmentLocation = findViewById(R.id.edit_agri_equip_location);
        //editTextEquipmentLocation.setFocusable(false);
        btnAddEquipment = findViewById(R.id.btn_add_agri_equip);
        textView = findViewById(R.id.textView52);
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

    private void initSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,agriEquipmentCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEquipmentCategory.setAdapter(arrayAdapter);
    }


    public void addEquipment(){
        Log.d("TAG", "getTransport: "+agriEquipment.toString());

        Gson gson = new Gson();
        String json = gson.toJson(agriEquipment);

        JSONObject jsonObjectTransportRide = null;
        try {
            jsonObjectTransportRide = new JSONObject(json);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }

//        JsonObject jsonObject = gson.fromJson(json, (Type) Fare.class);
        Log.d("TAG", "onCreate: JsonObject"+jsonObjectTransportRide);
        Log.d("ad", "addRide: "+"Equipment added");
        //startActivity(new Intent(getApplicationContext(),EquipmentAddedActivity.class));
    }

    public boolean validDetails(){

        if(spinEquipmentCategory.getSelectedItem().toString().equals(agriEquipmentCategory[0])){
            textView.setError("Select Equipment Category");
            textView.requestFocus();
            return false;
        }
        if(editTextEquipmentName.getText().toString().isEmpty()){
            editTextEquipmentName.setError("Please Enter Equipment Name");
            editTextEquipmentName.requestFocus();
            return false;
        }
        if(editTextYearsUsed.getText().toString().isEmpty()){
            editTextYearsUsed.setError("Please Enter Equipment Condition");
            editTextYearsUsed.requestFocus();
            return false;
        }
        if(editTextEquipmentRent.getText().toString().isEmpty()){
            editTextEquipmentRent.setError("Please Enter Rent");
            editTextEquipmentRent.requestFocus();
            return false;
        }
        if(editTextAvailableFrom.getText().toString().equals("")){
            editTextAvailableFrom.setError("Please Select Availability");
            editTextAvailableFrom.requestFocus();
            return false;
        }
        if(editTextAvailableTill.getText().toString().equals("")){
            editTextAvailableTill.setError("Please Select Availability");
            editTextAvailableTill.requestFocus();
            return false;
        }
//        if(editTextEquipmentLocation.getText().toString().equals("")){
//            editTextEquipmentLocation.setError("Please Select Location of Equipment");
//            editTextEquipmentLocation.requestFocus();
//            return false;
//        }
        if(editTextEquipmentOwner.getText().toString().isEmpty()){
            editTextEquipmentOwner.setError("Please Enter Owner Name");
            editTextEquipmentOwner.requestFocus();
            return false;
        }
        String contact = getEditTextEquipmentOwnerContact.getText().toString();
        String regNoRegex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        Pattern pattern = Pattern.compile(regNoRegex);
        Matcher matcher = pattern.matcher(contact);
        if(!matcher.matches()){
            getEditTextEquipmentOwnerContact.setError("Please Enter Valid Contact");
            getEditTextEquipmentOwnerContact.requestFocus();
            return false;
        }

        return true;
    }

    public void setEquipmentDetails(){
        List<Availability> availabilityList = new ArrayList<>();
        availabilityList.add(new Availability(
                editTextAvailableFrom.getText().toString(),
                editTextAvailableTill.getText().toString()
        ));

//        Address address = new Address(
//            String.valueOf(place.getLatLng().latitude),
//            String.valueOf(place.getLatLng().longitude),
//            place.getAddress(),
//            place.getName()
//        );

        Address address = new Address("lat", "lon","Addresss", "name");

        Owner owner = new Owner(
            editTextEquipmentOwner.getText().toString(),
            getEditTextEquipmentOwnerContact.getText().toString()
        );

        agriEquipment = new AgriEquipment(
                userId,
                spinEquipmentCategory.getSelectedItem().toString(),
                editTextEquipmentName.getText().toString(),
                editTextYearsUsed.getText().toString(),
                editTextEquipmentRent.getText().toString(),
                "",
                availabilityList,
                address,
                owner
        );

    }


    public void getUserId(){
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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


}