package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    final String TAG = "ServicesActivity";

    TextView textViewProfileStatus;
    ImageView imageViewProfileStatus;
    ImageView imageViewTransportService, imageViewLandService,imageViewWarehouseService,imageViewAgriEquipmentService;
    TextView textViewProfileDetails;
    List<Transport> transportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_services);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_services:
                        return true;
                    case R.id.navigation_explore:
                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_more:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        getSupportActionBar().setTitle("AgriServices");

        setActionBar();
        bindComponents();
        addListeners();
        initRides();
        showProfileStatus();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
    public void setActionBar(){
        getSupportActionBar().setTitle("Service");
    }

    public void showProfileStatus(){

        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                String uId = user.getUserId();
                Log.d("ServiceAct", "onDataChange: "+uId);

                int length = uId.length();

                char ch = uId.charAt(length-1);

                if(ch == 'F'){
                    textViewProfileStatus.setText(" VERIFIED ");
                    textViewProfileStatus.setBackgroundColor(Color.GREEN);
                    imageViewProfileStatus.setImageResource(R.drawable.check);
                    textViewProfileDetails.setText("Verified Profile");
                    Log.d(TAG, "onDataChange: verified");
                }
                else{
                    Log.d(TAG, "onDataChange: Profile not verified yet");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void bindComponents(){

        textViewProfileStatus = findViewById(R.id.text_profile_status);
        imageViewProfileStatus = findViewById(R.id.img_view_profile_status);
        imageViewLandService = findViewById(R.id.img_land_service);
        imageViewTransportService = findViewById(R.id.img_transport_service);
        textViewProfileDetails =findViewById(R.id.text_profile_detail);
       // imageViewWarehouseService = findViewById(R.id.img_warehouse_service);
        imageViewAgriEquipmentService = findViewById(R.id.img_agri_equip_service);
    }
    public void addListeners(){

        imageViewTransportService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TransportServiceActivity.class));
            }
        });

        imageViewLandService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandRentServiceActivity.class));
            }
        });

        imageViewAgriEquipmentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),ShowAvailableAgriEquipmentsActivity.class));
                startActivity(new Intent(getApplicationContext(),AgriEquipmentServiceActivity.class));

            }
        });



        imageViewProfileStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),VerifyProfileActivity.class);
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
                    //Log.d("", "onDataChange: "+transport.getVehicleNo());
                }
                //Log.d(TAG, "onDataChange : Size "+transportList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServicesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}