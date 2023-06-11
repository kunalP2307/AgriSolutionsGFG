package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.solutiontofarming.data.Driver;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDriverDetailsActivity extends AppCompatActivity {

    private static final String TAG = "AddDriverDetailsActivity";
    Button btnVerifyOTPr,btnSendOtp , btnContinue;
    EditText editTextMobile , editTextName,editTextOTP;
    TextView textViewOtpSentMsg;
    Transport transport;
    TransportRide transportRide;
    int otp;
    boolean otpStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver_details);

        getTransportRide();
        bindComponents();
        getSupportActionBar().setTitle("Driver Details");

        addListeners();


    }

    public void bindComponents(){
        //transport = (Transport) getIntent().getSerializableExtra("currentTransport");
        this.btnContinue = findViewById(R.id.button_continue_driver_det);
        this.editTextName = findViewById(R.id.edit_driver_name);
        this.editTextMobile = findViewById(R.id.edit_driver_mob);
    }
    private void getTransportRide(){
        transportRide = (TransportRide) getIntent().getSerializableExtra("EXTRA_RIDE_OBJ");
        Log.d(TAG, "onCreate: "+transportRide.toString());
    }

    public void addListeners(){
        this.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verifyDriverDetails()) {
                    Intent intent = new Intent(getApplicationContext(), SendVerifyOtpActivity.class);
                    Driver driver = new Driver(
                        editTextName.getText().toString(),
                        editTextMobile.getText().toString()
                    );
                    transportRide.setDriver(driver);
                    intent.putExtra("EXTRA_CONTACT", editTextMobile.getText().toString());
                    intent.putExtra("EXTRA_RIDE_OBJ", transportRide);
                    startActivity(intent);
                }
            }
        });
//        this.btnSendOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String phone = editTextMobile.getText().toString();
//                Pattern p = Pattern.compile("^[6-9]\\d{9}$");
//                Matcher m = p.matcher(phone);
//                if(!m.matches()){
//                    editTextMobile.setError("Please Provide Valid Number");
//                    editTextMobile.requestFocus();
//                }
//
//                else{
//                    otp = getOtp();
//                    try {
//                        textViewOtpSentMsg.setVisibility(View.VISIBLE);
//                        textViewOtpSentMsg.setText("OTP Sent Successfully!");
//
//                        textViewOtpSentMsg.postDelayed(new Runnable() {
//                            public void run() {
//                                textViewOtpSentMsg.setVisibility(View.INVISIBLE);
//                            }
//                        }, 3000);
//
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(phone, null, "Your OTP to verify Driver Details is "+otp+" -AgriSolutions ", null, null);
//                    } catch (Exception e) {
//                        textViewOtpSentMsg.setText("Something went wrong ");
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

//        this.btnVerifyOTPr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textViewOtpSentMsg.setVisibility(View.VISIBLE);
//                textViewOtpSentMsg.setVisibility(View.VISIBLE);
//                if(verifyOtp(editTextOTP.getText().toString())){
//                    textViewOtpSentMsg.setText("Mobile Number verification successful.!");
//                    otpStatus = true;
//                    textViewOtpSentMsg.postDelayed(new Runnable() {
//                        public void run() {
//                            textViewOtpSentMsg.setVisibility(View.INVISIBLE);
//                        }
//                    }, 3000);
//
//
//                }
//
//                else{
//                    textViewOtpSentMsg.setText("Mobile Number verification failed.!");
//
//                    textViewOtpSentMsg.postDelayed(new Runnable() {
//                        public void run() {
//                            textViewOtpSentMsg.setVisibility(View.INVISIBLE);
//                        }
//                    }, 3000);
//
//                }
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),TransportServiceActivity.class));
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

        String contact = editTextMobile.getText().toString();
        String regNoRegex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        Pattern pattern = Pattern.compile(regNoRegex);
        Matcher matcher = pattern.matcher(contact);


        if(editTextName.getText().toString().isEmpty()){
            editTextName.setError("Please Enter Driver Name");
            editTextName.requestFocus();
            return false;
        }
        if(!matcher.matches()){
            editTextMobile.setError("Please Enter Valid Contact Number");
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