package com.liu.jim.jobgo.entity.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.bean.JobSignedInfo;

import java.util.List;

/**
 * Created by lenovo on 2018/4/21.
 */

public class JobSignedRows {
    @SerializedName("rows")
    @Expose
    private List<JobSignedInfo> rows = null;
    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("hasNext")
    @Expose
    private boolean hasNext;

    public List<JobSignedInfo> getRows() {
        return rows;
    }

    public void setRows(List<JobSignedInfo> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
