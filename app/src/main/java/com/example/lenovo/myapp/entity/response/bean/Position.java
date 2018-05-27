package com.example.lenovo.myapp.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/13.
 */

public class Position {
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    public void setLon(double lon){
        this.lon = lon;
    }
    public double getLon(){
        return this.lon;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLat(){
        return this.lat;
    }
}
