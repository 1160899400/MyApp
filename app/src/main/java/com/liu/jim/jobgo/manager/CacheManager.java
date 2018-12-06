package com.liu.jim.jobgo.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.bean.Account;
import com.liu.jim.jobgo.entity.response.bean.LoginPersonalInfo;
import com.liu.jim.jobgo.util.ACache;

/**
 * Created by lenovo on 2018/4/8.
 */

public class CacheManager {
    private static CacheManager cacheManager;
    private ACache aCache;

    private CacheManager() {
    }

    /**
     * 获取该类的单一实例
     */
    public static CacheManager getCacheManager(){
        if(cacheManager ==null){
            cacheManager =new CacheManager();
        }
        return cacheManager;
    }



    /**
     * 获取token
     * @param ctx
     * @return
     */
    public String getToken(Context ctx){
        aCache = ACache.get(ctx);
        return  aCache.getAsString(CacheConstants.LOGIN_TOKEN);
    }

    /**
     * 获取用户id
     * @param ctx
     * @return
     */
    public int getAccountId(Context ctx){
        aCache = ACache.get(ctx);
        ModifyInfoRequest mir = new Gson().fromJson(aCache.getAsString(CacheConstants.PERSONAL_INFO),ModifyInfoRequest.class);
        if (mir == null){
            return 0;
        }
        return mir.getAccountId();
    }

    /**
     * 获取头像图片
     * @param context
     * @return
     */
    public Bitmap getAvatar(Context context){
        aCache = ACache.get(context);
        ModifyInfoRequest personalInfo = new Gson().fromJson(aCache.getAsString(CacheConstants.PERSONAL_INFO),ModifyInfoRequest.class);
        if (personalInfo == null){
            return null;
        } else if (personalInfo.getApplicant() == null || personalInfo.getApplicant().getAppPhotolink() == null){
            return null;
        }
        String bitmapName = personalInfo.getApplicant().getAppPhotolink();
        if (bitmapName == null){
            return null;
        }
        return ACache.get(context,CacheConstants.DIR_AVATAR).getAsBitmap(bitmapName);
    }

    /**
     * 展示个人信息时调用，获取手机号
     */
    public String getAccountPhone(){
        aCache = ACache.get(MyApplication.getContext());
        return aCache.getAsString(CacheConstants.PHONE);
    }

    /**
     *  主页面展示用户名时调用
     */
    public String getAccountName(){
        aCache = ACache.get(MyApplication.getContext());
        ModifyInfoRequest mir = new Gson().fromJson(aCache.getAsString(CacheConstants.PERSONAL_INFO),ModifyInfoRequest.class);
        if (mir == null || mir.getApplicant() == null){
            return null;
        }
        return mir.getApplicant().getAppRealname();
    }

    /**
     *  主页面展示邮箱时调用
     */
    public String getAccountEmail(){
        aCache = ACache.get(MyApplication.getContext());
        ModifyInfoRequest mir = new Gson().fromJson(aCache.getAsString(CacheConstants.PERSONAL_INFO),ModifyInfoRequest.class);
        if (mir == null || mir.getApplicant() == null){
            return null;
        }
        return mir.getAccount().getAccountEmail();
    }


    /**
     * 登录完成后调用,将返回的个人信息修改为另一实体放入缓存(同步)
     * 将个人信息及手机号码（账号）放入缓存
     * @param lpi 登录返回的个人信息实体（包含手机号）
     */
    public void putPersonalInfo(LoginPersonalInfo lpi){
        aCache = ACache.get(MyApplication.getContext());
        ModifyInfoRequest modifyInfoRequest = new ModifyInfoRequest();
        Account account = new Account();
        account.setAccountEmail(lpi.getAccountEmail());
        modifyInfoRequest.setAccountId(lpi.getAccountId());
        modifyInfoRequest.setApplicant(lpi.getApplicant());
        modifyInfoRequest.setAccount(account);
        aCache.put(CacheConstants.PHONE,lpi.getAccountPhone());
        aCache.put(CacheConstants.PERSONAL_INFO,new Gson().toJson(modifyInfoRequest));
    }

    /**
     * 修改个人信息时调用，将个人信息异步写入缓存
     * @param modifyInfoRequest  修改个人信息的请求实体（不包含手机号）
     */
    public void putPersonalInfo(ModifyInfoRequest modifyInfoRequest){
        String personalInfo = new Gson().toJson(modifyInfoRequest);
        asyWrite(CacheConstants.PERSONAL_INFO,personalInfo);
    }

    /**
     * 异步写入String类型数据到缓存
     * @param k
     * @param data
     */
    public void asyWrite(String k, String data) {
        new Thread(new writeStringRunnable(k, data)).start();
    }
    public void asyWrite(String k, String data, String acacheName) {
        new Thread(new writeStringRunnable(k, data, acacheName)).start();
    }
    /**
     * 用于写入string数据的runnable实现类
     */
    private class writeStringRunnable implements Runnable {
        private String wkey;
        private String wData;
        private String acache = "ACache";

        public writeStringRunnable(String key, String data) {
            this.wkey = key;
            this.wData = data;
            this.acache = "ACache";
        }
        public writeStringRunnable(String key, String data, String acacheName) {
            this.wkey = key;
            this.wData = data;
            this.acache = acacheName;

        }
        @Override
        public void run() {
            ACache aCache = ACache.get(MyApplication.getContext(), acache);
            if (wData == null){

            }else {
                aCache.put(wkey, wData);
            }
        }
    }

    /**
     * bitmap
     * @param k
     * @param data
     */
    public void asyWrite(String k, Bitmap data) {
        new Thread(new writeBitmapRunnable(k, data)).start();
    }
    public void asyWrite(String k, Bitmap data, String acacheName) {
        new Thread(new writeBitmapRunnable(k, data, acacheName)).start();
    }
    private class writeBitmapRunnable implements Runnable {
        private String wkey;
        private Bitmap wData;
        private String acache = "ACache";
        public writeBitmapRunnable(String key, Bitmap data) {
            this.wkey = key;
            this.wData = data;
            this.acache = "ACache";
        }
        public writeBitmapRunnable(String key, Bitmap data, String acacheName) {
            this.wkey = key;
            this.wData = data;
            this.acache = acacheName;
        }
        @Override
        public void run() {
            ACache aCache = ACache.get(MyApplication.getContext(), acache);
            aCache.put(wkey, wData);
        }
    }
}
