package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class ShowRideRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;
    private boolean isMapReady = false;

    private List<LatLng> geoPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ride_route);

        mapView = findViewById(R.id.maps_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Example geo points
        geoPoints = (List<LatLng>) getIntent().getSerializableExtra("EXTRA_PATH");
        Log.d("", "onCreate: "+geoPoints);

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        isMapReady = true;

        drawPolyline();

        // Optional: Enable other map settings or features as needed
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

    }
    private void drawPolyline() {
        if (!isMapReady) {
            return;
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(geoPoints)
                .color(getResources().getColor(R.color.colorPrimary))  // Set the color of the polyline
                .width(8f)                                      // Set the width of the polyline
                .geodesic(true);                                // Set whether the polyline is geodesic

        googleMap.addPolyline(polylineOptions);

        // Adjust the camera to fit the polyline within the map view after the layout is ready
        ViewTreeObserver viewTreeObserver = mapView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng point : geoPoints) {
                    builder.include(point);
                }
                LatLngBounds bounds = builder.build();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                // Remove the listener to avoid unnecessary callbacks
                mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}