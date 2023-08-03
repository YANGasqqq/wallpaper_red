package com.example.wallpaper_red.wallFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallpaper_red.PicActivity;
import com.example.wallpaper_red.base.BaseFragment;
import com.example.wallpaper_red.bean.FourKBean;
import com.example.wallpaper_red.bean.MallBeanData;
import com.example.wallpaper_red.databinding.FragmentFourkBinding;
import com.example.wallpaper_red.databinding.WallItemBinding;
import com.example.wallpaper_red.model.FourKModel;
import com.example.wallpaper_red.utils.OnItemClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class FourKFragment extends BaseFragment<FragmentFourkBinding> {
    private static final int REQUEST_CODE = 1;
    public static int ZXCZA = 0;
    private FragmentFourkBinding binding;
    private static Activity mActivity;
    private FourKModel model;
    private Handler handler;

    private boolean isRefresh=true;
    private List<Integer> randomNumbers;

    private static final String TAG = "FourKFragment";
    private List<MallBeanData> downPullUpdate;
    private RvAdapter adapter;
    private ArrayList<Integer> like_num;
    private int returnedData;

    @Override
    protected FragmentFourkBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        binding=FragmentFourkBinding.inflate(inflater,container,false);
        mActivity=getActivity();
        model=new FourKModel();
        return binding;
    }

    @Override
    protected void initData() {
        binding.fourkRv.setLayoutManager(  new GridLayoutManager(mActivity,2));
        binding.fourkSrl.setRefreshing(true);

        getMallData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    binding.fourkSrl.setRefreshing(false);
                }
            }).start();
        binding.fourkSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=false;
                getMallData();
                binding.fourkSrl.setRefreshing(false);
            }
        });
        binding.fourkRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position= 1;
                if (isSlideToBottom(recyclerView)){
                    Log.i(TAG, "onScrolled: "+downPullUpdate.size());
                    adapter.AddUpdateData(downPullUpdate.subList((position+1)*10,(position+2)*10));
                    adapter.showPb();
                    adapter.hidePb();


                }
            }
        });

    }
    private boolean isSlideToBottom(RecyclerView recyclerView){
        if (recyclerView==null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }
    private void getMallData(){
        handler=new Handler(Looper.getMainLooper()){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                FourKBean data= (FourKBean) msg.obj;
                List<MallBeanData> data1=new ArrayList<>();
                Random random = new Random();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    randomNumbers = random.ints(0, 100)
                            .distinct()
                            .limit(100)
                            .boxed()
                            .collect(Collectors.toList());
                }
                like_num = new ArrayList<>();
                for (int i = 0; i < data.getData().size(); i++) {
                    int randomNumber = random.nextInt(10000);
                    like_num.add(randomNumber);
                    if (isRefresh){
                    data1.add(new MallBeanData(data.getData().get(i).getImg_1024_768(),data.getData().get(i).getUtag(),randomNumber,false));
                    Log.i("initData", data.getData().get(i).getImg_1024_768());}
                    else {
                        int randomNumber1 = random.nextInt(data.getData().size());
                        Log.i("TAG", "handleMessage: "+randomNumbers.get(i));
                        data1.add(new MallBeanData(data.getData().get(randomNumbers.get(i)).getImg_1024_768(), data.getData().get(randomNumbers.get(i)).getUtag(), randomNumber, false));
                        Log.i("initData", data.getData().get(i).getImg_1024_768());
                    }
                }
                downPullUpdate=data1;
                Log.i("TAG", "handleMessage: "+data1.size());
                adapter = new RvAdapter(data1.subList(0,10),0);
                binding.fourkRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClick(new OnItemClick() {
                    @Override
                    public void onClick(int position) {
                        List<MallBeanData> list=new ArrayList<>();
                        ArrayList<String> imgUrl=new ArrayList<>();
                        ArrayList<String> title=new ArrayList<>();
                         list=adapter.getDataList();
                        Log.i(TAG, "onClick: "+list.size());

                        for (int i = 0; i < list.size(); i++) {
                            title.add(list.get(i).getImgTitle());
                            imgUrl.add(list.get(i).getImgUrl());
                        }
                        Intent intent= new Intent(mActivity, PicActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("position",position);

                        bundle.putStringArrayList("img_url", imgUrl);
                        bundle.putIntegerArrayList("randomNumber", like_num);
                        bundle.putStringArrayList("title", title);
                        intent.putExtras(bundle);
                        mActivity.startActivity(intent);


                    }
                });

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=handler.obtainMessage();
                model.fetchDataFromService(new FourKModel.DataCallBack() {
                    @Override
                    public void onDataReceived(FourKBean data) {
                        if (data!=null){
                            message.obj=data;
                            handler.sendMessage(message);

                        }
                    }

                    @Override
                    public void onDataError(String error) {
                        message.obj="网络错误";
                        handler.sendMessage(message);
                        Log.i("initData", "网络错误!");

                    }
                });
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: 1111");
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 接收从 Activity 返回的数据
            returnedData = data.getIntExtra("likeNum",0);
            Log.i(TAG, "onActivityResult: "+ returnedData);
            // 在这里处理返回的数据
        }
    }

    public static class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder>{
       private List<MallBeanData> dataList;

       public List<MallBeanData> getDataList() {
           return dataList;
       }

       public void setDataList(List<MallBeanData> dataList) {
           this.dataList = dataList;
       }

       private boolean isLoading=false;
        private OnItemClick onItemClick;
        private int VIEW_TYPE_ITEM = 0;
        private static final int VIEW_TYPE_LOADING = 1;
        private List<MallBeanData> newData=new ArrayList<>();

        public List<MallBeanData> getNewData() {
            return newData;
        }

        public void setNewData(List<MallBeanData> newData) {
            this.newData = newData;
        }


        public RvAdapter(List<MallBeanData> dataList,int VIEW_TYPE_ITEM) {
            this.VIEW_TYPE_ITEM=VIEW_TYPE_ITEM;
            this.dataList = dataList;

        }
        @SuppressLint("NotifyDataSetChanged")
        public void updateData(List<MallBeanData> newDataList) {
            this.dataList = newDataList;
            notifyDataSetChanged(); // 数据集合发生改变，通知RecyclerView更新显示
        }
        @NonNull
        @Override
        public RvAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            WallItemBinding binding=WallItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new RvHolder(binding,VIEW_TYPE_ITEM);
        }

        @Override
        public void onBindViewHolder(@NonNull RvAdapter.RvHolder holder, @SuppressLint("RecyclerView") int position) {
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
            return dataList.size();
        }
        @SuppressLint("NotifyDataSetChanged")
        public  void AddUpdateData(List<MallBeanData> newData){
            dataList.addAll(newData);
            this.newData=newData;
            notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void hidePb(){
            isLoading =false;
            notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void showPb(){
            isLoading =true;
            notifyDataSetChanged();
        }
        public void setOnItemClick(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }
       public class RvHolder extends RecyclerView.ViewHolder{
             private final WallItemBinding binding;
            private  int   VIEW_TYPE_ITEM;
            public RvHolder(@NonNull  WallItemBinding binding, int VIEW_TYPE_ITEM) {
                super(binding.getRoot());
                this .binding=binding;
                this.VIEW_TYPE_ITEM=VIEW_TYPE_ITEM;

            }

            @SuppressLint("NotifyDataSetChanged")
            public void bind(MallBeanData data) {
                Glide.with(mActivity).load(data.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true).skipMemoryCache(true).into(binding.wallItemIv);
                binding.wallItemTitle.setText(data.getImgTitle());
                binding.wallItemCount.setText(String.valueOf(data.getCount()));
                if (VIEW_TYPE_ITEM==1){
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) binding.wallItemLl.getLayoutParams();
                    lp.width=480;
                    binding.wallItemLl.setLayoutParams(lp);
                    Glide.with(mActivity).load(data.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true).skipMemoryCache(true).into(binding.wallItemIv);
                    binding.wallItemTitle.setVisibility(View.GONE);
                    binding.wallItemCount.setText(String.valueOf(data.getCount()));
                }



            }
        }



    }


    public  OnItemClick onItemClick;


}
