package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.Transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowAvailableAgriEquipmentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    AgriEquipmentAdapter agriEquipmentAdapter;
    TextView textViewHeader;
    ListView listViewAvailableAgriEquipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_agri_equipments);


        List<AgriculturalEquipment> availableEquipments = (List<AgriculturalEquipment>) getIntent().getSerializableExtra("availableEquipment");
        String userId = getIntent().getStringExtra("userId");

        Log.d("", "onCreate: "+userId);

        if(userId != null){


            for(int i=0;i<availableEquipments.size();i++){
                List<AgriculturalEquipment> newList = new ArrayList<AgriculturalEquipment>();
                if(availableEquipments.get(i).getEquipmentProviderId().equals(userId)){
                    newList.add(availableEquipments.get(i));

                    listViewAvailableAgriEquipments = findViewById(R.id.list_available_agri_equipments);

                    agriEquipmentAdapter = new AgriEquipmentAdapter(this, (ArrayList<AgriculturalEquipment>) newList);
                    listViewAvailableAgriEquipments.setAdapter(agriEquipmentAdapter);
                    listViewAvailableAgriEquipments.setOnItemClickListener(this);


                }
            }


        }
        else {

            listViewAvailableAgriEquipments = findViewById(R.id.list_available_agri_equipments);

            agriEquipmentAdapter = new AgriEquipmentAdapter(this, (ArrayList<AgriculturalEquipment>) availableEquipments);
            listViewAvailableAgriEquipments.setAdapter(agriEquipmentAdapter);
            listViewAvailableAgriEquipments.setOnItemClickListener(this);
            getSupportActionBar().setTitle("All Equipments");
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AgriculturalEquipment selectedEquipment = (AgriculturalEquipment) agriEquipmentAdapter.getItem(position);
        Intent showEquipmentDetailsIntent = new Intent(getApplicationContext(),ShowAgriEquipmentActivity.class);
        showEquipmentDetailsIntent.putExtra("selectedEquipment", (Serializable) selectedEquipment);
        startActivity(showEquipmentDetailsIntent);

    }
}