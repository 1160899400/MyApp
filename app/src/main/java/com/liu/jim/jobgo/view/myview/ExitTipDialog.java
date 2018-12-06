package com.liu.jim.jobgo.view.myview;



import android.content.Context;
import android.os.Bundle;

import com.liu.jim.jobgo.manager.ActivityManager;

/**
 * 退出app前弹出的对话框
 */

public class ExitTipDialog extends TipDialog {
    public ExitTipDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setText();
        setOnclickListener();
    }

    private void setText(){
        super.setTitle("退出提醒");
        super.setMessage("您确定要退出app吗");
    }

    /**
     * 设置确定取消的点击事件
     */
    private void setOnclickListener(){
        super.setOnYesClickListener(new onYesOnclickListener() {
            @Override
            public void onYesClick() {
                ExitTipDialog.this.dismiss();
                ActivityManager.getActManager().exitApp();
            }
        });
        super.setOnNoClickListener(new onNoOnclickListener() {
            @Override
            public void onNoClick() {
                ExitTipDialog.this.dismiss();
            }
        });
    }
}
