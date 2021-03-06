package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jim on 2018/4/20.
 */

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    //跳过倒计时提示5秒
    private int TimeWel = 5;
    private TextView tv_skip;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);//延迟5S后发送handler信息

    }

    @Override
    protected void bindView() {
        tv_skip = findViewById(R.id.tv_skip);//跳过
        tv_skip.setOnClickListener(this);//跳过监听
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_welcome;
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    TimeWel--;
                    tv_skip.setText("跳 过 " + TimeWel);
                    if (TimeWel < 0) {
                        timer.cancel();
                        tv_skip.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    /**
     * 点击跳过
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}
