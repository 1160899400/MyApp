package com.liu.jim.jobgo.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.bean.Position;

/**
 * 寻找附近的岗位请求
 */

public class JobDisRequest {
    @SerializedName("location")
    @Expose
    private Position location;
    @SerializedName("page")
    @Expose
    private int page;

    public void setLocation(Position location){
        this.location = location;
    }
    public Position getLocation(){
        return this.location;
    }
    public void setPage(int page){
        this.page = page;
    }
    public int getPage(){
        return this.page;
    }
}
