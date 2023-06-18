package com.example.solutiontofarming;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solutiontofarming.data.Crop;
import com.example.solutiontofarming.data.Land;

import java.util.List;


public class LandAdapter extends RecyclerView.Adapter<LandAdapter.ViewHolder> {

    private List<Land> dataList;
    private Context context;

    public LandAdapter(Context context, List<Land> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_land_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Retrieve data for this position
        Land landData = dataList.get(position);

        // Bind the data to the ViewHolder
        holder.bindData(landData);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_land_id;
        private TextView text_land_area_size;
        private TextView text_crop_recommend;

        public ViewHolder(View itemView) {
            super(itemView);
            text_land_id = itemView.findViewById(R.id.text_land_id);
            text_land_area_size = itemView.findViewById(R.id.text_land_area_size);
            text_crop_recommend = itemView.findViewById(R.id.text_crop_recommend);
        }

        public void bindData(Land landData) {
            text_land_id.setText(landData.getLandId());
            text_land_area_size.setText(landData.getLandArea());
            text_crop_recommend.setText(landData.getCropRecommend());
        }
    }

}



