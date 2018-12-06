package com.liu.jim.jobgo.view.adapter.list_view_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.constants.IntentConstants;
import com.liu.jim.jobgo.constants.JobApplyStateConstants;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.bean.JobSignedInfo;
import com.liu.jim.jobgo.view.activity.JobDetailActivity;

import java.util.List;

/**
 * Created by lenovo on 2018/4/19.
 */

public class JobSignedAdapter extends BaseAdapter {
    private List<JobSignedInfo> mData;
    private final int NO_DATA = 0, HAS_DATA = 1;
    private Activity ac;

    public JobSignedAdapter(Activity activity, List<JobSignedInfo> Data) {
        ac = activity;
        mData = Data;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(ac).inflate(R.layout.item_job_signed, parent, false);
            holder = new MyViewHolder();
            holder.jobType = (ImageView) convertView.findViewById(R.id.iv_work_type);
            holder.jobName = (TextView) convertView.findViewById(R.id.job_name);
            holder.jobSalary = (TextView) convertView.findViewById(R.id.job_salary);
            holder.jobPlace = (TextView) convertView.findViewById(R.id.job_place);
            holder.tv_jobType = (TextView) convertView.findViewById(R.id.job_type);
            holder.jobWorkTime = (TextView) convertView.findViewById(R.id.job_work_time);
            holder.appState = (TextView) convertView.findViewById(R.id.app_state);
            holder.btnLookDetail = convertView.findViewById(R.id.btn_look_detail);
            holder.btnLookDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnActivityDetail(position);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
            holder.btnLookDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnActivityDetail(position);
                }
            });
        }
        JobSignedInfo jd = mData.get(position);
        showJobInfo(jd, holder);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnActivityDetail(position);
            }
        });
        return convertView;
    }


    private void showJobInfo(JobSignedInfo jsi, MyViewHolder holder) {
        JobBasicInfo jd = jsi.getJobBasicInfo();
        String jobName, jobSal, jobPayType, hotSpot, starttime, endtime, appState;
        if (jd.getEjobName() == null || jd.getEjobName() == "") {
            jobName = "工作名未定";
        } else {
            jobName = jd.getEjobName();
        }
        if (jd.getEjobSalary() == null) {
            jobSal = "薪资未定";
        } else {
            jobSal = "薪资:" + jd.getEjobSalary();
        }
        if (jd.getEjobPaytype() == null) {
            jobPayType = "结算方式未定";
        } else {
            jobPayType = "结算方式:" + jd.getEjobSalary();
        }
        if (jd.getEjobHotspot() == null) {
            hotSpot = "工作地点未定";
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
        if (jd.getEjobWorktype() != null) {
            holder.tv_jobType.setText("工作类型:"+jd.getEjobWorktype());
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
            holder.tv_jobType.setText("工作类型未知");
            holder.jobType.setImageResource(R.mipmap.job_type_unknown);
        }
        switch (jsi.getAppstate()) {
            case JobApplyStateConstants.APPLYING:
                appState = "报名状态：报名中";
                break;
            case JobApplyStateConstants.HIRED:
                appState = "报名状态：报名成功";
                break;
            case JobApplyStateConstants.REJECTED:
                appState = "报名状态：未被录用";
                break;
            case JobApplyStateConstants.INTERVIEWING:
                appState = "报名状态：等待面试";
                break;
            case JobApplyStateConstants.WORKING:
                appState = "报名状态：工作完成中";
                break;
            case JobApplyStateConstants.SETTLING:
                appState = "报名状态：等待结算中";
                break;
            case JobApplyStateConstants.FINISHED:
                appState = "报名状态：工作已完成";
                break;
            default:
                appState = "暂无报名状态信息";
                break;
        }
        //文字展示设置等等
        holder.jobName.setText(jobName);
        holder.jobSalary.setText(jobSal + "/" + jobPayType);
        holder.jobPlace.setText(hotSpot);
        holder.jobWorkTime.setText("工作日期:" + starttime + "/" + endtime);
        holder.appState.setText(appState);
    }

    /**
     * 跳转到我的工作详情页面
     * @param JobInfoIndex
     */
    private void turnActivityDetail(int JobInfoIndex) {
        Intent intent = new Intent(ac, JobDetailActivity.class);
        Bundle bundle = new Bundle();
        int jobId = mData.get(JobInfoIndex).getJobBasicInfo().getEjobId();
        int appState = mData.get(JobInfoIndex).getAppstate();
        bundle.putInt(IntentConstants.JOB_ID,jobId);
        bundle.putInt(IntentConstants.APPLY_STATE,appState);
        intent.putExtras(bundle);
        ac.startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() <= 0) { //无数据情况处理
            return NO_DATA;
        } else {
            return HAS_DATA;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }


    public class MyViewHolder {
        ImageView jobType;
        TextView jobName;
        TextView jobSalary;
        TextView jobPlace;
        TextView tv_jobType;
        TextView jobWorkTime;
        TextView appState;
        Button btnLookDetail;
    }

    public void addListFromBottom(List<JobSignedInfo> jl) {
        if (jl != null && jl.size() != 0) {
            mData.addAll(jl);
        }
        this.notifyDataSetChanged();
    }

    public void clearList() {
        mData.clear();
        this.notifyDataSetChanged();
    }


}
