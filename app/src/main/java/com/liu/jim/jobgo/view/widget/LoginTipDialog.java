package com.liu.jim.jobgo.view.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.view.activity.LoginActivity;

/**
 * 登录提示框
 */

public class LoginTipDialog extends TipDialog {

    public LoginTipDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setText();
        setOnclickListener();
    }

    private void setText(){
        super.setTitle("登录提醒");
        super.setMessage("您还没登录，是否前去登录");
    }

    /**
     * 设置确定取消的点击事件
     */
    private void setOnclickListener(){
        super.setOnYesClickListener(new onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent = new Intent(MyApplication.getContext(),LoginActivity.class);
                MyApplication.getContext().startActivity(intent);
                LoginTipDialog.this.dismiss();
            }
        });
        super.setOnNoClickListener(new onNoOnclickListener() {
            @Override
            public void onNoClick() {
                LoginTipDialog.this.dismiss();
            }
        });
    }
}
