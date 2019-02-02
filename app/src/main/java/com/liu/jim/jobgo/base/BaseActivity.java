package com.liu.jim.jobgo.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author Jim.Liu
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 当前app是否在前台
     */
    private boolean inFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        bindView();
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    protected void onResume() {
        inFront = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        inFront = false;
        super.onPause();
    }

    /**
     * 绑定布局内的view资源id
     */
    protected abstract void bindView();

    /**
     * 返回当前ContentView的布局id
     * @return
     */
    protected abstract int getResourceId();

    /**
     * 初始化View中的Presenter
     */
//    protected abstract void initPresenter();


    /**
     * 调整pt到px的转换(通过设置dpi的值)完成屏幕的适配
     * @return
     */
//    @Override
//    public Resources getResources(){
//        return UIUtil.setScreenDpi(super.getResources());
//    }

    /**
     *
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (null != mCompositeDisposable){
            mCompositeDisposable.dispose();
        }
    }

    public boolean isInFront() {
        return inFront;
    }

}
