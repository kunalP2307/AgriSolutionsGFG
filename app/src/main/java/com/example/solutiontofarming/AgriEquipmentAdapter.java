package com.example.solutiontofarming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriculturalEquipment;
import com.example.solutiontofarming.data.AgriculturalLand;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AgriEquipmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<AgriculturalEquipment> agriculturalEquipmentArrayList;

    public AgriEquipmentAdapter(Context context,ArrayList<AgriculturalEquipment> agriculturalEquipmentArrayList){
        this.context = context;
        this.agriculturalEquipmentArrayList = agriculturalEquipmentArrayList;
    }

    @Override
    public int getCount() {
        return agriculturalEquipmentArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return agriculturalEquipmentArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.agri_equip_row,parent,false);
        }

        AgriculturalEquipment currAgriEquipment = (AgriculturalEquipment) getItem(position);

        TextView textViewLocation = convertView.findViewById(R.id.text_row_loc_agri_euip);
        TextView textViewName = convertView.findViewById(R.id.text_row_agri_equip_name);
        TextView textViewRent = convertView.findViewById(R.id.text_row_agri_equip_rent);

        textViewLocation.setText(currAgriEquipment.getEquipmentLocation());
        textViewName.setText(currAgriEquipment.getEquipmentName());
        textViewRent.setText(currAgriEquipment.getRentPerHour());

        String equipmentProviderId = currAgriEquipment.getEquipmentProviderId();
        int length = equipmentProviderId.length();

        char ch = equipmentProviderId.charAt(length-1);

        if(ch == 'F'){
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_agri_equip);
            TextView textView = convertView.findViewById(R.id.text_profile_status_in_agri_equi);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

}
