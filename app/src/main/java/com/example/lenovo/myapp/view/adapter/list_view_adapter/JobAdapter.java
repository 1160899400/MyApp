package com.example.lenovo.myapp.view.adapter.list_view_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 2018/3/21.
 */

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.constants.AppConstants;
import com.example.lenovo.myapp.constants.IntentConstants;
import com.example.lenovo.myapp.entity.request.ModifyInfoRequest;
import com.example.lenovo.myapp.entity.response.bean.JobBasicInfo;
import com.example.lenovo.myapp.view.activity.JobDetailActivity;
import com.example.lenovo.myapp.util.ACache;
import com.example.lenovo.myapp.constants.CacheConstants;
import com.example.lenovo.myapp.view.myview.LoginTipDialog;
import com.google.gson.Gson;

import java.util.List;

public class JobAdapter extends BaseAdapter {
    private Activity jobListAc;
    private List<JobBasicInfo> jobData;

    public JobAdapter(Activity ac, List<JobBasicInfo> list) {
        this.jobListAc = ac;
        this.jobData = list;
    }


    // getView()有三个参数，返回值为将要显示的item
    // position表示数据源的索引，
    // covertView是上一个被弹出的list item
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int index = position;
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(jobListAc).inflate(R.layout.item_job_list, parent, false);
            holder = new MyViewHolder();
            holder.jobType = (ImageView) convertView.findViewById(R.id.iv_work_type);
            holder.jobName = (TextView) convertView.findViewById(R.id.job_name);
            holder.jobSalary = (TextView) convertView.findViewById(R.id.job_salary);
            holder.jobPlace = (TextView) convertView.findViewById(R.id.job_place);
            holder.tv_jobType = (TextView) convertView.findViewById(R.id.job_type);
            holder.jobWorkTime = (TextView) convertView.findViewById(R.id.job_work_time);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (MyViewHolder) convertView.getTag();
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
        ImageView jobType;
        TextView jobName;
        TextView jobSalary;
        TextView jobPlace;
        TextView tv_jobType;
        TextView jobWorkTime;
    }

    /**
     * 将jobBasicInfo展示到view当中
     *
     * @return
     */
    private void showJobInfo(JobBasicInfo jd, MyViewHolder holder) {
        String jobName, jobSal, jobPayType, hotSpot, starttime, endtime;
        if (jd.getEjobName() == null || jd.getEjobName() == "") {
            jobName = "工作名:未定";
        } else {
            jobName = "工作名:" + jd.getEjobName();
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
            starttime = "起始日期:未定";
        } else {
            starttime = "起始日期:" + jd.getEjobStime();
        }
        if (jd.getEjobEtime() == null || jd.getEjobEtime() == "") {
            endtime = "结束日期:未定";
        } else {
            endtime = jd.getEjobEtime();
        }
        if (jd.getEjobWorktype() != null) {
            holder.tv_jobType.setText("工作类型:" + jd.getEjobWorktype());
            switch (jd.getEjobWorktype()) {
                case "调研":
                    holder.jobType.setImageResource(R.mipmap.job_type1);
                    break;
                case "送餐员":
                    holder.jobType.setImageResource(R.mipmap.job_type2);
                    break;
                case "促销":
                    holder.jobType.setImageResource(R.mipmap.job_type3);
                    break;
                case "礼仪":
                    holder.jobType.setImageResource(R.mipmap.job_type4);
                    break;
                case "安保":
                    holder.jobType.setImageResource(R.mipmap.job_type5);
                    break;
                case "销售":
                    holder.jobType.setImageResource(R.mipmap.job_type6);
                    break;
                case "服务员":
                    holder.jobType.setImageResource(R.mipmap.job_type7);
                    break;
                case "临时工":
                    holder.jobType.setImageResource(R.mipmap.job_type8);
                    break;
                case "校内":
                    holder.jobType.setImageResource(R.mipmap.job_type9);
                    break;
                case "设计":
                    holder.jobType.setImageResource(R.mipmap.job_type10);
                    break;
                case "文员":
                    holder.jobType.setImageResource(R.mipmap.job_type11);
                    break;
                case "派单":
                    holder.jobType.setImageResource(R.mipmap.job_type12);
                    break;
                case "家教":
                    holder.jobType.setImageResource(R.mipmap.job_type13);
                    break;
                case "演出":
                    holder.jobType.setImageResource(R.mipmap.job_type14);
                    break;
                case "客服":
                    holder.jobType.setImageResource(R.mipmap.job_type15);
                    break;
                case "翻译":
                    holder.jobType.setImageResource(R.mipmap.job_type16);
                    break;
                case "实习":
                    holder.jobType.setImageResource(R.mipmap.job_type17);
                    break;
                case "模特":
                    holder.jobType.setImageResource(R.mipmap.job_type18);
                    break;
                default:
                    holder.jobType.setImageResource(R.mipmap.job_type_unknown);
                    break;
            }
        } else {
            holder.tv_jobType.setText("工作类型:未知");
            holder.jobType.setImageResource(R.mipmap.job_type_unknown);
        }
        //文字展示设置等等
        holder.jobName.setText(jobName);
        holder.jobSalary.setText(jobSal + "/" + jobPayType);
        holder.jobPlace.setText(hotSpot);
        holder.jobWorkTime.setText("工作日期:" + starttime + "/" + endtime);
    }

    /**
     * 跳转到工作详情页面
     * 获取缓存的个人信息以及工作的id，并将数据发送给下一页面做查询
     */
    private void turnActivityDetail(int jobInfoIndex) {
        Intent intent = new Intent(jobListAc, JobDetailActivity.class);
        Bundle bundle = new Bundle();
        int jobID = jobData.get(jobInfoIndex).getEjobId();
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
        if (job != null) {
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
