package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.RideTime;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.getallapicalls.GetAllRides;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

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
    String GET_ALL_URL = "http://"+ Extras.VM_IP +":7000/find/rides";
    ProgressDialog dialog;
    List<TransportRide> availableRides;
    final static double RADIUS_LIMIT = 10;
    String TAG = "ShowAvailableRidesActivity";

    ShimmerFrameLayout shimmer_layout;
    ConstraintLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_rides);
        main_layout = findViewById(R.id.main_layout);
        shimmer_layout = findViewById(R.id.shimmer_activity_available_rides);

        main_layout.setVisibility(View.INVISIBLE);
        shimmer_layout.startShimmerAnimation();
//        Address s = new Address();
//        s.setLatitude("18.603428810812144");
//        s.setLongitude("73.78611887320858");
//        Address d = new Address();
//        d.setLatitude("18.601053626387692");
//        d.setLongitude("73.76474110847897");
//        List<LatLng> path = getGeoPtsAlongRoute(s,d);
//
//        for(int i=0; i<path.size(); i++){
//            Log.d(TAG, "onCreate: "+path.get(i).latitude + " "+path.get(i).longitude);
//        }

        renderShowRides();

    }

    private void renderShowRides(){
        initListView();
        if(getIntent().getStringExtra("RIDE_SHOW_TYPE") != null){
            if(getIntent().getStringExtra("RIDE_SHOW_TYPE").equals("SEARCHED")){
                List<TransportRide> allRides = (List<TransportRide>) getIntent().getSerializableExtra("RIDES_LIST");
                Address source = (Address) getIntent().getSerializableExtra("EXTRA_SOURCE_SEARCH");
                Address destination = (Address) getIntent().getSerializableExtra("EXTRA_DEST_SEARCH");

                searchRides(allRides, source, destination, new RideTime());
                Log.d(TAG, "renderShowRides: "+availableRides.size());
                main_layout.setVisibility(View.VISIBLE);
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
            }
        }
        else {
            getAllRides();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initListView(){

        Log.d("TAG", "onCreate: Before ");
        getSupportActionBar().setTitle("Available Rides");

//        Log.d("TAG", "onCreate: size of List "+availableRides.size());
//        Log.d("TAG", "onCreate: After ");

        availableRides = new ArrayList<>();
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


    private void setProgressDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    private void getAllRides(){
        setProgressDialog();
        GetAllRides getAllRides = new GetAllRides(this);

        getAllRides.fetchAllRides(new FetchNews.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                dialog.hide();
                main_layout.setVisibility(View.VISIBLE);
                shimmer_layout.stopShimmerAnimation();
                shimmer_layout.setVisibility(View.INVISIBLE);
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
            @Override
            public void onError(VolleyError error) {
                dialog.hide();
                Toast.makeText(ShowAvailableRidesActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchRides(List<TransportRide> allRides, Address source, Address destination, RideTime time){

        TransportRide ride;
        Address rideSource, rideDestination;
        //availableRides = new ArrayList<>();
        for(int i=0; i<allRides.size(); i++){
            ride = allRides.get(i);
            rideSource = ride.getSource();
            rideDestination = ride.getDestination();


            double sDistance = getDistanceBetweenGeoPts(
                    Double.parseDouble(source.getLatitude()),
                    Double.parseDouble(source.getLongitude()),
                    Double.parseDouble(rideSource.getLatitude()),
                    Double.parseDouble(rideSource.getLongitude())
                    );

            double dDistance = getDistanceBetweenGeoPts(
                    Double.parseDouble(destination.getLatitude()),
                    Double.parseDouble(destination.getLongitude()),
                    Double.parseDouble(rideDestination.getLatitude()),
                    Double.parseDouble(rideDestination.getLongitude())
            );

            Log.d(TAG, "searchRides: source Dis "+sDistance+" Destination Dis"+dDistance);

            if(dDistance < RADIUS_LIMIT && sDistance < RADIUS_LIMIT){
                Log.d(TAG, "searchRides: Inside If");
                availableRides.add(ride);
                transportAdapter.notifyDataSetChanged();
            }
            else {
                List<LatLng> path = getGeoPtsAlongRoute(rideSource, rideDestination);
                int minIndexSource = 0;
                double distanceSource = Double.MAX_VALUE;
                for (int k = 0; k < path.size(); k++) {
                    Address pointOnRoute = new Address();
                    pointOnRoute.setLatitude(Double.toString(path.get(k).latitude));
                    pointOnRoute.setLongitude(Double.toString(path.get(k).longitude));
                    double temp = getDistance(source, pointOnRoute);
                    if (temp < distanceSource) {
                        distanceSource = temp;
                        minIndexSource = k;
                    }
                }
                double distanceDest = Double.MAX_VALUE;
                int minIndexDist = 0;
                if (distanceSource < RADIUS_LIMIT) {
                    for (int j = path.size() - 1; j >= minIndexSource; j--) {
                        Address pointOnRoute = new Address();
                        pointOnRoute.setLatitude(Double.toString(path.get(j).latitude));
                        pointOnRoute.setLongitude(Double.toString(path.get(j).longitude));
                        double temp = getDistance(destination, pointOnRoute);
                        if (temp < distanceDest) {
                            distanceDest = temp;
                            minIndexDist = j;
                        }
                    }
                }
                Log.d(TAG, "searchRides: Source Distance" + distanceSource + " Destination SOurce" + distanceDest);

                Address s = new Address();
                s.setLatitude(Double.toString(path.get(minIndexSource).latitude));
                s.setLongitude(Double.toString(path.get(minIndexSource).longitude));

                Address d = new Address();
                d.setLatitude(Double.toString(path.get(minIndexDist).latitude));
                d.setLongitude(Double.toString(path.get(minIndexDist).longitude));

                double sd = getDistance(s, source);
                double dd = getDistance(d, destination);

                if(sd < RADIUS_LIMIT && dd<RADIUS_LIMIT){
                    availableRides.add(ride);
                    transportAdapter.notifyDataSetChanged();
                }
                Log.d(TAG, "searchRides: distance after looping" + sd + "\t" + dd);
            }
        }

        List<TransportRide> filteredRides = new ArrayList<>();

    }






    private double getDistance(Address source, Address destination){
        return getDistanceBetweenGeoPts(
                Double.parseDouble(source.getLatitude()),
                Double.parseDouble(source.getLongitude()),
                Double.parseDouble(destination.getLatitude()),
                Double.parseDouble(destination.getLongitude())
        );
    }

    private double getDistanceBetweenGeoPts(double sLat, double sLon, double dLat, double dLon){
        Location start = new Location("");
        start.setLatitude(sLat);
        start.setLongitude(sLon);

        Location end = new Location("");
        end.setLatitude(dLat);
        end.setLongitude(dLon);
        return (start.distanceTo(end)/1000);
    }

    private List<LatLng> getGeoPtsAlongRoute(Address source,Address destination){
        List<LatLng> path = new ArrayList();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBgXbVJEa9ev4akoZhezFVYeGfneYbHlRQ")
                .build();

        DirectionsApiRequest req = DirectionsApi.getDirections(context, source.getLatitude()+","+source.getLongitude(), destination.getLatitude()+","+destination.getLongitude());
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j+=10){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e("TAG", ex.getLocalizedMessage());
        }

        return path;
    }
    private void initJsonArray(){
        setProgressDialog();

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
                dialog.hide();
                Toast.makeText(ShowAvailableRidesActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void ignore(){
        //List<TransportRide> availableRides = (List<TransportRide>) getIntent().getSerializableExtra("availableRides");


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
    }
}