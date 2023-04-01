package com.example.solutiontofarming.data;

public class HelpItem {

    private String title;
    private String desc;
    private boolean isShrink = true;

    public HelpItem() {
    }

    public HelpItem(String title, String desc) {

        this.title = title;
        this.desc = desc;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
