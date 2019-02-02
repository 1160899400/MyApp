package com.liu.jim.jobgo.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.liu.jim.jobgo.JobGoApplication;

/**
 * 退出登录时弹出的对话框
 */

public class LogoutTipDialog extends TipDialog {
    private ILogout logout;
    public LogoutTipDialog(Context context,ILogout logout) {
        super(context);
        this.logout = logout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setText();
        setOnclickListener();
    }

    private void setText(){
        super.setTitle("退出登录提示");
        super.setMessage("你真的要退出账号吗");
    }

    /**
     * 设置确定取消的点击事件
     */
    private void setOnclickListener(){
        super.setOnYesClickListener(new onYesOnclickListener() {
            @Override
            public void onYesClick() {
                LogoutTipDialog.this.logout.userLogout();
                Toast.makeText(JobGoApplication.getContext(),"已退出登录",Toast.LENGTH_SHORT).show();
                LogoutTipDialog.this.dismiss();
            }
        });
        super.setOnNoClickListener(new onNoOnclickListener() {
            @Override
            public void onNoClick() {
                LogoutTipDialog.this.dismiss();
            }
        });
    }

    public interface ILogout{
        public void userLogout();
    };
}
