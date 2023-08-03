package com.example.wallpaper_red.sql;

public class Picture {

        private int id;
        private String imgUrl;
        private int likeNum;
        private boolean isCollect;
        private boolean isHistory;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    private boolean isLike;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }



        public Picture( String imgUrl, int likeNum, boolean isLike, boolean isCollect, boolean isHistory) {

            this.imgUrl = imgUrl;
            this.likeNum = likeNum;
            this.isCollect = isCollect;
            this.isLike = isLike;
            this.isHistory = isHistory;
        }


}
