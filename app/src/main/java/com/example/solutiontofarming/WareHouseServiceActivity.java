package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WareHouseServiceActivity extends AppCompatActivity {

    CardView cardViewRentWarehouse,cardViewBorrowWareHouse,cardViewMyWareHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_service);

        bindComponents();
    }
    private void bindComponents(){
        cardViewRentWarehouse = findViewById(R.id.card_add_warehouse);
        cardViewRentWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddWareHouseDetailsActivity.class));
            }
        });
        cardViewBorrowWareHouse = findViewById(R.id.card_borrow_warehouse);
        cardViewBorrowWareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShowAvailableWarehouseActivity.class));
            }
        });
        cardViewMyWareHouse =findViewById(R.id.card_my_warehouses);


    }

}