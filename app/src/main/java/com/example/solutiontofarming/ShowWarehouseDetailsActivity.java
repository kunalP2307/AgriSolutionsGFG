package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.solutiontofarming.data.Warehouse;

public class ShowWarehouseDetailsActivity extends AppCompatActivity {

    EditText editTextFreSpace,editTextArea,editTextDescription,editTextAvailFrom,editTextAvailTill,editTextLeasePeroid,editTextRent,editTextLocatio,editTextOwnerName;
    Button btnCallOwner;
    CheckBox checkBoxVentilation, checkBoxTemControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_warehouse_details);
        bindComponents();
        setWareHouseDetails();
    }


    private void bindComponents(){
        editTextFreSpace = findViewById(R.id.edit_text_free_space_show_warehouse);
        editTextArea = findViewById(R.id.edit_text_warehouse_dimensions_show_warehouse);
        editTextDescription = findViewById(R.id.edit_text_description_show_warehouse);
        checkBoxTemControl = findViewById(R.id.check_box_temperature_control_show_warehouse);
        checkBoxVentilation = findViewById(R.id.check_box_ventilation_show_warehouse);
        editTextAvailFrom = findViewById(R.id.edit_text_available_from_show_warehouse);
        editTextAvailTill = findViewById(R.id.edit_text_available_till_show_warehouse);
        editTextLeasePeroid = findViewById(R.id.edit_text_lease_period_show_warehouse);
        editTextRent = findViewById(R.id.edit_text_lent_per_period_show_warehouse);
        editTextLocatio = findViewById(R.id.edit_text_location_show_warehouse);
        editTextOwnerName = findViewById(R.id.edit_text_name_show_warehouse);
        btnCallOwner = findViewById(R.id.btn_call_owner_show_warehouse);
    }

    private void setWareHouseDetails(){
        Warehouse warehouse = (Warehouse)getIntent().getSerializableExtra("EXTRA_WARE_HOUSE_OBJ");

        editTextFreSpace.setText(warehouse.getFreeSpace());
        editTextArea.setText(warehouse.getArea());
        editTextDescription.setText(warehouse.getDescription());
        if(warehouse.getInfraFacilities().getTemperatureControl().equals("Yes"))
            checkBoxTemControl.setChecked(true);
        if(warehouse.getInfraFacilities().getVentilation().equals("Yes"))
            checkBoxVentilation.setChecked(true);
        editTextAvailFrom.setText(warehouse.getLease().getStartDate());
        editTextAvailTill.setText(warehouse.getLease().getEndDate());
        editTextLeasePeroid.setText(warehouse.getLease().getDuration());
        editTextRent.setText(warehouse.getRent());
        editTextLocatio.setText(warehouse.getAddress().getAddress());
        editTextOwnerName.setText(warehouse.getOwner().getName());
        btnCallOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + warehouse.getOwner().getContact());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });


    }
}