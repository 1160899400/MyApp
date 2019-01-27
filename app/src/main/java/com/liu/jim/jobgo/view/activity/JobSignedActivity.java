package com.liu.jim.jobgo.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseActivity;
import com.liu.jim.jobgo.contract.job_info.JobSignedContract;
import com.liu.jim.jobgo.entity.response.bean.JobSignedInfo;
import com.liu.jim.jobgo.presenter.job_info.JobSignedPresenter;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobSignedAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 查看我报名的工作
 */

public class JobSignedActivity extends BaseActivity implements JobSignedContract.IJobSignedView {
    private boolean LoadingMore = false;        //判断是否正在加载
    public ListView mListView;
    public List<JobSignedInfo> mData;
    public SwipeRefreshLayout swpRefresh;
    public JobSignedAdapter jobListAdapter;
    private JobSignedContract.IJobSignedPresenter jobSignedPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobSignedPresenter = new JobSignedPresenter(this);
        initJoblistArr();
        setRefreshLayout();
        setOnScrollListener();
    }


    @Override
    protected void bindView() {
        mListView = this.findViewById(R.id.job_list);
        swpRefresh = this.findViewById(R.id.swipe_refresh_job_list);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_job_signed;
    }


    /**
     * 设置下拉刷新效果
     */
    public void setRefreshLayout() {
        //设置RefreshLayout样式
        swpRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);      //改变加载显示的颜色
        swpRefresh.setBackgroundColor(Color.WHITE);     //设置背景颜色
        swpRefresh.setSize(SwipeRefreshLayout.DEFAULT);     //设置初始时的大小
        swpRefresh.setDistanceToTriggerSync(300);       //设置向下拉多少出现刷新
        swpRefresh.setProgressViewEndTarget(false, 200);        //设置刷新旋转出现的位置
        swpRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {        //设置SwipeRefreshLayout监听，监听是否下拉刷新
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
                            jobSignedPresenter.getJobSigned();
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
        mData = new ArrayList<>();
        this.jobListAdapter = new JobSignedAdapter(this, mData);
        this.mListView.setAdapter(jobListAdapter);
        jobSignedPresenter.getJobSigned();
    }

    /**
     * 下拉刷新时更新数据
     */
    public void updateJobList() {
        jobListAdapter.clearList();
        jobSignedPresenter.updateJobSigned();
    }


    @Override
    public void showJobSigned(List<JobSignedInfo> jobSignedInfoList) {
        jobListAdapter.addListFromBottom(jobSignedInfoList);
        if (swpRefresh.isRefreshing()){
            swpRefresh.setRefreshing(false);
        }
        LoadingMore = false;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(jobSignedPresenter != null){
            jobSignedPresenter.onDestroy();
            jobSignedPresenter = null;
            System.gc();
        }
    }
}
