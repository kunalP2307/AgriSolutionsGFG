package com.example.solutiontofarming;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.HowFar;
import com.example.solutiontofarming.data.TransportRide;

import java.util.ArrayList;

public class TransportAdapter extends BaseAdapter {
    Context context;
    ArrayList<TransportRide> transportList;
    ArrayList<HowFar> howFarList;
    public TransportAdapter(Context context,ArrayList<TransportRide> transportList, ArrayList<HowFar> howFarList){
        this.context = context;
        this.transportList = transportList;
        this.howFarList = howFarList;
    }


    @Override
    public int getCount() {
        return transportList.size();
    }

    @Override
    public Object getItem(int position) {
        return transportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.ride_row,parent,false);
        }

        TransportRide currTransport = (TransportRide)getItem(position);
        TextView textViewSource = convertView.findViewById(R.id.text_field_area);
        TextView textViewDestination = convertView.findViewById(R.id.scheduled_ride_destination);
        TextView textViewLoad =  convertView.findViewById(R.id.text_agri_land_type_row);
        TextView textViewDateTime =  convertView.findViewById(R.id.text_field_location);
        TextView textViewTruckType = convertView.findViewById(R.id.text_truck_type_row);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textViewPrice =  convertView.findViewById(R.id.text_field_rent);

        TextView textViewHowFarSource = convertView.findViewById(R.id.text_how_far_source);
        TextView textViewHowFarDest = convertView.findViewById(R.id.text_how_far_destination);
        ImageView temp1 = convertView.findViewById(R.id.imageView14);
        ImageView temp2 = convertView.findViewById(R.id.imageView15);


        /*if(position == 0 || position > 2)  {
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_transport);
            imageView.setVisibility(View.VISIBLE);
            TextView textView = convertView.findViewById(R.id.text_profile_status_in_transport);
            textView.setVisibility(View.VISIBLE);
        }*/

        textViewDateTime.setText(currTransport.getWhen().getDate());
        textViewSource.setText(currTransport.getSource().getAddress());
        textViewDestination.setText(currTransport.getDestination().getAddress());
        textViewLoad.setText(currTransport.getVehicle().getAvailableLimit() + " "+currTransport.getVehicle().getWeightUnit());
        textViewTruckType.setText(currTransport.getVehicle().getType());

        if(howFarList.size() != 0){
            HowFar howFar = howFarList.get(position);
            textViewHowFarSource.setVisibility(View.VISIBLE);
            textViewHowFarDest.setVisibility(View.VISIBLE);
            textViewHowFarSource.setText((Math.round(howFar.getFarFromSource()*10.0)/10.0)+ " KM");
            textViewHowFarDest.setText((Math.round(howFar.getFarFromDestination()*10.0)/10.0)+ " KM");
            temp1.setVisibility(View.VISIBLE);
            temp2.setVisibility(View.VISIBLE);

        }

//        textViewPrice.setText(currTransport.getFare().getPricePerKm());

//        String rideProviderId = currTransport.getRideProviderId();
//        int length = rideProviderId.length();
//
//        char ch = rideProviderId.charAt(length-1);
//
//        if(ch == 'F'){
//            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_transport);
//            TextView textView = convertView.findViewById(R.id.text_profile_status_in_transport);
//            imageView.setVisibility(View.VISIBLE);
//            textView.setVisibility(View.VISIBLE);
//        }

        return convertView;
    }
}
