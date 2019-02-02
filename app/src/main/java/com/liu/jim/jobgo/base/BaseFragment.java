package com.liu.jim.jobgo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Hongzhi.Liu
 * @date 2019/1/28
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    protected Context mContext;

    protected View mContentView;


    /**
     * if this app is running on a device with API 23 (marshmallow) below, then ${@link Fragment#onAttach(Activity)} will be called.
     * Otherwise, ${@link Fragment#onAttach(Context)} will be called.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onFragmentAttached(activity);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentAttached(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getResourceId(), container, false);
        bindView();
        return mContentView;
    }

    /**
     * 返回布局资源id
     *
     * @return
     */
    protected abstract int getResourceId();

    /**
     * 初始化view
     */
    protected abstract void bindView();

    protected void onFragmentAttached(Context context) {
        mContext = context;
        mActivity = (BaseActivity) getActivity();
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    /**
     * 判断当前Fragment是否正在显示 （所属Activity在前台，且该activity没有隐藏）
     */
    public boolean isShowing() {
        return mActivity.isInFront() && !isHidden();
    }


}
