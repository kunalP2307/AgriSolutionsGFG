package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextCnfPassword;
    private TextView textViewUid;
    private ProgressBar spinner;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__page);

        this.bindComponents();
        spinner.setVisibility(View.GONE);
        this.addListener();

        generateUserId();
        getSupportActionBar().setTitle("Register User");


    }

    private void bindComponents(){
        this.btnRegister = findViewById(R.id.btn_res_pass);
        this.editTextName = findViewById(R.id.text_doc_no);
        this.editTextEmail = findViewById(R.id.text_email);
        this.editTextPhone = findViewById(R.id.text_phone);
        this.editTextPassword = findViewById(R.id.text_password);
        this.editTextCnfPassword = findViewById(R.id.text_cnf_password);
        this.textViewUid = findViewById(R.id.textUid);
        this.spinner = findViewById(R.id.spin_weight_unit);
    }

    private void addListener(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        if(validateUserDetails()){
            spinner.setVisibility(View.VISIBLE);
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim()
                    ,editTextPassword.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //generateUserId();
                                //String abc = getId();
                                Log.d("RegAct", "onComplete: "+textViewUid.getText().toString());
                                User user = new User(userId,
                                                    editTextName.getText().toString().trim()
                                                    ,editTextEmail.getText().toString().trim()
                                                    ,editTextPhone.getText().toString().trim());
                                FirebaseDatabase.getInstance().getReference("(Q2-2021)Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            spinner.setVisibility(View.GONE);
                                            generateNextUserId();
                                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));

                                            //Toast.makeText(Registration.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        }else {
                                            //Toast.makeText(Registration.this, "User Registration Failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                //Toast.makeText(Registration.this, "User Registration Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void generateUserId(){

       FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID id = snapshot.getValue(ID.class);
                userId = id.getUserId();

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

    public void generateNextUserId(){
        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID dBId = snapshot.getValue(ID.class);
                String uId = dBId.getUserId().substring(1);
                int newUId = Integer.parseInt(uId) + 1;
                dBId.setUserId("U"+Integer.toString(newUId));
                FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").setValue(dBId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegistrationActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getId(String userId   ){
        Log.d("getId", "getId: "+userId);
        String newId = userId+" ";
        return  newId;
    }

    private boolean validateUserDetails(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cnfPassword = editTextCnfPassword.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Name Cannot be Empty");
            editTextName.requestFocus();
            return false;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email Cannot be Empty");
            editTextEmail.requestFocus();
            return false;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Phone Number Cannot be Empty");
            editTextPhone.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password cannot be Empty");
            editTextPassword.requestFocus();
            return false;
        }
        if(cnfPassword.isEmpty()){
            editTextCnfPassword.setError("Re-Enter Password");
            editTextCnfPassword.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return false;
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            editTextPhone.setError("Provide Valid Phone Number");
            editTextPhone.requestFocus();
            return false;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password length should be greater than 6");
            editTextPassword.requestFocus();
            return false;
        }
        if(!password.equals(cnfPassword)){
            editTextCnfPassword.setError("Password doesn't matches");
            editTextCnfPassword.requestFocus();
            return false;
        }
        return true;
    }


    private  interface getIdCallBack{
        void onCallBack(String id);
    }
}
