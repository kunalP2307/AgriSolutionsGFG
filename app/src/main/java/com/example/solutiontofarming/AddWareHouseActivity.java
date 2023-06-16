package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.example.solutiontofarming.data.AgriEquipment;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.Warehouse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddWareHouseActivity extends AppCompatActivity {

    Button buttonContinue;
    ProgressDialog dialog;
    ConstraintLayout mainLayout;
    Warehouse warehouse;
    String URL = "http://"+ Extras.VM_IP+":7000/insert-one/warehouses";
    JSONObject jsonObjectWarehouse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ware_house);

        bindComponents();
        setProgressDialog();
        getWarehouse();
        addAgriEquipment();
    }

    private void bindComponents(){
        mainLayout = findViewById(R.id.constrain_warehouse_added);
        buttonContinue = findViewById(R.id.btn_continue_warehouse_added);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private void setProgressDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Hold On Adding Your Warehouse..!");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void getWarehouse(){
        warehouse = (Warehouse) getIntent().getSerializableExtra("WAREHOUSE_OBJ");
        Log.d("TAG", "getTransport: "+warehouse.toString());

        Gson gson = new Gson();
        String json = gson.toJson(warehouse);
        try {
            jsonObjectWarehouse = new JSONObject(json);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
//        JsonObject jsonObject = gson.fromJson(json, (Type) Fare.class);
        Log.d("TAG", "onCreate: JsonObject"+jsonObjectWarehouse);
    }

    private void addAgriEquipment(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final String requestBody = jsonObjectWarehouse.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                dialog.hide();
                mainLayout.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Something Went Wrong", error.toString());
                dialog.hide();
                startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
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

}