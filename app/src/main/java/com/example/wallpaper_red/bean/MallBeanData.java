package com.example.wallpaper_red.bean;

public class MallBeanData {

    public MallBeanData(String imgUrl, String imgTitle, Integer count, boolean isLike) {
        this.imgUrl = imgUrl;
        this.imgTitle = imgTitle;
        Count = count;
        this.isLike = isLike;
    }

    private String imgUrl;
    private String imgTitle;
    private Integer Count;
    private boolean isLike;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
