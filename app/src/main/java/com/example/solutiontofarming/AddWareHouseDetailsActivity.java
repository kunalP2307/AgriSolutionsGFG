package com.example.solutiontofarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solutiontofarming.data.Address;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.InfraFacilities;
import com.example.solutiontofarming.data.Lease;
import com.example.solutiontofarming.data.Owner;
import com.example.solutiontofarming.data.Warehouse;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddWareHouseDetailsActivity extends AppCompatActivity {
    EditText editTextOwnerName ,editTextOwnerAddress,editTextOwnerMob, editTextWareHouseLocation ,editTextFloorSpace, editTextArea,editTextTransportAccess,editTextDescription,
            editLeaseTermDuration ,editTextLeaseTermStart , editTextLeaseTermEnd,editTextRent,editTextWearHouseDes;
    Button btnAddWarehouse;
    CheckBox checkBoxVentilation,checkBoxTempControl;
    final Calendar myCalendar= Calendar.getInstance();
    Address address;
    String userId;
    Warehouse warehouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ware_house_details);
        bindComponents();
        addListeners();
        initPlaces();
        getUserId();
    }

    private void initPlaces(){
        Places.initialize(getApplicationContext(), Extras.API_KEY);
    }
    public void getUserId(){
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    private void bindComponents() {

        editTextOwnerName = findViewById(R.id.edit_text_owner_name_warehouse);
        editTextOwnerMob = findViewById(R.id.edit_text_owner_contact_warehouse);
        editTextWareHouseLocation = findViewById(R.id.edit_text_select_address_warehouse);
        editTextFloorSpace = findViewById(R.id.edit_text_floor_space_warehouse);
        editTextArea = findViewById(R.id.edit_text_area_warehouse);
        editLeaseTermDuration = findViewById(R.id.edit_text_lease_term_duration_warehouse);
        editTextLeaseTermStart =findViewById(R.id.edit_text_lease_term_start_date_warehouse);
        editTextLeaseTermEnd=findViewById(R.id.edit_text_select_lease_term_end_date_warehouse);
        editTextRent =findViewById(R.id.edit_text_rent_warehouse);
//      textview is given end date , edittext id given rent
        editTextDescription = findViewById(R.id.edit_text_desciption_warehouse);
        btnAddWarehouse = findViewById(R.id.btn_add_warehouse);
        checkBoxTempControl = findViewById(R.id.check_boc_temp_contro);
        checkBoxVentilation = findViewById(R.id.checkBox_ventilation);
        editTextLeaseTermStart.setFocusable(false);
        editTextLeaseTermEnd.setFocusable(false);
        editTextOwnerAddress = findViewById(R.id.edit_text_select_address_warehouse);
        editTextOwnerAddress.setFocusable(false);
    }
    public void addListeners() {
        btnAddWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    setWareHouseDetails();
                    Intent intent = new Intent(getApplicationContext(), AddWareHouseActivity.class);
                    intent.putExtra("WAREHOUSE_OBJ", warehouse);
                    startActivity(intent);
                } else {

                }
            }
        });

        editTextWareHouseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AddWareHouseDetailsActivity.this);

                startActivityForResult(intent, 100);
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
        editTextLeaseTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(AddWareHouseDetailsActivity.this,dateFrom,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        editTextLeaseTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(AddWareHouseDetailsActivity.this,dateTill,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


    }

    private void updateAvlFrom(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editTextLeaseTermStart.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateAvlTill(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editTextLeaseTermEnd.setText(dateFormat.format(myCalendar.getTime()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            editTextWareHouseLocation.setText(place.getName());
            address = new Address(Double.toString(place.getLatLng().latitude), Double.toString(place.getLatLng().longitude), place.getAddress(), place.getName());
        }

        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, ""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setWareHouseDetails(){
        Owner owner = new Owner(
                editTextOwnerName.getText().toString(),
                editTextOwnerMob.getText().toString()
        );

        Lease lease = new Lease(
            editLeaseTermDuration.getText().toString(),
            editTextLeaseTermStart.getText().toString(),
                editTextLeaseTermEnd.getText().toString()
        );
        String ventilation = "No";
        String tempControl = "No";

        if(checkBoxVentilation.isChecked())
            ventilation = "Yes";
        if(checkBoxTempControl.isChecked())
            tempControl = "Yes";

        InfraFacilities infraFacilities = new InfraFacilities(ventilation, tempControl);
        warehouse = new Warehouse(
                userId,
                address,
                owner,
                editTextFloorSpace.getText().toString(),
                editTextArea.getText().toString(),
                editTextDescription.getText().toString(),
                infraFacilities,
                lease,
                editTextRent.getText().toString()
        );

    }


    private boolean isValidOwnerName(String ownerName) {
        //  only letters
        return !TextUtils.isEmpty(ownerName);
    }

    private boolean isValidAddress(String address) {
        // valid pincode
        return !TextUtils.isEmpty(address);
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        //  exactly 10 digits
        return !TextUtils.isEmpty(mobileNumber) && mobileNumber.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$");
    }

    private boolean isValidTermDuration(String termDuration) {
        // only numbers
        return !TextUtils.isEmpty(termDuration);
    }

    private boolean isValidDate(String date) {
        return !TextUtils.isEmpty(date);
    }
    private boolean isValidFreeSpace(String date) {
        return !TextUtils.isEmpty(date);
    }

    private boolean isValidRent(String date) {
        return !TextUtils.isEmpty(date);
    }
    private boolean isValidArea(String date) {
        return !TextUtils.isEmpty(date);
    }

    private boolean isFormValid() {
        String ownerName = editTextOwnerName.getText().toString().trim();
        String address = editTextOwnerAddress.getText().toString().trim();
        String mobileNumber = editTextOwnerMob.getText().toString().trim();
        String termDuration = editLeaseTermDuration.getText().toString().trim();
        String startDate = editTextLeaseTermStart.getText().toString().trim();
        String endDate = editTextLeaseTermEnd.getText().toString().trim();
        String freesSpace = editTextFloorSpace.getText().toString().trim();
        String rent = editTextRent.getText().toString().trim();
        String area = editTextArea.getText().toString().trim();

        boolean isOwnerNameValid = isValidOwnerName(ownerName);
        boolean isAddressValid = isValidAddress(address);
        boolean isMobileNumberValid = isValidMobileNumber(mobileNumber);
        boolean isTermDurationValid = isValidTermDuration(termDuration);
        boolean isStartDateValid = isValidDate(startDate);
        boolean isEndDateValid = isValidDate(endDate);
        boolean iaValidFreeSpace = isValidFreeSpace(freesSpace);
        boolean isValidRent = isValidRent(rent);
        boolean isValidArea = isValidArea(area);

        if (!isOwnerNameValid) {
            editTextOwnerName.setError("Please enter a valid owner name");
        }
        if (!isAddressValid) {
            editTextOwnerAddress.setError("Please enter a valid address with a pincode");
        }
        if (!isMobileNumberValid) {
            editTextOwnerMob.setError("Please enter a valid 10-digit mobile number");
        }
        if (!isTermDurationValid) {
            editLeaseTermDuration.setError("Please enter a valid term duration (numbers only)");
        }
        if (!isStartDateValid) {
            editTextLeaseTermStart.setError("Please select a valid start date");
        }
        if (!isEndDateValid) {
            editTextLeaseTermEnd.setError("Please select a valid end date");
        }
        if(!iaValidFreeSpace)
            editTextFloorSpace.setError("Please Enter Free Space");
        if(!isValidRent)
            editTextRent.setError("Please Enter Rent Per Term");
        if(!isValidArea)
            editTextArea.setError("Please Enter Warehouse Area");

        if(!startDate.equals("") && !endDate.equals("")) {
            try {
                Date startD = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                Date endD = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                if(endD.before(startD)){
                    editTextLeaseTermEnd.setError("Date Cannot be before Start");
                    editTextLeaseTermEnd.requestFocus();
                    return false;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return isOwnerNameValid && isAddressValid && isMobileNumberValid && isTermDurationValid && isStartDateValid && isEndDateValid
                && iaValidFreeSpace && isValidRent && isValidArea;
    }

}