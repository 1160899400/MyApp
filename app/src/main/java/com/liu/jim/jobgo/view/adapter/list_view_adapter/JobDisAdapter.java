package com.liu.jim.jobgo.view.adapter.list_view_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.constants.IntentConstants;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.view.activity.JobDetailActivity;
import com.liu.jim.jobgo.view.widget.LoginTipDialog;

import java.util.List;

/**
 * Created by lenovo on 2018/5/8.
 */

public class JobDisAdapter extends BaseAdapter {
    private Activity jobListAc;
    private List<JobBasicInfo> jobData;

    public JobDisAdapter(Activity ac, List<JobBasicInfo> list) {
        this.jobListAc = ac;
        this.jobData = list;
    }


    // getView()有三个参数，返回值为将要显示的item
    // position表示数据源的索引，
    // covertView是上一个被弹出的list item
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int index = position;
        JobDisAdapter.MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(jobListAc).inflate(R.layout.item_job_distance, parent, false);
            holder = new JobDisAdapter.MyViewHolder();
            holder.jobName = (TextView) convertView.findViewById(R.id.job_name);
            holder.jobDistance = (TextView)convertView.findViewById(R.id.job_distance);
            holder.jobSalary = (TextView) convertView.findViewById(R.id.job_salary);
            holder.jobPlace = (TextView) convertView.findViewById(R.id.job_place);
            holder.jobWorkTime = (TextView) convertView.findViewById(R.id.job_work_time);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (JobDisAdapter.MyViewHolder) convertView.getTag();
        }
        JobBasicInfo jd = jobData.get(index);
        showJobInfo(jd, holder);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.loginStatus == false) {
                    Toast.makeText(jobListAc, "请先登录再试", Toast.LENGTH_SHORT).show();
                    LoginTipDialog loginTipDialog = new LoginTipDialog(jobListAc);
                    loginTipDialog.show();
                } else {
                    turnActivityDetail(index);
                }
            }
        });
        return convertView;
    }

    public class MyViewHolder {
        TextView jobName;
        TextView jobDistance;
        TextView jobPlace;
        TextView jobSalary;
        TextView jobWorkTime;
    }

    /**
     * 将jobBasicInfo展示到view当中
     *
     * @return
     */
    private void showJobInfo(JobBasicInfo jd, JobDisAdapter.MyViewHolder holder) {
        String jobName, jobDis, jobSal, jobPayType, hotSpot, starttime, endtime;
        if (jd.getEjobName() == null || jd.getEjobName() == "") {
            jobName = "工作名未定";
        } else {
            jobName = jd.getEjobName();
        }
        if (jd.getEjobSalary() == null) {
            jobSal = "薪资:未定";
        } else {
            jobSal = "薪资:" + jd.getEjobSalary();
        }
        if (jd.getEjobPaytype() == null) {
            jobPayType = "结算方式:未定";
        } else {
            jobPayType = "结算方式:" + jd.getEjobSalary();
        }
        if (jd.getEjobHotspot() == null) {
            hotSpot = "工作地点:未定";
        } else {
            hotSpot = "工作地点:" + jd.getEjobHotspot();
        }
        if (jd.getEjobStime() == null || jd.getEjobStime() == "") {
            starttime = "起始日期未定";
        } else {
            starttime = jd.getEjobStime();
        }
        if (jd.getEjobEtime() == null || jd.getEjobEtime() == "") {
            endtime = "结束日期未定";
        } else {
            endtime = jd.getEjobEtime();
        }
        if (jd.getDistance() == null){
            jobDis = "距离:未知";
        }else {
            jobDis = "距离:" + jd.getDistance() + "km";
        }
        //文字展示设置等等
        holder.jobName.setText(jobName);
        holder.jobDistance.setText(jobDis);
        holder.jobSalary.setText(jobSal + "/" + jobPayType);
        holder.jobPlace.setText(hotSpot);
        holder.jobWorkTime.setText("工作日期:" + starttime + "/" + endtime);
    }

    /**
     * 跳转到工作详情页面
     * 获取缓存的个人信息以及工作的id，并将数据发送给下一页面做查询
     */
    private void turnActivityDetail(int jobInfoIndex) {
        Activity ac = jobListAc;
        int jobID = jobData.get(jobInfoIndex).getEjobId();
        Intent intent = new Intent(ac, JobDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConstants.JOB_ID, jobID);
        intent.putExtras(bundle);
        jobListAc.startActivity(intent);
    }


    @Override
    public int getCount() {
        return jobData.size();
    }

    @Override
    public Object getItem(int position) {
        return jobData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 从listview底部添加数据
     *
     * @param job
     */
    public void addListFromBottom(JobBasicInfo job) {
        if (job != null){
            jobData.add(job);
        }
        this.notifyDataSetChanged();
    }

    public void addListFromBottom(List<JobBasicInfo> jl) {
        if (jl != null && jl.size() != 0) {
            jobData.addAll(jl);
        }
        this.notifyDataSetChanged();
    }

    public void clearList() {
        jobData.clear();
        this.notifyDataSetChanged();
    }


}
