package com.liu.jim.jobgo.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import io.reactivex.disposables.CompositeDisposable;


/**
 * @author Jim.Liu
 */

public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner {

    protected LifecycleRegistry mLifecycleRegistry;

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 当前app是否在前台
     */
    private boolean inFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleRegistry = new LifecycleRegistry(this);
        setContentView(getResourceId());
        bindView();
        mCompositeDisposable = new CompositeDisposable();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }


    @Override
    protected void onResume() {
        super.onResume();
        inFront = true;
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        inFront = false;
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    /**
     * 绑定布局内的view资源id
     */
    protected abstract void bindView();

    /**
     * 返回当前ContentView的布局id
     *
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
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
        }
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public boolean isInFront() {
        return inFront;
    }

}
