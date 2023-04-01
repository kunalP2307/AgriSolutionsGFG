package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.Transport;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShowAvailableRidesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    TransportAdapter transportAdapter;
    TextView textViewHeader;
    ListView listViewAvailableRides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_rides);


        List<Transport> availableRides = (List<Transport>) getIntent().getSerializableExtra("availableRides");


        String source = getIntent().getExtras().getString("source");
        String destination  = getIntent().getExtras().getString("destination");
        String date = getIntent().getExtras().getString("date");
        if(source != null){
            textViewHeader = findViewById(R.id.text_header_available_rides);
            textViewHeader.setText(source+"  <->  "+destination+"   Date : "+date);
        }

        listViewAvailableRides = findViewById(R.id.list_available_rides);
        transportAdapter = new TransportAdapter(this, (ArrayList<Transport>) availableRides);
        listViewAvailableRides.setAdapter(transportAdapter);
        listViewAvailableRides.setOnItemClickListener(this);
        getSupportActionBar().setTitle("Available Rides");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Transport selectedRide = (Transport) transportAdapter.getItem(position);
        Intent showRideDetailsIntent = new Intent(getApplicationContext(),ShowRideDetailsActivity.class);
        showRideDetailsIntent.putExtra("selectedRide",selectedRide);
        startActivity(showRideDetailsIntent);
    }
}