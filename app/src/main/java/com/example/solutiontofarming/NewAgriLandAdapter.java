package com.example.solutiontofarming;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.solutiontofarming.data.AgriLand;

import java.util.List;

public class NewAgriLandAdapter extends ArrayAdapter<AgriLand> {
    private Context context;
    private List<AgriLand> agriLandList;

    public NewAgriLandAdapter(Context context, List<AgriLand> agriLandList) {
        super(context, R.layout.land_row_container, agriLandList);
        this.context = context;
        this.agriLandList = agriLandList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.land_row_container, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvLocation = convertView.findViewById(R.id.text_field_location);
            viewHolder.tvArea = convertView.findViewById(R.id.text_field_area);
            viewHolder.tvLandType = convertView.findViewById(R.id.text_agri_land_type_row);
            viewHolder.tvAmount = convertView.findViewById(R.id.text_field_rent);
            viewHolder.tvRentType = convertView.findViewById(R.id.text_field_rent_type);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            TextView tvLocation = (TextView) convertView.findViewById(R.id.text_field_location);
            TextView tvArea = (TextView) convertView.findViewById(R.id.text_field_area);
            TextView tvLandType = (TextView) convertView.findViewById(R.id.text_agri_land_type_row);
            TextView tvAmount = (TextView) convertView.findViewById(R.id.text_field_rent);
            TextView tvRentType = (TextView) convertView.findViewById(R.id.text_field_rent_type);

            AgriLand currAgriLand = (AgriLand) getItem(position);

            tvLocation.setText(currAgriLand.getAddress().getName());
            tvArea.setText(String.valueOf(currAgriLand.getField().getArea())+" "+currAgriLand.getField().getUnit());
            tvLandType.setText(currAgriLand.getField().getType());

            Log.d("Fetch_Lands", "getView: "+currAgriLand.getAddress().getName());
            Log.d("Fetch_Lands", "getView: "+currAgriLand.getField().getArea());
            Log.d("Fetch_Lands", "getView: "+currAgriLand.getField().getType());
            Log.d("Fetch_Lands", "getView: "+currAgriLand.getRent().getType());

            String rentType = currAgriLand.getRent().getType();

            if(rentType.equals("Rent"))
            {
                tvRentType.setText(rentType);
                String text = String.valueOf(currAgriLand.getRent().getAmount());
                tvAmount.setText("Rs."+text);
            }
            else if(rentType.equals("Share of Income"))
            {
                tvRentType.setText(rentType);

                String text = String.valueOf(currAgriLand.getRent().getShare_percent());
                tvAmount.setText(text+"%");
            }
            else if(rentType.equals("Spend Equal Get Equal"))
            {
                tvRentType.setText(rentType);
                tvAmount.setText("");
            }
        return convertView;
    }

    private static class ViewHolder {
        TextView tvLocation;
        TextView tvArea;
        TextView tvLandType;
        TextView tvAmount;
        TextView tvRentType;
    }
}
