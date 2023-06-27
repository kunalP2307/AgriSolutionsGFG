package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.solutiontofarming.data.AgriLand;
import com.example.solutiontofarming.data.LandUser;
import com.example.solutiontofarming.data.User;
import com.example.solutiontofarming.getallapicalls.FetchAllLands;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowMyLandsActivity extends AppCompatActivity {

    TextView textViewLandAddress, textViewLandArea, textViewLandRent, textViewLandType;
    ListView listViewAgriLand;
    NewAgriLandAdapter newAgriLandAdapter;
    List<AgriLand> myLands;

    LandUser landUser;

    ShimmerFrameLayout shimmer;
    ProgressDialog progressDialog;

    String TAG = "Fetch_my_Lands";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_lands);

        getNameFromFireBase();
        bindComponents();
        getSupportActionBar().setTitle("Your Lands");

        shimmer = findViewById(R.id.shimmer_activity_my_lands);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();

        progressDialog = new ProgressDialog(ShowMyLandsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        myLands = new ArrayList<>();

        fetch_my_lands();

        listViewAgriLand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AgriLand selectedLand = (AgriLand) newAgriLandAdapter.getItem(position);
                Intent showLandDetailsIntent = new Intent(getApplicationContext(),ShowAgriLandDetailsNew.class);
                showLandDetailsIntent.putExtra("EXTRA_SELECTED_LAND",selectedLand);
                startActivity(showLandDetailsIntent);
            }
        });
    }

    public void fetch_my_lands(){

        FetchAllLands fetchAllLands = new FetchAllLands(this);

        fetchAllLands.fetchData(new FetchNews.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                Log.d(TAG, "onResponse: Total Lands "+response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
                        AgriLand agriLand = new Gson().fromJson(jsonObject, AgriLand.class);

                        if(agriLand.getUser().getUserEmailId().equals(landUser.getUserEmailId())){
                            myLands.add(agriLand);
                        }

                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
                        Log.d("TAG", "onResponse:mapped Java "+agriLand.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                progressDialog.dismiss();
                shimmer.stopShimmerAnimation();
                shimmer.setVisibility(View.INVISIBLE);

                newAgriLandAdapter = new NewAgriLandAdapter(ShowMyLandsActivity.this,(ArrayList<AgriLand>) myLands);
                listViewAgriLand.setAdapter(newAgriLandAdapter);
            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                shimmer.stopShimmerAnimation();
                shimmer.setVisibility(View.INVISIBLE);
                Toast.makeText(ShowMyLandsActivity.this, "Unable to Fetch Available Lands. Try Again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getNameFromFireBase(){
        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user  = snapshot.getValue(User.class);

                String userName = user.getFullName();
                String userEmail = user.getEmail();
                Log.d(TAG, "onDataChange: userName: "+userName);
                Log.d(TAG, "onDataChange: userEmail: "+userEmail);

                landUser = new LandUser(userName, userEmail);

                Log.d("Add_Land", "onDataChange: "+user.getFullName()+user.getEmail()+user.getPhoneNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void bindComponents(){
        this.listViewAgriLand = findViewById(R.id.list_available_agri_lands);
        this.textViewLandAddress = findViewById(R.id.text_field_location);
        this.textViewLandArea = findViewById(R.id.text_field_area);
        this.textViewLandRent = findViewById(R.id.text_field_rent);
        this.textViewLandType = findViewById(R.id.text_agri_land_type_row);
    }
}