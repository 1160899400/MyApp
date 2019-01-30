package com.liu.jim.jobgo.view.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.liu.jim.ILocationManager;
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseFragment;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataByDisContract;
import com.liu.jim.jobgo.db.model.Location;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.job_info.JobDataByDisPresenter;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobDisAdapter;
import com.liu.jim.locator.ILocationListener;
import com.liu.jim.locator.LocationService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by jim on 2018/3/6.
 */

public class HomepageFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener, JobDataByDisContract.IJobDataByDisView {
    private static final String TAG = "HomepageFragment";
    private JobDataByDisPresenter jobDataByDisPresenter;
    private boolean LoadingMore = false;
    private View mBannerView;
    private SliderLayout mBanner;
    public ListView mListView;
    public List<JobBasicInfo> mData;
    public SwipeRefreshLayout swpRefresh;

    public Context context;
    private JobDisAdapter jobDisAdapter;
    private TextView tvLocation;

    private boolean boundLocationService;


    public HomepageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据和视图
        jobDataByDisPresenter = new JobDataByDisPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initBanner();
        initJobList();
        initSwipeRefresh();
        setOnScrollListener();
        mListView.addHeaderView(mBannerView);
        initLocation();
        return mContentView;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fg_homepage;
    }


    @Override
    public void bindView() {
        mListView = mContentView.findViewById(R.id.job_list);
        mBannerView = LayoutInflater.from(mActivity).inflate(R.layout.view_banner, null, false);
        mBanner = mBannerView.findViewById(R.id.slider);
        swpRefresh = mContentView.findViewById(R.id.swipe_refresh_job_list);
        tvLocation = mContentView.findViewById(R.id.tv_Location_address);
        Button btnLocate = mContentView.findViewById(R.id.btn_locate);
        btnLocate.setOnClickListener(this);
    }


    private ServiceConnection locationServiceConnection = new ServiceConnection() {
        private ILocationManager mILocationManager;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mILocationManager = ILocationManager.Stub.asInterface(service);
            try {
                mILocationManager.registerListener(new ILocationListener.Stub() {
                    @Override
                    public void onReturnLocation(Location location) throws RemoteException {
                        Log.i(TAG,"on return location: " + location.getProvince() + location.getCity() + location.getDistrict());
                        if (null != tvLocation && null != location) {
                            Log.i(TAG,"get location: " + location.getProvince() + location.getCity() + location.getDistrict());
                            tvLocation.setText(location.getProvince() + location.getCity() + location.getDistrict());
                            //获取到后立刻解除service绑定
                            HomepageFragment.this.mActivity.unbindService(locationServiceConnection);
                        }
                    }
                });
                mILocationManager.getLocation();
                Log.i(TAG,"location service connected ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            boundLocationService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mILocationManager.unregisterListener();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            boundLocationService = false;
        }
    };


    /**
     * 初始化用户地理位置信息 (连接service，连接后立刻获取)
     */
    private void initLocation() {
        if (!boundLocationService) {
            Intent intent = new Intent(mContext, LocationService.class);
            mActivity.bindService(intent, locationServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    /**
     * 初始化轮播控件
     */
    private void initBanner() {
        HashMap<String, Integer> urlMap = new HashMap<String, Integer>();
        urlMap.put("平台推广", R.drawable.slider_view1);
        urlMap.put("义工旅行", R.drawable.slider_view2);
        urlMap.put("云地推服务", R.drawable.slider_view3);
        for (String name : urlMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(name)
                    .image(urlMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mBanner.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mBanner.setCustomAnimation(new DescriptionAnimation());
            mBanner.setDuration(3000);
            mBanner.addOnPageChangeListener(this);
            //add your extra information 点击图片时可用到
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mBanner.addSlider(textSliderView);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_locate) {

        }
    }

    /**
     * 设置下拉刷新效果
     */
    public void initSwipeRefresh() {
        //设置RefreshLayout样式
        swpRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);  //改变加载显示的颜色
        swpRefresh.setBackgroundColor(Color.WHITE);     //设置背景颜色
        swpRefresh.setSize(SwipeRefreshLayout.DEFAULT);     //设置初始时的大小
        swpRefresh.setDistanceToTriggerSync(300);       //设置向下拉多少出现刷新
        swpRefresh.setProgressViewEndTarget(false, 200);    //设置刷新旋转出现的位置
        swpRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {    //设置SwipeRefreshLayout监听，监听是否下拉刷新
            @Override
            public void onRefresh() {
                //检查是否处于刷新状态
                if (swpRefresh.isRefreshing()) {
                    swpRefresh.setRefreshing(true);
                    //模拟加载网络数据
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            updateJobList();
                        }
                    });
                }
            }
        });
    }

    /**
     * 设置ListView的滑动监听，监听是否上拉滑动到底部
     */
    public void setOnScrollListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:        // 当不滚动时
                        if (view.getLastVisiblePosition() == (view.getCount() - 1) && !LoadingMore) {       // 判断滚动到底部且是否正在加载
                            LoadingMore = true;
                            HomepageFragment.this.requestJobList();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    /**
     * 初始化job数据，从缓存读取或请求网络
     */
    private void initJobList() {
        mData = new LinkedList<>();
        this.jobDisAdapter = new JobDisAdapter(getActivity(), mData);
        this.mListView.setAdapter(jobDisAdapter);
        requestJobList();
    }

    /**
     * 发送异步网络请求，请求数据并读入缓存
     */
    private void requestJobList() {
        NoticeManager.build(this.getActivity()).setNoticeType(NoticeManager.NoticeType.Loading);
        NoticeManager.build(this.getActivity()).show();
        jobDataByDisPresenter.getJobDataByDis();
    }

    /**
     * 下拉刷新时更新数据
     */
    public void updateJobList() {
        jobDisAdapter.clearList();
        jobDataByDisPresenter.refreshJobDataByDis();
    }

    /**
     * 将返回的jobDataInfo展示
     *
     * @param jbi
     */
    @Override
    public void showJobDataByDis(List<JobBasicInfo> jbi) {
        jobDisAdapter.addListFromBottom(jbi);
        if (swpRefresh.isRefreshing()) {
            swpRefresh.setRefreshing(false);
        }
        LoadingMore = false;
        NoticeManager.build(this.getActivity()).loadSuccess();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListView.removeHeaderView(mBannerView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jobDataByDisPresenter != null) {
            jobDataByDisPresenter.onDestroy();
            jobDataByDisPresenter = null;
            System.gc();
        }
        if (boundLocationService) {
            mActivity.unbindService(locationServiceConnection);
        }

    }
}
