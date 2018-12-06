package com.liu.jim.jobgo.entity.response.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/13.
 */

public class JobBasicInfo {
    @SerializedName("ejobId")
    @Expose
    private int ejobId;
    @SerializedName("ejobName")
    @Expose
    private String ejobName;
    @SerializedName("ejobHotspot")
    @Expose
    private String ejobHotspot;
    @SerializedName("ejobSalary")
    @Expose
    private String ejobSalary;
    @SerializedName("ejobStime")
    @Expose
    private String ejobStime;
    @SerializedName("ejobEtime")
    @Expose
    private String ejobEtime;
    @SerializedName("ejobPaytype")
    @Expose
    private String ejobPaytype;
    @SerializedName("ejobWorktype")
    @Expose
    private String ejobWorktype;
    @SerializedName("ejobWorktypeID")
    @Expose
    private int ejobWorktypeID;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("status")
    @Expose
    private String status;
    public void setEjobId(int ejobId) {
        this.ejobId = ejobId;
    }
    public int getEjobId() {
        return ejobId;
    }

    public void setEjobName(String ejobName) {
        this.ejobName = ejobName;
    }
    public String getEjobName() {
        return ejobName;
    }

    public void setEjobHotspot(String ejobHotspot) {
        this.ejobHotspot = ejobHotspot;
    }
    public String getEjobHotspot() {
        return ejobHotspot;
    }

    public void setEjobSalary(String ejobSalary) {
        this.ejobSalary = ejobSalary;
    }
    public String getEjobSalary() {
        return ejobSalary;
    }

    public void setEjobStime(String ejobStime) {
        this.ejobStime = ejobStime;
    }
    public String getEjobStime() {
        return ejobStime;
    }

    public void setEjobEtime(String ejobEtime) {
        this.ejobEtime = ejobEtime;
    }
    public String getEjobEtime() {
        return ejobEtime;
    }

    public void setEjobPaytype(String ejobPaytype) {
        this.ejobPaytype = ejobPaytype;
    }
    public String getEjobPaytype() {
        return ejobPaytype;
    }

    public void setEjobWorktype(String ejobWorktype) {
        this.ejobWorktype = ejobWorktype;
    }
    public String getEjobWorktype() {
        return ejobWorktype;
    }

    public void setEjobWorktypeID(int ejobWorktypeID) {
        this.ejobWorktypeID = ejobWorktypeID;
    }
    public int getEjobWorktypeID() {
        return ejobWorktypeID;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Double getDistance() {
        return distance;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
