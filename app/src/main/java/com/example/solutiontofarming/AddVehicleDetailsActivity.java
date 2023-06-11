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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.Goods;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.data.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVehicleDetailsActivity extends AppCompatActivity{

    Button btnContinue;
    EditText editTextVehicleNo, editTextLimit,editTextGoodsName, editTextVehicleName,editTextContainerType;
    Spinner spinnerWeightUnit,spinnerGoodsCategory;
    TextView textViewOther;
    RadioButton radioButtonPickUp, radioButtonRefrigeratedTruck,radioButtonTipperTruck,radioButtonTrailer, radioButtonTractorTrolley, radioButtonOther;
    String vehicleOther, goodsCategory;

    String vehicleType;
    Transport transport;

    TransportRide transportRide = null;
    String weightUnit = null;
    TextView textViewTypeOfVehicle;
    List<String> weightUnitList,goodsTypeList;
    final String TAG = "AddVehicleDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_details);

        getSupportActionBar().setTitle("Vehicle Details");
        bindComponents();
        initSpinners();
        addListeners();
    }

    public void bindComponents(){
        transport = (Transport) getIntent().getSerializableExtra("currentTransport");
        weightUnitList = new ArrayList(Arrays.asList(new String[]{"KG", "Quintal", "Tonne"}));
        goodsTypeList = new ArrayList(Arrays.asList(new String[]{"Cereals", "Pulses", "Nuts", "Sugar and starch", "Fiber crops", "Spices and condiments", "Rubber forages","Green and green leaf manure","Other"}));

        this.textViewTypeOfVehicle = findViewById(R.id.textView50);
        this.spinnerWeightUnit = findViewById(R.id.spin_weight_unit);
        this.editTextLimit = findViewById(R.id.edit_available_load);
        this.editTextVehicleNo = findViewById(R.id.edit_reg_vehicle_no);
        this.btnContinue = findViewById(R.id.btn_continue_add_vehicle);
        this.radioButtonPickUp = findViewById(R.id.radio_btn_pick_up);
        this.radioButtonRefrigeratedTruck = findViewById(R.id.radio_btn_refrigarated_truck);
        this.radioButtonTipperTruck = findViewById(R.id.radio_btn_tipper);
        this.radioButtonTrailer = findViewById(R.id.radio_btn_trailer);

        radioButtonTractorTrolley = findViewById(R.id.radio_btn_tractor_trolley);
        radioButtonOther = findViewById(R.id.radio_btn_other_vehicle);
        editTextVehicleName = findViewById(R.id.edit_vehicle_type_other);
        editTextGoodsName = findViewById(R.id.edit_text_goods_name);
        textViewOther = findViewById(R.id.textViewOther);
        spinnerGoodsCategory = findViewById(R.id.spinner_goods_category);
        editTextContainerType = findViewById(R.id.edit_vehicle_container_type);
    }

    private void initSpinners(){
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, weightUnitList);
        spinnerWeightUnit.setAdapter(unitAdapter);

        ArrayAdapter<String> goodsCategoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, goodsTypeList);
        spinnerGoodsCategory.setAdapter(goodsCategoryAdapter);
        spinnerGoodsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), TransportServiceActivity.class));
    }

    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyVehicleDetails()){
                    Intent intent = new Intent(getApplicationContext(), AddDriverDetailsActivity.class);
                    transportRide = (TransportRide) getIntent().getSerializableExtra("EXTRA_RIDE_OBJ");
                    addVehicleDetails();
                    intent.putExtra("EXTRA_RIDE_OBJ", (Serializable) transportRide);
                    Log.d("TAG", "onClick: "+transportRide.toString());
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
                radioButtonRefrigeratedTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                radioButtonTractorTrolley.setChecked(false);
                radioButtonOther.setChecked(false);
                vehicleType = "Pick Up Truck";
                Log.d(TAG, "onClick: "+vehicleType);
                unSetOtherVehicleVisible();
            }
        });

        this.radioButtonRefrigeratedTruck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                radioButtonTractorTrolley.setChecked(false);
                radioButtonOther.setChecked(false);
                vehicleType = "Refrigerated Truck";
                Log.d(TAG, "onClick: "+vehicleType);
                unSetOtherVehicleVisible();
            }
        });

        this.radioButtonTipperTruck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonRefrigeratedTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                radioButtonTractorTrolley.setChecked(false);
                radioButtonOther.setChecked(false);
                vehicleType = "Tipper Truck";
                Log.d(TAG, "onClick: "+vehicleType);
                unSetOtherVehicleVisible();

            }
        });
        this.radioButtonTrailer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonRefrigeratedTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTractorTrolley.setChecked(false);
                radioButtonOther.setChecked(false);
                vehicleType = "Trailer Truck";
                Log.d(TAG, "onClick: "+vehicleType);
                unSetOtherVehicleVisible();
            }
        });

        this.radioButtonTractorTrolley.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonRefrigeratedTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                radioButtonOther.setChecked(false);
                vehicleType = "Tractor Trolley";
                Log.d(TAG, "onClick: "+vehicleType);
                unSetOtherVehicleVisible();
            }
        });this.radioButtonOther.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                textViewTypeOfVehicle.clearFocus();
                textViewTypeOfVehicle.setError(null);
                radioButtonPickUp.setChecked(false);
                radioButtonRefrigeratedTruck.setChecked(false);
                radioButtonTipperTruck.setChecked(false);
                radioButtonTrailer.setChecked(false);
                radioButtonTractorTrolley.setChecked(false);
                vehicleType = "Trailer Truck";
                Log.d(TAG, "onClick: "+vehicleType);
                setOtherVehicleVisible();
            }
        });

    }

    private void addVehicleDetails(){
        Vehicle vehicle = new Vehicle(
                vehicleType,
                editTextContainerType.getText().toString(),
                editTextVehicleNo.getText().toString(),
                editTextLimit.getText().toString(),
                spinnerWeightUnit.getSelectedItem().toString()
        );

        Goods goods = new Goods(
                spinnerGoodsCategory.getSelectedItem().toString(),
                editTextGoodsName.getText().toString()
        );
        transportRide.setVehicle(vehicle);
        transportRide.setGoods(goods);
    }
    private void setOtherVehicleVisible(){
        textViewOther.setVisibility(View.VISIBLE);
        editTextVehicleName.setVisibility(View.VISIBLE);
    }

    private void unSetOtherVehicleVisible(){
        textViewOther.setVisibility(View.GONE);
        editTextVehicleName.setVisibility(View.GONE);
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

        if(radioButtonOther.isChecked()){
            if(editTextVehicleName.getText().toString().equals("")){
                editTextVehicleName.setError("Enter Vehicle Type");
                editTextVehicleName.requestFocus();
                return false;
            }
        }

        if(editTextContainerType.getText().toString().equals("")){
            editTextContainerType.setError("Please Enter Container Type");
            editTextContainerType.requestFocus();
            return false;
        }

        if(!matcher.matches()){
            editTextVehicleNo.setError("Enter Valid Registration No");
            editTextVehicleNo.requestFocus();
            return false;
        }

        if(editTextVehicleNo.getText().toString().isEmpty()){
            editTextVehicleNo.setError("PLase Enter Vehicle Number");
            editTextVehicleNo.requestFocus();
            return false;
        }

        if(editTextGoodsName.getText().toString().equals("")){
            editTextGoodsName.setError("Enter Goods Name");
            editTextGoodsName.requestFocus();
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