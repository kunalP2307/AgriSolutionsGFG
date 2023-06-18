package com.example.solutiontofarming;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solutiontofarming.data.Crop;

import java.util.List;


public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    private List<Crop> dataList;
    private Context context;

    public CropAdapter(Context context, List<Crop> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_crop_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Retrieve data for this position
        Crop cropData = dataList.get(position);

        // Bind the data to the ViewHolder
        holder.bindData(cropData);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cropNameTextView;
        private TextView plantingSeasonTextView;
        private TextView marketValueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cropNameTextView = itemView.findViewById(R.id.text_crop_name);
            plantingSeasonTextView = itemView.findViewById(R.id.text_crop_planting_season);
            marketValueTextView = itemView.findViewById(R.id.text_crop_market_value);
        }

        public void bindData(Crop cropData) {
            cropNameTextView.setText(cropData.getCropName());
            plantingSeasonTextView.setText(cropData.getPlantingSeason());
            marketValueTextView.setText(cropData.getMarketValue());
        }
    }

}



