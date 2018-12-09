package com.liu.jim.jobgo.view.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataByDisContract;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.manager.LocationManager;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.job_info.JobDataByDisPresenter;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobDisAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by jim on 2018/3/6.
 */

public class HomepageFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener, JobDataByDisContract.IJobDataByDisView {

    private JobDataByDisPresenter jobDataByDisPresenter;
    private boolean LoadingMore = false;
    private View mView;
    private View mBannerView;
    private SliderLayout mBanner;
    public ListView mListView;
    public List<JobBasicInfo> mData;
    public SwipeRefreshLayout swpRefresh;

    public Context context;
    private JobDisAdapter jobDisAdapter;
    private Button btn_locate;
    private TextView tv_address;


    public HomepageFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据和视图
        context = this.getActivity();
        this.mView = getActivity().getLayoutInflater().inflate(R.layout.fg_homepage, null, false);
        jobDataByDisPresenter = new JobDataByDisPresenter(this);
        LocationManager.getLocationManager().getPostition(this.getActivity());
        bindView();
        initBanner();
        mListView.addHeaderView(mBannerView);
        initJoblistArr();
        initSwipeRefresh();
        setOnScrollListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (AppConstants.address == null) {
            tv_address.setText("当前位置:未知");
        } else {
            tv_address.setText("当前位置:" + AppConstants.address);
        }
        return this.mView;
    }


    /**
     * 绑定控件
     */
    public void bindView() {
        mListView = this.mView.findViewById(R.id.job_list);
        mBannerView = LayoutInflater.from(this.getActivity()).inflate(R.layout.view_banner, null, false);
        mBanner = mBannerView.findViewById(R.id.slider);
        swpRefresh = this.mView.findViewById(R.id.swipe_refresh_job_list);
        tv_address = this.mView.findViewById(R.id.tv_Location_address);
        btn_locate = this.mView.findViewById(R.id.btn_locate);
        btn_locate.setOnClickListener(this);
    }

    /**
     * 初始化轮播控件
     */
    private void initBanner() {
        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();
        url_maps.put("平台推广", R.drawable.slider_view1);
        url_maps.put("义工旅行", R.drawable.slider_view2);
        url_maps.put("云地推服务", R.drawable.slider_view3);
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
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
            LocationManager.getLocationManager().stopLocation();
            LocationManager.getLocationManager().getPostition(this.getActivity());
            tv_address.setText("当前位置:" + AppConstants.address);
            updateJobList();
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
    private void initJoblistArr() {
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


    /**
     * 通知回收Presenter,防止内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jobDataByDisPresenter != null) {
            jobDataByDisPresenter.onDestroy();
            jobDataByDisPresenter = null;
            System.gc();
        }
    }
}
