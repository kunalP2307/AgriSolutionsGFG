package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddRideDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    TextView textViewRideDate, textViewRideTime;
    EditText editTextRideDescription,editTextRideSource,editTextRideDestination,editTextPricePerKM;
    Button btnContinue;
    Transport transport;
    int year,month,day;
    int hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    int PLACE_PICKER_REQUEST = 1;
    String userId;
    Calendar date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride_details);
        getUserId();
        bindComponents();
        addListeners();
        getSupportActionBar().setTitle("Ride Details");


    }

    public void bindComponents(){
        transport =(Transport) getIntent().getSerializableExtra("currentTransport");
        this.btnContinue = findViewById(R.id.btn_continue_ride_act);
        this.editTextRideSource = findViewById(R.id.edit_source_address);
        this.editTextRideDestination = findViewById(R.id.edit_destination_address);
        this.textViewRideDate = findViewById(R.id.text_ride_date);
        this.textViewRideTime = findViewById(R.id.text_ride_time);
        this.editTextPricePerKM = findViewById(R.id.edit_ride_price_km);
        this.editTextRideDescription = findViewById(R.id.edit_ride_description);
    }


    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verifyRideDetails()){
                    setRideDetails();

                    Intent intent = new Intent(getApplicationContext(),ScheduleTransportActivity.class);
                    intent.putExtra("currentTransport",transport);
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

        if(textViewRideDate.getText().toString().equals("Select Date")){
            textViewRideDate.setError("Please Select Ride Date");
            textViewRideDate.requestFocus();
            return false;
        }

        return true;
    }
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddRideDetailsActivity.this, AddRideDetailsActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

        textViewRideDate.setText(""+myday+"-"+myMonth+"-"+myYear);
        textViewRideTime.setText(""+myHour+":"+myMonth);

        System.out.println("Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myday + "\n" +
                "Hour: " + myHour + "\n" +
                "Minute: " + myMinute);
    }

    private void getUserId(){

        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                userId = user.getUserId();
                Log.d("", "onDataChange:userId "+userId);
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