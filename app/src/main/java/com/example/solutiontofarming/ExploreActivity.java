package com.example.solutiontofarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.solutiontofarming.data.News;
import com.example.solutiontofarming.data.Transport;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listViewNews;
    NewsAdapter newsAdapter;
    ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_explore);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_services:
                        startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_explore:
                        return true;
                    case R.id.navigation_more:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        getSupportActionBar().setTitle("Bulletins");
        initNews();
        listViewNews = findViewById(R.id.list_news);
        newsAdapter = new NewsAdapter(this,newsList);
        listViewNews.setAdapter(newsAdapter);
        listViewNews.setOnItemClickListener(this);
    }

    public void initNews(){

        newsList = new ArrayList<News>();
        String[] newsHeader = {"PM Kisan 2021 New List: Know about your Payment Status, Rejected Applications & Other Details",
                                "Supreme Court Committee of Experts calls in for Public Comments on three Farm Laws",
                                "Krishi Jagran is now an Official Media Partner at BioAg, World Congress 2021",
                                "Kisan Credit Card Latest Update: Only 50% Beneficiaries Received KCC So Far; Center Begins Drive to Cover All",
                                "Odisha Government Announces Compensation Package for Dairy Farmers Affected By Lockdown",
                                "Karnataka plans to expand agricultural lending in the cooperative sector",
                                "Meghalaya Exports Lakadong Turmeric & Ginger Powder to UK & Netherlands\n"};

        int[] newsImageIds = {R.drawable.img_news_1,
                                R.drawable.img_news_2,
                                R.drawable.img_news_3,
                                R.drawable.img_news_4,
                                R.drawable.img_news_5,
                                R.drawable.img_news_6,
                                R.drawable.img_news_7};

        String[] news = {"More than 11 Crore farmers have joined PM Kisan Samman Nidhi Yojana so far. The latest instalment (April-July duration) of 2000 Rs. has been received by many farmers.\n" +
                "And if your previous instalment or any of your friend’s instalment is stuck somewhere, then you can easily check it on your mobile phone. \n" +
                "You can check the list of your entire village easily on your mobile phone, that who has received the instalment and who hasn’t. There can be many reasons due to which payment may be stuck, like any fuss in Aadhar name and Bank account name, failure of Aadhar authentication, or any other issue. You can easily check on your phone that what’s the issue. Visit the PM Kisan portal and check what’s gone wrong.\n" +
                "How to Check How Many Instalments you have Received?\n" +
                "Open the official website http://pmkisan.gov.in/\n" +
                "Click on the Farmers Corner option on the right side\n" +
                "After that, click on the Beneficiary Status option\n" +
                "You will be directed to a new page\n" +
                "Select one of the options from Aadhar number, Bank Account number, or mobile number\n" +
                "And you will get all the transaction information",
                "The  Committee of Experts appointed by the Hon’ble Supreme Court of India has called in comments from the public on the 3 Farm Laws namely 1.\n" +
                        "The Farmers’ Produce Trade and Commerce (Promotion and Facilitation) Act, 2020, 2. The Farmers (Empowerment and Protection) Agreement on Price Assurance and Farm Services Act, 2020, and 3. The Essential Commodities (Amendment) Act, 2020, which have recently been the basis of the current farmers agitation on their implementation\n" +
                        "Public comments on the 2 Farm Laws as mentioned are invited by February 20, 2021. The comments/suggestions may be posted online at www.farmer.gov.in/sccommittee or by email to sccommittee-agri@gov.in",
                "Krishi Jagran (KJ) is proud to announce that it is now an official Media Partner of the BioAg World Congress 2021. For further details on the Congress refer to https://www.bioagworld.com/  \n" +
                        "The BioAg World Congress core theme relates to four main BioAg segments, namely \n" +
                        "Biofertilizers\n" +
                        "Bionutritionals  \n" +
                        "Biopesticides and \n" +
                        "Biostimulants \n" +
                        "These segments are further split into 6 focused sessions: \n" +
                        "    • Balancing Food Security & Food Safety to Support True Sustainability: Leaders in Food & Bioagspeak up \n" +
                        "    • Soil Health Revolution with BioAgand water management: A need not a choice \n" +
                        "    • Regulatory Streamlining & Harmonization in BioAg: Need Globally Local\n" +
                        "    • BioAg Commercialization & Adaptability Challenges: Time to walk the talk for the sake of sustainability \n" +
                        "    • Role of Institutions, Investment in BioAg& tangible ROI: What are the missing Links and why \n" +
                        "    • Unavoidable Marriage between Precision Ag and Bio Ag in IPM and ICM: For a business case with investors",
                        "Even after the government's claim to cover all the farmers under PM Kisan (PM-Kisan Samman Nidhi scheme), recent data reveals that only half of the claimed people have received the benefits of Kisan Credit Card.\n" +
                                "Report shows that while the state government had raced to include 2.4 crore farmers under PM Kisan scheme, it could manage to provide KCCs to only 1.56 crore farmers in the state. \n" +
                                "Devesh Chaturvedi, Additional chief secretary of agriculture told that farmers who have been left out are mostly from east UP where land holdings are smaller in comparison to west UP. \n" +
                                "As a result, banks are not taking requisite interest in issuing KCC to farmers,” he said. Chaturvedi added that the drive would ensure that cases of the intended farmers are taken up by the government. \n" +
                                "“As per the guidelines of the Centre, the farmers will be provided KCC within 15 days of filing the application,” he said. Sources said the state government also seeks to get the credit cards issued, mainly from cooperative banks.",
                        "Odisha Chief Minister Naveen Patnaik on Thursday, announced a Covid support package worth Rs 11 crore for 120,000 dairy farmers\n" +
                                " registered with the state's largest milk procurement group, the Odisha State Cooperative Milk Producers Federation (OMFED). \n" +
                                "The initiative will provide each farmer with a maximum of Rs 6,000 to purchase fodder. \n" +
                                "While practically every other sector of the economy was in disarray, Patnaik said that agriculture and related industries were keeping the economy afloat during the Covid-19 pandemic. \n" +
                                "Dairy farmers were impacted badly during the state-wide lockdown amid the second wave of Covid-19, when OMFED, the state's top organization of milk producers and other cooperative unions, reduced milk procurement from roughly 5.45 lakh litres a day to 2-3.75 lakh litres\n" +
                                " a day due to low demand. \n" +
                                "With businesses remaining closed with lockdown restrictions forcing distress sales, sales of dairy-based products like cheese, ghee, and curd have also decreased significantly, according to some dairy farmers. \n" +
                                " “Dairy farming contributes significantly to the rural economy, particularly for small and marginal farmers. The Covid epidemic has resulted in a significant drop in demand for milk and dairy products, which has had a significant impact on dairy farmers' livelihoods,” Patnaik explained, explaining the rationale behind the support plan.",
                                "Karnataka has hiked its agriculture lending target in the cooperative sector by around 14% this fiscal year over 2020-21 \n" +
                                        "in order to assist farmers who are suffering as a result of the COVID-19-induced shutdown.\n" +
                                        "Earlier this week, Chief Minister B.S. Yediyurappa announced that a total of Rs 20,810 crore would be provided to \n" +
                                        "nearly 31 lakh farmers this year, up 13.97 percent from 2020-21, when a total of Rs 17,901 crore was disbursed to around 26 lakh farmers. \n" +
                                        "While 30.26 lakh farmers will receive a total of Rs. 19,370 crore in zero-interest short-term loans, roughly 60,000 farmers will receive Rs. 1,440 crore in 3 percent-interest mid-term and long-term loans, according to the Chief Minister. \n" +
                                        "RELATED LINKS\n" +
                                        "State Bank of India Launches ‘Kavach’ Personal Loan for Covid-19 Patients; Details Inside\n" +
                                        "The scheme will also reimburse payments already incurred for COVID-19-related medical bills, according to SBI.…\n" +
                                        "Mahindra & Mahindra Launches M-Protect COVID Plan for Farmers, Customers\n" +
                                        "Mahindra & Mahindra comes up with M-Protect COVID Plan for its new customers.…\n" +
                                        "He was addressing at Raitha Spandana in Bengaluru, during the launch of the Bengaluru District Central\n" +
                                        " Cooperative Bank's agriculture loan disbursal. \n" +
                                        "Agriculture loans worth Rs 17,901 crore were disbursed in 2020-21, exceeding the objective by 114 percent.\n" +
                                        " While about 25.6 lakh farmers received approximately Rs 16,641 crore in short-term loans, approximately 52,000 farmers received approximately Rs 1,260 crore in mid-term and long-term loans. \n" +
                                        "“With the COVID-19 situation in mind, we set a greater goal. We are a government that supports farmers\n" +
                                        ". Agriculture, health, and education are among Mr. Yediyurappa's top priorities. \n" +
                                        "The government had set a goal of providing a total of Rs 1,400 crore in loans to approximately 40 lakh self-help groups, with 6,700 groups receiving Rs 233.33 crore by the end of May. \n" +
                                        "Yediyurappa told that the government is aware of the issues faced by farmers and other groups as a result of COVID-19. “As a result, the cooperative sector's repayment period has been extended by three months. 6.17 lakh farmers and 8.15 lakh members of 65,000 SHGs will profit from this, he said. ",
                                        "The Agriculture Minister of Meghalaya, Banteidor Lyngdoh said that for the first time, high value Lakadong turmeric\n" +
                                                " and Ginger powder were transported to the Netherlands and the United Kingdom.\n" +
                                                "Also read : 10 Highly Nutritious Roots and Tuber Vegetables in India\n" +
                                                "The variety of Lakadong turmeric originates from the area of Lakadong, of the West Jaintia Hills district.\n" +
                                                " It is considered to be one of the best varieties of turmeric in the whole world and that too with a curcumin content \n" +
                                                "of about 6.8% to 7.5%.\n" +
                                                "The high-quality ginger powder is acquired from villages in the Ri-Bhoi districts.\n" +
                                                "Banteidor said about 150 kg of Lakadong turmeric and around 150 kg ginger powder were transported to \n" +
                                                "the Netherlands and about 210 kg of Lakadong turmeric & 5 kg of ginger powder are leap for the United Kingdom.\n" +
                                                "He also said that the export of Lakadong turmeric & Ginger powder is a part of trial and the process assisted directly by \n" +
                                                "the state government following a demand among consumers in these two countries of the United Kingdom and Netherlands.\n" +
                                                "The minister said that export of the produce , sourced from the women’s self help groups in these two districts, \n" +
                                                "would have started in the year 2020 but, it could not be done because of the Covid-19 Pandemic.\n"};

        String[] postedAgp = {"2 Days Ago","1 Day Ago","4 Days Ago","2 Days Ago","1 Day Ago","4 Days Ago","2 Days Ago"};

        for(int i=0; i<7; i++){
            News news1 = new News(newsHeader[i],newsImageIds[i],postedAgp[i],news[i]);
            newsList.add(news1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News selectedNews = (News) newsAdapter.getItem(position);
        Intent showNewsIntent = new Intent(getApplicationContext(),ShowNewsActivity.class);
        showNewsIntent.putExtra("selectedNews",selectedNews);
        startActivity(showNewsIntent);
    }
}