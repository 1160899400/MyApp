package com.liu.jim.jobgo.view.myview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liu.jim.jobgo.R;

/**
 * 自定义提醒框
 * 可以设置标题、内容、确认取消按钮的点击事件
 */

public class TipDialog extends Dialog implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_message;
    private Button btn_yes;
    private Button btn_no;
    private onYesOnclickListener yesOnclickListener;
    private onNoOnclickListener noOnclickListener;
    public TipDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        setCanceledOnTouchOutside(true);   //设置按阴影区域时消失
        bindView();
    }


    /**
     * 设置对话框标题和内容
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }
    public void setMessage(String message){
        tv_message.setText(message);
    }

    /**
     * 绑定控件
     */
    private void bindView(){
        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
        tv_title = findViewById(R.id.tv_dialog_title);
        tv_message = findViewById(R.id.tv_dialog_message);
        initBtnEvent();
    }


    /**
     * 初始化是和否按钮的监听器
     */
    private void initBtnEvent(){
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yes){
            if (this.yesOnclickListener != null){
                this.yesOnclickListener.onYesClick();
            }
        }
        if (v.getId() == R.id.btn_no){
            if (this.noOnclickListener != null){
                this.noOnclickListener.onNoClick();
            }
        }
    }

    /**
     * 确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    /**
     * 建立对话框时对yes和no按钮的点击事件的设立
     */
    public void setOnYesClickListener(onYesOnclickListener onYesClickListener){
        this.yesOnclickListener = onYesClickListener;
    }
    public void setOnNoClickListener(onNoOnclickListener onNoClickListener){
        this.noOnclickListener = onNoClickListener;
    }
}
