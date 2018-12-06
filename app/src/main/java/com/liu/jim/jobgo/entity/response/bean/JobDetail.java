package com.liu.jim.jobgo.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/11.
 */

public class JobDetail {
    @SerializedName("jobName")
    @Expose
    private String jobName;
    @SerializedName("jobNumber")
    @Expose
    private int jobNumber;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("jobSalary")
    @Expose
    private int jobSalary;
    @SerializedName("jobStime")
    @Expose
    private String jobStime;
    @SerializedName("jobEtime")
    @Expose
    private String jobEtime;
    @SerializedName("jobCity")
    @Expose
    private String jobCity;
    @SerializedName("jobHotspot")
    @Expose
    private String jobHotspot;
    @SerializedName("jobAddress")
    @Expose
    private String jobAddress;
    @SerializedName("jobLng")
    @Expose
    private double jobLng;
    @SerializedName("jobLat")
    @Expose
    private double jobLat;
    @SerializedName("jobWorktype")
    @Expose
    private String jobWorkType;
    @SerializedName("jobCredit")
    @Expose
    private float jobCredit;
    @SerializedName("jobPayType")
    @Expose
    private String jobPayType;
    @SerializedName("jobGender")
    @Expose
    private String jobGender;
    @SerializedName("jobNeedStudent")
    @Expose
    private String jobNeedStudent;
    @SerializedName("jobIsurgent")
    @Expose
    private String jobIsurgent;
    @SerializedName("jobJobperiod")
    @Expose
    private String jobJobperiod;
    //指示工作是否在招人的状态
    @SerializedName("jobState")
    @Expose
    private String jobState;


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public int getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(int jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobStime() {
        return jobStime;
    }

    public void setJobStime(String jobStime) {
        this.jobStime = jobStime;
    }

    public String getJobEtime() {
        return jobEtime;
    }

    public void setJobEtime(String jobEtime) {
        this.jobEtime = jobEtime;
    }

    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    public String getJobHotspot() {
        return jobHotspot;
    }

    public void setJobHotspot(String jobHotspot) {
        this.jobHotspot = jobHotspot;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public double getJobLng() {
        return jobLng;
    }

    public void setJobLng(double jobLng) {
        this.jobLng = jobLng;
    }

    public double getJobLat() {
        return jobLat;
    }

    public void setJobLat(double jobLat) {
        this.jobLat = jobLat;
    }

    public String getJobWorkType() {
        return jobWorkType;
    }

    public void setJobWorkType(String jobWorkType) {
        this.jobWorkType = jobWorkType;
    }

    public float getJobCredit() {
        return jobCredit;
    }

    public void setJobCredit(float jobCredit) {
        this.jobCredit = jobCredit;
    }

    public String getJobPayType() {
        return jobPayType;
    }

    public void setJobPayType(String jobPayType) {
        this.jobPayType = jobPayType;
    }

    public String getJobGender() {
        return jobGender;
    }

    public void setJobGender(String jobGender) {
        this.jobGender = jobGender;
    }

    public String getJobNeedStudent() {
        return jobNeedStudent;
    }

    public void setJobNeedStudent(String jobNeedStudent) {
        this.jobNeedStudent = jobNeedStudent;
    }

    public String getJobIsurgent() {
        return jobIsurgent;
    }

    public void setJobIsurgent(String jobIsurgent) {
        this.jobIsurgent = jobIsurgent;
    }

    public String getJobJobperiod() {
        return jobJobperiod;
    }

    public void setJobJobperiod(String jobJobperiod) {
        this.jobJobperiod = jobJobperiod;
    }

    public String getJobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

}
