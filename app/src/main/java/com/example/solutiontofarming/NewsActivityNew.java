package com.example.solutiontofarming;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class NewsActivityNew extends AppCompatActivity {

    List<SliderItems> sliderItems = new ArrayList<>();

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> newslinks = new ArrayList<>();
//    ArrayList<String> heads = new ArrayList<>();
    ArrayList<String> news_date = new ArrayList<>();

    VerticalViewPager verticalViewPager;

    ShimmerFrameLayout shimmer_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_new);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);
        shimmer_layout = findViewById(R.id.shimmer_activity_news_new);

        verticalViewPager.setVisibility(View.INVISIBLE);
        shimmer_layout.startShimmerAnimation();
//        addDummyData();

        fetchOriginalData();
       //verticalViewPager.setAdapter((new ViewPagerAdapter(NewsActivityNew.this, sliderItems, titles, desc, newslinks, heads, verticalViewPager)));
   }

    private void fetchOriginalData(){

        FetchNews demoFetchNews = new FetchNews(this);

        demoFetchNews.fetchData(new FetchNews.ApiResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                // Handle the successful response
                try {

                    for(int i = 0; i < response.length(); i++)
                    {
                        JSONObject obj = (JSONObject) response.get(i);

                        String full_desc = obj.getString("news_description");
                        String half_desc = cutDesc(full_desc);


                        String imageUrl = obj.getString("story_img_url");
                        String updatedImageUrl = imageUrl.replace("width-160", "width-490")
                                .replace("height-120", "height-310");

                        titles.add(obj.getString("heading"));
                        desc.add(half_desc);
                        images.add(updatedImageUrl);
                        newslinks.add(obj.getString("story_link"));
//                        heads.add(obj.getString("heading"));
                        news_date.add(obj.getString("updated_date_time"));

                    }

                    for(int j = 0; j < images.size(); j++)
                    {
                        sliderItems.add(new SliderItems(images.get(j)));
                    }

                    shimmer_layout.stopShimmerAnimation();
                    verticalViewPager.setVisibility(View.VISIBLE);
                    shimmer_layout.setVisibility(View.INVISIBLE);

                    verticalViewPager.setAdapter((new ViewPagerAdapter(NewsActivityNew.this, sliderItems, titles, desc, newslinks, news_date, verticalViewPager)));

                } catch (JSONException e) {
                    Log.d("News_fetch_Demo", "Error: "+e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                // Handle the error response
                Log.e("News_fetch_Demo", "Error: " + error.toString());
            }
        });

    }

    private String cutDesc(String full_desc){

        StringBuilder half_desc = new StringBuilder();

        int numberOfWords = 90;

        String[] desc_split = full_desc.split("\\s+");

        int words_to_take = Math.min(numberOfWords, desc_split.length);

        for(int i = 0; i < words_to_take; i++)
        {
            half_desc.append(desc_split[i]).append(" ");
        }

        String res_half_desc = half_desc.toString().trim();
        res_half_desc += "...";

        return res_half_desc;
    }

}