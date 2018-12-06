package com.liu.jim.jobgo.entity.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.bean.Area;
import com.liu.jim.jobgo.entity.response.bean.Position;
import com.liu.jim.jobgo.entity.response.bean.Screen;

import java.util.List;

/**
 * Created by lenovo on 2018/4/23.
 */

public class JobCriteriaRequest {
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("position")
    @Expose
    private Position position;
    @SerializedName("type")
    @Expose
    private List<String> type = null;
    @SerializedName("area")
    @Expose
    private Area area;
    @SerializedName("sort")
    @Expose
    private List<Integer> sort = null;
    @SerializedName("screen")
    @Expose
    private Screen screen;

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Integer> getSort() {
        return sort;
    }

    public void setSort(List<Integer> sort) {
        this.sort = sort;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
