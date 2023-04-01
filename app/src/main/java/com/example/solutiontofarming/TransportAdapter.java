package com.example.solutiontofarming;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;

public class TransportAdapter extends BaseAdapter {
    Context context;
    ArrayList<Transport> transportList;

    public TransportAdapter(Context context,ArrayList<Transport> transportList){
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

        Transport currTransport = (Transport)getItem(position);
        TextView textViewSource = (TextView) convertView.findViewById(R.id.text_field_area);
        TextView textViewDestination = (TextView) convertView.findViewById(R.id.scheduled_ride_destination);
        TextView textViewLoad = (TextView) convertView.findViewById(R.id.text_field_type);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.text_field_rent);
        TextView textViewDateTime = (TextView) convertView.findViewById(R.id.text_field_location);


        /*if(position == 0 || position > 2)  {
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_transport);
            imageView.setVisibility(View.VISIBLE);
            TextView textView = convertView.findViewById(R.id.text_profile_status_in_transport);
            textView.setVisibility(View.VISIBLE);
        }*/

        textViewDateTime.setText(currTransport.getRideDate());
        textViewSource.setText(currTransport.getSourceAddress());
        textViewDestination.setText(currTransport.getDestinationAddress());
        textViewLoad.setText(currTransport.getAvailableLoad());
        textViewPrice.setText(currTransport.getPricePerKm());

        String rideProviderId = currTransport.getRideProviderId();
        int length = rideProviderId.length();

        char ch = rideProviderId.charAt(length-1);

        if(ch == 'F'){
            ImageView imageView = convertView.findViewById(R.id.img_profile_status_in_transport);
            TextView textView = convertView.findViewById(R.id.text_profile_status_in_transport);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
