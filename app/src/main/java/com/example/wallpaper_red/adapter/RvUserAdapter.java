package com.example.wallpaper_red.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallpaper_red.bean.MallBeanData;
import com.example.wallpaper_red.databinding.WallUserItemBinding;
import com.example.wallpaper_red.utils.OnItemClick;

import java.util.List;

public class RvUserAdapter extends RecyclerView.Adapter<RvUserAdapter.RvUserHolder> {
    private List<MallBeanData> dataList;
    private Context context;
    public OnItemClick onItemClick;
    public RvUserAdapter(List<MallBeanData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public RvUserAdapter.RvUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        WallUserItemBinding binding=WallUserItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RvUserHolder(binding,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RvUserAdapter.RvUserHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(dataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick!=null){
                    onItemClick.onClick(position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    static class RvUserHolder extends RecyclerView.ViewHolder{

         WallUserItemBinding binding;
        Context context;

        public RvUserHolder(@NonNull  WallUserItemBinding binding, Context context) {
            super(binding.getRoot());
            this .binding=binding;
            this.context=context;

        }

        @SuppressLint("NotifyDataSetChanged")
        public void bind(MallBeanData data) {
            Glide.with(context).load(data.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true).skipMemoryCache(true).into(binding.wallUserItemIv);
            binding.wallUserItemCount.setText(String.valueOf(data.getCount()));



            }



        }
    }

