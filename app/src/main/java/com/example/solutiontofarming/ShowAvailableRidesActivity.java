package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShowAvailableRidesActivity extends AppCompatActivity {

    TransportAdapter transportAdapter;
    TextView textViewHeader;
    ListView listViewAvailableRides;
    String GET_ALL_URL = "http://34.133.71.237:7000/find/user";
    ProgressDialog dialog;
    List<TransportRide> availableRides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_rides);


        //List<TransportRide> availableRides = (List<TransportRide>) getIntent().getSerializableExtra("availableRides");

        availableRides = new ArrayList<>();
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

//        availableRides.add(transportRide);
//        availableRides.add(transportRide);
//        availableRides.add(transportRide);


//        String source = getIntent().getExtras().getString("source");
//        String destination  = getIntent().getExtras().getString("destination");
//        String date = getIntent().getExtras().getString("date");
//        if(source != null){
//            textViewHeader = findViewById(R.id.text_header_available_rides);
//            textViewHeader.setText(source+"  <->  "+destination+"   Date : "+date);
//        }

        Log.d("TAG", "onCreate: Before ");
        getSupportActionBar().setTitle("Available Rides");
        setProgressDialog();
        initJsonArray();

         Log.d("TAG", "onCreate: size of List "+availableRides.size());
        Log.d("TAG", "onCreate: After ");

        listViewAvailableRides = findViewById(R.id.list_available_rides);
        transportAdapter = new TransportAdapter(getApplicationContext(), (ArrayList<TransportRide>) availableRides);
        listViewAvailableRides.setAdapter(transportAdapter);
        listViewAvailableRides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransportRide selectedRide = (TransportRide) transportAdapter.getItem(position);
                Intent showRideDetailsIntent = new Intent(getApplicationContext(),ShowRideDetailsActivity.class);
                showRideDetailsIntent.putExtra("EXTRA_SELECTED_RIDE",selectedRide);
                startActivity(showRideDetailsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void setProgressDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    private void initJsonArray(){
        RequestQueue queue = Volley.newRequestQueue(ShowAvailableRidesActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, GET_ALL_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dialog.hide();
                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        TransportRide transportRide = new Gson().fromJson(jsonObject, TransportRide.class);
                        availableRides.add(transportRide);
                        transportAdapter.notifyDataSetChanged();
                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+transportRide.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG", "onCreate: size of List in Request"+availableRides.size());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowAvailableRidesActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void getAllRides(){

    }
}