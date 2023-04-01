package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.solutiontofarming.data.Transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchTransportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    final String TAG = "SearchTransportActivity";
    EditText editTextSource,editTextDestination,editTextDate;
    Button btnFindRide;
    int year,month,day;
    int myday, myMonth, myYear;
    List<Transport> transportList = new ArrayList<>();
    List<Transport> temp = new ArrayList<>();
    String source,destination,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transport);
        bindComponents();
        addListener();
        getSupportActionBar().setTitle("Find Ride");

        temp = (List<Transport>) getIntent().getSerializableExtra("availableRides");
        transportList = new ArrayList<>();

    }

    public void bindComponents(){
        this.editTextDate = findViewById(R.id.edit_transport_date_search);
        this.editTextSource = findViewById(R.id.edit_source_address_search);
        this.editTextDestination = findViewById(R.id.edit_destination_address_search);
        this.btnFindRide = findViewById(R.id.btn_search_ride);
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
                Log.d(TAG, "onClick: date "+year+month+day);
                datePickerDialog.show();
            }
        });
        this.btnFindRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month+1;
        Log.d(TAG, "onDateSet: month"+myMonth+"day"+myday);
        String date = myday+"-"+myMonth+"-"+myYear;
        editTextDate.setText(date);
    }
}