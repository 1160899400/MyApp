package com.liu.jim.jobgo.helper;

import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.constants.SpKey;
import com.liu.jim.jobgo.util.PreferenceUtil;
import com.liu.jim.jobgo.util.TextUtils;

/**
 * @author Hongzhi.Liu
 * @date 2019/2/2
 */
public class LogginHelper {


    /**
     * 检查登录状态
     * 根据本地数据库是否存在用户信息以及token来校验
     * @return 用户是否在线
     */
    public static boolean isOnline() {
        return JobGoApplication.isOnline() && TextUtils.isEmpty(LogginHelper.getLastLoginToken());
    }

    /**
     * 获取上次登录的token
     * @return
     */
    public static String getLastLoginToken(){
        return PreferenceUtil.getInstance().getString(SpKey.LAST_LOGIN_TOKEN);
    }
}
