package com.example.solutiontofarming;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.RideTime;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.data.User;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddRideDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    TextView textViewRideDate, textViewRideTime;
    EditText editTextRideDescription,editTextRideSource,editTextRideDestination,editTextPricePerKM;
    Button btnContinue;
    Transport transport;
    int year,month,day;
    int hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    CheckBox checkBoxFlexWithDate, checkBoxFlexWithTime,checkBoxOnDemandFareAva;

    String flexWithDays = "NA";

    Address source, destination;
    int PLACE_PICKER_REQUEST = 1;
    String userId;
    TransportRide transportRide;
    Calendar date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride_details);
        Places.initialize(getApplicationContext(),"AIzaSyBgXbVJEa9ev4akoZhezFVYeGfneYbHlRQ");

        getUserId();
        bindComponents();
        checkForSelectedAddress();
        addListeners();
        getSupportActionBar().setTitle("Ride Details");
    }

    public void bindComponents(){
        transport =(Transport) getIntent().getSerializableExtra("currentTransport");
        this.btnContinue = findViewById(R.id.btn_continue_ride_act);
        this.editTextRideSource = findViewById(R.id.edit_source_address);
        editTextRideSource.setFocusable(false);
        editTextRideSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectPlaceActivity.class);
                intent.putExtra(Extras.EXTRA_SELECT_ADDRESS_TYPE, "source");
                startActivity(intent);
            }
        });

        this.editTextRideDestination = findViewById(R.id.edit_destination_address);
        editTextRideDestination.setFocusable(false);

        editTextRideDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectPlaceActivity.class);
                intent.putExtra(Extras.EXTRA_SELECT_ADDRESS_TYPE, "dest");
                startActivity(intent);
            }
        });

        this.textViewRideDate = findViewById(R.id.text_ride_date);
        this.textViewRideTime = findViewById(R.id.text_ride_time);
        this.editTextPricePerKM = findViewById(R.id.edit_ride_price_km);
        this.editTextRideDescription = findViewById(R.id.edit_ride_description);
        checkBoxFlexWithDate = findViewById(R.id.check_box_flex_with_date);
        checkBoxFlexWithDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForFlexWithDate();
            }

        });

        checkBoxFlexWithTime = findViewById(R.id.check_box_flex_with_time);
        checkBoxOnDemandFareAva = findViewById(R.id.check_box_on_demand_price_aval);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), TransportServiceActivity.class));
    }
    private void checkForSelectedAddress(){

        SharedPreferences sh = getSharedPreferences("DATA", MODE_PRIVATE);

        String sAdd = sh.getString("s_add", "");
        String dAdd = sh.getString("d_add", "");

        editTextRideSource.setText(sAdd);
        editTextRideDestination.setText(dAdd);

    }

    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verifyRideDetails()){
                    Intent intent = new Intent(getApplicationContext(),AddVehicleDetailsActivity.class);
                    createRideDetails();
                    intent.putExtra("EXTRA_RIDE_OBJ", transportRide);
                    Log.d("", "onClick: "+transportRide.toString());
                    startActivity(intent);
                }
            }
        });

        this.textViewRideDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRideDetailsActivity.this,AddRideDetailsActivity.this::onDateSet,year, month,day);
                datePickerDialog.show();
            }
        });
    }


    private void showDialogForFlexWithDate(){
        if(checkBoxFlexWithDate.isChecked()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddRideDetailsActivity.this);
            alertDialog.setTitle("Flexible With Date");
            alertDialog.setMessage("Enter No of Days You are Flexible With");

            final EditText input = new EditText(AddRideDetailsActivity.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            flexWithDays = input.getText().toString();
                        }
                    });

            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            checkBoxFlexWithDate.setChecked(false);
                        }
                    });

            alertDialog.show();
        }
    }
    public boolean verifyRideDetails(){

        if(editTextRideSource.getText().toString().equals("")){
            editTextRideSource.setError("Please Select Source Address");
            editTextRideSource.requestFocus();
            return false;
        }

        if(editTextRideDestination.getText().toString().equals("")){
            editTextRideDestination.setError("Please Select Destination Address");
            editTextRideDestination.requestFocus();
            return false;
        }

        if(textViewRideDate.getText().toString().equals("")){
            textViewRideDate.setError("Please Select Ride Date");
            textViewRideDate.requestFocus();
            return false;
        }

        if(editTextPricePerKM.getText().toString().equals("")){
            editTextPricePerKM.setError("Please Enter Fare");
            editTextPricePerKM.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month + 1;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddRideDetailsActivity.this, AddRideDetailsActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
        textViewRideDate.setText(""+myday+"-"+myMonth+"-"+myYear);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

        textViewRideTime.setText(""+myHour+":"+minute);

        System.out.println("Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myday + "\n" +
                "Hour: " + myHour + "\n" +
                "Minute: " + myMinute);
    }

    private void createRideDetails(){

        SharedPreferences sh = getSharedPreferences("DATA", MODE_PRIVATE);

        String sLat = sh.getString("s_lat", "");
        String sLon = sh.getString("s_lon", "");
        String sAdd = sh.getString("s_add", "");
        String sName = sh.getString("s_loc", "");

        String dLat = sh.getString("d_lat", "");
        String dLon = sh.getString("d_lon", "");
        String dAdd = sh.getString("d_add", "");
        String dName = sh.getString("d_loc", "");

        source = new Address(sLat, sLon, sAdd, sName);
        destination = new Address(dLat, dLon, dAdd, dName);

        transportRide = new TransportRide();
        transportRide.setSource(source);
        transportRide.setDestination(destination);
        transportRide.setRoute(editTextRideDescription.getText().toString());

        String flexWithTime = "No";
        if(checkBoxFlexWithTime.isChecked())
            flexWithTime = "Yes";

        String flexWithDate = "No";
        if(checkBoxFlexWithDate.isChecked())
            flexWithDate = "Yes";


        RideTime rideTime = new RideTime(
                textViewRideDate.getText().toString(),
                textViewRideTime.getText().toString(),
                flexWithDate,
                flexWithDays,
                flexWithTime
        );
        transportRide.setWhen(rideTime);
    }

    //-------- FIREBASE-------------
    public void setRideDetails(){
        transport.setRideProviderId(userId);
        transport.setSourceAddress(editTextRideSource.getText().toString().trim());
        transport.setDestinationAddress(editTextRideDestination.getText().toString().trim());
        transport.setRideTime(textViewRideTime.getText().toString());
        transport.setRideDate(textViewRideDate.getText().toString());
        transport.setPricePerKm(editTextPricePerKM.getText().toString());
        transport.setRideDescription(editTextRideDescription.getText().toString());
        transport.setRideStatus("Visible to All");
        String geoPoints = "18.603009+73.786869+20.896545+76.202581";
        transport.setGeoPoints(geoPoints);
        Log.d("", "setRideDetails: "+transport.getRideDate());
    }

    private void getUserId(){

        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                userId = user.getUserId();
                Log.d("", "onDataChange:userId "+userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}