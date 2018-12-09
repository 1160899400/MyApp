package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.contract.job_info.JobDataByKwContract;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.presenter.job_info.JobDataByKwPresenter;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jim on 2018/4/13.
 */

public class SearchJobActivity extends AppCompatActivity implements JobDataByKwContract.IJobDataByKwView {

    private boolean LoadingMore = false;
    private SearchView mSearchView;
    public SwipeRefreshLayout swpRefresh;
    private ListView mListView;
    private List<JobBasicInfo> jobBasicInfoList = new LinkedList<>();
    private JobAdapter jobAdapter;
    private String SearchContent;
    private JobDataByKwContract.IJobDataByKwPresenter jobDataByKwPresenter;

    /**
     * 获取intent传入的搜索关键字
     * 初始化成员变量
     * 绑定控件资源
     * 设置searchview 和listview的adapter
     * activity管理类添加该activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        Intent intent = getIntent();
        SearchContent = intent.getStringExtra("SearchStr");
        jobDataByKwPresenter = new JobDataByKwPresenter(this);
        bindView();
        reqJobByKw(SearchContent);
        initSearchView();
        initSwipeRefresh();
        setOnScrollListener();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("SearchJobActivity", this);
    }

    public void bindView() {
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setQueryHint(SearchContent);
        mListView = findViewById(R.id.job_list);
        swpRefresh = findViewById(R.id.swipe_refresh_job_list);
        jobAdapter = new JobAdapter(this, jobBasicInfoList);
        mListView.setAdapter(jobAdapter);
    }

    /**
     * 设置搜索框searchView
     */
    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchJobActivity.this.SearchContent = query;
                jobAdapter.clearList();
                reqJobByKw(query);
                return true;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    /**
     * 根据关键字查询
     */
    private void reqJobByKw(String keyword) {
        jobDataByKwPresenter.getJobDataByKw(keyword);
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
                            jobDataByKwPresenter.loadMore(SearchContent);
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
                            jobAdapter.clearList();
                            SearchJobActivity.this.jobDataByKwPresenter.updateJobDataByKw(SearchContent);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void showJobDataByKw(List<JobBasicInfo> jobBasicInfoList) {
        jobAdapter.addListFromBottom(jobBasicInfoList);
        if (swpRefresh.isRefreshing()) {
            swpRefresh.setRefreshing(false);
        }
        LoadingMore = false;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (jobDataByKwPresenter != null){
            jobDataByKwPresenter.onDestroy();
            jobDataByKwPresenter = null;
            System.gc();
        }
    }
}
