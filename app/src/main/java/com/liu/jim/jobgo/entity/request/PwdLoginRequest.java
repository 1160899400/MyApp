package com.liu.jim.jobgo.entity.request;

/**
 * Created by jim on 2018/4/5.
 * 密码登录时请求的bean类
 */

public class PwdLoginRequest {

    private String accountName; //用户名，一般为手机号
    private String accountPasswd; //密码
    private String driverId;
    private String driverType;  //值为ANDROID
    private double longitude;
    private double latitude;
    private String host;    //值为okHttp
    private int version;  //值为1 表示求职版

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPasswd() {
        return accountPasswd;
    }

    public void setAccountPasswd(String accountPasswd) {
        this.accountPasswd = accountPasswd;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
