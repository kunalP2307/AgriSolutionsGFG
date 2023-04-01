package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    private TextView textViewMoreNews;
    private ImageView imageViewHomeNews;
    TextView textViewTemperature,textViewAddress;
    Button btnGetTemperature,btnShowWeatherDetails;

    // abbhis

    public LocationManager locationManager;
    public LocationListener locationListener = new HomeActivity.MyLocationListener();
    String lat, lon;

    private boolean gps_enable = false;
    private boolean network_enable = false;
    Geocoder geocoder;
    List<Address> myaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setTitle("Welcome Home");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_services:
                        Intent intent = new Intent(getApplicationContext(),ServicesActivity.class);
                        intent.putExtra("isVerified",false);
                        startActivity(intent);
                        overridePendingTransition(0,0);
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
        bindComponents();
        addListeners();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        getMyLocation();
        getMyLocation();

        imageSlider=findViewById(R.id.image_slider);

        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.p1,null));
        images.add(new SlideModel(R.drawable.p2,null));
        images.add(new SlideModel(R.drawable.p3,null));
        images.add(new SlideModel(R.drawable.p4,null));
        images.add(new SlideModel(R.drawable.p5,null));
        imageSlider.setImageList(images);
        
    }
    private void bindComponents(){
        textViewMoreNews = findViewById(R.id.textv_more);
        imageViewHomeNews = findViewById(R.id.img_home_news);
        textViewAddress = findViewById(R.id.text_address_for_temp);
        textViewTemperature = findViewById(R.id.text_tempreture);
        //textViewTemperature.setTextSize(70);
        btnGetTemperature = findViewById(R.id.btn_get_temperature);
        btnShowWeatherDetails = findViewById(R.id.btn_get_weather_details);

    }

    private void addListeners(){
        textViewMoreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
        imageViewHomeNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeNewsActivity.class);
                startActivity(intent);
            }
        });

       btnGetTemperature.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getMyLocation();
           }
       });
       btnShowWeatherDetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),WeatherDetailsActivity.class));
           }
       });
    }


    private boolean checkLocation() {

        int location= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location2= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if(location!= PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }if(!listPermission.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermission.toArray(new String[listPermission.size()]),1);
        }
        return  true;
    }

    class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);
                lat = "" + location.getLatitude();
                lon = "" + location.getLongitude();

                // Log.d("My Activity", "Lat1 "+lat);
                // Log.d("Lat", "getMyLocation: "+lat);



                geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());

                try {
                    myaddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address= myaddress.get(0).getAddressLine(0);
                // tv1.setText("Your Location :: "+address);
                // latitude.setText(lat);
                //     longitude.setText(lon);
                textViewAddress.setText(address);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
    //--------------------------------------------------------------------

    public void getMyLocation() {

        Log.d("hii", "Hiii ");

        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        if (!gps_enable && !network_enable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Attenstion");
            builder.setMessage("Turn On GPS");
            builder.create().show();

        }

        if (gps_enable) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }


        if(network_enable){

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        }


        String id="0852853b3628f9f0ef79308eacb461b4";
        String Url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=0852853b3628f9f0ef79308eacb461b4";

        RequestQueue que1 = Volley.newRequestQueue(getApplicationContext());


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Json Object
                    Log.d("In getLocation", "onResponces: ");
                    JSONObject object1 = response.getJSONObject("main");
                    String temp = object1.getString("temp");
                    //Temperature
                    Double temp1 = Double.parseDouble(temp)-273.15;
                    textViewTemperature.setTextSize(70);
                    textViewTemperature.setText(temp1.toString().substring(0,5)+"\u2103");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        que1.add(req);
    }
}