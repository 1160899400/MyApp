package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.contract.user_auth.GetMsgContract;
import com.liu.jim.jobgo.contract.user_auth.MsgLoginContract;
import com.liu.jim.jobgo.entity.response.result.MessageResult;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.presenter.user_auth.GetMsgPresenter;
import com.liu.jim.jobgo.presenter.user_auth.MsgLoginPresenter;
import com.liu.jim.jobgo.util.Validator;


/**
 * Created by jim on 2018/4/15.
 */

public class MsgLoginActivity extends AppCompatActivity implements View.OnClickListener, GetMsgContract.IGetMsgView, MsgLoginContract.IMsgLoginView {

    private int smsId;
    private String phone;
    private Toolbar mToolbar;
    private EditText et_phone;
    private EditText et_message;
    private static Button btn_getMessage;
    private Button btn_login;
    private static TimeCount timeCount;   //用于记录验证码发送时间
    private GetMsgPresenter getMsgPresenter;
    private MsgLoginPresenter msgLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_login);
        timeCount = new TimeCount(60000, 1000);
        getMsgPresenter = new GetMsgPresenter(this);
        msgLoginPresenter = new MsgLoginPresenter(this);
        bindView();
        setToolbar();
        ActivityManager.getActManager().addActivity("MsgLoginActivity", this);
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
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    public void setToolbar() {
        setSupportActionBar(mToolbar);
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
                    requestMessage();
                } else {
                    Toast.makeText(this, "请输入正确格式的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login:
                msgLoginPresenter.loginByMsg(et_phone.getText().toString(), et_message.getText().toString(), smsId);
                break;
        }
    }

    /**
     * 发送网络请求，请求验证码
     */
    private void requestMessage() {
        timeCount.start();   //倒计时开始
        byte bt = 0;
        getMsgPresenter.getMsg(phone, bt);
    }



    @Override
    public void showGetMsg(MessageResult messageResult) {
        int code = messageResult.getResult().getCode();
        if (code == 0) {
            Toast.makeText(this, "验证码发送失败", Toast.LENGTH_SHORT).show();
        } else {
            smsId = messageResult.getData().getSmsId();
            Toast.makeText(this, "已向您发送验证码，请注意查收", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoginResult(int resultCode) {
        if (resultCode == 1){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            ActivityManager activityManager = ActivityManager.getActManager();
            activityManager.destroyActivity("MainActivity");
            Intent intent = new Intent(MsgLoginActivity.this, MainActivity.class);
            startActivity(intent);
            activityManager.destroyActivity("MsgLoginActivity");
        }else if (resultCode == 2){

        }else {
            Toast.makeText(this, "手机号未注册和密码错误", Toast.LENGTH_SHORT).show();
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
        if (getMsgPresenter != null) {
            getMsgPresenter.onDestroy();
            getMsgPresenter = null;
            System.gc();
        }
        if (msgLoginPresenter != null){
            msgLoginPresenter.onDestroy();
            msgLoginPresenter = null;
            System.gc();
        }
    }

}
