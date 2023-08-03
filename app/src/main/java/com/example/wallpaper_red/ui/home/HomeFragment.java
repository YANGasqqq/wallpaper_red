package com.example.wallpaper_red.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wallpaper_red.R;
import com.example.wallpaper_red.UserActivity;
import com.example.wallpaper_red.adapter.HomeFragmentAdapter;
import com.example.wallpaper_red.base.BaseFragment;
import com.example.wallpaper_red.databinding.FragmentHomeBinding;
import com.example.wallpaper_red.databinding.TabTvStyleBinding;
import com.example.wallpaper_red.wallFragment.AnimeFragment;
import com.example.wallpaper_red.wallFragment.BabyFragment;
import com.example.wallpaper_red.wallFragment.BelleFragment;
import com.example.wallpaper_red.wallFragment.FourKFragment;
import com.example.wallpaper_red.wallFragment.SceneryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private FragmentHomeBinding binding;
    private Activity mActivity;
    private com.example.wallpaper_red.databinding.TabTvStyleBinding bind;

    @Override
    protected FragmentHomeBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        mActivity=getActivity();
        return binding;
    }

    @Override
    protected void initData() {
        bind = TabTvStyleBinding.inflate(getLayoutInflater());
        List<Fragment> list=new ArrayList<>();
        list.add( new FourKFragment());
        list.add( new BelleFragment());
        list.add( new SceneryFragment());
        list.add( new AnimeFragment());
        list.add( new BabyFragment());
        binding.homeVp.setAdapter(new HomeFragmentAdapter(getChildFragmentManager(),list));
        binding.homeTl.setupWithViewPager(binding.homeVp);
        binding.homeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mActivity, UserActivity.class);
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.in_activity_anim,R.anim.out_activity_anim);
            }
        });

        binding.homeTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TextView tabTextView = bind.tabText;
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                tabTextView.setTextColor(ContextCompat.getColor(mActivity,R.color.black));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabTextView =bind.tabText;
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Reset font size of unselected tab
                tabTextView.setTextColor(ContextCompat.getColor(mActivity, R.color.yellow));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}