package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WareHouseServiceActivity extends AppCompatActivity {

    CardView cvRentWarehouse, cvBorrowWarehouse, cvMyWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_service);

        bindComponents();
        addListeners();
    }

    private void bindComponents(){
        cvRentWarehouse = findViewById(R.id.card_rent_warehouse);
        cvBorrowWarehouse = findViewById(R.id.card_borrow_warehouse);
        cvMyWarehouse = findViewById(R.id.card_my_warehouse);
    }

    private void addListeners(){
        cvRentWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WareHouseServiceActivity.this, AddWareHouseActivity.class);
                startActivity(intent);
            }
        });

        cvBorrowWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WareHouseServiceActivity.this, AddWareHouseActivity.class);
                startActivity(intent);
            }
        });

        cvMyWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WareHouseServiceActivity.this, AddWareHouseActivity.class);
                startActivity(intent);
            }
        });
    }


}