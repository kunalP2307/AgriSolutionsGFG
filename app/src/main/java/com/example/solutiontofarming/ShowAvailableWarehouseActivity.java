package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.Warehouse;
import com.example.solutiontofarming.getallapicalls.GetAllEquipments;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowAvailableWarehouseActivity extends AppCompatActivity {

    WareHouseAdapter wareHouseAdapter;
    ArrayList<Warehouse> warehouseArrayList;
    ArrayList<Warehouse> filteredList;
    CheckBox checkBoxLowToHigh;
    ListView listViewWarehouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_available_warehouse);
//        bindComponents();
    }


//    private void getAllWareHouses(){
//        GetAllEquipments getAllEquipments = new GetAllEquipments(this);
//
//        getAllEquipments.fetchAllEquipments(new GetAllEquipments.ApiResponseListener() {
//            @Override
//            public void onSuccess(JSONArray response) {
//                listViewAvailableAgriEquipments.setVisibility(View.VISIBLE);
//                shimmer_layout.stopShimmerAnimation();
//                shimmer_layout.setVisibility(View.INVISIBLE);
//                for (int i = 0; i < response.length(); i++) {
//                    // creating a new json object and
//                    // getting each object from our json array.
//                    try {
//                        JSONObject responseObj = response.getJSONObject(i);
//
//                        JsonObject jsonObject = new Gson().fromJson(responseObj.toString(), JsonObject.class);
//                        AgriEquipment agriEquipment = new Gson().fromJson(jsonObject, AgriEquipment.class);
//                        agriEquipmentArrayList.add(agriEquipment);
//                        filteredList.add(agriEquipment);
//                        agriEquipmentAdapter.notifyDataSetChanged();
//                        Log.d("TAG", "onResponse:jsonObject "+responseObj.toString());
//                        Log.d("TAG", "onResponse:mapped Java "+agriEquipment.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public void onError(VolleyError error) {
//                shimmer_layout.stopShimmerAnimation();
//                shimmer_layout.setVisibility(View.INVISIBLE);
//                Toast.makeText(ShowAvailableAgriEquipmentsActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//

}