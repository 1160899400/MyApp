package com.liu.jim.jobgo.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.contract.job_info.JobDataHiringContract;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.job_info.JobDataHirPresenter;
import com.liu.jim.jobgo.view.activity.SearchJobActivity;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2018/3/6.
 */

public class JobHiringFragment extends Fragment implements JobDataHiringContract.IJobDataHirView {


    public View mView;
    public ListView mListView;
    public List<JobBasicInfo> mData;
    public SwipeRefreshLayout swpRefresh;
    private SearchView mSearchView;
    public JobAdapter jobAdapter;
    private JobDataHiringContract.IJobDataHirPresenter jobDataHirPresenter;

    public JobHiringFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mView = getActivity().getLayoutInflater().inflate(R.layout.fg_job_hiring, null, false);
        this.jobDataHirPresenter = new JobDataHirPresenter(this);
        bindView();
        initJobListArr();
        initSearchView();
        initRefreshLayout();
        setOnScrollListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.mView;
    }

    /**
     * 绑定控件
     */
    public void bindView() {
        mListView = mView.findViewById(R.id.job_list);
        swpRefresh = mView.findViewById(R.id.swipe_refresh_job_list);
        mSearchView = this.mView.findViewById(R.id.search_view);
    }

    /**
     * 设置搜索框searchView
     */
    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchJobActivity.class);
                intent.putExtra("SearchStr", query);
                startActivity(intent);
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
     * 获取正在招聘的工作
     */
    public void requestJobHiring() {
        jobDataHirPresenter.getJobDataHir();
    }

    /**
     * 设置下拉刷新效果
     */
    public void initRefreshLayout() {
        //设置RefreshLayout样式
        swpRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);  //改变加载显示的颜色
        swpRefresh.setBackgroundColor(Color.WHITE);         //设置背景颜色
        swpRefresh.setSize(SwipeRefreshLayout.DEFAULT);          //设置初始时的大小
        swpRefresh.setDistanceToTriggerSync(300);       //设置向下拉多少出现刷新
        swpRefresh.setProgressViewEndTarget(false, 200);        //设置刷新旋转出现的位置
        swpRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {           //设置SwipeRefreshLayout监听，监听是否下拉刷新
                if (swpRefresh.isRefreshing()) {        //检查是否处于刷新状态
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
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    View lastVisibleItemView = mListView.getChildAt(mListView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListView.getHeight()) {
                        NoticeManager.build(JobHiringFragment.this.getActivity()).setNoticeType(NoticeManager.NoticeType.Loading);
                        NoticeManager.build(JobHiringFragment.this.getActivity()).show();
                        requestJobHiring();
                    }
                }
            }
        });
    }


    /**
     * 初始化job数据，从缓存读取或请求网络
     */
    private void initJobListArr() {
        mData = new LinkedList<>();
        this.jobAdapter = new JobAdapter(getActivity(), mData);
        this.mListView.setAdapter(jobAdapter);
        requestJobHiring();
    }

    /**
     * 下拉刷新时更新数据
     */
    public void updateJobList() {
        jobAdapter.clearList();
        jobDataHirPresenter.refreshJobDataHir();
    }



    @Override
    public void showJobDataHir(List<JobBasicInfo> jobBasicInfoList) {
        jobAdapter.addListFromBottom(jobBasicInfoList);
        if (swpRefresh.isRefreshing()){
            swpRefresh.setRefreshing(false);
        }
        NoticeManager.build(JobHiringFragment.this.getActivity()).loadSuccess();
    }
}
