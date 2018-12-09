package com.liu.jim.jobgo.util;

/**
 * Created by jim on 2018/5/5.
 */


public class CriteriaUtil {
    public String getCityCode(int crItemPosition) {
        String cityCode = null;
        switch (crItemPosition) {    //为0时条件为全部
            case 1:  //武汉
                cityCode = "420100";
                break;
            case 2:  //北京
                cityCode = "110000";
                break;
            case 3:  //上海
                cityCode = "310000";
                break;
            case 4:  //广州
                cityCode = "440100";
                break;
            case 5:  //深圳
                cityCode = "440300";
                break;
            case 6:  //南昌
                cityCode = "360121";
                break;
            case 7:  //杭州
                cityCode = "330100";
                break;
            case 8:  //济南
                cityCode = "370100";
                break;
            case 9:  //长春
                cityCode = "220100";
                break;
            case 10:  //厦门
                cityCode = "350200";
                break;
            case 11:  //郑州
                cityCode = "410100";
                break;
            case 12:  //南京
                cityCode = "320100";
                break;
            case 13:  //天津
                cityCode = "120000";
                break;
            default:  //默认为全部  还有待添加
                break;
        }
        return cityCode;
    }

    public String getWorkTypeStr(int crItemPosition) {
        String workTypeStr = null;
        switch (crItemPosition) {
            case 1:
                workTypeStr = "调研";
                break;
            case 2:
                workTypeStr = "送餐员";
                break;
            case 3:
                workTypeStr = "促销";
                break;
            case 4:
                workTypeStr = "礼仪";
                break;
            case 5:
                workTypeStr = "安保";
                break;
            case 6:
                workTypeStr = "销售";
                break;
            case 7:
                workTypeStr = "服务员";
                break;
            case 8:
                workTypeStr = "临时工";
                break;
            case 9:
                workTypeStr = "校内";
                break;
            case 10:
                workTypeStr = "设计";
                break;
            case 11:
                workTypeStr = "文员";
                break;
            case 12:
                workTypeStr = "派单";
                break;
            case 13:
                workTypeStr = "家教";
                break;
            case 14:
                workTypeStr = "演出";
                break;
            case 15:
                workTypeStr = "客服";
                break;
            case 16:
                workTypeStr = "翻译";
                break;
            case 17:
                workTypeStr = "实习";
                break;
            case 18:
                workTypeStr = "模特";
                break;
            case 19:
                workTypeStr = "其它";
                break;
            default:
                break;
        }
        return workTypeStr;
    }

    public String getPayTypeStr(int crItemPosition) {
        String payTypeStr = null;
        switch (crItemPosition){
            case 1:
                payTypeStr = "日结";
                break;
            case 2:
                payTypeStr = "周结";
                break;
            case 3:
                payTypeStr = "半月结";
                break;
            case 4:
                payTypeStr = "月结";
                break;
            default:
                break;
        }
        return payTypeStr;
    }
}
