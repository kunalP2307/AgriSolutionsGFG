package com.example.solutiontofarming.getallapicalls;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solutiontofarming.FetchNews;
import com.example.solutiontofarming.data.Extras;

import org.json.JSONArray;

public class FetchAllLands {
    private static final String TAG = com.example.solutiontofarming.FetchNews.class.getSimpleName();
    private RequestQueue requestQueue;
    private static final String API_URL = "http://"+ Extras.VM_IP +":7000/find/agri_lands";

    public FetchAllLands(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void fetchData(final FetchNews.ApiResponseListener listener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    public interface ApiResponseListener {
        void onSuccess(JSONArray response);
        void onError(VolleyError error);
    }
}


