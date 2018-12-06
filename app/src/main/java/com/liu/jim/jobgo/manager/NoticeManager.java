package com.liu.jim.jobgo.manager;

import android.app.Activity;

import com.liu.jim.jobgo.R;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

/**
 * LoadingDialog参考  https://github.com/ForgetAll/LoadingDialog
 */

public class NoticeManager {
    public enum NoticeType {
        Login, Register, Loading, Applying
    }

    private static NoticeManager noticeManager;
    private LoadingDialog loadingDialog;

    private NoticeManager() {
    }

    private NoticeManager(Activity activity) {
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.setDrawColor(activity.getResources().getColor(R.color.color_onselect))
                .setInterceptBack(true)
                .closeSuccessAnim()
                .closeFailedAnim()
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .setShowTime(100);
    }

    /**
     * 获取提示框实例
     *
     * @return
     */
    public static NoticeManager build(Activity activity) {
        if (noticeManager == null) {
            noticeManager = new NoticeManager(activity);
        }
        return noticeManager;
    }

    /**
     * 获取提示框类型
     *
     * @param noticeType 0为加载，1为注册，2为登录
     */
    public void setNoticeType(NoticeType noticeType) {
        switch (noticeType) {
            case Login:
                loadingDialog.setLoadingText("正在登录")
                        .setRepeatCount(0)
                        .setSuccessText("登录成功")
                        .setFailedText("登录失败");
                break;
            case Register:
                loadingDialog.setLoadingText("正在注册")
                        .setRepeatCount(0)
                        .setSuccessText("注册成功")
                        .setFailedText("注册失败");
                break;
            case Loading:
                loadingDialog.setLoadingText("加载中")
                        .setRepeatCount(0)
                        .setSuccessText("加载成功")
                        .setFailedText("加载失败");
                break;
            case Applying:
                loadingDialog.setLoadingText("报名中")
                        .setSuccessText("报名成功")
                        .setFailedText("报名失败")
                        .setRepeatCount(0);
                break;
        }

    }

    public void show() {
        loadingDialog.show();
    }

    public void loadSuccess() {
        loadingDialog.loadSuccess();
        noticeManager = null;
        loadingDialog = null;
    }

    public void loadFail() {
        loadingDialog.loadFailed();
        noticeManager = null;
        loadingDialog = null;
    }

    public void close() {
        loadingDialog.close();
        noticeManager = null;
        loadingDialog = null;
    }

}
