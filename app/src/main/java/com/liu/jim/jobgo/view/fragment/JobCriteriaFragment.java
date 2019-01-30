package com.liu.jim.jobgo.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseFragment;
import com.liu.jim.jobgo.contract.job_info.JobDataByCrContract;
import com.liu.jim.jobgo.entity.response.bean.Area;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.bean.Screen;
import com.liu.jim.jobgo.presenter.job_info.JobDataByCrPresenter;
import com.liu.jim.jobgo.util.CriteriaUtil;
import com.liu.jim.jobgo.view.adapter.list_view_adapter.JobAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jim on 2018/3/6.
 */

public class JobCriteriaFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, JobDataByCrContract.IJobDataByCrView {
    private View topFilter;
    private View popupScreenView;
    private PopupWindow popupWndScreen;
    public ListView mListView;
    public List<JobBasicInfo> mData;
    public SwipeRefreshLayout swpRefresh;
    public Activity context;
    private JobAdapter jobAdapter;
    private boolean loadingMore = false;

    private AppCompatSpinner spinnerCity;

    private CheckBox ckScreen;
    private CheckBox[] ckJobType = new CheckBox[20];
    private CheckBox[] ckGender = new CheckBox[3];
    private CheckBox[] ckPayType = new CheckBox[5];

    private List<String> jobTypes;
    private Area area = new Area();
    private Screen screen = new Screen();

    private JobDataByCrContract.IJobDataByCrPresenter jobDataByCrPresenter;
    private boolean firstChoose = true;


