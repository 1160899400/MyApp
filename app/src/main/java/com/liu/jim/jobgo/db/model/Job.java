package com.liu.jim.jobgo.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 *
 * @author jim
 * @date 2018/4/11
 */

@Entity
public class Job {

    @SerializedName("id")
    @Id(assignable = true)
    private long id;

    /**
     * 职位名
     */
    @SerializedName("name")
    private String name;

    /**
     * 招聘人数
     */
    @SerializedName("number")
    private int number;

    /**
     * 工作描述
     */
    @SerializedName("description")
    private String description;

    /**
     * 工资
     */
    @SerializedName("salary")
    private int salary;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("location")
    private ToOne<Location> location;

    @SerializedName("type")
    private int type;

    @SerializedName("credit")
    private float credit;

    @SerializedName("payType")
    private String payType;

    @SerializedName("gender")
    private Byte jobGender;

    @SerializedName("needStudent")
    private boolean needStudent;

    @SerializedName("urgent")
    private boolean urgent;

    @SerializedName("period")
    private String period;

    @SerializedName("state")
    private String state;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ToOne<Location> getLocation() {
        return location;
    }

    public void setLocation(ToOne<Location> location) {
        this.location = location;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Byte getJobGender() {
        return jobGender;
    }

    public void setJobGender(Byte jobGender) {
        this.jobGender = jobGender;
    }

    public boolean isNeedStudent() {
        return needStudent;
    }

    public void setNeedStudent(boolean needStudent) {
        this.needStudent = needStudent;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
