
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherDetailsActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,add;
    Button btn,next;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    String lat, lon;
    EditText latitude;
    EditText longitude,humdity,pressure,sunset,sunrise;
    //  TextView tv1,tv3;
    Button btn2;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    Geocoder geocoder;
    List<Address> myaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        btn=findViewById(R.id.btn1);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        add=findViewById(R.id.address);
        next=findViewById(R.id.next);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        getSupportActionBar().setTitle("Weather");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });
        checkLocation();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WeatherForecastActivity.class));
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



                geocoder = new Geocoder(WeatherDetailsActivity.this, Locale.getDefault());

                try {
                    myaddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address= myaddress.get(0).getAddressLine(0);
                // tv1.setText("Your Location :: "+address);
                // latitude.setText(lat);
                //     longitude.setText(lon);
                add.setText(address);




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

    //--------------------------------------------------------------------------

    public void getMyLocation() {


        Log.d("My activity","Inside get my location: ");
        Log.d("Lat", "getMyLocation: "+lat);
        Log.d("Lon", "getMyLocation: "+lon);



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
            AlertDialog.Builder builder = new AlertDialog.Builder(WeatherDetailsActivity.this);
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
                    JSONObject object1 = response.getJSONObject("main");
                    String temp = object1.getString("temp");
                    //Temperature
                    Double temp1 = Double.parseDouble(temp)-273.15;
                    tv1.setText(temp1.toString().substring(0,5));

                    // Humdity
                    JSONObject obj2=response.getJSONObject("main");
                    String hum=obj2.getString("humidity");
                    tv2.setText(hum);

                    //Pressures
                    JSONObject obj3=response.getJSONObject("main");
                    String pre=obj3.getString("pressure");
                    tv3.setText(pre);



                    //Sunset
                    JSONObject obj5=response.getJSONObject("sys");
                    String sunset=obj5.getString("sunset");
                    tv5.setText(sunset);

                    //SunRise
                    JSONObject obj4=response.getJSONObject("sys");
                    String sunrise=obj4.getString("sunrise");
                    tv4.setText(sunrise);

                    //WindSpeed
                    JSONObject obj6=response.getJSONObject("wind");
                    String speed=obj6.getString("speed");
                    tv6.setText(speed);


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(WeatherDetailsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });



        que1.add(req);


    }
    //-------------------------------------------------------------------

}