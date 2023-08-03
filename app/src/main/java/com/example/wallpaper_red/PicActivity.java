package com.example.wallpaper_red;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallpaper_red.R;
import com.example.wallpaper_red.application.MyApplication;
import com.example.wallpaper_red.databinding.ActivityPicBinding;
import com.example.wallpaper_red.sql.DatabaseHelper;
import com.example.wallpaper_red.sql.Picture;

import java.util.ArrayList;

public class PicActivity extends AppCompatActivity  implements View.OnClickListener {
    private static final String TAG = "PicActivity";
    private    ActivityPicBinding binding;
    private DatabaseHelper dbHelper;
    private ArrayList<String> imgUrl;
    private ArrayList<String> title;
    private ArrayList<Integer> randomNumber;
    private int position;
    private boolean isCollect;
    private int like_num;
    private boolean isLike =false;
    private View view;
    private float posX;
    private float posY;
    private float curPosX;
    private float curPosY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        MyApplication myApp = (MyApplication) getApplicationContext();
        dbHelper = myApp.getDbHelper();
        binding.picLike.setOnClickListener(this);
        binding.picCollect.setOnClickListener(this);
        binding.picBtn.setOnClickListener(this);

          if (dbHelper.isLikeByUrl(imgUrl.get(position))){
              Log.i(TAG, "dbHelper: true");
              binding.picLike.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_like1),null,null);
              isLike=true;
          }else{
              Log.i(TAG, "dbHelper: false");

              binding.picLike.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_like),null,null);
              isLike=false;

          }
        dbHelper.insertOrUpdatePicture(new Picture(imgUrl.get(position), randomNumber.get(position),isLike,isCollect,true));
      binding.picBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              setWallpaperFromUrl(imgUrl.get(position));
          }
      });
    }


    private void setWallpaperFromUrl(String imageUrl) {
        try {
            Uri imageUri = Uri.parse(imageUrl);
            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.setDataAndType(imageUri, "image/*");
            intent.putExtra("mimeType", "image/*");
            startActivity(Intent.createChooser(intent, "Set as Wallpaper"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(PicActivity.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
        }
    }
    private void initData() {
        Intent intent=getIntent();
        position = intent.getIntExtra("position",0);

        imgUrl = intent.getStringArrayListExtra("img_url");
        randomNumber = intent.getIntegerArrayListExtra("randomNumber");
        title = intent.getStringArrayListExtra("title");
        String [] words= title.get(position).split(" ");
        int i=0;
        for (String work :
                words) {
            TextView tv=new TextView(this);
            tv.setText(work);
            tv.setTextSize(16);
            tv.setPadding(15,15,15,15);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.drawable.pic_shape_style);
            Log.i(TAG, "onCreate: "+work);
            binding.llContainer.addView(tv);
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) tv.getLayoutParams();
            lp.leftMargin=15;
            tv.setLayoutParams(lp);
            i++;
        }
        binding.picLike.setText(String.valueOf(randomNumber.get(position)));
        Log.i(TAG, "onCreate: "+ imgUrl.get(position));
        Glide.with(this).load(imgUrl.get(position)).into(binding.picIvBg);
    }
    private void getTouchView(){
        // 获取布局文件
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_pic, null);
        // 设置滑动监听
        setOnLayoutTouchListener();
    }
    private void  setOnLayoutTouchListener(){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        posX = event.getX();
                        posY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curPosX = event.getX();
                        curPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((curPosX - posX > 0) && (Math.abs(curPosX - posX) > 25)){
                            Log.v(TAG,"向左滑动");
                        }
                        else if ((curPosX - posX < 0) && (Math.abs(curPosX - posX) > 25)){
                            Log.v(TAG,"向右滑动");
                        }
                        if ((curPosY - posY > 0) && (Math.abs(curPosY - posY) > 25)){
                            Log.v(TAG,"向下滑动");
                        }
                        else if ((curPosY - posY < 0) && (Math.abs(curPosY - posY) > 25)){
                            Log.v(TAG,"向上滑动");
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.pic_like:
                Log.i(TAG, "isLike: "+!isLike);
                if (!isLike){
                    Log.i(TAG, "onClick: true");
                    isLike = true;
                    like_num = randomNumber.get(position)+1;
                    binding.picLike.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_like1),null,null);
                    binding.picLike.setText(String.valueOf(like_num));
                    dbHelper.insertOrUpdatePicture(new Picture(imgUrl.get(position), like_num,isLike,isCollect,true));
                }else {
                    Log.i(TAG, "onClick: false");
                    String[] selectionArgs = {imgUrl.get(position)};
                    Cursor cursor=dbHelper.executeRawQuery("SELECT  id FROM pic_tab WHERE img_url=?", selectionArgs);
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                            Log.i(TAG, "onClickId: "+id);
                            dbHelper.updateIsLikeToFalse(id);
                        } while (cursor.moveToNext());
                        cursor.close();
                }
                    binding.picLike.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_like),null,null);
                    int like= dbHelper.getLikeNumByImgUrl(imgUrl.get(position))-1;
                    binding.picLike.setText(String.valueOf(like));
                    isLike=false;

                }

                break;
            case  R.id.pic_collect:
                Log.i(TAG, "onClick:vpic_collect");
                isCollect = true;
                dbHelper.insertPicture(new Picture(imgUrl.get(position), like_num,isLike,isCollect,true ));
                binding.picCollect.setBackgroundResource(R.drawable.ic_collect_1);
                break;
            case R.id.pic_btn:
                Log.i(TAG, "onClick:pic_btn");
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
         Intent returnIntent=new Intent();
         returnIntent.putExtra("likeNum",like_num);
         setResult(Activity.RESULT_OK,returnIntent);
         finish();

    }
}