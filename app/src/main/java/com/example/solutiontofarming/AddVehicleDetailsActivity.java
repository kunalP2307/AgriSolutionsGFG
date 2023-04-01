package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVehicleDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnContinue;
    EditText editTextVehicleNo, editTextLimit;
    Spinner spinnerWeightUnit;
    RadioButton radioButtonPickUp,radioButtonLiveStockTruck,radioButtonTipperTruck,radioButtonTrailer;
    String vehicleType;
    Transport transport;
    String weightUnit = null;
    TextView textViewTypeOfVehicle;
    List<String> weightUnitList;
    final String TAG = "AddVehicleDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_details);

        bindComponents();
        spinnerWeightUnit.setOnItemSelectedListener(this);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weightUnitList);
        spinnerWeightUnit.setAdapter(unitAdapter);
        getSupportActionBar().setTitle("Vehicle Details");

        addListeners();
    }

    public void bindComponents(){
        transport = (Transport) getIntent().getSerializableExtra("currentTransport");
        weightUnitList = new ArrayList<String>();
        weightUnitList.add("NA");
        weightUnitList.add("Quintal");
        weightUnitList.add("Ton");
        this.textViewTypeOfVehicle = findViewById(R.id.textView22);
        this.spinnerWeightUnit = findViewById(R.id.spin_weight_unit);
        this.editTextLimit = findViewById(R.id.edit_available_load);
        this.editTextVehicleNo = findViewById(R.id.edit_reg_vehicle_no);
        this.btnContinue = findViewById(R.id.btn_continue_add_vehicle);
        this.radioButtonPickUp = findViewById(R.id.radio_btn_pick_up);
        this.radioButtonLiveStockTruck = findViewById(R.id.radio_btn_livestock);
        this.radioButtonTipperTruck = findViewById(R.id.radio_btn_tipper);
        this.radioButtonTrailer = findViewById(R.id.radio_btn_trailer);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        weightUnit = weightUnitList.get(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyVehicleDetails()){
                    Intent intent = new Intent(getApplicationContext(),ScheduleTransportActivity.class);
                    setVehicleDetails();
                    intent.putExtra("currentTransport",transport);
                    startActivity(intent);
                }
            }
        });
        this.radioButtonPickUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonLiveStockTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                vehicleType = "Pick Up Truck";
                Log.d(TAG, "onClick: "+vehicleType);
            }
        });

        this.radioButtonLiveStockTruck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                vehicleType = "Livestock Truck";
                Log.d(TAG, "onClick: "+vehicleType);

            }
        });

        this.radioButtonTipperTruck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonLiveStockTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                vehicleType = "Tipper Truck";
                Log.d(TAG, "onClick: "+vehicleType);

            }
        });

        this.radioButtonTrailer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonLiveStockTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                vehicleType = "Trailer Truck";
                Log.d(TAG, "onClick: "+vehicleType);
            }
        });

    }


    public boolean verifyVehicleDetails(){

        String regNo = editTextVehicleNo.getText().toString();
        String regNoRegex = "^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";
        Pattern pattern = Pattern.compile(regNoRegex);
        Matcher matcher = pattern.matcher(regNo);

        if(vehicleType == null){
            textViewTypeOfVehicle.setError("Please Select Vehicle Type");
            textViewTypeOfVehicle.requestFocus();
            return false;
        }

        if(editTextVehicleNo.getText().toString().isEmpty()){
            editTextVehicleNo.setError("PLase Enter Vehicle Number");
            editTextVehicleNo.requestFocus();
            return false;
        }
        
        if(editTextLimit.getText().toString().isEmpty()){
            editTextLimit.setError("Please Enter Available Goods Limit");
            editTextLimit.requestFocus();
            return false;
        }


        return true;
    }

    public void setVehicleDetails(){
        transport.setVehicleNo(editTextVehicleNo.getText().toString());
        transport.setAvailableLoad(editTextLimit.getText().toString()+" "+weightUnit);
        transport.setVehicleType(vehicleType);
    }
}