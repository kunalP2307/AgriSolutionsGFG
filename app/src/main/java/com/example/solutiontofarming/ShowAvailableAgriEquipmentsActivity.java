package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.getallapicalls.GetAllEquipments;
import com.example.solutiontofarming.getallapicalls.GetAllRides;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowAvailableAgriEquipmentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    AgriEquipmentAdapter agriEquipmentAdapter;
    Spinner spinnerEquipCat, spinnerSortByDistance;
    ArrayList<AgriEquipment> agriEquipmentArrayList;
    ArrayList<AgriEquipment> filteredList;
    CheckBox checkBoxLowToHigh;
    ShimmerFrameLayout shimmer_layout;
    ListView listViewAvailableAgriEquipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_agri_equipments);
        bindComponents();
        iniListView();
        getAllEquipments();
        initFilters();

    }

    private void initFilters(){

    }
    private void bindComponents(){
        listViewAvailableAgriEquipments = findViewById(R.id.list_available_agri_equipments);
        shimmer_layout = findViewById(R.id.shimmer_activity_available_equips);
        shimmer_layout.startShimmerAnimation();

        spinnerEquipCat = findViewById(R.id.spinner_select_equip_cat_show);
        ArrayAdapter arrayAdapterCat = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AddAgriEquipDetailsActivity.AGRI_EQUIPMENT_CATEGORY);
        arrayAdapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipCat.setAdapter(arrayAdapterCat);

        spinnerEquipCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 filterRidesByCategory(AddAgriEquipDetailsActivity.AGRI_EQUIPMENT_CATEGORY[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSortByDistance = findViewById(R.id.spinner_sort_by_distance);
        ArrayAdapter arrayAdapterDistance = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Select","Within 1KM", "Within 5KM", "Within 10KM"});
        arrayAdapterDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortByDistance.setAdapter(arrayAdapterDistance);

        spinnerSortByDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                    Toast.makeText(ShowAvailableAgriEquipmentsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBoxLowToHigh = findViewById(R.id.checkBox_sort_by_low_price);
        checkBoxLowToHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    filterRidesByLowToHighPrice();
                }
            }
        });
    }

    private void filterRidesByCategory(String category){
        if(category.equals(AddAgriEquipDetailsActivity.AGRI_EQUIPMENT_CATEGORY[0])){
            if(agriEquipmentArrayList.size()!=0){
                filteredList.clear();
                for(int i=0; i<agriEquipmentArrayList.size(); i++){
                    filteredList.add(agriEquipmentArrayList.get(i));
                    agriEquipmentAdapter.notifyDataSetChanged();
                }
            }
        }
        else{
            filteredList.clear();
            agriEquipmentAdapter.notifyDataSetChanged();
            for(int i=0; i<agriEquipmentArrayList.size(); i++){
                if(agriEquipmentArrayList.get(i).getCategory().equals(category)){
                    filteredList.add(agriEquipmentArrayList.get(i));
                    agriEquipmentAdapter.notifyDataSetChanged();
                }
            }
        }
        if(filteredList.size() == 0)
            Toast.makeText(this, "No Equipments Available", Toast.LENGTH_SHORT).show();
    }

    private void filterRidesByLowToHighPrice(){
        Log.d("TAG", "filterRidesByLowToHighPrice: ");
        class RentComparator implements Comparator<AgriEquipment>{
            public int compare(AgriEquipment o1, AgriEquipment o2){
                double o1Rent = Double.parseDouble(o1.getRentPerDay());
                double o2Rent = Double.parseDouble(o2.getRentPerDay());
                return Double.compare(o1Rent, o2Rent);
            }
        }
        Collections.sort(filteredList, new RentComparator());

        agriEquipmentAdapter.notifyDataSetChanged();
        Log.d("TAG", "filterRidesByLowToHighPrice: "+filteredList.get(0).getRentPerDay());

    }
    private void iniListView(){
        agriEquipmentArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();
        agriEquipmentAdapter = new AgriEquipmentAdapter(this, (ArrayList<AgriEquipment>) filteredList);
        listViewAvailableAgriEquipments.setAdapter(agriEquipmentAdapter);
        listViewAvailableAgriEquipments.setOnItemClickListener(this);
    }

    private void getAllEquipments(){

        GetAllEquipments getAllEquipments = new GetAllEquipments(this);

        getAllEquipments.fetchAllEquipments(new GetAllEquipments.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                listViewAvailableAgriEquipments.setVisibility(View.VISIBLE);
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        AgriEquipment agriEquipment = new Gson().fromJson(jsonObject, AgriEquipment.class);
                        agriEquipmentArrayList.add(agriEquipment);
                        filteredList.add(agriEquipment);
                        agriEquipmentAdapter.notifyDataSetChanged();
                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+agriEquipment.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(VolleyError error) {
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
                Toast.makeText(ShowAvailableAgriEquipmentsActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AgriEquipment selectedEquipment = (AgriEquipment) agriEquipmentAdapter.getItem(position);
        Intent showEquipmentDetailsIntent = new Intent(getApplicationContext(),ShowAgriEquipmentActivity.class);
        showEquipmentDetailsIntent.putExtra("selectedEquipment", selectedEquipment);
        startActivity(showEquipmentDetailsIntent);

    }

//    private void ignoreOnCreate(){
//
//        List<AgriEquipment> availableEquipments = (List<AgriEquipment>) getIntent().getSerializableExtra("availableEquipment");
//        String userId = getIntent().getStringExtra("userId");
//
//        Log.d("", "onCreate: "+userId);
//
//        if(userId != null){
//
//
//            for(int i=0;i<availableEquipments.size();i++){
//                List<AgriculturalEquipment> newList = new ArrayList<AgriculturalEquipment>();
//                if(availableEquipments.get(i).getEquipmentProviderId().equals(userId)){
//                    newList.add(availableEquipments.get(i));
//
//                    listViewAvailableAgriEquipments = findViewById(R.id.list_available_agri_equipments);
//                    agriEquipmentAdapter = new AgriEquipmentAdapter(this, (ArrayList<AgriculturalEquipment>) newList);
//                    listViewAvailableAgriEquipments.setAdapter(agriEquipmentAdapter);
//                    listViewAvailableAgriEquipments.setOnItemClickListener(this);
//
//                }
//            }
//        }
//        else {
//
//            listViewAvailableAgriEquipments = findViewById(R.id.list_available_agri_equipments);
//            agriEquipmentAdapter = new AgriEquipmentAdapter(this, (ArrayList<AgriEquipment>) availableEquipments);
//            listViewAvailableAgriEquipments.setAdapter(agriEquipmentAdapter);
//            listViewAvailableAgriEquipments.setOnItemClickListener(this);
//            getSupportActionBar().setTitle("All Equipments");
//        }
//    }

}