package com.example.solutiontofarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.getallapicalls.GetAllRides;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SearchTransportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    final String TAG = "SearchTransportActivity";
    EditText editTextSource,editTextDestination,editTextDate;
    CheckBox checkBoxFlexWithDate;
    Button btnFindRide;
    int year,month,day;
    int myday, myMonth, myYear;
    List<Transport> transportList = new ArrayList<>();
    List<Transport> temp = new ArrayList<>();
    String source,destination,date;
    final int SOURCE_REQ = 100, DEST_REQ = 200;
    ProgressDialog dialog;
    Address sourcePlace, destPlace;
    List<TransportRide> allRidesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transport);
        bindComponents();
        addListener();
        getSupportActionBar().setTitle("Find Ride");

        temp = (List<Transport>) getIntent().getSerializableExtra("availableRides");
        transportList = new ArrayList<>();
        initPlaces();

    }
    public void bindComponents(){
        this.editTextDate = findViewById(R.id.edit_transport_date_search);
        this.editTextSource = findViewById(R.id.edit_source_address_search);
        this.editTextDestination = findViewById(R.id.edit_destination_address_search);
        this.btnFindRide = findViewById(R.id.btn_search_ride);
        editTextSource.setFocusable(false);
        editTextDestination.setFocusable(false);
        editTextDate.setFocusable(false);
        checkBoxFlexWithDate = findViewById(R.id.check_box_flex_with_date_ride_search);
    }
    private void setProgressDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    public void addListener(){
        this.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchTransportActivity.this, SearchTransportActivity.this,year, month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Log.d(TAG, "onClick: date "+year+month+day);
                datePickerDialog.show();
            }
        });

        editTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SearchTransportActivity.this);

                startActivityForResult(intent, SOURCE_REQ);
            }
        });

        editTextDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SearchTransportActivity.this);

                startActivityForResult(intent, DEST_REQ);
            }
        });
        this.btnFindRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validDetails()){
                    setProgressDialog();
                    searchForAvailableRides();
                }
            }
        });
    }

    private void initPlaces(){
        Places.initialize(getApplicationContext(), Extras.API_KEY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SOURCE_REQ && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            editTextSource.setText(place.getName());
            sourcePlace = new Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
        }
        if(requestCode == DEST_REQ && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            editTextDestination.setText(place.getName());
            destPlace = new Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean validDetails(){
        if(editTextSource.getText().toString().isEmpty()){
            editTextSource.setError("Please Select Source Address");
            editTextSource.requestFocus();
            return false;
        }
        if(editTextDestination.getText().toString().isEmpty()){
            editTextDestination.setError("Please Select Destination Address");
            editTextDestination.requestFocus();
            return false;
        }
        if(editTextDate.getText().toString().isEmpty()){
            editTextDate.setError("Please Select Transport Date");
            editTextDate.requestFocus();
            return false;
        }

        return true;
    }
    public void addRequestedRides(){

        source = editTextSource.getText().toString();
        destination = editTextDestination.getText().toString();
        date = editTextDate.getText().toString();
        boolean status = false;
        transportList.clear();
        for(int i=0; i<temp.size(); i++){
            Transport transport = temp.get(i);
            if(transport.getSourceAddress().contains(source) || transport.getDestinationAddress().contains(destination) || transport.getRideDescription().contains(source) || transport.getRideDescription().contains(destination)){
                if(transport.getRideDate().contains(date)){
                    transportList.add(transport);
                }
            }
        }
        Log.d(TAG, "addRequestedRides: all rides size "+temp.size());
        Log.d(TAG, "addRequestedRides: searched Rides size"+transportList.size());
    }

    private void searchForAvailableRides(){

        GetAllRides getAllRides = new GetAllRides(this);

        getAllRides.fetchAllRides(new GetAllRides.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        TransportRide transportRide = new Gson().fromJson(jsonObject, TransportRide.class);
                        allRidesList.add(transportRide);
                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+transportRide.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                filterRidesByDate();
            }

            @Override
            public void onError(VolleyError error) {
                dialog.hide();
                Toast.makeText(SearchTransportActivity.this, "Something went wrong try Later!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filterRidesByDate(){
        List<TransportRide> filteredRides = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        String searchedDate = editTextDate.getText().toString();
        dates.add(searchedDate);
        LocalDate givenDate = null;
        DateTimeFormatter formatter = null;

        if(checkBoxFlexWithDate.isChecked()){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                givenDate = LocalDate.parse(searchedDate, formatter);
            }
            for (int i = 1; i <= 2; i++) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate nextDate = givenDate.plusDays(i);
                    LocalDate previousDate = givenDate.minusDays(i);
                    dates.add(nextDate.format(formatter));
                    dates.add(previousDate.format(formatter));
                }
            }
        }

        filteredRides.clear();
        for(int i=0; i<allRidesList.size(); i++){
            if(dates.contains(allRidesList.get(i).getWhen().getDate().toString())){
                filteredRides.add(allRidesList.get(i));
            }
        }

        Intent intent = new Intent(getApplicationContext(), ShowAvailableRidesActivity.class);
        intent.putExtra("RIDE_SHOW_TYPE", "SEARCHED");
        intent.putExtra("RIDES_LIST", (Serializable) filteredRides);
        intent.putExtra("EXTRA_SOURCE_SEARCH", sourcePlace);
        intent.putExtra("EXTRA_DEST_SEARCH", destPlace);
        Log.d(TAG, "onSuccess:SIZE in JSOn Array "+allRidesList.size());
        dialog.hide();
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        myYear = year;
        myday = dayOfMonth;
        myMonth = month+1;
        Log.d(TAG, "onDateSet: month"+myMonth+"day"+myday);
        String date = myday+"-"+myMonth+"-"+myYear;
        editTextDate.setText(date);
    }




    private void ignoreOnClick(){
        if(validDetails()){
            addRequestedRides();
            if (transportList.size() != 0) {

                Intent showSearchedRidesIntent = new Intent(getApplicationContext(),ShowAvailableRidesActivity.class);
                showSearchedRidesIntent.putExtra("source",source);
                showSearchedRidesIntent.putExtra("destination",destination);
                showSearchedRidesIntent.putExtra("date",date);
                showSearchedRidesIntent.putExtra("availableRides", (Serializable) transportList);
                startActivity(showSearchedRidesIntent);
            }
            else{
                startActivity(new Intent(getApplicationContext(),NoRidesActivity.class));
            }
        }
    }
}