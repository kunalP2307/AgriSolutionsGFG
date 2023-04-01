package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.solutiontofarming.data.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifyProfileActivity extends AppCompatActivity {

    ImageView imageViewDocument;
    Button btmVerifyAccount;
    RadioButton radioAadhaar,radioPan;
    EditText editTextDocNo;
    TextView textViewVerifyThrough;
    int documentType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_profile);
        getSupportActionBar().setTitle("Profile Verification");

        bindComponents();
        addListeners();

        imageViewDocument.setOnClickListener(v -> ImagePicker.Companion.with(VerifyProfileActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize( 700, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(20));

    }

    public void bindComponents(){
        radioAadhaar = findViewById(R.id.radio_aadhaar);
        radioPan = findViewById(R.id.radio_pan);
        imageViewDocument = findViewById(R.id.image_verify_doc);
        btmVerifyAccount = findViewById(R.id.btn_verify_profile);
        textViewVerifyThrough = findViewById(R.id.text_verify_through);
        editTextDocNo = findViewById(R.id.edit_doc_no);
    }

    public void addListeners(){

        this.btmVerifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validProfileDetails()){
                    FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            String uId = user.getUserId();
                            Log.d("ServiceAct", "onDataChange: "+uId);

                            uId = uId+"F";

                            user.setUserId(uId);
                            FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                    Intent intent = new Intent(getApplicationContext(),ServicesActivity.class);
                    startActivity(intent);
                }

            }
        });
        this.radioAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioPan.setChecked(false);
                documentType = 1;
            }
        });
        this.radioPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAadhaar.setChecked(false);
                documentType = 2;
            }
        });
    }


    public boolean validProfileDetails(){

        String docNo = editTextDocNo.getText().toString();

        String adhaarRegex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
        String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";


        if(documentType == 0){
            textViewVerifyThrough.setError("Please Select Document Type");
            textViewVerifyThrough.requestFocus();
            return false;
        }

        if(docNo.isEmpty()){
            editTextDocNo.setError("Please Enter Document Number");
            editTextDocNo.requestFocus();
            return false;
        }

        if(documentType == 1){
            Pattern pattern = Pattern.compile(adhaarRegex);
            Matcher matcher = pattern.matcher(docNo);
            if(!matcher.matches()){
                editTextDocNo.setError("Please Enter Valid Adhhar Number.!");
                editTextDocNo.requestFocus();
                return false;
            }
        }

        if(documentType == 2){

            Pattern pattern = Pattern.compile(panRegex);
            Matcher matcher = pattern.matcher(docNo);
            if(!matcher.matches()){
                editTextDocNo.setError("Please Enter Valid PAN Number.!");
                editTextDocNo.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 20){
            assert data != null;
            Uri uri2 = data.getData();
            imageViewDocument.setImageURI(uri2);
        }
    }



}