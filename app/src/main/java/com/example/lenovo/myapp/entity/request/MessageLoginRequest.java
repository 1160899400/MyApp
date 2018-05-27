package com.example.lenovo.myapp.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 验证码登录请求发送的实体
 */

public class MessageLoginRequest {

    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("driverType")
    @Expose
    private String driverType;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("accountPhone")
    @Expose
    private String accountPhone;
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("smsId")
    @Expose
    private int smsId;
    @SerializedName("version")
    @Expose
    private int version;

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

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
