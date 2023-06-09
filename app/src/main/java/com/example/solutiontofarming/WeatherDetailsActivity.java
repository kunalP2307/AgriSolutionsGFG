
package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherDetailsActivity extends AppCompatActivity {

    TextView textViewAddress, textViewTemperature, textViewFeelsLike, textViewDescription, textViewDayNight, textViewSunrise, textViewSunset,
                textViewHumidity, textViewPressure, textViewWindSpeed, textViewDirection, textViewVisibility, textViewCloudiness,textViewLastUpdated;

    ScrollView scrollView;
    ProgressDialog dialog;
    TextView textViewToday;
    TextView textViewDates[] = new TextView[7];
    ImageView imageViewIcWeather;
    String address;

    String lat, lon;

//    private boolean gps_enable = false;
//    private boolean network_enable = false;
//    public LocationManager locationManager;
//    public LocationListener locationListener = new MyLocationListener();
//    Geocoder geocoder;
//    List<Address> myaddress;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        getSupportActionBar().setTitle("Weather");

        dialog = new ProgressDialog(this);
        bindComponents();
        getWeatherDetails();

    }

    private void bindComponents(){
        setProgressDialog();
        textViewTemperature = findViewById(R.id.text_view_temp_wd);
        textViewAddress = findViewById(R.id.text_view_address_wd);
        textViewFeelsLike = findViewById(R.id.text_view_feels_like_wd);
        textViewDescription = findViewById(R.id.text_view_desc_wd);
        textViewDayNight = findViewById(R.id.text_view_time_type_wd);
        textViewSunrise = findViewById(R.id.text_view_sunrise_wd);
        textViewSunset = findViewById(R.id.text_view_sunset_wd);
        textViewSunset = findViewById(R.id.text_view_sunset_wd);
        textViewHumidity = findViewById(R.id.text_view_humidity_wd);
        textViewPressure = findViewById(R.id.text_view_pressure_wd);
        textViewWindSpeed = findViewById(R.id.text_view_wind_speed_wd);
        textViewDirection = findViewById(R.id.text_view_wind_direction_wd);
        textViewVisibility = findViewById(R.id.text_view_visibility_wd);
        textViewCloudiness = findViewById(R.id.text_view_cloudiness_wd);
        textViewLastUpdated = findViewById(R.id.text_view_updated_at_wd);
        imageViewIcWeather = findViewById(R.id.image_view_weather_ic_wd);
        textViewToday = findViewById(R.id.text_view_today_wd);

        scrollView = findViewById(R.id.sc_weather_details);
        scrollView.setVisibility(View.INVISIBLE);

        textViewToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressDialog();
                getWeatherDetails();
                deselectDate();
                textViewToday.setBackgroundColor(Color.parseColor("#EAE9E9"));
            }
        });

        int ids[] = {R.id.text_view_day1, R.id.text_view_day2,R.id.text_view_day3, R.id.text_view_day4, R.id.text_view_day5, R.id.text_view_day6,R.id.text_view_day7};
        for(int i=0; i<7; i++){
            textViewDates[i] = findViewById(ids[i]);
            int finalI = i;
            textViewDates[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getForecastForDay(finalI +1);
                    setProgressDialog();
                }
            });
        }
        setNextDates();
    }

    public void setNextDates(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
        for (int i = 1; i < 8; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            textViewDates[i-1].setText(day);
            Log.i("WeatherDetailsActivity", "dayyy"+day);
        }
    }

    private void setProgressDialog(){
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    public void getWeatherDetails(){

        lat = getIntent().getStringExtra("EXTRA_LAT");
        lon = getIntent().getStringExtra("EXTRA_LON");
        address = getIntent().getStringExtra("EXTRA_ADDRESS");

        String id="0852853b3628f9f0ef79308eacb461b4";
        String Url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+id;

        RequestQueue que1 = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Json Object
                    JSONObject main = response.getJSONObject("main");
                    Log.d("Weather Acti", "onResponse: " + response.toString());
                    dialog.hide();
                    textViewAddress.setText(address);
                    scrollView.setVisibility(View.VISIBLE);

                    //Temperature
                    String temp = main.getString("temp");
                    int temperature = (int) Math.round(Double.parseDouble(temp) - 273.15);
                    textViewTemperature.setText(temperature+"\u2103");

                    //Feels like
                    String feelsLike = main.getString("feels_like");
                    int feelsLikeTemp = (int)Math.round(Double.parseDouble(feelsLike) - 273.15);
                    textViewFeelsLike.setText("Feels like "+feelsLikeTemp+"\u2103");

                    // Humdity
                    String hum = main.getString("humidity");
                    textViewHumidity.setText(hum+"%");

                    //Pressures
                    String pre = main.getString("pressure");
                    textViewPressure.setText(pre+"hPa");


                    //Sunset
                    JSONObject sys = response.getJSONObject("sys");
                    String sunset = sys.getString("sunset");
                    textViewSunset.setText(Utils.utcToIST(sunset));

                    //SunRise
                    String sunrise = sys.getString("sunrise");
                    textViewSunrise.setText(Utils.utcToIST(sunrise));

                    //WindSpeed
                    JSONObject wind = response.getJSONObject("wind");
                    String speed = wind.getString("speed");
                    textViewWindSpeed.setText(speed+"m/s");

                    String direction = wind.getString("deg");
                    textViewDirection.setText(direction+" degrees");

                    String visibility = response.getString("visibility");
                    textViewVisibility.setText(visibility+"m");

                    JSONObject clouds = response.getJSONObject("clouds");
                    String cloudiness = clouds.getString("all");
                    textViewCloudiness.setText(cloudiness+"%");

                    // description
                    JSONObject weather = (JSONObject) response.getJSONArray("weather").get(0);
                    Log.d("", "onResponse: JSON array 0 "+weather.toString());
                    String description = weather.getString("description");
                    textViewDescription.setText(description);

                    //icon
                    String icon = weather.getString("icon");
                    setWeatherIcon(icon);

                    if(icon.charAt(icon.length() - 1) == 'd') {
                        textViewDayNight.setText("Day");
                    }else textViewDayNight.setText("Night");

                    //updated at
                    String updatedAt = response.getString("dt");
                    textViewLastUpdated.setText("Updated at : "+Utils.utcToIST(updatedAt));

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        que1.add(req);
    }

    private void setSelectedDate(int i){
        deselectDate();
        textViewDates[i].setBackgroundColor(Color.parseColor("#EAE9E9"));
        textViewToday.setBackgroundColor(Color.WHITE);
    }
    private void deselectDate(){
        for(int i=0; i<7; i++){
            textViewDates[i].setBackgroundColor(Color.WHITE);
        }
    }
    public void getForecastForDay(int i) {

        setSelectedDate(i-1);

        String id="0852853b3628f9f0ef79308eacb461b4";
        String Url="https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude{part}&appid="+id;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("daily");
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    JSONObject main = jsonObject.getJSONObject("temp");

                    // Tempreture
                    String min = main.getString("min");
                    String max = main.getString("max");
                    int minTemp = (int) Math.round(Double.parseDouble(min) - 273.15);
                    int maxTemp = (int) Math.round(Double.parseDouble(max) - 273.15);

                    dialog.hide();
                    int temp = Math.round((minTemp + maxTemp)/2);
                    textViewTemperature.setText(temp+"\u2103");

                    // feels like
                    textViewFeelsLike.setText("Day Average Temperature");

                    JSONObject feelsLike = jsonObject.getJSONObject("feels_like");

                    // Tempreture
                    String day = feelsLike.getString("day");
                    String night = feelsLike.getString("night");
                    int dayTemp = (int) Math.round(Double.parseDouble(day) - 273.15);
                    int nightTemp = (int) Math.round(Double.parseDouble(night) - 273.15);

                    int feelsLikeTemp = Math.round((dayTemp + nightTemp)/2);
                    textViewFeelsLike.setText("Feels like "+feelsLikeTemp+"\u2103");

                    // Pressure
                    String pressure = jsonObject.getString("pressure");
                    textViewPressure.setText(pressure+"hPa");

                    // Humidity
                    String humidity = jsonObject.getString("humidity");
                    textViewHumidity.setText(humidity+"%");

                    // wind_speed
                    String windSpeed = jsonObject.getString("wind_speed");
                    textViewWindSpeed.setText(windSpeed+"m/s");

                    // wind direction
                    String windDirection = jsonObject.getString("wind_deg");
                    textViewDirection.setText(windDirection+" degrees");

                    // description
                    JSONObject weather = (JSONObject) jsonObject.getJSONArray("weather").get(0);
                    Log.d("", "onResponse: JSON array 0 "+weather.toString());
                    String description = weather.getString("description");
                    String icon = weather.getString("icon");
                    setWeatherIcon(icon);
                    textViewDescription.setText(description);

                    // cloudiness
                    String cloudiness = jsonObject.getString("clouds");
                    textViewCloudiness.setText(cloudiness+"%");

                    // Visibility
                    textViewVisibility.setText("NA");

                    // sunrise
                    String sunrise = jsonObject.getString("sunrise");
                    textViewSunrise.setText(Utils.utcToIST(sunrise));

                    // sunrise
                    String sunset = jsonObject.getString("sunset");
                    textViewSunrise.setText(Utils.utcToIST(sunset));


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(WeatherDetailsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }

     private void setWeatherIcon(String icon){
        Toast.makeText(this, ""+icon, Toast.LENGTH_SHORT).show();
        switch (icon){
            case "01d":
                imageViewIcWeather.setBackgroundResource(R.drawable._01d);
                break;
            case "01n":
                imageViewIcWeather.setBackgroundResource(R.drawable._01n);
                break;
            case "02d":
                imageViewIcWeather.setBackgroundResource(R.drawable._02d);
                break;
            case "02n":
                imageViewIcWeather.setBackgroundResource(R.drawable._02n);
                break;

            case "03d":
            case "03n":
                imageViewIcWeather.setBackgroundResource(R.drawable._03d);
                break;

            case "04n":
            case "04d":
                imageViewIcWeather.setBackgroundResource(R.drawable._04d);
                break;

            case "09n":
            case "09d":
                imageViewIcWeather.setBackgroundResource(R.drawable._09d);
                break;

            case "10n":
                imageViewIcWeather.setBackgroundResource(R.drawable._10n);
                break;
            case "10d":
                imageViewIcWeather.setBackgroundResource(R.drawable._10d);
                break;

            case "11n":
            case "11d":
                imageViewIcWeather.setBackgroundResource(R.drawable._11n);
                break;

        }
//
  }


    // --------------- Ignoew --------------------------------------

    //bindComponents();
    //getWeatherDetails();
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        checkLocation();


//    private boolean checkLocation() {
//
//        int location= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        List<String> listPermission = new ArrayList<>();
//
//        if(location!= PackageManager.PERMISSION_GRANTED) {
//            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if(!listPermission.isEmpty()) {
//            ActivityCompat.requestPermissions(this,listPermission.toArray(new String[listPermission.size()]),1);
//        }
//        return  true;
//    }
//
//    class MyLocationListener implements LocationListener {
//
//        @Override
//        public void onLocationChanged(@NonNull Location location) {
//            if (location != null) {
//                locationManager.removeUpdates(locationListener);
//                lat = "" + location.getLatitude();
//                lon = "" + location.getLongitude();
//
//                geocoder = new Geocoder(WeatherDetailsActivity.this, Locale.getDefault());
//
//                try {
//                    myaddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                String address= myaddress.get(0).getAddressLine(0);
//                textViewAddress.setText(address);
//            }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(@NonNull String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(@NonNull String provider) {
//
//        }
//    }

    //--------------------------------------------------------------------------

//    public void getMyLocation() {
//
//
//        Log.d("My activity", "Inside get my location: ");
//        Log.d("Lat", "getMyLocation: " + lat);
//        Log.d("Lon", "getMyLocation: " + lon);
//
//        try {
//            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            network_enable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        if (!gps_enable && !network_enable) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(WeatherDetailsActivity.this);
//            builder.setTitle("Attenstion");
//            builder.setMessage("Turn On GPS");
//            builder.create().show();
//
//        }
//
//        if (gps_enable) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//        }
//
//        if (network_enable) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//    }

}