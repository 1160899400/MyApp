package com.liu.jim.jobgo.view.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.liu.jim.jobgo.view.activity.MainActivity;
import com.liu.jim.jobgo.view.fragments.HomepageFragment;
import com.liu.jim.jobgo.view.fragments.JobCriteriaFragment;
import com.liu.jim.jobgo.view.fragments.JobHiringFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private HomepageFragment hpFr = null;
    private JobCriteriaFragment messFr = null;
    private JobHiringFragment myJobFr = null;

    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        hpFr = new HomepageFragment();
        messFr = new JobCriteriaFragment();
        myJobFr = new JobHiringFragment();
    }


    @Override
    public int getCount(){
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = hpFr;
                break;
            case MainActivity.PAGE_TWO:
                fragment = messFr;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myJobFr;
                break;
        }
        return fragment;
    }

}
