package com.liu.jim.jobgo.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.bean.Position;

/**
 * Created by jim on 2018/4/14.
 */

public class JobKeywordRequest {

    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("locator")
    @Expose
    private Position location;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }
}
