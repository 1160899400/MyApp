package com.example.lenovo.myapp.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lenovo on 2018/4/17.
 */

public class Applicant {
    @SerializedName("appId")
    @Expose
    private Integer appId;
    @SerializedName("appAccountId")
    @Expose
    private Integer appAccountId;
    @SerializedName("appGender")
    @Expose
    private Byte appGender;
    @SerializedName("appGovid")
    @Expose
    private String appGovid;
    @SerializedName("appBirth")
    @Expose
    private String appBirth;
    @SerializedName("appDescript")
    @Expose
    private String appDescript;
    @SerializedName("appResume")
    @Expose
    private String appResume;
    @SerializedName("appPhotolink")
    @Expose
    private String appPhotolink;
    @SerializedName("appEdu")
    @Expose
    private Integer appEdu;
    @SerializedName("appRealname")
    @Expose
    private String appRealname;


    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getAppAccountId() {
        return appAccountId;
    }

    public void setAppAccountId(Integer appAccountId) {
        this.appAccountId = appAccountId;
    }

    public Byte getAppGender() {
        return appGender;
    }

    public void setAppGender(Byte appGender) {
        this.appGender = appGender;
    }

    public String getAppGovid() {
        return appGovid;
    }

    public void setAppGovid(String appGovid) {
        this.appGovid = appGovid;
    }

    public String getAppBirth() {
        return appBirth;
    }

    public void setAppBirth(String appBirth) {
        this.appBirth = appBirth;
    }

    public String getAppDescript() {
        return appDescript;
    }

    public void setAppDescript(String appDescript) {
        this.appDescript = appDescript;
    }

    public String getAppResume() {
        return appResume;
    }

    public void setAppResume(String appResume) {
        this.appResume = appResume;
    }

    public String getAppPhotolink() {
        return appPhotolink;
    }

    public void setAppPhotolink(String appPhotolink) {
        this.appPhotolink = appPhotolink;
    }

    public Integer getAppEdu() {
        return appEdu;
    }

    public void setAppEdu(Integer appEdu) {
        this.appEdu = appEdu;
    }

    public String getAppRealname() {
        return appRealname;
    }

    public void setAppRealname(String appRealname) {
        this.appRealname = appRealname;
    }
}
