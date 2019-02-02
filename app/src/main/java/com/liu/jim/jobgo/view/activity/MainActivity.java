package com.liu.jim.jobgo.view.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseActivity;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.view.adapter.fragment.MyFragmentPagerAdapter;
import com.liu.jim.jobgo.view.widget.ExitTipDialog;
import com.liu.jim.jobgo.view.widget.LoginTipDialog;
import com.liu.jim.jobgo.view.widget.LogoutTipDialog;
import com.liu.jim.jobgo.view.widget.NoScrollViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * app的主页面，使用ViewPager+fragment展示不同页面的内容
 * 分为3个fragment实现
 */

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private TextView toolbarTitle;
    private Toolbar mtoolbar;
    private CircleImageView mAvadar;        //显示头像
    private TextView mName;             //显示姓名
    private TextView mEmail;            //显示邮箱
    private DrawerLayout mdrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    private NoScrollViewPager viewPager;
    private MyFragmentPagerAdapter mFrPagerAdp;

    //代表页面状态的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        checkLoginStatus();
        setToolbar();
        setViewPagerAdp();
        addDrawerToggle();
        setLeftNavigationView();
        setBottomNavigationView();
        setPerInfo();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("MainActivity", this);

    }

    @Override
    protected void bindView() {
        toolbarTitle = findViewById(R.id.tv_toolbar_title);
        mtoolbar = findViewById(R.id.toolbar);
        mtoolbar.setTitle("");
        mdrawerLayout = findViewById(R.id.drawerlayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mAvadar = mNavigationView.getHeaderView(0).findViewById(R.id.avatar);
        mAvadar.setOnClickListener(this);
        mName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        mEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_user_email);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }


    /**
     * 进入应用时检查登录状态
     * 读取Cache查看登录状态
     */
    private void checkLoginStatus() {
        ACache aCache = ACache.get(this);
        String Token = aCache.getAsString(CacheConstants.LOGIN_TOKEN);
        if (Token == null) {
            AppConstants.LOGIN_STATUS = false;
        } else {
            AppConstants.LOGIN_STATUS = true;
        }
    }

    /**
     * 设置actionbar
     */
    public void setToolbar() {
        this.setSupportActionBar(mtoolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * 设置头像\昵称\邮箱等个人信息
     */
    private void setPerInfo() {
        Bitmap avatar = CacheManager.getCacheManager().getAvatar(this);
        String name = CacheManager.getCacheManager().getAccountName();
        String email = CacheManager.getCacheManager().getAccountEmail();
        if (AppConstants.LOGIN_STATUS) {
            if (avatar != null) {
                mAvadar.setImageBitmap(avatar);
            } else {
                mAvadar.setImageResource(R.mipmap.app_icon);
            }
            if (name == null) {
                mName.setText("请设置姓名");
            } else {
                mName.setText("您好，" + name);
            }
            if (email == null) {
                mEmail.setText("还未设置邮箱");
            } else {
                mEmail.setText(email);
            }
        } else {
            mAvadar.setImageResource(R.mipmap.app_icon);
            mName.setText("您好，请点击上方头像登录");
            mEmail.setVisibility(View.GONE);
        }
    }

    //给左侧导航页面drawelayout设置ActionBarDrawerToggle监听侧滑事件
    public void addDrawerToggle() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mdrawerLayout, mtoolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();//初始化状态
        mdrawerLayout.addDrawerListener(mDrawerToggle);
    }


    //设置左侧导航栏监听事件
    public void setLeftNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent it;
                switch (menuItem.getItemId()) {
                    case R.id.nav_homepage:
                        onPageSelected(0);
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_personal_center:
                        if (!AppConstants.LOGIN_STATUS) {
                            LoginTipDialog loginTipDialog = new LoginTipDialog(MainActivity.this);
                            loginTipDialog.show();
                        } else {
                            it = new Intent(MainActivity.this, PerCenterActivity.class);
                            startActivity(it);
                            ActivityManager.getActManager().destroyActivity("MainActivity");
                            finish();
                        }
                        break;
                    case R.id.nav_jobApplied:   //查看我报名的工作
                        if (!AppConstants.LOGIN_STATUS) {
                            LoginTipDialog loginTipDialog = new LoginTipDialog(MainActivity.this);
                            loginTipDialog.show();
                        } else {
                            it = new Intent(MainActivity.this, JobSignedActivity.class);
                            startActivity(it);
                        }
                        break;
                    case R.id.nav_exit:
                        if (!AppConstants.LOGIN_STATUS) {
                            ExitTipDialog exitTipDialog = new ExitTipDialog(MainActivity.this);
                            exitTipDialog.show();
                        } else {
                            LogoutTipDialog logoutTipDialog = new LogoutTipDialog(MainActivity.this, new LogoutTipDialog.ILogout() {
                                @Override
                                public void userLogout() {
                                    ACache aCache = ACache.get(JobGoApplication.getContext());       //清空缓存 token和personalinfo
                                    aCache.remove(CacheConstants.LOGIN_TOKEN);
                                    aCache.remove(CacheConstants.PERSONAL_INFO);
                                    AppConstants.LOGIN_STATUS = false;
                                    setPerInfo();
                                }
                            });
                            logoutTipDialog.show();
                        }
                        break;
                    default:
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mdrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    /**
     * 用户点击退出登录后触发
     */
    public void uerExit() {

    }

    //设置viewPager适配器和fragment适配器
    public void setViewPagerAdp() {
        mFrPagerAdp = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mFrPagerAdp);
        viewPager.addOnPageChangeListener(this);
    }


    //设置底部导航栏bottom_navigationview的点击事件
    public void setBottomNavigationView() {
        mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_homepage);       //设置默认首页展示
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_homepage:
                        viewPager.setCurrentItem(PAGE_ONE);
                        return true;
                    case R.id.bottom_navigation_job_screen:
                        viewPager.setCurrentItem(PAGE_TWO);
                        return true;
                    case R.id.bottom_navigation_job_hiring:
                        viewPager.setCurrentItem(PAGE_THREE);
                        return true;
                    default:
                        break;
                }
                return false;
            }

        });
    }

    /**
     * 监听点击事件
     * 主要监听头像的点击事件
     * 未登录时点击跳转到登录页面,requestcode为0
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.avatar) {
            if (!AppConstants.LOGIN_STATUS) {   //如果没有登录，点击头像登录
                Intent it = new Intent(this, LoginActivity.class);
                startActivity(it);
            }
        }
    }

    //重写viewpager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case PAGE_ONE:
                mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_homepage);
                toolbarTitle.setText("主页");
                break;
            case PAGE_TWO:
                mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_job_screen);
                toolbarTitle.setText("精选");
                break;
            case PAGE_THREE:
                mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_job_hiring);
                toolbarTitle.setText("热招中");
                break;
            default:
                mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_homepage);
                toolbarTitle.setText("主页");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitTipDialog exitTipDialog = new ExitTipDialog(this);
            exitTipDialog.show();
        }
        return true;
    }
}
