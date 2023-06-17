package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.Availability;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.ID;
import com.example.solutiontofarming.data.Owner;
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
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAgriEquipDetailsActivity extends AppCompatActivity{

    final String TAG = "AddAgriEquipment";
    EditText editTextEquipmentOwner,getEditTextEquipmentOwnerContact,editTextEquipmentLocation,editTextEquipmentName, editTextEquipmentRent,editTextEquipmentDetails,
                editTextYearsUsed,editTextAvailableFrom,editTextAvailableTill;
    Spinner spinEquipmentCategory;
    TextView textView;
    ImageView imageViewAgriEquip;
    final Calendar myCalendar= Calendar.getInstance();

    Button btnAddEquipment,buttonAddImage, buttonAddAvailability;
    String imageUrl = "";
    final public static String[] AGRI_EQUIPMENT_CATEGORY = {"Select","Tractor","Sprayer","Field Cultivator","Shredders And Cutters","Seeders And Planters","Soil Cultivation","Irrigation","Other"};
    //AgriculturalEquipment agriculturalEquipment;
    AgriEquipment agriEquipment;
    String equipmentCategory;
    String userId,equipmentId;
    Address address;

    private static final int PERMISSION_CODE =1;
    private static final int PICK_IMAGE=1;

    String filePath;
    Map config = new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agri_equip_details);

        getSupportActionBar().setTitle("Add Equipment");

        bindComponents();
        configCloudinary();
        addListeners();
        initSpinner();
        getUserId();
        initPlaces();

    }
    private void initPlaces(){
        Places.initialize(getApplicationContext(), Extras.API_KEY);
    }

    public void bindComponents(){
        editTextEquipmentName = findViewById(R.id.edit_agri_equip_name);
        editTextYearsUsed = findViewById(R.id.edit_agri_equip_year_used);
        editTextEquipmentOwner = findViewById(R.id.edit_equip_owner_name);
        getEditTextEquipmentOwnerContact = findViewById(R.id.edit_equip_owner_contact);
        editTextEquipmentName = findViewById(R.id.edit_agri_equip_name);
        editTextEquipmentRent = findViewById(R.id.edit_agri_equip_rent);
        spinEquipmentCategory = findViewById(R.id.spin_agri_equip_cat);
        editTextAvailableFrom = findViewById(R.id.edit_agri_euip_avialable_from);
        editTextAvailableTill = findViewById(R.id.edit_agri_euip_avialable_to);
        buttonAddAvailability = findViewById(R.id.btn_add_new_availability);
        buttonAddImage = findViewById(R.id.btn_add_img_equip);
        editTextEquipmentLocation = findViewById(R.id.edit_agri_equip_location);
        imageViewAgriEquip = findViewById(R.id.img_agri_equp);
        editTextEquipmentLocation.setFocusable(false);
        editTextAvailableFrom.setFocusable(false);
        editTextAvailableTill.setFocusable(false);
        btnAddEquipment = findViewById(R.id.btn_add_agri_equip);
        textView = findViewById(R.id.textView52);

    }

    private void configCloudinary() {
        config.put("cloud_name", "dy0ogzrix");
        config.put("api_key", "881912264742965");
        config.put("api_secret", "C8oUYp3G6Fd7MljO43tXaVSkDmM");
        MediaManager.init(AddAgriEquipDetailsActivity.this, config);
    }

    public void addListeners(){
        this.btnAddEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validDetails()) {
                    uploadToCloudinary(filePath);
                    setEquipmentDetails();
                    Intent intent = new Intent(getApplicationContext(), AddAgriEquipmentActivity.class);
                    intent.putExtra("AGRO_EQUIP_OBJ", agriEquipment);
                    startActivity(intent);
                }
            }
        });
        editTextEquipmentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AddAgriEquipDetailsActivity.this);

                startActivityForResult(intent, 100);
            }
        });

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        DatePickerDialog.OnDateSetListener dateFrom =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateAvlFrom();
            }
        };

        DatePickerDialog.OnDateSetListener dateTill=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateAvlTill();
            }
        };
        editTextAvailableFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(AddAgriEquipDetailsActivity.this,dateFrom,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        editTextAvailableTill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(AddAgriEquipDetailsActivity.this,dateTill,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }


    private void updateAvlFrom(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editTextAvailableFrom.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateAvlTill(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editTextAvailableTill.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (AddAgriEquipDetailsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    AddAgriEquipDetailsActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d(TAG, "onProgress: ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d(TAG, "onSuccess: "+resultData.get("url").toString());
                imageUrl = resultData.get("url").toString();
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d(TAG, "onReschedule: ");
            }
        }).dispatch();
    }
    public void accessTheGallery(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery();
            }else {
                Toast.makeText(AddAgriEquipDetailsActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            editTextEquipmentLocation.setText(place.getName());
            address = new Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
        }

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            filePath = getRealPathFromUri(data.getData(), AddAgriEquipDetailsActivity.this);
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageViewAgriEquip.setImageBitmap(bitmap);
                imageViewAgriEquip.setVisibility(View.VISIBLE);
                uploadToCloudinary(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    private void initSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AGRI_EQUIPMENT_CATEGORY);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEquipmentCategory.setAdapter(arrayAdapter);
    }


    public void addEquipment(){
        Log.d("TAG", "getTransport: "+agriEquipment.toString());

        Gson gson = new Gson();
        String json = gson.toJson(agriEquipment);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }

        JsonObject jsonObject1 = new Gson().fromJson(jsonObject.toString(), JsonObject.class);
        AgriEquipment agriEquipment1 = new Gson().fromJson(jsonObject1, AgriEquipment.class);

//        JsonObject jsonObject = gson.fromJson(json, (Type) Fare.class);
        Log.d("TAG", "onCreate: JsonObject"+jsonObject);
        Log.d("ad", "addRide: "+"Equipment added");
        Log.d("","AgriEquip Java Obj "+agriEquipment1.toString());
        //startActivity(new Intent(getApplicat ionContext(),EquipmentAddedActivity.class));
    }

    public boolean validDetails(){

        if(spinEquipmentCategory.getSelectedItem().toString().equals(AGRI_EQUIPMENT_CATEGORY[0])){
            textView.setError("Select Equipment Category");
            textView.requestFocus();
            return false;
        }
        if(editTextEquipmentName.getText().toString().isEmpty()){
            editTextEquipmentName.setError("Please Enter Equipment Name");
            editTextEquipmentName.requestFocus();
            return false;
        }
        if(editTextYearsUsed.getText().toString().isEmpty()){
            editTextYearsUsed.setError("Please Enter Equipment Condition");
            editTextYearsUsed.requestFocus();
            return false;
        }

        if(Double.parseDouble(editTextYearsUsed.getText().toString()) > 30){
            editTextYearsUsed.setError("Please Enter Valid Years");
            editTextYearsUsed.requestFocus();
            return false;
        }
        if(editTextEquipmentRent.getText().toString().isEmpty()){
            editTextEquipmentRent.setError("Please Enter Rent");
            editTextEquipmentRent.requestFocus();
            return false;
        }
        if(editTextAvailableFrom.getText().toString().equals("")){
            editTextAvailableFrom.setError("Please Select Availability");
            editTextAvailableFrom.requestFocus();
            return false;
        }
        if(editTextAvailableTill.getText().toString().equals("")){
            editTextAvailableTill.setError("Please Select Availability");
            editTextAvailableTill.requestFocus();
            return false;
        }
        if(editTextEquipmentLocation.getText().toString().equals("")){
            editTextEquipmentLocation.setError("Please Select Location of Equipment");
            editTextEquipmentLocation.requestFocus();
            return false;
        }
        if(editTextEquipmentOwner.getText().toString().isEmpty()){
            editTextEquipmentOwner.setError("Please Enter Owner Name");
            editTextEquipmentOwner.requestFocus();
            return false;
        }
        String contact = getEditTextEquipmentOwnerContact.getText().toString();
        String regNoRegex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        Pattern pattern = Pattern.compile(regNoRegex);
        Matcher matcher = pattern.matcher(contact);
        if(!matcher.matches()){
            getEditTextEquipmentOwnerContact.setError("Please Enter Valid Contact");
            getEditTextEquipmentOwnerContact.requestFocus();
            return false;
        }
        String start = editTextAvailableFrom.getText().toString();
        String end = editTextAvailableTill.getText().toString();
        if(!start.equals("") && !end.equals("")) {
            try {
                Date startD = new SimpleDateFormat("dd/MM/yyyy").parse(start);
                Date endD = new SimpleDateFormat("dd/MM/yyyy").parse(end);
                if(endD.before(startD)){
                    editTextAvailableTill.setError("Date Cannot be before Start");
                    editTextAvailableTill.requestFocus();
                    return false;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    public void setEquipmentDetails(){
        List<Availability> availabilityList = new ArrayList<>();
        availabilityList.add(new Availability(
                editTextAvailableFrom.getText().toString(),
                editTextAvailableTill.getText().toString()
        ));

        Owner owner = new Owner(
            editTextEquipmentOwner.getText().toString(),
            getEditTextEquipmentOwnerContact.getText().toString()
        );

        agriEquipment = new AgriEquipment(
                userId,
                spinEquipmentCategory.getSelectedItem().toString(),
                editTextEquipmentName.getText().toString(),
                editTextYearsUsed.getText().toString(),
                editTextEquipmentRent.getText().toString(),
                imageUrl,
                availabilityList,
                address,
                owner
        );

    }


    public void getUserId(){
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void getEquipmentId(){

        FirebaseDatabase.getInstance().getReference("IDs").child("Q2-2021").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ID id = snapshot.getValue(ID.class);
                equipmentId = id.getWareHouseId();

                Log.d(TAG, "onDataChange: Equipment ID :"+equipmentId);
                //getId(userId);
                /*if(id != null){
                   textViewUid.setText(id.getUserId());
                   Log.d("RegAct", "onDataChange: "+ textViewUid.getText().toString());
               }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  Log.d("RegAct", "onDataChange2: "+ textViewUid.getText().toString());
    }


}