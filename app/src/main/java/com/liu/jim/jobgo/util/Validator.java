package com.liu.jim.jobgo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2018/3/29.
 */

//登录验证器
public class Validator {
    private static Validator validator = new Validator();
    private final String phoneRegex = "^[1][3,4,5,7,8][0-9]{9}$";
    private final String pwdRegex = "^.{1,14}$";
    private final String EmailRegex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    private final String GovidRegex = "^\\d{15}$|^\\d{17}[0-9Xx]$";
    private final String UrlRegex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))" + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";

    private Validator() {
    }

    public static Validator getValidator() {
        if (validator == null) {
            validator = new Validator();
        }
        return validator;
    }

    public boolean valLogin(String phone, String pwd) {
        if (valPhone(phone) && valPwd(pwd)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean valPhone(String phone) {
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean valPwd(String pwd){
        Pattern pattern = Pattern.compile(pwdRegex);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }

    public boolean valEmail(String mail){
        Pattern pattern = Pattern.compile(EmailRegex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
    public boolean valGovid(String govid){
        Pattern pattern = Pattern.compile(GovidRegex);
        Matcher matcher = pattern.matcher(govid);
        return matcher.matches();
    }

    public boolean valHttpUrl(String url){
        boolean isurl = false;
        Pattern pat = Pattern.compile(UrlRegex.trim());
        Matcher mat = pat.matcher(url.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }
}
