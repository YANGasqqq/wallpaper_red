package com.example.wallpaper_red.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;


public  abstract class BaseFragment<T extends ViewBinding>  extends Fragment {

    private  View root;
   protected  T binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            binding=inflateBinding(inflater,container);
            root=binding.getRoot();
        }
        return root;
    }

    protected abstract T inflateBinding(LayoutInflater inflater,ViewGroup container);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract void initData();
}
