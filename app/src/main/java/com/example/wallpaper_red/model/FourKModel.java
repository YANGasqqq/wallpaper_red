package com.example.wallpaper_red.model;

import android.util.Log;

import com.example.wallpaper_red.bean.FourKBean;
import com.example.wallpaper_red.http.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FourKModel {
    private static String BASE_URL="http://wallpaper.apc.360.cn/";
    private Retrofit retrofit;
    private ApiService apiService;

    public FourKModel() {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService=retrofit.create(ApiService.class);
    }

    public interface DataCallBack{
       void onDataReceived(FourKBean data);
       void onDataError(String error);
   }
   public void fetchDataFromService(final DataCallBack callBack){
       Call<FourKBean> call=apiService.fetchWallData("WallPaper", "getAppsByCategory","36",1,100,"360chrome");
       call.enqueue(new Callback<FourKBean>() {
           @Override
           public void onResponse(Call<FourKBean> call, Response<FourKBean> response) {
               if (response.isSuccessful()){
                   FourKBean data=response.body();
                   if (data!=null){
                       Log.i("FourK",response.body().getTotal());

                       callBack.onDataReceived(data);
                   }else{
                       Log.i("FourK","数据为空！");

                   }
               }
           }

           @Override
           public void onFailure(Call<FourKBean> call, Throwable t) {

           }
       });
   }
}
