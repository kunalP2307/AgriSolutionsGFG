package com.example.solutiontofarming;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalLand;
import com.example.solutiontofarming.data.Transport;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AgriculturalLandAdapter extends BaseAdapter {
    Context context;
    ArrayList<AgriculturalLand> agriculturalLandArrayList;

    public AgriculturalLandAdapter(Context context,ArrayList<AgriculturalLand> agriculturalLandArrayList){
        this.context = context;
        this.agriculturalLandArrayList = agriculturalLandArrayList;
    }


    @Override
    public int getCount() {
        return agriculturalLandArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return agriculturalLandArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.field_row,parent,false);
        }

        AgriculturalLand currAgriLand = (AgriculturalLand)getItem(position);
        TextView textViewLocation = (TextView)convertView.findViewById(R.id.text_field_location);
        TextView textViewArea = (TextView)convertView.findViewById(R.id.text_field_area);
        TextView textViewRent = (TextView)convertView.findViewById(R.id.text_field_rent);
        TextView textViewType = (TextView)convertView.findViewById(R.id.text_field_type);

        String landOwnerId = currAgriLand.getProviderId();
        int length = landOwnerId.length();

        char ch = landOwnerId.charAt(length-1);

        if(ch == 'F'){
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_land);
            TextView textView = convertView.findViewById(R.id.text_verified_profile_in_land);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        
        textViewArea.setText(currAgriLand.getLandArea());
        textViewLocation.setText(currAgriLand.getLandAddress());
        textViewRent.setText(currAgriLand.getRentPerYear());
        textViewType.setText(currAgriLand.getLandType());
        return convertView;
    }
}
