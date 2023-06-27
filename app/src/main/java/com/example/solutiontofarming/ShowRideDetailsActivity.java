package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.Transport;
import com.example.solutiontofarming.data.TransportRide;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowRideDetailsActivity extends AppCompatActivity {

    TextView textViewDate,textViewTime,textViewSource,textViewDestination,textViewRoute,textViewVehicleType,textViewAvailableLoad,textViewPricePerKm,textViewDriverName,
            textViewContainerType,textViewGoodsTransported,textViewFare;
    CheckBox checkBoxFlexWithDate, checkBoxFlexWithTime,checkBoxOnDemandFare;
    Button btnCall,btnGetRide;
    TransportRide transport;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ride_details);
        transport = (TransportRide) getIntent().getSerializableExtra("EXTRA_SELECTED_RIDE");
        getSupportActionBar().setTitle("Ride Details");

        bindComponents();
        showRideDetails();
        addListeners();

    }
    public void bindComponents(){
        this.textViewSource = findViewById(R.id.text_ride_details_source);
        this.textViewDestination = findViewById(R.id.text_ride_details_destination);
        this.textViewDate = findViewById(R.id.text_ride_details_date);
        this.textViewTime = findViewById(R.id.text_ride_details_time);
        this.textViewPricePerKm = findViewById(R.id.text_ride_details_price_per_km);
        this.textViewAvailableLoad = findViewById(R.id.text_view_ride_details_available_load);
//        this.textViewDriverName = findViewById(R.id.text_ride_details_driver_name);
        this.textViewVehicleType = findViewById(R.id.text_view_ride_details_vehicle_type);
        this.btnCall = findViewById(R.id.btn_view_ride_details_call);
        this.textViewRoute = findViewById(R.id.text_ride_details_view_route);
//        this.btnGetRide = findViewById(R.id.btn_view_ride_details_get_this_ride);
        textViewContainerType = findViewById(R.id.text_view_ride_details_container_type);
        textViewGoodsTransported = findViewById(R.id.text_view_ride_details_goods_to_transport);
        textViewFare = findViewById(R.id.text_ride_details_price_per_km);
        checkBoxFlexWithDate = findViewById(R.id.chk_flex_date_det);
        checkBoxFlexWithTime = findViewById(R.id.chk_flex_time_det);
        checkBoxOnDemandFare = findViewById(R.id.checkboxOnDemand);
    }
    public void showRideDetails(){
        textViewSource.setText(transport.getSource().getName());
        textViewDestination.setText(transport.getDestination().getName());
        textViewDate.setText(transport.getWhen().getDate()+"  "+transport.getWhen().getTime());
        textViewTime.setText(transport.getWhen().getTime());
        textViewVehicleType.setText(transport.getVehicle().getType());
        textViewAvailableLoad.setText(transport.getVehicle().getAvailableLimit()+ " "+transport.getVehicle().getWeightUnit());
        textViewContainerType.setText(transport.getVehicle().getContainerType());
        textViewGoodsTransported.setText(transport.getGoods().getName());
        textViewFare.setText(transport.getFare().getPricePerKm());

        if(transport.getWhen().getFlexWithDate().equals("Yes"))
            checkBoxFlexWithDate.setChecked(true);
        if(transport.getWhen().getFlexWithTime().equals("Yes"))
            checkBoxFlexWithTime.setChecked(true);
        if(transport.getFare().getOnDemandAvailable().equals("Yes"))
            checkBoxOnDemandFare.setChecked(true);
//        textViewDriverName.setText(transport.getDriver().getName());
    }

    public void addListeners(){
        this.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + transport.getDriver().getContact());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
//        this.btnGetRide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent requestRideIntent = new Intent(getApplicationContext(),GetRideActivity.class);
//                requestRideIntent.putExtra("phone",transport.getDriver().getContact());
//                requestRideIntent.putExtra("name",transport.getDriver().getName());
//                requestRideIntent.putExtra("date",transport.getWhen().getDate());
//                startActivity(requestRideIntent);
//            }
//        });

        this.textViewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             Intent intent;
//             String url = String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",transport.getSource().getLatitude(), transport.getSource().getLongitude(), transport.getDestination().getLatitude(),transport.getDestination().getLongitude());
//
//             intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse(url));
//             Uri.parse(url);
//             startActivity(intent);
                List<LatLng> path = getGeoPtsAlongRoute(transport.getSource(), transport.getDestination());
                Intent intent = new Intent(getApplicationContext(),ShowRideRouteActivity.class);
                intent.putExtra("EXTRA_PATH", (Serializable) path);
                startActivity(intent);
            }
        });
    }

    private List<LatLng> getGeoPtsAlongRoute(Address source, Address destination){


        List<LatLng> path = new ArrayList();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(Extras.API_KEY)
                .build();

        DirectionsApiRequest req = DirectionsApi.getDirections(context, source.getLatitude()+","+source.getLongitude(), destination.getLatitude()+","+destination.getLongitude());
        try {
            DirectionsResult res = req.await();

            Log.d("TAG", "getGeoPtsAlongRoute: "+res.toString());
            Log.d("TAG", "getGeoPtsAlongRoute: "+res.routes);

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
}