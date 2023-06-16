package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solutiontofarming.data.AgriLand;
import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.TransportRide;
import com.example.solutiontofarming.getallapicalls.FetchAllLands;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowAvailableAgriLandsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textViewLandAddress, textViewLandArea, textViewLandRent, textViewLandType;
    ListView listViewAgriLand;
    NewAgriLandAdapter newAgriLandAdapter;
    List<AgriLand> availableLands;

    String TAG = "Fetch_Lands";

    private static final String API_URL = "http://"+ Extras.VM_IP +":7000/find/agri_lands";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_agri_lands);
        bindComponents();
        getSupportActionBar().setTitle("All Lands");


//        availableRides = (List<AgriculturalLand>) getIntent().getSerializableExtra("availableLand");
//
//        listViewAgriLand = findViewById(R.id.list_available_agri_lands);
//        agriculturalLandAdapter = new AgriculturalLandAdapter(this, (ArrayList<AgriculturalLand>) availableRides);
//        listViewAgriLand.setAdapter(agriculturalLandAdapter);
//        listViewAgriLand.setOnItemClickListener(this);

        availableLands = new ArrayList<>();

        fetch_all_lands();

    }

    public void fetch_all_lands(){

        FetchAllLands fetchAllLands = new FetchAllLands(this);

        fetchAllLands.fetchData(new FetchNews.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        AgriLand agriLand = new Gson().fromJson(jsonObject, AgriLand.class);
                        availableLands.add(agriLand);
                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+agriLand.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                newAgriLandAdapter = new NewAgriLandAdapter(ShowAvailableAgriLandsActivity.this,(ArrayList<AgriLand>) availableLands);
                listViewAgriLand.setAdapter(newAgriLandAdapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }


    public void bindComponents(){
        this.listViewAgriLand = findViewById(R.id.list_available_agri_lands);
        this.textViewLandAddress = findViewById(R.id.text_field_location);
        this.textViewLandArea = findViewById(R.id.text_field_area);
        this.textViewLandRent = findViewById(R.id.text_field_rent);
        this.textViewLandType = findViewById(R.id.text_available_limit_row);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

////        AgriculturalLand agriculturalLand = (AgriculturalLand) agriculturalLandAdapter.getItem(position);
//
//        Intent intent = new Intent(getApplicationContext(),AddAgriLandActivity.class);
//        intent.putExtra("display",true);
//        intent.putExtra("displayAgriLand",agriculturalLand);
//        startActivity(intent);
    }
}