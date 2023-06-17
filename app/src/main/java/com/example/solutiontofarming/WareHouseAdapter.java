package com.example.solutiontofarming;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.solutiontofarming.data.Warehouse;

import java.util.ArrayList;

public class WareHouseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Warehouse> warehouseArrayList;
    ArrayList<String> listDistances;

    public WareHouseAdapter(Context context,ArrayList<Warehouse> warehouseArrayList, ArrayList<String> listDistances){
        this.context = context;
        this.warehouseArrayList = warehouseArrayList;
        this.listDistances = listDistances;
    }

    @Override
    public int getCount() {
        return warehouseArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return warehouseArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.warehouse_row,parent,false);
        }

        Warehouse warehouse = (Warehouse) getItem(position);
        TextView textViewLocation = convertView.findViewById(R.id.text_warehouse_locaation_row);
        TextView textViewAvail = convertView.findViewById(R.id.text_field_aval_space_w_row);
        TextView textViewAreaDistance = convertView.findViewById(R.id.text_w_row_dis_area);
        TextView textViewRent = convertView.findViewById(R.id.text_field_rent_ware_row);
        ImageView imageView = convertView.findViewById(R.id.imageView16);
        textViewAreaDistance.setText(warehouse.getArea());

        if(!"NA".equals(listDistances.get(position))) {
            imageView.setVisibility(View.VISIBLE);
            textViewAreaDistance.setText(listDistances.get(position));
            Log.d("TAG", "getView: "+listDistances.get(position));
        }

        textViewLocation.setText(warehouse.getAddress().getAddress());
        textViewAvail.setText(warehouse.getFreeSpace());
        textViewRent.setText("\u20A8 "+warehouse.getRent()+" per term");
        return convertView;
    }
}
