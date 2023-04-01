package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetRideActivity extends AppCompatActivity {

    EditText editTextSource,editTextDestination,editTextGoods,editTextGoodsWeight,editTextAdditionalDetails;
    Button btnSendRideRequest;
    String loggedUser,loggedUserMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ride);

        getSupportActionBar().setTitle("Book Ride");
        bindComponents();
        addListener();
        getUserDetails();

    }

    public void bindComponents(){
        this.editTextAdditionalDetails = findViewById(R.id.edit_add_des_ride_req);
        this.editTextDestination = findViewById(R.id.edit_destination_ride_req);
        this.editTextGoods = findViewById(R.id.edit_goods_ride_req);
        this.editTextGoodsWeight = findViewById(R.id.edit_goods_weight_ride_req);
        this.editTextSource = findViewById(R.id.edit_source_ride_req);
        this.btnSendRideRequest =findViewById(R.id.btn_req_ride);
    }

    public void addListener(){
        this.btnSendRideRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidDetails()){
                    Log.d("", "onClick: "+"inside is Valid");
                    btnSendRideRequestSMS();
                    startActivity(new Intent(getApplicationContext(),RideRequestSentActivity.class));
                }
            }
        });
    }

    public void btnSendRideRequestSMS(){

        Log.d("", "onClick: "+"inside send Sms");

        String driverPhone = getIntent().getStringExtra("phone");
        String driverName = getIntent().getStringExtra("name");
        String rideDate = getIntent().getStringExtra("date");
        String source = editTextSource.getText().toString();
        String destination = editTextDestination.getText().toString();
        Log.d("", "btnSendRideRequestSMS: "+driverName+driverPhone+loggedUserMobile+loggedUser);
        String message = "Dear "+driverName+", \n Ride Share Request by : "+loggedUser+"for Your Ride Scheduled on "+rideDate+"from "+source+" to :"+destination+" Contact Details  "+loggedUserMobile+" - AgriSolutions";

        try {
            Log.d("", "onClick: "+"inside is Try");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9922068267", null, message.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isValidDetails(){
        if(editTextSource.getText().toString().isEmpty()){
            editTextSource.setError("PLease Enter Source Address");
            editTextSource.requestFocus();
            return false;
        }
        if(editTextDestination.getText().toString().isEmpty()){
            editTextDestination.setError("PLease Enter Destination Address");
            editTextDestination.requestFocus();
            return false;
        }
        if(editTextGoods.getText().toString().isEmpty()){
            editTextGoods.setError("Please Enter Goods ");
            editTextGoods.requestFocus();
            return false;
        }
        if(editTextGoodsWeight.getText().toString().isEmpty()){
            editTextGoodsWeight.setError("Please Enter Goods Weight");
            editTextGoodsWeight.requestFocus();
            return false;
        }
        return true;
    }

    public void getUserDetails(){

        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user  = snapshot.getValue(User.class);
                loggedUser = user.getFullName();
                loggedUserMobile = user.getPhoneNo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}