package com.liu.jim.jobgo.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author Hongzhi.Liu
 * @date 2019/1/28
 */
public class UIUtil {

    /**
     * 正常情况下1pt = 1px, 参见${@link android.util.TypedValue#applyDimension(int, float, DisplayMetrics)}
     * 这里通过设置dpi来改变pt到px的转换，因为所有的单位最终要转换为px来显示。
     * pt为长度单位，1pt = 1/72 inch，px代表像素个数
     *
     * @param resources
     * @return
     */
//    public static Resources setScreenDpi(Resources resources){
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//
//    }
}
