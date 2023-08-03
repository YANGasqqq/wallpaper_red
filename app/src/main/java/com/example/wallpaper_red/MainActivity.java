package com.example.wallpaper_red;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.wallpaper_red.R;
import com.example.wallpaper_red.adapter.FragmentAdapter;
import com.example.wallpaper_red.databinding.ActivityMainBinding;
import com.example.wallpaper_red.ui.me.MeFragment;
import com.example.wallpaper_red.ui.video.DashboardFragment;
import com.example.wallpaper_red.ui.home.HomeFragment;
import com.example.wallpaper_red.ui.messgae.NotificationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//         getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainHome.setOnClickListener(this);
        binding.mainVideo.setOnClickListener(this);
        binding.mainAdd.setOnClickListener(this);
        binding.mainMessage.setOnClickListener(this);
        binding.mainMe.setOnClickListener(this);
        List<Fragment> fg=new ArrayList<>();
        fg.add(new HomeFragment());
        fg.add(new DashboardFragment());
        fg.add(new NotificationsFragment());
        fg.add(new MeFragment());
        binding.mainVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fg));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_home:
                binding.mainHome.setTextColor(Color.BLACK);
                binding.mainVideo.setTextSize(16);
                binding.mainMessage.setTextSize(16);
                binding.mainMe.setTextSize(16);
                binding.mainHome.setTextSize(18);
                binding.mainVideo.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMessage.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMe.setTextColor(Color.parseColor("#7A000000"));
                binding.mainVp.setCurrentItem(0);

                break;
            case R.id.main_video:
                binding.mainVideo.setTextColor(Color.BLACK);
                binding.mainVideo.setTextSize(18);
                binding.mainHome.setTextSize(16);
                binding.mainHome.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMessage.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMe.setTextColor(Color.parseColor("#7A000000"));
                binding.mainVp.setCurrentItem(1);


                break;
            case  R.id.main_message:
                binding.mainMessage.setTextColor(Color.BLACK);
                binding.mainVideo.setTextSize(16);
                binding.mainMessage.setTextSize(18);
                binding.mainMe.setTextSize(16);
                binding.mainHome.setTextSize(16);
                binding.mainVideo.setTextColor(Color.parseColor("#7A000000"));
                binding.mainHome.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMe.setTextColor(Color.parseColor("#7A000000"));
                binding.mainVp.setCurrentItem(2);

                break;
            case  R.id.main_me:
                binding.mainMe.setTextColor(Color.BLACK);
                binding.mainVideo.setTextSize(16);
                binding.mainMessage.setTextSize(16);
                binding.mainMe.setTextSize(18);
                binding.mainHome.setTextSize(16);
                binding.mainVideo.setTextColor(Color.parseColor("#7A000000"));
                binding.mainHome.setTextColor(Color.parseColor("#7A000000"));
                binding.mainMessage.setTextColor(Color.parseColor("#7A000000"));
                binding.mainVp.setCurrentItem(3);

                break;

        }
    }
}