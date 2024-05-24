package com.example.wallpaper_red.bean;

public class PostTestBean {
    private String type;
    private int much;

    public PostTestBean(String type, int much) {
        this.type = type;
        this.much = much;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setMuch(int much) {
        this.much = much;
    }
    public int getMuch() {
        return much;
    }
}
