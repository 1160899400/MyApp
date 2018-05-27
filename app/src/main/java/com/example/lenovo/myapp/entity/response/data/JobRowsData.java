package com.example.lenovo.myapp.entity.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *{
  "result": {
  "code": 1,
  "msg": msg
  },
 ****"data"****8: {
    "data" {
        "rows": [
               {
                "ejobId": 9,
                "ejobName": "暑期周末大学生兼职程序员",
                "ejobHotspot": "江汉",
                "ejobSalary": "100",
                "ejobStime": "2017/12/23",
                "ejobEtime": "2017/12/30",
                "ejobPaytype": "日结",
                "ejobWorktype": "促销",
                "ejobWorktypeID":9,
                "distance":
              },
            ...,
            {
                "ejobId": 10,
                "ejobName": "超市伊利牛奶促销",
                "ejobHotspot": "江汉",
                "ejobSalary": "100",
                "ejobStime": "2017/12/30",
                "ejobEtime": "2018/1/21",
                "ejobPaytype": "日结",
                "ejobWorktype": "促销",
                "ejobWorktypeID":10,
                "distance":
            }
            ],
                "total": 30(查询到的总岗位数)
            }
        }
 }
 */


public class JobRowsData {
    @SerializedName("data")
    @Expose
    private JobRows data;
    public void setData(JobRows data) {
        this.data = data;
    }
    public JobRows getData() {
        return data;
    }
}
