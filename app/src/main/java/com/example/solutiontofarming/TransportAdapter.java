package com.example.solutiontofarming;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.TransportRide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TransportAdapter extends BaseAdapter {
    Context context;
    ArrayList<TransportRide> transportList;
    public TransportAdapter(Context context,ArrayList<TransportRide> transportList){
        this.context = context;
        this.transportList = transportList;
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
        TextView textViewSource = (TextView) convertView.findViewById(R.id.text_field_area);
        TextView textViewDestination = (TextView) convertView.findViewById(R.id.scheduled_ride_destination);
        TextView textViewLoad = (TextView) convertView.findViewById(R.id.text_available_limit_row);
        TextView textViewDateTime = (TextView) convertView.findViewById(R.id.text_field_location);
        TextView textViewTruckType = convertView.findViewById(R.id.text_truck_type_row);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.text_field_rent);


        /*if(position == 0 || position > 2)  {
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_transport);
            imageView.setVisibility(View.VISIBLE);
            TextView textView = convertView.findViewById(R.id.text_profile_status_in_transport);
            textView.setVisibility(View.VISIBLE);
        }*/

        textViewDateTime.setText(currTransport.getWhen().getDate());
        textViewSource.setText(currTransport.getSource().getName());
        textViewDestination.setText(currTransport.getDestination().getName());
        textViewLoad.setText(currTransport.getVehicle().getAvailableLimit() + " "+currTransport.getVehicle().getWeightUnit());
        textViewTruckType.setText(currTransport.getVehicle().getType());


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
