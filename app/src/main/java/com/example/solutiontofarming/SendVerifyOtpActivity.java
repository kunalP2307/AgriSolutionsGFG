package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solutiontofarming.data.Fare;
import com.example.solutiontofarming.data.TransportRide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendVerifyOtpActivity extends AppCompatActivity {

    EditText editTextOtp;
    Button buttonVerifyOtp;
    int otp;
    TransportRide transportRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_verify_otp);
        askSendOtpPermission();
        bindComponents();
        sendOtp();
        getTransport();

    }
    private void askSendOtpPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
    }
    private void sendOtp(){
        String phone = getIntent().getStringExtra("EXTRA_CONTACT");
        otp = getOtp();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, "Your OTP to verify Driver Details is "+otp+" -AgriSolutions ", null, null);
        } catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void getTransport(){
        transportRide = (TransportRide) getIntent().getSerializableExtra("EXTRA_RIDE_OBJ");
        Log.d("TAG", "getTransport: "+transportRide.toString());

        Gson gson = new Gson();
        String json = gson.toJson(transportRide);

        JSONObject jsonObjectTransportRide = null;
        try {
            jsonObjectTransportRide = new JSONObject(json);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }

//        JsonObject jsonObject = gson.fromJson(json, (Type) Fare.class);
        Log.d("TAG", "onCreate: JsonObject"+jsonObjectTransportRide);
    }
    public int getOtp(){
        Random rand = new Random();
        int rand_otp = rand.nextInt(1000000);
        return  rand_otp;
    }

    private void bindComponents(){
        editTextOtp = findViewById(R.id.edit_text_otp);
        buttonVerifyOtp = findViewById(R.id.button_verify_otp);
        buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyOtp(editTextOtp.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), AddTransportRideActivity.class);
                    intent.putExtra("EXTRA_RIDE_OBJ", transportRide);
                    startActivity(intent);
                }
                else{
                    editTextOtp.setError("Invalid Otp");
                    editTextOtp.requestFocus();
                }
            }
        });
    }
    public boolean verifyOtp(String enteredOtp){
        Log.d("", "verifyOtp: "+enteredOtp+Integer.toString(otp));

        if(enteredOtp.equals(Integer.toString(otp)))
            return true;
        else
            return false;
    }
}