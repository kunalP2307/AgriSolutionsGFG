package com.example.solutiontofarming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

    int new_position;
    float x1, x2;



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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = myLayoutInflater.inflate(R.layout.item_container, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_news_item_container);
        ImageView imageView2 = itemView.findViewById(R.id.iv_news2_item_container);
        TextView title = itemView.findViewById(R.id.tv_news_headline_item_container);
        TextView tv_desc = itemView.findViewById(R.id.tv_news_desc_item_conatiner);
//        TextView head = itemView.findViewById(R.id.tv_head_item_container);
        TextView newsdate = itemView.findViewById(R.id.tv_news_date_item_container);

        TextView tap_here = itemView.findViewById(R.id.tv_tap_here_item_container);

        // Set data to views

        title.setText(titles.get(position));
        tv_desc.setText(desc.get(position));
//        head.setText(heads.get(position));
        newsdate.setText(news_date.get(position));

        tap_here.setPaintFlags(tap_here.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tap_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newslinks.get(position)));
                context.startActivity(intent);
            }
        });


        Glide.with(context)
                    .load(sliderItems.get(position).getImage())
                    .into(imageView);

        Glide.with(context)
                    .load(sliderItems.get(position).getImage())
                    .override(12, 12)
                    .into(imageView2);

        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Save Position value in new position variable on page change
                new_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        float deltaX = x1-x2;

                        if(deltaX > 300)
                        {
                            if(position == 1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newslinks.get(0)));
                                context.startActivity(intent);

                            }
                            else{
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newslinks.get(new_position)));
                                context.startActivity(intent);

                            }
                        }
                        break;
                }
                return false;
            }
        });

        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
