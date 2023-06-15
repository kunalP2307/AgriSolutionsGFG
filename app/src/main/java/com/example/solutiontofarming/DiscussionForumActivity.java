package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
import com.example.solutiontofarming.data.ChatMessage;
import com.example.solutiontofarming.data.Extras;
import com.example.solutiontofarming.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiscussionForumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatModel> chatList;
    private ImageButton btnSendMessage;
    private EditText etUserMessage;
    private Button btnFailedLoad;

    private volatile String userEmail = null;
    private volatile String userName = null;

    private static final String API_URL = "http://"+ Extras.VM_IP +":7000/find-latest/agri_news/10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);

        bindComponents();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(chatAdapter);

//        addDummyData();
        addListeners();


        getNameFromFireBase();

    }

    private void getNameFromFireBase(){
        FirebaseDatabase.getInstance().getReference("(Q2-2021)Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user  = snapshot.getValue(User.class);

                userName = user.getFullName();
                userEmail = user.getEmail();
                fetchChats();

                Log.d("Fetch_Chats", "onDataChange: "+user.getFullName()+user.getEmail()+user.getPhoneNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addDummyData(){

        String userImage = "https://img.etimg.com/thumb/msid-100677516,width-160,height-120,imgsize-558312/total-wage-for-tea-estate-workers-in-west-bengal-to-increase-soon-icra.jpg";

        chatList.add(new ChatModel("John", "Hello", userImage));
        chatList.add(new ChatModel("Amy", "Hi there!", userImage));
        chatList.add(new ChatModel("John", "How are you?", userImage));
        chatList.add(new ChatModel("Amy", "I'm doing great. Thanks!", userImage));
        chatList.add(new ChatModel("John", "That's good to hear!", userImage));

        chatAdapter.notifyDataSetChanged();

    }

    private void addListeners(){

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etUserMessage.getText().toString().trim();

                if(!message.isEmpty()){
                    saveMessageToDB(message);
                }

            }
        });
    }

    private void bindComponents(){

        recyclerView = findViewById(R.id.recyclerview_chat_room);
        btnSendMessage = findViewById(R.id.btn_send_message_chat_room);
        etUserMessage = findViewById(R.id.et_user_message_chat_room);
    }

    private void saveMessageToDB(String message){

        etUserMessage.setText("");

        long currentTime = System.currentTimeMillis();
        String formattedDateTime = String.valueOf(currentTime);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Get the current timestamp in IST
            ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
            ZonedDateTime istDateTime = ZonedDateTime.now(istZoneId);

            // Format the timestamp as a string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedDateTime = istDateTime.format(formatter);
        }

        String chatImage = "NoImage";
        String userImage = "NoImage";

        ChatMessage chatMessage = new ChatMessage(userEmail, userName, message, formattedDateTime, chatImage, userImage);

        Gson gson = new Gson();
        String json = gson.toJson(chatMessage);
        Log.d("Fetch_Chats", "saveMessageToDB: "+json);

        JSONObject jsonObjectChatMessage = null;

        try {
            jsonObjectChatMessage = new JSONObject(json);

            saveMessageToDBFinal(jsonObjectChatMessage);

        } catch (Throwable t) {
            Log.e("TAG", "Could not parse malformed JSON: \"" + json + "\"");
        }

//        chatList.add(new ChatModel(userName, message, userImage));

        fetchChats();
//        chatList.add(new ChatModel(userName, message, userImage));
//        chatAdapter.notifyDataSetChanged();
//        int lastPosition = chatList.size() - 1;
//        recyclerView.scrollToPosition(lastPosition);

    }

    private void saveMessageToDBFinal(JSONObject jsonObjectChatMessage){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "http://"+ Extras.VM_IP+":7000/insert-one/group_chats";

        final String requestBody = jsonObjectChatMessage.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
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

    private void fetchChats(){


        FetchChats fetchChats = new FetchChats(this);

        fetchChats.fetchData(new FetchNews.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                // Handle the successful response
                try {
                    chatList.clear();

                    for(int i = 0; i < response.length(); i++)
                    {
                        JSONObject obj = (JSONObject) response.get(i);

                        String userName = obj.getString("name");
                        String message = obj.getString("message");
                        String userImage = "NoImage";

//                        Log.d("Fetch_Chats", "onSuccess: "+userName);
//                        Log.d("Fetch_Chats", "onSuccess: "+message);
//                        Log.d("Fetch_Chats", "onSuccess: "+userImage);

                        chatList.add(new ChatModel(userName, message, userImage));
                    }
                    chatAdapter.notifyDataSetChanged();
                    int lastPosition = chatList.size() - 1;
                    recyclerView.scrollToPosition(lastPosition);
                }
                catch (JSONException e) {
                Log.d("Fetch_Chats", "Error: "+e.getMessage());
            }
            }

            @Override
            public void onError(VolleyError error) {
                // Handle the error response
                Log.e("Fetch_Chats", "Error: " + error.toString());
            }
        });

    }
}
