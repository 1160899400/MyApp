package com.example.lenovo.myapp.manager;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2018/4/4.
 */

public class ActivityManager {
    private Map<String, Activity> ActManager = new HashMap<>();
    private static ActivityManager activityManager;

    private ActivityManager() {
    }

    /**
     * 获取该类的单一实例
     */
    public static ActivityManager getActManager() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public void addActivity(String activityName, Activity activity) {
        ActManager.put(activityName, activity);
    }


    /**
     * 销毁指定Activity
     */
    public void destroyActivity(String activityName) {
        Set<String> keySet = ActManager.keySet();
        for (String key : keySet) {
            ActManager.get(key).finish();
        }
        ActManager.remove(activityName);
    }

    public void exitApp() {
        try {
            for (Map.Entry<String, Activity> stringActivityEntry : ActManager.entrySet()) {
                Map.Entry entry = (Map.Entry) stringActivityEntry;
                Activity val = (Activity) entry.getValue();
                val.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
