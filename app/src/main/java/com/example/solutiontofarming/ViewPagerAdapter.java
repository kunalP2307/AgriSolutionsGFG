package com.example.solutiontofarming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<SliderItems> sliderItems;
    LayoutInflater myLayoutInflater;
    Context context;

    ArrayList<String> titles;
    ArrayList<String> desc;
    ArrayList<String> newslinks;
//    ArrayList<String> heads;
    ArrayList<String> news_date;

    VerticalViewPager verticalViewPager;

    public ViewPagerAdapter(Context context, List<SliderItems> sliderItems, ArrayList<String> titles, ArrayList<String> desc, ArrayList<String> newslinks, ArrayList<String> news_date, VerticalViewPager verticalViewPager) {
        this.context = context;
        this.sliderItems = sliderItems;

        this.titles = titles;
        this.desc = desc;
        this.newslinks = newslinks;
//        this.heads = heads;
        this.news_date = news_date;

        this.verticalViewPager = verticalViewPager;

        myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = myLayoutInflater.inflate(R.layout.item_container, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_news_item_container);
        ImageView imageView2 = itemView.findViewById(R.id.iv_news2_item_container);
        TextView title = itemView.findViewById(R.id.tv_news_headline_item_container);
        TextView tv_desc = itemView.findViewById(R.id.tv_news_desc_item_conatiner);
//        TextView head = itemView.findViewById(R.id.tv_head_item_container);
        TextView newsdate = itemView.findViewById(R.id.tv_news_date_item_container);

        // Set data to views

        title.setText(titles.get(position));
        tv_desc.setText(desc.get(position));
//        head.setText(heads.get(position));
        newsdate.setText(news_date.get(position));


        Glide.with(context)
                    .load(sliderItems.get(position).getImage())
                    .centerCrop()
                    .into(imageView);

        Glide.with(context)
                    .load(sliderItems.get(position).getImage())
                    .centerCrop()
                    .override(12, 12)
                    .into(imageView2);

        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
