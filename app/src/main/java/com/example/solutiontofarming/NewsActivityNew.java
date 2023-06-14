package com.example.solutiontofarming;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

        fetchOriginalData();

        //verticalViewPager.setAdapter((new ViewPagerAdapter(NewsActivityNew.this, sliderItems, titles, desc, newslinks, heads, verticalViewPager)));

    }

    private void fetchOriginalData(){

        DemoFetchNews demoFetchNews = new DemoFetchNews(this);

        demoFetchNews.fetchData(new DemoFetchNews.ApiResponseListener() {
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

    private void addDummyData(){

        titles.add("Ram Temple sanctum sanctorum likely to be built by end of the year");
        desc.add("New Delhi/Lucknow The construction of the sanctum sanctorum of the Ram Temple in Ayodhya is expected to be completed by the end of the year, temple authorities said on Monday, adding that the inauguration of the grand structure may take place in January next year on a date that is yet to be finalised.");
        images.add("https://www.hindustantimes.com/ht-img/img/2023/06/12/550x309/Construction-work-of-Ram-temple-underway--in-Ayodh_1686596023170.jpg");
        newslinks.add("https://www.hindustantimes.com/india-news/ram-temple-in-ayodhya-to-be-inaugurated-in-january-2024-invitation-sent-to-pm-modi-new-delhilucknow-101686596031624.html");
//        heads.add("According to a functionary aware of the details, the construction work on the ground floor is nearly complete.");

        titles.add("Tokyo hero Simranjeet savours his India comeback");
        desc.add("Simranjeet Singh has this uncanny knack of delivering on the big stage. When others fail under pressure, the 26-year-old hockey player makes it count when it matters the most.");
        images.add("https://www.hindustantimes.com/ht-img/img/2023/06/12/550x309/simranjeet_hockey_india_1686595910781_1686595920090.jpg");
        newslinks.add("https://www.hindustantimes.com/sports/hockey/tokyo-hero-simranjeet-savours-his-india-comeback-101686593281006.html");
//        heads.add("The striker, who scored the winning goal in the hockey team’s bronze-winning match.");

        titles.add("CoWIN ‘data leak’: How a bot reignited privacy fears");
        desc.add("Reports and screenshots by journalists and opposition politicians on Monday purported to show unauthorised access into a government database, possibly the Covid-19 vaccine booking service CoWIN, with sensitive personal information tied to any mobile phone number becoming available.");
        images.add("https://www.hindustantimes.com/ht-img/img/2023/06/12/550x309/At-risk-may-be-personally-identifiable-information_1686596443261.jpg");
        newslinks.add("https://www.hindustantimes.com/india-news/reports-and-screenshots-reveal-potential-breach-of-india-s-cowin-vaccine-booking-system-with-personal-data-at-risk-101686596451165.html");
//        heads.add("The screenshots -- the unredacted versions of some were seen by HT – also carried details that purported to show where these people would have had their latest Covid-19 vaccine doses.");

        titles.add("22-year-old man shot dead as fresh violence hits Manipur: Police");
        desc.add("A 22-year-old man was shot dead in Manipur’s Churachandpur district on Monday as the area witnessed fresh bout of ethnic violence that has claimed over 100 lives in the state since May 3, officials familiar with the matter said.");
        images.add("https://www.hindustantimes.com/ht-img/img/2023/06/12/550x309/The-incident-took-place-around-2-35-pm-when-the-vi_1686596443263.jpg");
        newslinks.add("https://www.hindustantimes.com/india-news/fresh-bout-of-ethnic-violence-claims-life-of-22-year-old-in-manipur-s-churachandpur-district-death-toll-surpasses-100-101686596451076.html");
//        heads.add("Police have identified the victim as Muansang, a resident of Chandrapur’s Songtal village who belongs to the Kuki tribe.");

        titles.add("20 years of Chalte Chalte: I said yes to the film because it had Shah Rukh Khan, says Meghna Malik");
        desc.add("Meghna Malik might have had a cameo in Shah Rukh Khan and Rani Mukerji starrer Chalte Chalte, but the film will always hold a special place in her heart because it was the beginning of her acting career when she got the role.");
        images.add("https://www.hindustantimes.com/ht-img/img/2023/06/12/550x309/Meghna-Malik-on-20-years-of-Chalte-Chalte_1686596263219.jpg");
        newslinks.add("https://www.hindustantimes.com/entertainment/bollywood/meghna-malik-shares-memories-of-working-with-shah-rukh-khan-in-chalte-chalte-on-film-s-20th-anniversary-101686596266869.html");
//        heads.add("Actor Meghna Malik shares anecdotes and special memories from the shoot of Chalte Chalte in 2003, as it completed 20 years of its release.");



        for(int i = 0; i < images.size(); i++)
        {
            sliderItems.add(new SliderItems(images.get(i)));
        }
    }
}