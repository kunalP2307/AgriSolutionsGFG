package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.solutiontofarming.Validators.AgriLandValidator;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.AgriLand;
import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.Field;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Lease;
import com.example.solutiontofarming.data.Owner;
import com.example.solutiontofarming.data.Rent;
import com.example.solutiontofarming.data.User;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddAgriLandActivity extends AppCompatActivity {

    final String TAG = "AddAgriLandActivity";

    //New Implementation

    EditText etFieldType, etFieldArea, etFieldDescription, etFieldLocation, etLeaseDuration;
    EditText etLandRent, etLandSharePercent, etLandOwnerName, etLandOwnerContact;
    RadioGroup rgFieldAreaUnit;
    Button btnStartDate, btnEndDate, btnAddLand;
    Spinner spRentType;

    String leaseStartDate, leaseEndDate;

    private String selectedAreaUnit = "";
    private String rentType = "";

    AgriLand agriLand = null;
    final int ADDRESS_REQ = 100;
    Address address = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agri_land_new);
//        getFarmId();
//        getUserId();

        bindComponents();
        addListeners();
        getSupportActionBar().setTitle("Add Farm");
        initPlaces();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        if(intent != null) {
//            boolean useThisActivityToDisplayLand = getIntent().getExtras().getBoolean("display");
//            agriculturalLand = (AgriculturalLand)intent.getSerializableExtra("displayAgriLand");
//
//            if (useThisActivityToDisplayLand) {
//                if(agriculturalLand != null){
//                    textViewName.setText(agriculturalLand.getProviderName());
//                    textViewPhone.setText(agriculturalLand.getProviderPhone());
//                    textViewLocation.setText(agriculturalLand.getLandAddress());
//                    textViewArea.setText(agriculturalLand.getLandArea());
//                    textViewRent.setText(agriculturalLand.getRentPerYear());
//                    textViewType.setText(agriculturalLand.getLandType());
//                    btnAddLand.setVisibility(View.INVISIBLE);
//                    btnViewLocation.setVisibility(View.VISIBLE);
//                    btnCallOwner.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }

    public void bindComponents(){
        this.btnAddLand = findViewById(R.id.btn_add_land);
        this.btnStartDate = findViewById(R.id.btn_start_date_picker);
        this.btnEndDate = findViewById(R.id.btn_end_date_picker);

        this.etFieldType = findViewById(R.id.et_field_type_add_land_new);
        this.etFieldArea = findViewById(R.id.et_field_area_add_land_new);
        this.etFieldDescription = findViewById(R.id.et_field_description_add_land_new);
        this.etFieldLocation = findViewById(R.id.et_field_location_add_land_new);
        this.etFieldLocation.setFocusable(false);

        this.etLeaseDuration = findViewById(R.id.et_lease_duration_add_land_new);

        this.etLandRent = findViewById(R.id.et_land_rent_amount);
        this.etLandSharePercent = findViewById(R.id.et_land_percent_share_amount);
        this.etLandOwnerName = findViewById(R.id.et_land_owner_name);
        this.etLandOwnerContact = findViewById(R.id.et_land_owner_contact);

        rgFieldAreaUnit = findViewById(R.id.radioGroup_field_area_unit);
        spRentType = findViewById(R.id.land_rent_type_spinner);
    }

    public void addListeners(){

        btnAddLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                setLandDetailsNew();

                if(agriLand != null)
                    addLandNew();
            }
        });


        etFieldLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AddAgriLandActivity.this);

                startActivityForResult(intent, 100);
         }
        });

        rgFieldAreaUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);

                if(radioButton != null)
                {
                    selectedAreaUnit = radioButton.getText().toString();
                    Log.d("Add_Land", "onCheckedChanged: "+selectedAreaUnit);
                }
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAgriLandActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                leaseStartDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
                                btnStartDate.setText(leaseStartDate);
                                Log.d("Add_Land", "onDateSet: "+leaseStartDate);
                            }
                        }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAgriLandActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                leaseEndDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
                                btnEndDate.setText(leaseEndDate);
                                Log.d("Add_Land", "onDateSet: "+leaseEndDate);
                            }
                        }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        spRentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                rentType = parent.getItemAtPosition(position).toString();

                if(rentType.equals("Rent"))
                {
                    etLandRent.setEnabled(true);
                    etLandRent.setText("");

                    etLandSharePercent.setEnabled(false);
                    etLandSharePercent.setText("Not Required");
                }
                else if(rentType.equals("Share of Income"))
                {
                    etLandRent.setEnabled(false);
                    etLandRent.setText("Not Required");

                    etLandSharePercent.setEnabled(true);
                    etLandSharePercent.setText("");
                }
                else if(rentType.equals("Spend Equal Get Equal"))
                {
                    etLandRent.setEnabled(false);
                    etLandRent.setText("Not Required");

                    etLandSharePercent.setEnabled(false);
                    etLandSharePercent.setText("Not Required");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected (optional)
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADDRESS_REQ && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            etFieldLocation.setText(place.getName());
            address = new Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setLandDetailsNew(){

        // Field Details
        String field_type = etFieldType.getText().toString();
        String text = etFieldArea.getText().toString();
        double field_area = 0;
        try {
            field_area = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            Log.d("Add_Land", "setLandDetailsNew: "+"Enter Valid Details");
        }
        String field_area_unit = selectedAreaUnit;

        Field field = new Field(field_type, field_area, field_area_unit);

        // Description
        String description = etFieldDescription.getText().toString();


        // Address will be fetched automatically through API

        // Lease
        String duration = etLeaseDuration.getText().toString();
        String startDate = leaseStartDate;
        String endDate = leaseEndDate;

        Lease lease = new Lease(duration, startDate, endDate);


        //Rent
        String rent_type = rentType;
        double rent_amount = 0;
        double rent_share_percent = 0;

        if(rentType.equals("Rent"))
        {
            text = etFieldArea.getText().toString();
            try {
                rent_amount = Double.parseDouble(text);
            } catch (NumberFormatException e) {
                Log.d("Add_Land", "setLandDetailsNew: "+"Enter Valid Details");
            }

            rent_share_percent = 0;
        }
        else if(rentType.equals("Share of Income"))
        {
            text = etLandSharePercent.getText().toString();

            try {
                rent_share_percent = Double.parseDouble(text);
            } catch (NumberFormatException e) {
                Log.d("Add_Land", "setLandDetailsNew: "+"Enter Valid Details");
            }
            rent_amount = 0;
        }
        else if(rentType.equals("Spend Equal Get Equal"))
        {
            rent_share_percent = 0;
            rent_amount = 0;
        }

        Rent rent = new Rent(rent_type, rent_amount, rent_share_percent);


        // Owner
        String owner_name = etLandOwnerName.getText().toString();
        String contact = etLandOwnerContact.getText().toString();

        Owner owner = new Owner(owner_name, contact);

        if(AgriLandValidator.validateField(field) && AgriLandValidator.validateDescription(description)
                && AgriLandValidator.validateLease(lease) && AgriLandValidator.validateRent(rent)
                && AgriLandValidator.validateOwner(owner) && address != null)
        {
            agriLand = new AgriLand(field,description, address, lease, rent, owner);
            clearEditTexts();
            Toast.makeText(this, "Your Land is Added. You can check it in My Lands.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Please fill all details correctly...!!!", Toast.LENGTH_LONG).show();
        }

    }

    private void clearEditTexts(){

        etFieldType.setText("");
        etFieldArea.setText("");
        etFieldLocation.setText("");
        etFieldDescription.setText("");
        etLeaseDuration.setText("");
        etLandRent.setText("");
        etLandSharePercent.setText("");
        etLandOwnerName.setText("");
        etLandOwnerContact.setText("");

        rgFieldAreaUnit.clearCheck();

        btnStartDate.setText("Select Start Date");
        btnEndDate.setText("Select End Date");

        spRentType.setSelection(0);

        etLandRent.setEnabled(true);
        etLandSharePercent.setEnabled(true);

        leaseStartDate = "";
        leaseEndDate = "";
        rentType = "";
        selectedAreaUnit = "";
        address = null;

        View focusedView = getCurrentFocus();

        if (focusedView != null && focusedView instanceof TextView) {
            // Clear the focus from the focused TextView
            focusedView.clearFocus();
        }
    }

    public void addLandNew(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "http://"+ Extras.VM_IP+":7000/insert-one/agri_lands";


        Log.d("Add_Land", "addLandNew: "+agriLand.toString());

        Gson gson = new Gson();
        String json = gson.toJson(agriLand);

        JSONObject jsonObjectAgriLand = null;

        try {
            jsonObjectAgriLand = new JSONObject(json);
        } catch (Throwable t) {
            Log.e("Add_Land", "Could not parse malformed JSON: \"" + json + "\"");
        }

        final String requestBody = jsonObjectAgriLand.toString();

        Log.d("Add_Land", "addLandNew: "+requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Add_Land", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Add_Land", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);

    }

    private void initPlaces(){
        Places.initialize(getApplicationContext(), Extras.API_KEY);
    }

//    public void setLandDetails(){
//        agriculturalLand = new AgriculturalLand();
//        agriculturalLand.setProviderId(userId);
//        agriculturalLand.setProviderName(textViewName.getText().toString());
//        agriculturalLand.setProviderPhone(textViewPhone.getText().toString());
//        agriculturalLand.setLandAddress(textViewLocation.getText().toString());
//        agriculturalLand.setLandType(textViewType.getText().toString());
//        agriculturalLand.setRentPerYear(textViewRent.getText().toString());
//        agriculturalLand.setLandArea(textViewArea.getText().toString());
//        agriculturalLand.setLandLatitude("18.677225");
//        agriculturalLand.setLandLongitude("73.844797");
//        agriculturalLand.setLandStatus("Visible to All");
//    }


//    public void getFarmId(){
//
//        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                ID id = snapshot.getValue(ID.class);
//                farmId = id.getFieldId();
//                Log.d(TAG, "onDataChange: Farm Id "+farmId);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        //  Log.d("RegAct", "onDataChange2: "+ textViewUid.getText().toString());
//
//    }

//    public void getUserId(){
//        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                userId = user.getUserId();
//                Log.d("", "onDataChange: User ID"+userId);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    public void addLand(){
//        FirebaseDatabase.getInstance().getReference("Land-Renting").child(farmId).setValue(agriculturalLand);
//        startActivity(new Intent(getApplicationContext(),AgriLandAddedActivity.class));
//    }

}