package com.example.wallpaper_red.http;

import com.example.wallpaper_red.bean.FourKBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public  interface  ApiService {
    @GET("/index.php") // 替换为实际的API接口地址
    Call<FourKBean> fetchWallData(@Query("c") String c,
                                  @Query("a") String a,
                                  @Query("cid") String cid,
                                  @Query("start") int start,
                                  @Query("count") int count,
                                  @Query("form") String form); // DataResponse是您定义的用于解析服务器返回数据的实体类

}
