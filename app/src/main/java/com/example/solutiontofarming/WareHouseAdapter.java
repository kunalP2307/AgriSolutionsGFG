package com.example.solutiontofarming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.solutiontofarming.data.Warehouse;

import java.util.ArrayList;

public class WareHouseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Warehouse> warehouseArrayList;

    public WareHouseAdapter(Context context,ArrayList<Warehouse> warehouseArrayList){
        this.context = context;
        this.warehouseArrayList = warehouseArrayList;
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
        TextView textViewArea = convertView.findViewById(R.id.text_ware_area_row);
        TextView textViewRent = convertView.findViewById(R.id.text_field_rent_ware_row);

        textViewLocation.setText(warehouse.getAddress().getAddress());
        textViewAvail.setText(warehouse.getFreeSpace());
        textViewArea.setText(warehouse.getArea());
        textViewRent.setText("\u20A8"+warehouse.getRent()+" per term");
        return convertView;
    }
}
