package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.Warehouse;
import com.example.solutiontofarming.getallapicalls.GetAllEquipments;
import com.example.solutiontofarming.getallapicalls.GetAllWarehouses;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ShowAvailableWarehouseActivity extends AppCompatActivity  {

    WareHouseAdapter wareHouseAdapter;
    ArrayList<Warehouse> allWarehouseArrayList;
    ArrayList<Warehouse> filteredList;
    ShimmerFrameLayout shimmer_layout;
    ListView listViewWarehouses;
    EditText editTextLocation;
    ImageView imageViewClear;
    TextView textViewWarehouseFilter;
    Address address;
    ArrayList<String> listDistances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_warehouse);
        bindComponents();
        initListView();
        getAllWareHouses();
        initPlaces();
    }

    private void initPlaces(){
        Places.initialize(getApplicationContext(), Extras.API_KEY);
    }

    private void bindComponents(){
        shimmer_layout = findViewById(R.id.shimmer_activity_available_warehouses);
        shimmer_layout.startShimmerAnimation();

        listViewWarehouses = findViewById(R.id.list_view_warehouses);
        editTextLocation = findViewById(R.id.edit_select_lo_ware_search);
        editTextLocation.setFocusable(false);
        imageViewClear = findViewById(R.id.image_clear_location);
        textViewWarehouseFilter = findViewById(R.id.text_ware_show_filter);
        editTextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(ShowAvailableWarehouseActivity.this);

                startActivityForResult(intent, 100);
            }
        });
        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
            }
        });
    }

    private void initListView(){
        allWarehouseArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();
        listDistances = new ArrayList<>();
        wareHouseAdapter = new WareHouseAdapter(this, (ArrayList<Warehouse>) filteredList, listDistances);
        listViewWarehouses.setAdapter(wareHouseAdapter);
        listViewWarehouses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowWarehouseDetailsActivity.class);
                intent.putExtra("EXTRA_WARE_HOUSE_OBJ", filteredList.get(position));
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            editTextLocation.setText(place.getName());
            address = new com.example.solutiontofarming.data.Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
            textViewWarehouseFilter.setText("Showing warehouse between 20km");
            filterWareHouseByLocation(address);
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void filterWareHouseByLocation(Address address){
        filteredList.clear();
        listDistances.clear();
        for(int i=0; i<allWarehouseArrayList.size(); i++){
            double distance = getDistance(allWarehouseArrayList.get(i).getAddress(), address);
            if(distance < 20){
                filteredList.add(allWarehouseArrayList.get(i));
                listDistances.add((Math.round(distance*10.0)/10.0)+" KM");
                wareHouseAdapter.notifyDataSetChanged();
            }
        }
        if(filteredList.size() == 0){
            Toast.makeText(this, "No Warehouses Available Near by", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFilter(){
        filteredList.clear();
        listDistances.clear();
        editTextLocation.setText("");
        textViewWarehouseFilter.setText("Showing All Rides");
        for(int i=0; i<allWarehouseArrayList.size(); i++){
            filteredList.add(allWarehouseArrayList.get(i));
            wareHouseAdapter.notifyDataSetChanged();
            listDistances.add("NA");
        }
    }

    private double getDistance(Address source, Address destination){
        return getDistanceBetweenGeoPts(
                Double.parseDouble(source.getLatitude()),
                Double.parseDouble(source.getLongitude()),
                Double.parseDouble(destination.getLatitude()),
                Double.parseDouble(destination.getLongitude())
        );
    }

    private double getDistanceBetweenGeoPts(double sLat, double sLon, double dLat, double dLon){
        Location start = new Location("");
        start.setLatitude(sLat);
        start.setLongitude(sLon);

        Location end = new Location("");
        end.setLatitude(dLat);
        end.setLongitude(dLon);
        return (start.distanceTo(end)/1000);
    }

    private void getAllWareHouses(){
        GetAllWarehouses getAllWarehouses = new GetAllWarehouses(this);

        getAllWarehouses.fetchAllWarehouses(new GetAllWarehouses.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                listViewWarehouses.setVisibility(View.VISIBLE);
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        Warehouse warehouse = new Gson().fromJson(jsonObject, Warehouse.class);
                        allWarehouseArrayList.add(warehouse);
                        filteredList.add(warehouse);
                        listDistances.add("NA");
                        wareHouseAdapter.notifyDataSetChanged();
                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+warehouse.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(VolleyError error) {
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
                Toast.makeText(ShowAvailableWarehouseActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
//

}