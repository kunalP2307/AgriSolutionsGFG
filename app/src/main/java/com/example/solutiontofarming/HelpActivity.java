package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.solutiontofarming.data.HelpItem;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    private ArrayList<HelpItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Help");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new HelpAdapter(items,this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //i am prepare strings for this project
        items.add(new HelpItem("Unable to access service","Check if you have completed the pre-required verification. If not, then complete the process and then try to access Services"));
        items.add(new HelpItem("Where the Transport Service is Available ?","Our Service is Currently Available in Maharashtra Only"));
        items.add(new HelpItem("What about Security While Taking Service ?","Consider the service at your risk, it is recommended that firstly you should contact the contact the service provider first."));
        items.add(new HelpItem("Unable to Contact Driver","Try for some time. If still the driver is out of your reach, call on our Helpline No: xxxxxxx342"));
    }
}