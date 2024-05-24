package com.example.wallpaper_red.bean;

import java.util.List;

public class TestBean {
    private int code;
    private boolean result;
    private String msg;
    private List<Data> data;
    public void setCode(int code) {

        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
public  static class Data{
    private String id;
    private String name;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
}
