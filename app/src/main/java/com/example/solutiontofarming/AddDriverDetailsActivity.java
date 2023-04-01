package com.example.solutiontofarming;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.solutiontofarming.data.Transport;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDriverDetailsActivity extends AppCompatActivity {

    Button btnVerifyOTPr,btnSendOtp , btnContinue;
    EditText editTextMobile , editTextName,editTextOTP;
    TextView textViewOtpSentMsg;
    Transport transport;
    int otp;
    boolean otpStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver_details);
        bindComponents();
        getSupportActionBar().setTitle("Driver Details");

        addListeners();

    }

    public void bindComponents(){
        transport = (Transport) getIntent().getSerializableExtra("currentTransport");
        this.btnContinue = findViewById(R.id.btn_add_driver);
        this.btnSendOtp = findViewById(R.id.btn_add_driver_send_otp);
        this.btnVerifyOTPr = findViewById(R.id.btn_add_driver_verify_otp);
        this.editTextName = findViewById(R.id.edit_driver_name);
        this.editTextMobile = findViewById(R.id.edit_driver_mob);
        this.editTextOTP = findViewById(R.id.edit_add_driver_otp);
        this.textViewOtpSentMsg = findViewById(R.id.text_otp_sent);
    }

    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verifyDriverDetails()) {

                    Intent intent = new Intent(getApplicationContext(), ScheduleTransportActivity.class);
                    setDriverDetails();
                    intent.putExtra("currentTransport", transport);
                    startActivity(intent);
                }
            }
        });
        this.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = editTextMobile.getText().toString();
                Pattern p = Pattern.compile("^[6-9]\\d{9}$");
                Matcher m = p.matcher(phone);
                if(!m.matches()){
                    editTextMobile.setError("Please Provide Valid Number");
                    editTextMobile.requestFocus();
                }

                else{
                    otp = getOtp();
                    try {
                        textViewOtpSentMsg.setVisibility(View.VISIBLE);
                        textViewOtpSentMsg.setText("OTP Sent Successfully!");

                        textViewOtpSentMsg.postDelayed(new Runnable() {
                            public void run() {
                                textViewOtpSentMsg.setVisibility(View.INVISIBLE);
                            }
                        }, 3000);

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, "Your OTP to verify Driver Details is "+otp+" -AgriSolutions ", null, null);
                    } catch (Exception e) {
                        textViewOtpSentMsg.setText("Something went wrong ");
                        e.printStackTrace();
                    }
                }
            }
        });

        this.btnVerifyOTPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewOtpSentMsg.setVisibility(View.VISIBLE);
                textViewOtpSentMsg.setVisibility(View.VISIBLE);
                if(verifyOtp(editTextOTP.getText().toString())){
                    textViewOtpSentMsg.setText("Mobile Number verification successful.!");
                    otpStatus = true;
                    textViewOtpSentMsg.postDelayed(new Runnable() {
                        public void run() {
                            textViewOtpSentMsg.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);


                }

                else{
                    textViewOtpSentMsg.setText("Mobile Number verification failed.!");

                    textViewOtpSentMsg.postDelayed(new Runnable() {
                        public void run() {
                            textViewOtpSentMsg.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);

                }

            }
        });
    }

    public int getOtp(){
        Random rand = new Random();
        int rand_otp = rand.nextInt(1000000);
        return  rand_otp;
    }

    public boolean verifyOtp(String enteredOtp){
        Log.d("", "verifyOtp: "+enteredOtp+Integer.toString(otp));

        if(enteredOtp.equals(Integer.toString(otp)))
            return true;
        else
            return false;
    }

    private boolean verifyDriverDetails(){
        if(editTextName.getText().toString().isEmpty()){
            editTextName.setError("Please Enter Driver Name");
            editTextName.requestFocus();
            return false;
        }
        if(editTextMobile.getText().toString().isEmpty()){
            editTextMobile.setError("Please Enter Mobile Number");
            editTextMobile.requestFocus();
            return false;
        }
        if(otpStatus == false){
            editTextMobile.setError("Please Verify Number to Continue");
            editTextMobile.requestFocus();
            return false;
        }

        return true;
    }

    public void setDriverDetails(){
        transport.setDriverName(editTextName.getText().toString());
        transport.setDrivePhone(editTextMobile.getText().toString());
    }
}