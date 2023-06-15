package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddWareHouseActivity extends AppCompatActivity {
    EditText editTextOwnerName ,editTextOwnerAddress,editTextOwnerMob, editTextWareHouseLocation ,editTextFloorSpace, editTextCeiling,editTextTransportAccess,editTextDescription,
            editLeaseTermDuration ,editTextLeaseTermStart , editTextLeaseTermEnd,editTextRent;
    Button btnAddWarehouse;
    RadioButton radioFirePreventYes, radioFirePreventNo, radioTransportAccessYes, radioTransportAccessNo;
    Date leaseTermStart, leaseTermEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ware_house);
        bindComponents();
        addListeners();
    }

    private void bindComponents() {

        editTextOwnerName = findViewById(R.id.edit_text_select_name_warehouse);
        editTextOwnerMob = findViewById(R.id.edit_text_select_contact_warehouse);
        editTextWareHouseLocation = findViewById(R.id.edit_text_select_address_warehouse);
        editTextFloorSpace = findViewById(R.id.edit_text_select_floor_space_warehouse);
        editTextCeiling = findViewById(R.id.edit_text_select_ceiling_height_warehouse);
        editTextTransportAccess = findViewById(R.id.edit_text_select_road_conditions_warehouse);

        editLeaseTermDuration = findViewById(R.id.edit_text_select_lease_term_duration_warehouse);
        editTextLeaseTermStart =findViewById(R.id.edit_text_select_lease_term_start_date_warehouse);
        editTextLeaseTermEnd=findViewById(R.id.edit_text_select_lease_term_end_date_warehouse);
        editTextRent =findViewById(R.id.edit_text_rent);
//      textview is given end date , edittext id given rent
        btnAddWarehouse = findViewById(R.id.btn_rent_warehouse);

        radioFirePreventYes = findViewById(R.id.edit_text_select_fire_prevention_system_yes_warehouse);
        radioFirePreventNo = findViewById(R.id.edit_text_select_fire_prevention_system_no_warehouse);
        radioTransportAccessYes = findViewById(R.id.edit_text_select_transportation_access_yes_warehouse);
        radioTransportAccessNo = findViewById(R.id.edit_text_select_transportation_access_no_warehouse);



    }
    public void addListeners()
    {
        btnAddWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    Log.d("", "onClick: "+editTextWareHouseLocation.toString());
                } else {

                }
            }
        });

    }

    private boolean isValidOwnerName(String ownerName) {
        //  only letters
        return !TextUtils.isEmpty(ownerName) && ownerName.matches("[a-zA-Z]+");
    }

    private boolean isValidAddress(String address) {
        // valid pincode
        return !TextUtils.isEmpty(address) && address.matches(".\\d{6}.");
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        //  exactly 10 digits
        return !TextUtils.isEmpty(mobileNumber) && mobileNumber.matches("\\d{10}");
    }

    private boolean isValidTermDuration(String termDuration) {
        // only numbers
        return !TextUtils.isEmpty(termDuration) && termDuration.matches("\\d+");
    }

    private boolean isValidDate(String date) {
        return !TextUtils.isEmpty(date);
    }

    private boolean isFormValid() {
        String ownerName = editTextOwnerName.getText().toString().trim();
        String address = editTextOwnerAddress.getText().toString().trim();
        String mobileNumber = editTextOwnerMob.getText().toString().trim();
        String termDuration = editLeaseTermDuration.getText().toString().trim();
        String startDate = editTextLeaseTermStart.getText().toString().trim();
        String endDate = editTextLeaseTermEnd.getText().toString().trim();

        boolean isOwnerNameValid = isValidOwnerName(ownerName);
        boolean isAddressValid = isValidAddress(address);
        boolean isMobileNumberValid = isValidMobileNumber(mobileNumber);
        boolean isTermDurationValid = isValidTermDuration(termDuration);
        boolean isStartDateValid = isValidDate(startDate);
        boolean isEndDateValid = isValidDate(endDate);

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

        return isOwnerNameValid && isAddressValid && isMobileNumberValid && isTermDurationValid && isStartDateValid && isEndDateValid;
    }

}