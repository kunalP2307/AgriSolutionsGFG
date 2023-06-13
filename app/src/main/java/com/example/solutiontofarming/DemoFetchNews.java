package com.example.solutiontofarming;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class DemoFetchNews {
    private static final String TAG = DemoFetchNews.class.getSimpleName();
    private RequestQueue requestQueue;
    private static final String API_URL = "http://34.135.102.197:7000/find-latest/agri_news/10";

    public DemoFetchNews(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void fetchData(final ApiResponseListener listener) {
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


