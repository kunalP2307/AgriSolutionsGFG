package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
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


        //List<TransportRide> availableRides = (List<TransportRide>) getIntent().getSerializableExtra("availableRides");

        List<TransportRide> availableRides = new ArrayList<>();
        TransportRide transportRide;

        String jsonString = "{\n" +
                "    \"destination\": {\n" +
                "      \"address\": \"Bengaluru, Karnataka, India\",\n" +
                "      \"latitude\": \"12.9715987\",\n" +
                "      \"longitude\": \"77.5945627\",\n" +
                "      \"name\": \"Bengaluru\"\n" +
                "    },\n" +
                "    \"driver\": {\n" +
                "      \"contact\": \"7028991096\",\n" +
                "      \"name\": \"Kunal Patil\"\n" +
                "    },\n" +
                "    \"goods\": {\n" +
                "      \"category\": \"Cereals\",\n" +
                "      \"name\": \"Wheat\"\n" +
                "    },\n" +
                "    \"route\": \"NA\",\n" +
                "    \"source\": {\n" +
                "      \"address\": \"Railway station, Agarkar Nagar, Pune, Maharashtra 411001, India\",\n" +
                "      \"latitude\": \"18.528913\",\n" +
                "      \"longitude\": \"73.87441989999999\",\n" +
                "      \"name\": \"Pune Railway Station\"\n" +
                "    },\n" +
                "    \"vehicle\": {\n" +
                "      \"availableLimit\": \"20\",\n" +
                "      \"containerType\": \"  Closed Container \",\n" +
                "      \"registrationNo\": \"MH 12 FG 4491\",\n" +
                "      \"type\": \"Refrigerated Truck\",\n" +
                "      \"weightUnit\": \"Quintal\"\n" +
                "    },\n" +
                "    \"when\": {\n" +
                "      \"date\": \"15-6-2023\",\n" +
                "      \"flexWithDate\": \"Yes\",\n" +
                "      \"flexWithTime\": \"Yes\",\n" +
                "      \"noOfDays\": \"2\",\n" +
                "      \"time\": \"6.2\"\n" +
                "    }\n" +
                "  }";

        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
        transportRide = new Gson().fromJson(jsonObject, TransportRide.class);

        availableRides.add(transportRide);
        availableRides.add(transportRide);
        availableRides.add(transportRide);


//        String source = getIntent().getExtras().getString("source");
//        String destination  = getIntent().getExtras().getString("destination");
//        String date = getIntent().getExtras().getString("date");
//        if(source != null){
//            textViewHeader = findViewById(R.id.text_header_available_rides);
//            textViewHeader.setText(source+"  <->  "+destination+"   Date : "+date);
//        }

        listViewAvailableRides = findViewById(R.id.list_available_rides);
        transportAdapter = new TransportAdapter(this, (ArrayList<TransportRide>) availableRides);
        listViewAvailableRides.setAdapter(transportAdapter);
        listViewAvailableRides.setOnItemClickListener(this);
        getSupportActionBar().setTitle("Available Rides");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void getAllRides(){

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TransportRide selectedRide = (TransportRide) transportAdapter.getItem(position);
        Intent showRideDetailsIntent = new Intent(getApplicationContext(),ShowRideDetailsActivity.class);
        showRideDetailsIntent.putExtra("EXTRA_SELECTED_RIDE",selectedRide);
        startActivity(showRideDetailsIntent);
    }
}