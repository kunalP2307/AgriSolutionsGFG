package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;
import java.util.List;

public class ShowMyRidesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final String TAG = "ShowMyRidesActivity";
    List<Transport> allRides,myRides;
    String userId;
    TransportAdapter transportAdapter;
    ListView listViewMyRides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_rides);

        allRides = (List<Transport>) getIntent().getSerializableExtra("availableRides");
        userId = getIntent().getStringExtra("userId");
        initMyRides();

        if(!(myRides.size()==0)) {
            listViewMyRides = findViewById(R.id.list_my_rides);
            transportAdapter = new TransportAdapter(this, (ArrayList<Transport>) myRides);
            listViewMyRides.setAdapter(transportAdapter);
            listViewMyRides.setOnItemClickListener(this);
        }else{
            startActivity(new Intent(getApplicationContext(),NoMyRidesActivity.class));
        }
        getSupportActionBar().setTitle("My Rides");



    }

    public void initMyRides(){

        myRides = new ArrayList<Transport>();

        for(int i=0; i<allRides.size(); i++){
            if(allRides.get(i).getRideProviderId().contains(userId)){
                myRides.add(allRides.get(i));
            }
        }

        Log.d(TAG, "initMyRides: myRIdes Count "+myRides.size());

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Transport selectedRide = (Transport) transportAdapter.getItem(position);
        Intent showRideDetailsIntent = new Intent(getApplicationContext(),ShowRideDetailsActivity.class);
        showRideDetailsIntent.putExtra("selectedRide",selectedRide);
        startActivity(showRideDetailsIntent);
    }
}