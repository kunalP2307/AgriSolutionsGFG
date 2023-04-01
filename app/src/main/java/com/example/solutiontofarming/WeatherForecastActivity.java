package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherForecastActivity extends AppCompatActivity {

    Button btn;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    public LocationManager locationManager;
    TextView day1,day2,day3,day4,day5,day6,day7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        getSupportActionBar().setTitle("Weather Forecast");

        day1=findViewById(R.id.day1);
        day2=findViewById(R.id.day2);
        day3=findViewById(R.id.day3);
        day4=findViewById(R.id.day4);
        day5=findViewById(R.id.day5);
        day6=findViewById(R.id.day6);
        day7=findViewById(R.id.day7);
        getMylocation();




    }

    //------------------------------------
    private void getMylocation() {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(WeatherForecastActivity.this);
            builder.setTitle("Attenstion");
            builder.setMessage("Turn On GPS");
            builder.create().show();

        }
        if (gps_enable) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            //    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
        if(network_enable){

            //  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        }
        String la="18.520430";
        String lo="73.856743";
        String id="0852853b3628f9f0ef79308eacb461b4";
        String Url="https://api.openweathermap.org/data/2.5/onecall?lat="+la+"&lon="+lo+"&exclude{part}&appid=0852853b3628f9f0ef79308eacb461b4";

        RequestQueue que1 = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
            /*
                     JSONObject object1 = response.getJSONObject("main");
                    String temp = object1.getString("temp");
                    //Temperature
                    Double temp1 = Double.parseDouble(temp)-273.15;
                    tv3.setText(temp1.toString().substring(0,5));

                    // Humdity
                    JSONObject obj2=response.getJSONObject("main");
                    String hum=obj2.getString("humidity");
                    humdity.setText("Humidity :"+hum);

                    //Pressures
                    JSONObject obj3=response.getJSONObject("main");
                    String pre=obj3.getString("pressure");
                    pressure.setText("Pressure :"+pre);

                    JsonObjectRequest

            */
                    //---------------Variables------------------
                    JSONArray ar1,temp2;
                    JSONObject onj1,obj2;
                    String pre;
                    String hum;
                    JSONObject temp;
                    String dtemp,ntemp;
                    Double cov1,cov2;
                    String out1,out2,out3,what,des;

                    //----------------------------------------------------------
                    //Day1
                    //  JSONObject obj1=response.getJSONObject();
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(1);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day1.setText("  \tDay1 \n  "+out1+" \n  "+out2+"  \n  "+out3);





                    //Day1 end-------------------------------------------------------------



                    //Day2--------------------------------------------------------------------------------

                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(2);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day2.setText("  \tDay2 \n  "+out1+" \n  "+out2+"  \n  "+out3);


                    //Day3--------------------------------------------------------------------------------------------
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(3);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day3.setText("  \tDay3 \n  "+out1+" \n  "+out2+"  \n  "+out3);


                    //Day4-------------------------------------------------------------------------------------
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(4);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day4.setText("  \tDay4 \n  "+out1+" \n  "+out2+"  \n  "+out3);

                    //Day5--------------------------------------------------------------------------------
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(5);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day5.setText("  \tDay5 \n  "+out1+" \n  "+out2+"  \n  "+out3);

                    //Day6----------------------------------------------------------------------------------------
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(6);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,4)+"  Night Temp : "+cov2.toString().substring(0,4);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day6.setText("  \tDay6 \n  "+out1+" \n  "+out2+"  \n  "+out3);

                    //Day7---------------------------------------------------------------------
                    ar1=response.getJSONArray("daily");
                    onj1=ar1.getJSONObject(7);
                    pre=onj1.getString("pressure");
                    hum=onj1.getString("humidity");

                    temp=onj1.getJSONObject("temp");
                    dtemp=temp.getString("day");
                    ntemp=temp.getString("night");

                    cov1 = Double.parseDouble(dtemp)-273.15;
                    cov2 = Double.parseDouble(ntemp)-273.15;


                    temp2=onj1.getJSONArray("weather");
                    obj2=temp2.getJSONObject(0);
                    what = obj2.getString("main");
                    des=obj2.getString("description");

                    out1="Day Temp :"+cov1.toString().substring(0,5)+"  Night Temp : "+cov2.toString().substring(0,5);
                    out2="Description :   "+what+"    "+des+"  ";
                    out3="Pressure : "+pre+"    "+"Humidity  :"+hum+" ";

                    day7.setText("  \tDay7 \n  "+out1+" \n  "+out2+"  \n  "+out3);

                    //day8--------------------












                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(WeatherForecastActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        que1.add(req);
    }

}