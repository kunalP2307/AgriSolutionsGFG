package com.example.solutiontofarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SelectPlaceActivity extends AppCompatActivity {

    EditText editTextAddress;
    ConstraintLayout constraintLayoutTemp, constraintLayoutSelectedAddress;
    TextView textViewLocation, textViewAddress;
    Button buttonAddCompleteAddress;
    Address address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);
        bindComponents();

    }

    private void bindComponents(){
        editTextAddress = findViewById(R.id.edit_text_address_autoc);
        editTextAddress.setFocusable(false);

        editTextAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Places.initialize(getApplicationContext(),Extras.API_KEY);
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SelectPlaceActivity.this);

                startActivityForResult(intent, 100);
            }
        });

        textViewLocation = findViewById(R.id.text_view_location_autoc);
        textViewAddress = findViewById(R.id.text_view_address_autoc);
        buttonAddCompleteAddress = findViewById(R.id.button_enter_complete_add);
        buttonAddCompleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra(Extras.EXTRA_SELECT_ADDRESS_TYPE).equals("source") && address != null){
                    Intent intent = new Intent(getApplicationContext(), AddRideDetailsActivity.class);
                    intent.putExtra(Extras.EXTRA_SELECTED_ADDRESS_FOR_S, (Serializable) address);
                    SharedPreferences sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("s_lat", address.getLatitude());
                    editor.putString("s_lon", address.getLongitude());
                    editor.putString("s_add", address.getAddress());
                    editor.putString("s_loc", address.getName());
                    editor.apply();
                    startActivity(intent);
                }
                 if(getIntent().getStringExtra(Extras.EXTRA_SELECT_ADDRESS_TYPE).equals("dest") && address != null){
                    Intent intent = new Intent(getApplicationContext(), AddRideDetailsActivity.class);
                    intent.putExtra(Extras.EXTRA_SELECTED_ADDRESS_FOR_D, (Serializable) address);
                    SharedPreferences sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("d_lat", address.getLatitude());
                    editor.putString("d_lon", address.getLongitude());
                    editor.putString("d_add", address.getAddress());
                    editor.putString("d_loc", address.getName());
                    editor.apply();
                    startActivity(intent);
                }
            }
        });

        constraintLayoutTemp = findViewById(R.id.cs_nothing);
        constraintLayoutSelectedAddress = findViewById(R.id.cs_selected_address_det);
        constraintLayoutTemp.setVisibility(View.INVISIBLE);
        constraintLayoutSelectedAddress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);

            textViewLocation.setText(place.getName());
            textViewAddress.setText(place.getAddress());

            address = new Address(
                String.valueOf(place.getLatLng().latitude), String.valueOf(place.getLatLng().longitude), place.getAddress(), place.getName()
            );

            constraintLayoutTemp.setVisibility(View.VISIBLE);
            constraintLayoutSelectedAddress.setVisibility(View.VISIBLE);
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }
}