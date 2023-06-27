package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.solutiontofarming.data.AgriLand;

public class ShowAgriLandDetailsNew extends AppCompatActivity {

    EditText etFieldType, etFieldArea, etFieldDescription, etFieldLocation, etLeaseDuration, etRentType;
    EditText etLandRent, etLandSharePercent, etLandOwnerName, etLandOwnerContact, etStartDate, etEndDate;
    RadioGroup rgFieldAreaUnit;
    RadioButton rbHectare, rbAcre;
    Button btnBorrowLand, btnRouteOnMap, btnCallOwner;

    AgriLand agriLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_agri_land_details_new);

        agriLand = (AgriLand) getIntent().getSerializableExtra("EXTRA_SELECTED_LAND");

        bindComponents();
        disableEditTexts();
        displayDetails();
        getSupportActionBar().setTitle("Land Details");

        addListeners();
    }

    private void addListeners(){

        btnRouteOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + agriLand.getAddress().getLatitude() + "," + agriLand.getAddress().getLongitude() + " (" + agriLand.getAddress().getName() + ")";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);

            }
        });

        btnCallOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+agriLand.getOwner().getContact()));
                startActivity(intent);
            }
        });

        btnBorrowLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void displayDetails(){

        //For Display Field Details
        etFieldType.setText(agriLand.getField().getType());
        etFieldArea.setText(String.valueOf(agriLand.getField().getArea()));

        String fieldUnit = agriLand.getField().getUnit();

        if (fieldUnit.equalsIgnoreCase("Acre")) {
            rbAcre.setChecked(true);
            rbAcre.setTextColor(Color.BLACK);
        } else if (fieldUnit.equalsIgnoreCase("Hectare")) {
            rbHectare.setChecked(true);
            rbAcre.setTextColor(Color.BLACK);
        }

        for (int i = 0; i < rgFieldAreaUnit.getChildCount(); i++) {
            rgFieldAreaUnit.getChildAt(i).setEnabled(false);
        }

        etFieldDescription.setText(agriLand.getDescription());

        etFieldLocation.setText(agriLand.getAddress().getName());


        //For displaying Lease details

        etStartDate.setText(agriLand.getLease().getStartDate());
        etEndDate.setText(agriLand.getLease().getEndDate());
        etLeaseDuration.setText(agriLand.getLease().getDuration());

        //For displaying Rent Details


        String rentType = agriLand.getRent().getType();

        etRentType.setText(agriLand.getRent().getType());

        switch (rentType) {
            case "Rent":
                etLandRent.setText("Rs "+agriLand.getRent().getAmount());
                etLandSharePercent.setText("No Share Percent");
                etLandSharePercent.setTextColor(Color.GRAY);
                break;
            case "Share of Income":
                etLandRent.setText("No Rent");
                etLandRent.setTextColor(Color.GRAY);
                etLandSharePercent.setText(agriLand.getRent().getShare_percent()+"%");
                break;
            case "Spend Equal Get Equal":
                etLandRent.setText("No Rent");
                etLandRent.setTextColor(Color.GRAY);
                etLandSharePercent.setText("No Share Percent");
                etLandSharePercent.setTextColor(Color.GRAY);
                break;
        }

        //For Displaying Owner Details

        etLandOwnerName.setText(agriLand.getOwner().getName());
        etLandOwnerContact.setText(agriLand.getOwner().getContact());
    }

    private void disableEditTexts(){

        EditText[] editTextArray = {
                etFieldType, etFieldArea, etFieldDescription, etFieldLocation, etLeaseDuration,
                etRentType, etLandRent, etLandSharePercent, etLandOwnerName, etLandOwnerContact,
                etStartDate, etEndDate
        };

        for (EditText editText : editTextArray) {
            editText.setEnabled(false);
            editText.setTextColor(Color.BLACK);
        }

    }


    public void bindComponents(){
        this.btnBorrowLand = findViewById(R.id.btn_borrow_land_agri_land_details);
        this.btnRouteOnMap = findViewById(R.id.btn_route_on_map_agri_land_details);
        this.btnCallOwner = findViewById(R.id.btn_call_owner_agri_land_details);

        this.etStartDate = findViewById(R.id.et_start_date_picker);
        this.etEndDate = findViewById(R.id.et_end_date_picker);

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

        this.rgFieldAreaUnit = findViewById(R.id.radioGroup_field_area_unit);
        this.etRentType = findViewById(R.id.et_land_rent_type);

        this.rbHectare = findViewById(R.id.rb_hectare_field_unit);
        this.rbAcre = findViewById(R.id.rb_acre_field_unit);
    }
}