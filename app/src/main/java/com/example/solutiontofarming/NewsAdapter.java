package com.example.solutiontofarming;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.News;
import com.example.solutiontofarming.data.Transport;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    android.content.Context context;
    ArrayList<News> newsArrayList;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList){
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.news_row, parent, false);
        }

        News currNews = (News) getItem(position);

        ImageView imageViewNews = convertView.findViewById(R.id.img_news_image);
        TextView textViewNewsHeader = convertView.findViewById(R.id.text_news_headline);
        TextView textViewPostedAgo = convertView.findViewById(R.id.text_posted_ago);
        ImageView newsImage = convertView.findViewById(R.id.img_news_image);

        newsImage.setImageResource(currNews.getImageId());
        textViewNewsHeader.setText(currNews.getHeadLine());
        textViewPostedAgo.setText(currNews.getPostedAgo());

        return convertView;
    }
}
