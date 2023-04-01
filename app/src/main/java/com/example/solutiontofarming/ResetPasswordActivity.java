package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    Button btnResetPass;
    TextView textViewEmailSent;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        bindComponents();
        addListeners();
        getSupportActionBar().setTitle("Reset Password");

    }

    public void bindComponents(){
        this.editTextEmail = findViewById(R.id.text_for_pass_email);
        this.btnResetPass = findViewById(R.id.btn_res_pass);
        this.textViewEmailSent = findViewById(R.id.label_res_pass_mail_sent);
        auth = FirebaseAuth.getInstance();
    }

    public void addListeners(){
        this.btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                if(email.isEmpty()){
                    editTextEmail.setError("Email Cannot be empty");
                    editTextEmail.requestFocus();
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Please Provide Valid Email!");
                    editTextEmail.requestFocus();
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            textViewEmailSent.setVisibility(View.VISIBLE);
                            Toast.makeText(ResetPasswordActivity.this, "Password Reset Link sent to your email ", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ResetPasswordActivity.this, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

}