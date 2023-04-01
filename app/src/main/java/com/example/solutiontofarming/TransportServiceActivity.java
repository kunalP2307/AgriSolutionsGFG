package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransportServiceActivity extends AppCompatActivity {
    final String TAG = "TransportServiceActivity";

    List<Transport> transportList = new ArrayList<>();

    ImageView imageViewFind, imageViewGive,imageViewShowAll,imageViewMyRides;
    String loggedInUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_service);
        getSupportActionBar().setTitle("Transport Pool");

        bindComponents();
        addListeners();
        initRides();
        getLoggedInUserId();

    }

    public void bindComponents(){
        imageViewFind = findViewById(R.id.img_find_ride);
        imageViewGive = findViewById(R.id.img_give_ride);
        imageViewShowAll = findViewById(R.id.img_show_all_rides);
        imageViewMyRides = findViewById(R.id.img_my_rides);
    }

    public void addListeners(){
        this.imageViewGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScheduleTransportActivity.class));
            }
        });
        this.imageViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchTransportActivity.class);
                intent.putExtra("availableRides", (Serializable) transportList);
                startActivity(intent);
            }
        });
        this.imageViewShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowAvailableRidesActivity.class);
                intent.putExtra("availableRides", (Serializable) transportList);
                startActivity(intent);

            }
        });
        this.imageViewMyRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowMyRidesActivity.class);
                intent.putExtra("availableRides", (Serializable) transportList);
                intent.putExtra("userId",loggedInUserId);
                startActivity(intent);

            }
        });

    }
    public void initRides(){
        FirebaseDatabase.getInstance().getReference("Transport-Rides").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transportList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Transport transport = postSnapshot.getValue(Transport.class);
                    transportList.add(transport);
                    Log.d("", "onDataChange: "+transportList.size());
                    //Log.d("", "onDataChange: "+transport.getVehicleNo());
                }
                //Log.d(TAG, "onDataChange : Size "+transportList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLoggedInUserId() {
        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                loggedInUserId = user.getUserId();
                Log.d(TAG, "onDataChange: LOgged In User Id "+loggedInUserId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}