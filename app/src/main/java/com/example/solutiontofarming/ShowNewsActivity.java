package com.example.solutiontofarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solutiontofarming.data.News;

public class ShowNewsActivity extends AppCompatActivity {

    ImageView imageViewNewsImage;
    TextView textViewHeader,textViewNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        bindComponents();
        showNews();
        getSupportActionBar().setTitle("Bulletin");

    }

    public void bindComponents(){
        this.imageViewNewsImage = findViewById(R.id.img_show_news_image);
        this.textViewHeader = findViewById(R.id.text_show_news_header);
        this.textViewNews = findViewById(R.id.text_show_news_news);
    }
    public void showNews(){
        News selectedNews = (News) getIntent().getSerializableExtra("selectedNews");
        textViewHeader.setText(selectedNews.getHeadLine());
        textViewNews.setText(selectedNews.getNews());
        imageViewNewsImage.setImageResource(selectedNews.getImageId());
    }
}