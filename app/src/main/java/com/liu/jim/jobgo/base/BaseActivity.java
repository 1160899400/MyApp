package com.liu.jim.jobgo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author Jim.Liu
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        bindView();
        mCompositeDisposable = new CompositeDisposable();
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
     *
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (null != mCompositeDisposable){
            mCompositeDisposable.dispose();
        }
    }

}
