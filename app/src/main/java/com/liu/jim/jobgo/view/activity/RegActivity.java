package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.contract.user_auth.GetMsgContract;
import com.liu.jim.jobgo.contract.user_auth.RegContract;
import com.liu.jim.jobgo.entity.response.result.MessageResult;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.presenter.user_auth.GetMsgPresenter;
import com.liu.jim.jobgo.presenter.user_auth.RegPresenter;
import com.liu.jim.jobgo.util.Validator;

/**
 * 验证码登录/注册页面
 * reqType： 0 登录   1 注册
 * Created by lenovo on 2018/3/28.
 */

public class RegActivity extends AppCompatActivity implements View.OnClickListener, GetMsgContract.IGetMsgView, RegContract.IRegView {

    private int smsId;
    private String phone;
    private Toolbar mToolbar;
    private EditText et_phone;
    private EditText et_message;
    private EditText et_pwd;
    private static Button btn_getMessage;
    private Button btn_register;
    private static TimeCount timeCount;   //用于记录验证码发送事件
    private RegPresenter regPresenter;
    private GetMsgPresenter getMsgPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        timeCount = new TimeCount(60000, 1000);
        regPresenter = new RegPresenter(this);
        getMsgPresenter = new GetMsgPresenter(this);
        bindView();
        setToolbar();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("RegActivity", this);
    }


    /**
     * 绑定控件变量和资源id
     */
    public void bindView() {
        mToolbar = findViewById(R.id.toolbar);
        et_phone = findViewById(R.id.et_phone);
        et_message = findViewById(R.id.et_message);
        btn_getMessage = findViewById(R.id.btn_get_message);
        btn_getMessage.setOnClickListener(this);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        et_pwd = findViewById(R.id.et_pwd);
    }

    public void setToolbar() {
        setSupportActionBar(mToolbar);
    }

    /**
     * 重写监听返回按钮的点击事件,点击后结束当前activity，自动返回上一activity
     *
     * @param item 返回按钮代表的菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityManager actManager = ActivityManager.getActManager();
                actManager.destroyActivity("RegActivity");
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重写onClick方法，实现点击监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_message:
                phone = et_phone.getText().toString();
                if (Validator.getValidator().valPhone(phone)) {
                    timeCount.start();
                    requestMessage();
                } else {
                    Toast.makeText(this, "请输入正确格式的手机号", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_register:
                phone = et_phone.getText().toString();
                regPresenter.register(phone, et_pwd.getText().toString(), smsId, et_message.getText().toString());
                break;
        }
    }


    /**
     * 发送网络请求，请求验证码
     */
    private void requestMessage() {
        byte type = 1;
        getMsgPresenter.getMsg(phone, type);
    }


    @Override
    public void showRegResult(int resultCode) {
        if (resultCode == 0) {
            Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
            startActivity(intent);
            ActivityManager actManager = ActivityManager.getActManager();
            actManager.destroyActivity("RegActivity");
        }
    }

    @Override
    public void showGetMsg(MessageResult messageResult) {
        int code = messageResult.getResult().getCode();
        if (code == 0) {
            Toast.makeText(this, "获取验证码失败，该手机号已被注册", Toast.LENGTH_SHORT).show();
        } else {
            smsId = messageResult.getData().getSmsId();
            Toast.makeText(this, "已向您发送验证码，请注意查收", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * TimeCount内部类
     * 用于记录发送验证码时间
     */
    static class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_getMessage.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btn_getMessage.setClickable(false);
            btn_getMessage.setText(millisUntilFinished / 1000 + " 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            btn_getMessage.setText("重新获取验证码");
            btn_getMessage.setClickable(true);
            btn_getMessage.setBackgroundColor(Color.parseColor("#15e0de"));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (regPresenter != null) {
            regPresenter.onDestroy();
            regPresenter = null;
            System.gc();
        }
        if (getMsgPresenter != null) {
            getMsgPresenter.onDestroy();
            getMsgPresenter = null;
            System.gc();
        }
    }
}
