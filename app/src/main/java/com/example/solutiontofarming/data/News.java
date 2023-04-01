package com.example.solutiontofarming.data;

import java.io.Serializable;

public class News implements Serializable {

    private String headLine;
    private int imageId;
    private String postedAgo;
    private String news;

    public News(){

    }

    public News(String headLine, int imageId, String postedAgo, String news) {
        this.headLine = headLine;
        this.imageId = imageId;
        this.postedAgo = postedAgo;
        this.news = news;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPostedAgo() {
        return postedAgo;
    }

    public void setPostedAgo(String postedAgo) {
        this.postedAgo = postedAgo;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
