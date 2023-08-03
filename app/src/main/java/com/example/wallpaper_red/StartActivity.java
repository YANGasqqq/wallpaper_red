package com.example.wallpaper_red;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.wallpaper_red.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_start);
          Thread myThread=new Thread(){
              @Override
              public void run() {
                try {
                    sleep(1000);
                   startActivity(new Intent(StartActivity.this,MainActivity.class));
                   finish();
                }catch (Exception e) {
                   e.printStackTrace();
                }
                }
              };
            myThread.start();
          }
    }
