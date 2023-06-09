package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Button bnext;
    //List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome!");
        bnext=(Button) findViewById(R.id.next);
     //   this.addListener();
        Thread thread =new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent i=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    } else {
                        // User is signed out
                        Log.d("MainActivity", "onAuthStateChanged:signed_out");
                    }

                }
            }
        };thread.start();


       /* FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                    Log.d("", "onDataChange: "+user.getFullName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */


    }
 /* private void addListener(){

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }*/

}