    public JobCriteriaFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.jobDataByCrPresenter = new JobDataByCrPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initSwipeRefresh();
        initPopWndScreen();
        initListView();
        setOnScrollListener();
        return mContentView;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fg_job_cirterial;
    }


    @Override
    public void bindView() {
        popupScreenView = mActivity.getLayoutInflater().inflate(R.layout.popup_screen, null, false);
        mListView = mContentView.findViewById(R.id.job_list);
        swpRefresh = mContentView.findViewById(R.id.swipe_refresh_job_list);
        spinnerCity = mContentView.findViewById(R.id.spinner_city);
        spinnerCity.setOnItemSelectedListener(this);
        ckScreen = mContentView.findViewById(R.id.ck_screen);
        ckScreen.setOnCheckedChangeListener(this);
        ckJobType[0] = this.popupScreenView.findViewById(R.id.ck_screen_job_type_all);
        ckJobType[1] = this.popupScreenView.findViewById(R.id.ck_screen_job_type1);
        ckJobType[2] = this.popupScreenView.findViewById(R.id.ck_screen_job_type2);
        ckJobType[3] = this.popupScreenView.findViewById(R.id.ck_screen_job_type3);
        ckJobType[4] = this.popupScreenView.findViewById(R.id.ck_screen_job_type4);
        ckJobType[5] = this.popupScreenView.findViewById(R.id.ck_screen_job_type5);
        ckJobType[6] = this.popupScreenView.findViewById(R.id.ck_screen_job_type6);
        ckJobType[7] = this.popupScreenView.findViewById(R.id.ck_screen_job_type7);
        ckJobType[8] = this.popupScreenView.findViewById(R.id.ck_screen_job_type8);
        ckJobType[9] = this.popupScreenView.findViewById(R.id.ck_screen_job_type9);
        ckJobType[10] = this.popupScreenView.findViewById(R.id.ck_screen_job_type10);
        ckJobType[11] = this.popupScreenView.findViewById(R.id.ck_screen_job_type11);
        ckJobType[12] = this.popupScreenView.findViewById(R.id.ck_screen_job_type12);
        ckJobType[13] = this.popupScreenView.findViewById(R.id.ck_screen_job_type13);
        ckJobType[14] = this.popupScreenView.findViewById(R.id.ck_screen_job_type14);
        ckJobType[15] = this.popupScreenView.findViewById(R.id.ck_screen_job_type15);
        ckJobType[16] = this.popupScreenView.findViewById(R.id.ck_screen_job_type16);
        ckJobType[17] = this.popupScreenView.findViewById(R.id.ck_screen_job_type17);
        ckJobType[18] = this.popupScreenView.findViewById(R.id.ck_screen_job_type18);
        ckJobType[19] = this.popupScreenView.findViewById(R.id.ck_screen_job_type19);
        ckGender[0] = this.popupScreenView.findViewById(R.id.ck_screen_job_gender_all);
        ckGender[1] = this.popupScreenView.findViewById(R.id.ck_screen_job_gender_man);
        ckGender[2] = this.popupScreenView.findViewById(R.id.ck_screen_job_gender_woman);
        ckPayType[0] = this.popupScreenView.findViewById(R.id.ck_screen_pay_type_all);
        ckPayType[1] = this.popupScreenView.findViewById(R.id.ck_screen_pay_type_day);
        ckPayType[2] = this.popupScreenView.findViewById(R.id.ck_screen_pay_type_week);
        ckPayType[3] = this.popupScreenView.findViewById(R.id.ck_screen_pay_type_half_month);
        ckPayType[4] = this.popupScreenView.findViewById(R.id.ck_screen_pay_type_month);
        for (int i = 0; i < ckJobType.length; i++) {
            ckJobType[i].setOnCheckedChangeListener(this);
        }
        for (int i = 0; i < ckGender.length; i++) {
            ckGender[i].setOnCheckedChangeListener(this);
        }
        for (int i = 0; i < ckPayType.length; i++) {
            ckPayType[i].setOnCheckedChangeListener(this);
        }
        Button btnStartScreen = this.popupScreenView.findViewById(R.id.btn_start_screen);
        Button btnResetScreen = this.popupScreenView.findViewById(R.id.btn_reset_screen);
        btnStartScreen.setOnClickListener(this);
        btnResetScreen.setOnClickListener(this);
        topFilter = mContentView.findViewById(R.id.ll_filter);
    }

    private void initPopWndScreen() {
        popupWndScreen = new PopupWindow(popupScreenView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWndScreen.setAnimationStyle(R.style.PopupWndScreen);
        popupWndScreen.setFocusable(true);
        //设置屏幕外点击无效，即不能通过点击外面使popupwindow消失
        popupWndScreen.setOutsideTouchable(true);
        popupWndScreen.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ckScreen.setChecked(false);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_screen) {
            if (isChecked) {
                popupWndScreen.showAsDropDown(topFilter);
            } else {
                popupWndScreen.dismiss();
            }
        }
        switch (buttonView.getId()) {
            case R.id.ck_screen_job_type_all:
                if (isChecked) {
                    for (int i = 1; i < ckJobType.length; i++) {
                        if (ckJobType[i].isChecked()) {
                            ckJobType[i].setChecked(false);
                        }
                    }
                }
                break;
            case R.id.ck_screen_job_type1:
            case R.id.ck_screen_job_type2:
            case R.id.ck_screen_job_type3:
            case R.id.ck_screen_job_type4:
            case R.id.ck_screen_job_type5:
            case R.id.ck_screen_job_type6:
            case R.id.ck_screen_job_type7:
            case R.id.ck_screen_job_type8:
            case R.id.ck_screen_job_type9:
            case R.id.ck_screen_job_type10:
            case R.id.ck_screen_job_type11:
            case R.id.ck_screen_job_type12:
            case R.id.ck_screen_job_type13:
            case R.id.ck_screen_job_type14:
            case R.id.ck_screen_job_type15:
            case R.id.ck_screen_job_type16:
            case R.id.ck_screen_job_type17:
            case R.id.ck_screen_job_type18:
            case R.id.ck_screen_job_type19:
                if (isChecked) {                 //当有checkbox被选择时，取消全部职位的选中状态
                    ckJobType[0].setChecked(false);
                }
                break;

            case R.id.ck_screen_job_gender_all:
                if (isChecked) {
                    ckGender[1].setChecked(false);
                    ckGender[2].setChecked(false);
                }
                break;
            case R.id.ck_screen_job_gender_man:
                if (isChecked) {
                    ckGender[0].setChecked(false);
                    ckGender[2].setChecked(false);
                }
                break;
            case R.id.ck_screen_job_gender_woman:
                if (isChecked) {
                    ckGender[0].setChecked(false);
                    ckGender[1].setChecked(false);
                }
                break;
            case R.id.ck_screen_pay_type_all:
                if (isChecked) {
                    ckPayType[1].setChecked(false);
                    ckPayType[2].setChecked(false);
                    ckPayType[3].setChecked(false);
                    ckPayType[4].setChecked(false);
                }
                break;
            case R.id.ck_screen_pay_type_day:
                if (isChecked) {
                    ckPayType[0].setChecked(false);
                    ckPayType[2].setChecked(false);
                    ckPayType[3].setChecked(false);
                    ckPayType[4].setChecked(false);
                }
                break;
            case R.id.ck_screen_pay_type_week:
                if (isChecked) {
                    ckPayType[0].setChecked(false);
                    ckPayType[1].setChecked(false);
                    ckPayType[3].setChecked(false);
                    ckPayType[4].setChecked(false);
                }
                break;
            case R.id.ck_screen_pay_type_half_month:
                if (isChecked) {
                    ckPayType[0].setChecked(false);
                    ckPayType[1].setChecked(false);
                    ckPayType[2].setChecked(false);
                    ckPayType[4].setChecked(false);
                }
                break;
            case R.id.ck_screen_pay_type_month:
                if (isChecked) {
                    ckPayType[0].setChecked(false);
                    ckPayType[1].setChecked(false);
                    ckPayType[2].setChecked(false);
                    ckPayType[3].setChecked(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_screen:
                startScreen();
                ckScreen.setChecked(false);    //开始筛选并隐藏筛选菜单
                break;
            case R.id.btn_reset_screen:
                resetScreen();
                ckScreen.setChecked(false);
                break;
            default:
                break;
        }
    }

    /**
     * 开始根据各CheckBox状态 收集 筛选条件
     */
    private void initScreenState() {
        CriteriaUtil cu = new CriteriaUtil();           //筛选条件的格式转换工具
        if (ckJobType[0].isChecked()) {     //选中了全部时，直接设为null
            jobTypes = null;
        } else {                            //没有选择全部岗位
            jobTypes = new ArrayList<>();
            for (int i = 1; i < ckJobType.length; i++) {
                if (ckJobType[i].isChecked()) {      //将选择的jobType放入工作类型条件数组中
                    jobTypes.add(cu.getWorkTypeStr(i));
                }
            }
            if (jobTypes.size() == 0) {          //如果一个条件都没选，则默认为选择所有职位
                jobTypes = null;
                ckJobType[0].setChecked(true);     //ckeckbox条件设为全部
            }
        }
        String cityCode = cu.getCityCode(spinnerCity.getSelectedItemPosition());       //设置筛选地区
        area.setCity(cityCode);
        String gender;             //性别
        if (ckGender[1].isChecked()) {      //选择了仅限男性
            gender = "男性";
        } else if (ckGender[2].isChecked()) {     //选择了仅限女性
            gender = "女性";
        } else {
            gender = null;
            ckGender[0].setChecked(true);
        }
        screen.setGender(gender);
        String payType;
        if (ckPayType[1].isChecked()) {
            payType = "日结";
        } else if (ckPayType[2].isChecked()) {
            payType = "周结";
        } else if (ckPayType[3].isChecked()) {
            payType = "半月结";
        } else if (ckPayType[4].isChecked()) {
            payType = "月结";
        } else {
            payType = null;
            ckPayType[0].setChecked(true);
        }
        screen.setPayType(payType);
    }

    /**
     * 开始发送请求查询jobList
     */
    private void startScreen() {
        jobAdapter.clearList();     //更换条件筛选前清空list内的数据源
        initScreenState();   //收集筛选条件
        jobDataByCrPresenter.startScreen(jobTypes, area, screen);
    }

    /**
     * 重置所有的筛选条件（不包括city），并查询jobList
     */
    private void resetScreen() {
        ckJobType[0].setChecked(true);
        ckGender[0].setChecked(true);
        ckPayType[0].setChecked(true);
        startScreen();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //判断是否为进入页面时的首次选择
        if (!firstChoose) {
            switch (parent.getId()) {
                case R.id.spinner_city:
                    startScreen();
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    /**
     * 进入页面时初始化listView，设置listView适配器，查询内容
     */
    private void initListView() {
        mData = new ArrayList<>();
        jobAdapter = new JobAdapter(getActivity(), mData);
        mListView.setAdapter(jobAdapter);
        startScreen();
        firstChoose = false;
    }

    /**
     * 初始化刷新布局RefreshLayout
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
     * 更新joblist
     */
    private void updateJobList() {
        jobAdapter.clearList();
        jobDataByCrPresenter.refreshList(jobTypes, area, screen);
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
                        if (view.getLastVisiblePosition() == (view.getCount() - 1) && !loadingMore) {       // 判断滚动到底部且是否正在加载
                            loadingMore = true;
                            jobDataByCrPresenter.LoadMore(jobTypes, area, screen);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void showJobDataByCr(List<JobBasicInfo> jbi) {
        jobAdapter.addListFromBottom(jbi);
        if (swpRefresh.isRefreshing()) {
            swpRefresh.setRefreshing(false);
        }
        loadingMore = false;
    }

    /**
     * 通知回收Presenter,防止内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jobDataByCrPresenter != null) {
            jobDataByCrPresenter.onDestroy();
            jobDataByCrPresenter = null;
            System.gc();
        }
    }
}
