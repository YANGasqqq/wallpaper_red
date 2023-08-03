package com.example.wallpaper_red;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wallpaper_red.application.MyApplication;
import com.example.wallpaper_red.bean.MallBeanData;
import com.example.wallpaper_red.databinding.ActivityUserBinding;
import com.example.wallpaper_red.sql.DatabaseHelper;
import com.example.wallpaper_red.sql.Picture;
import com.example.wallpaper_red.utils.OnItemClick;
import com.example.wallpaper_red.wallFragment.FourKFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserActivity extends AppCompatActivity {
   private DatabaseHelper dbHelper;
    private static final String TAG = "UserActivity";
    ActivityUserBinding binding;
    private List<Picture> historyPictures;
    private List<MallBeanData> dataList;
    private ArrayList<Integer> like_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        binding= ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MyApplication myApp = (MyApplication) getApplicationContext();
        dbHelper = myApp.getDbHelper();
        historyPictures = dbHelper.getHistoryPictures();
        Log.i(TAG, "onCreate: "+ historyPictures.size());
        initData();
       binding.guanbi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initData() {
        dataList = new ArrayList<>();

        for (int i = 0; i <historyPictures.size() ; i++) {
             int lin= dbHelper.getLikeNumByImgUrl(historyPictures.get(i).getImgUrl());
             boolean isl=dbHelper.isLikeByUrl(historyPictures.get(i).getImgUrl());
            dataList.add(new MallBeanData(historyPictures.get(i).getImgUrl(),"Title",lin,isl));
        }
        Log.i(TAG, "initData: "+ dataList.size());
        Collections.reverse(dataList);
        FourKFragment.RvAdapter rvAdapter=new FourKFragment.RvAdapter(dataList,1);

        binding.userRv.setAdapter(rvAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        binding.userRv.setLayoutManager(layoutManager);
        rvAdapter.notifyDataSetChanged();

        rvAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onClick(int position) {
                like_num=new ArrayList<>();
                List<MallBeanData> list=new ArrayList<>();
                ArrayList<String> imgUrl=new ArrayList<>();
                ArrayList<String> title=new ArrayList<>();
                list=rvAdapter.getDataList();
                Log.i(TAG, "onClick: "+list.size());

                for (int i = 0; i < list.size(); i++) {
                    title.add(list.get(i).getImgTitle());
                    imgUrl.add(list.get(i).getImgUrl());
                    like_num.add(list.get(i).getCount());
                }
                Intent intent= new Intent(UserActivity.this, PicActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);

                bundle.putStringArrayList("img_url", imgUrl);
                bundle.putIntegerArrayList("randomNumber", like_num);
                bundle.putStringArrayList("title", title);
                intent.putExtras(bundle);
               startActivity(intent);


            }
        });
    }
}