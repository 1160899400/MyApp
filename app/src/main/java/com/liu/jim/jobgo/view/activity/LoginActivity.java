package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseActivity;
import com.liu.jim.jobgo.contract.user_auth.PwdLoginContract;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.user_auth.PwdLoginPresenter;
import com.liu.jim.jobgo.util.Validator;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


/**
 * Created by jim on 2018/3/8.
 */

public class LoginActivity extends BaseActivity implements PwdLoginContract.IPwdLoginView {
    private Toolbar mToolbar;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnLoginMessage;
    private EditText etPhone;
    private EditText etPwd;
    private PwdLoginPresenter pwdLoginPresenter;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pwdLoginPresenter = new PwdLoginPresenter(this);
        setToolbar();
        initBtnClickListener();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("LoginActivity", this);
    }


    @Override
    protected void bindView() {
        mToolbar = findViewById(R.id.toolbar);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        etPhone = findViewById(R.id.et_phone);
        etPwd = findViewById(R.id.et_pwd);
        btnLoginMessage = findViewById(R.id.btn_login_message);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_pwd_login;
    }


    /**
     * 设置actionbar
     */
    public void setToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }


    /**
     * 重写监听toolbar返回按钮的点击事件
     *
     * @param item 返回按钮代表的菜单项,点击后结束当前activity，自动返回上一activity
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化按钮点击事件
     */
    private void initBtnClickListener() {
        OnClickListener onClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_login:
                        clickLogin();
                        break;
                    case R.id.btn_register:
                        clickRegister();
                        break;
                    case R.id.btn_login_message:
                        clickMessage();
                        break;

                }
            }
        };
        btnLogin.setOnClickListener(onClickListener);
        btnRegister.setOnClickListener(onClickListener);
        btnLoginMessage.setOnClickListener(onClickListener);

    }

    /**
     * 点击登录按钮执行的操作
     */
    private void clickLogin() {
        String usernameStr = etPhone.getText().toString();
        String pwdStr = etPwd.getText().toString();
        if (validate(usernameStr, pwdStr)) {
            NoticeManager.build(this).setNoticeType(NoticeManager.NoticeType.Login);
            NoticeManager.build(this).show();
            requestLoginByPwd(usernameStr, pwdStr);
        } else {
            Toast.makeText(this, "请输入正确格式的账号密码", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 登录时发送请求
     */
    private void requestLoginByPwd(String phone, String pwd) {
        pwdLoginPresenter.loginByPwd(phone, pwd);
    }

    /**
     * 点击注册按钮执行的操作,转入注册页面
     * reqType为1，表示注册请求
     */
    private void clickRegister() {
        Intent it = new Intent(LoginActivity.this, RegActivity.class);
        startActivity(it);
    }

    /**
     * 判断手机号格式是否为11位数字，密码不超过14位
     *
     * @return true为正确
     */
    private boolean validate(String usernameStr, String pwdStr) {
        Validator validator = Validator.getValidator();
        return validator.valLogin(usernameStr, pwdStr);
    }

    /**
     * 点击短信登录后进行的操作
     * reqType为0，表示登录请求
     */
    private void clickMessage() {
        Intent it = new Intent(this, MsgLoginActivity.class);
        startActivity(it);
    }

    /**
     * presenter返回登录结果，view进行展示
     */
    @Override
    public void showLoginResult(int resultCode) {
        if (resultCode == 1) {
            NoticeManager.build(this).loadSuccess();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            ActivityManager actManager = ActivityManager.getActManager();               //跳转acticity
            actManager.destroyActivity("MainActivity");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            actManager.destroyActivity("LoginActivity");
        }else if (resultCode == 2){     //网络连接断开
            NoticeManager.build(this).loadFail();
        }
        else {
            NoticeManager.build(this).loadFail();
            Toast.makeText(this, "手机号未注册或账号密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pwdLoginPresenter != null){
            pwdLoginPresenter.onDestroy();
            pwdLoginPresenter = null;
            System.gc();
        }
    }
}
