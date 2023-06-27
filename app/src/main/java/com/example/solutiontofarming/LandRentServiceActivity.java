package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.solutiontofarming.data.AgriLand;
import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.Transport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LandRentServiceActivity extends AppCompatActivity {

    CardView cardFindLand,cardAddLand,cardMyLand;
    List<AgriculturalLand> landRendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_rent_service);
        initLands();
        addListeners();
        getSupportActionBar().setTitle("Rent In/Out Farms");

    }
    public void addListeners(){
        cardAddLand = findViewById(R.id.card_rent_land);
        cardFindLand = findViewById(R.id.card_borrow_land);
        cardMyLand = findViewById(R.id.card_my_land);

        cardAddLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddAgriLandActivity.class);
                intent.putExtra("display",false);
                startActivity(intent);
            }
        });
        cardFindLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowAvailableAgriLandsActivity.class);
                intent.putExtra("availableLand", (Serializable) landRendList);
                startActivity(intent);
            }
        });

        cardMyLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShowMyLandsActivity.class);
//                intent.putExtra("availableLand", (Serializable) landRendList);
                startActivity(intent);
            }
        });

       /* imageViewShowAllLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowAvailableAgriLandsActivity.class);
                intent.putExtra("availableLand", (Serializable) landRendList);
                startActivity(intent);
            }
        });*/
    }


    public void initLands(){
        FirebaseDatabase.getInstance().getReference("Land-Renting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                landRendList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    AgriculturalLand agriculturalLand = postSnapshot.getValue(AgriculturalLand.class);
                    landRendList.add(agriculturalLand);
                    Log.d("", "onDataChange: "+landRendList.size());
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
}