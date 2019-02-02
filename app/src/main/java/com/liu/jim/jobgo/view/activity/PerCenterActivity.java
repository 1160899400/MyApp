package com.liu.jim.jobgo.view.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.base.BaseActivity;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.contract.ModifyInfoContract;
import com.liu.jim.jobgo.db.model.Account;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.ModInfoPresenter;
import com.liu.jim.jobgo.util.BitmapUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by jim on 2018/4/4.
 */

public class PerCenterActivity extends BaseActivity implements ViewGroup.OnClickListener, ModifyInfoContract.IModInfoView {

    private Toolbar mToolbar;

    private int accountId;
    private String phone;
    private String nickname;
    private String realname;
    private String email;
    private String govid;
    private String intr;
    private String photolink;
    private byte gender;
    private String birth;
    private Integer edu;

    private final int REQ_MOD_NICKNAME = 2;
    private final int REQ_MOD_NAME = 3;
    private final int REQ_MOD_EMAIL = 4;
    private final int REQ_MOD_GOVID = 5;
    private final int REQ_MOD_INTRODUCTION = 7;
    private final int REQ_MOD_AVATAR = 8;
    private final int REQ_MOD_GENDER = 9;
    private final int REQ_MOD_EDU = 10;
    private final int REQ_MOD_BIRTH = 13;
    private final int REQ_TAKE_PHOTO = 11;   //拍照请求的reqestcode
    private final int REQ_CHOOSE_PHOTO = 12;  //选择图片请求的reqestcode


    private TextView tv_gender;
    private TextView tv_name;
    private TextView tv_birth;
    private TextView tv_mail;
    private TextView tv_edu;
    private TextView tv_govid;
    private TextView tv_phone;
    private TextView tv_introduction;
    private ImageView iv_avatar;
    private AlertDialog alertDialog;   //待选择的对话框
    private Dialog dialog;    //选择上传头像方式的对话框
    private TextView tv_choosePhoto;
    private TextView tv_takePhoto;

    private Bitmap avatar;

    private ModifyInfoContract.IModInfoPresenter modInfoPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        initPersonalInfo();
        showInfo();
        modInfoPresenter = new ModInfoPresenter(this);
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("PerCenterActivity", this);
    }


    /**
     * 从缓存中读取个人信息
     */
    public void initPersonalInfo() {
        NoticeManager.build(this).setNoticeType(NoticeManager.NoticeType.Loading);      //显示加载框
        NoticeManager.build(this).show();
        String personalInfo = ACache.get(this).getAsString(CacheConstants.PERSONAL_INFO);
        if (personalInfo != null) {
            ModifyInfoRequest myInfo = new Gson().fromJson(personalInfo, ModifyInfoRequest.class);
            accountId = myInfo.getAccountId();
            email = myInfo.getAccount().getAccountEmail();
            realname = myInfo.getAccount().getAppRealname();
            govid = myInfo.getAccount().getAppGovid();
            intr = myInfo.getAccount().getAppDescript();
            photolink = myInfo.getAccount().getAppPhotolink();
            gender = myInfo.getAccount().getAppGender();
            birth = myInfo.getAccount().getAppBirth();
            edu = myInfo.getAccount().getAppEdu();
            phone = CacheManager.getCacheManager().getAccountPhone();
            avatar = CacheManager.getCacheManager().getAvatar(MyApplication.getContext());
        }
    }

    /**
     * 将内存的个人信息展示到控件外观上
     */
    private void showInfo() {
        if (null == realname) { //姓名
            tv_name.setText("未填写");
        } else {
            tv_name.setText(realname);
        }
        if (1 == gender) { //性别
            tv_gender.setText("男");
        } else if (-1 == gender) {
            tv_gender.setText("女");
        } else {
            tv_gender.setText("未填写");
        }
        if (null == birth) {  //生日
            tv_birth.setText("未填写");
        } else {
            tv_birth.setText(birth);
        }
        if (null == email) {  //邮箱
            tv_mail.setText("未填写");
        } else {
            tv_mail.setText(email);
        }
        if (null == intr) {   //个人介绍
            tv_introduction.setText("未填写");
        } else {
            tv_introduction.setText(intr);
        }
        if (null == phone) {  //手机号
            tv_phone.setText("未填写");
        } else {
            StringBuilder phoneSb = new StringBuilder(phone);
            phoneSb.replace(3, 7, "****");
            tv_phone.setText(phoneSb.toString());
        }
        switch (edu) {   //学历
            case 1:
                tv_edu.setText("文盲");
                break;
            case 2:
                tv_edu.setText("小学");
                break;
            case 3:
                tv_edu.setText("初中");
                break;
            case 4:
                tv_edu.setText("高中");
                break;
            case 5:
                tv_edu.setText("大学专科");
                break;
            case 6:
                tv_edu.setText("大学本科");
                break;
            case 7:
                tv_edu.setText("硕士研究生");
                break;
            case 8:
                tv_edu.setText("博士研究生");
                break;
            case 9:
                tv_edu.setText("博士后");
                break;
            default:
                tv_edu.setText("未填写");
                break;
        }
        if (null == govid) {   //身份证号
            tv_govid.setText("未填写");
        } else {
            StringBuilder govidSb = new StringBuilder(govid);
            govidSb.replace(4, govid.length()-1, "**************");
            tv_govid.setText(govidSb.toString());
        }
        if (avatar == null) { //头像
            iv_avatar.setImageResource(R.mipmap.app_icon);
        } else {
            iv_avatar.setImageBitmap(avatar);
        }
        NoticeManager.build(this).loadSuccess();
    }

    @Override
    protected void bindView() {
        mToolbar = findViewById(R.id.toolbar);

        iv_avatar = findViewById(R.id.image_avatar);
        tv_gender = findViewById(R.id.tv_gender);
        tv_birth = findViewById(R.id.tv_birth);
        tv_name = findViewById(R.id.tv_name);
        tv_edu = findViewById(R.id.tv_edu);
        tv_mail = findViewById(R.id.tv_email);
        tv_govid = findViewById(R.id.tv_govid);
        tv_phone = findViewById(R.id.tv_phone);
        tv_introduction = findViewById(R.id.tv_introduction);

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_personal_center;
    }

    public void setToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * 设置个人中心各布局的点击事件
     * reqType表示请求类型：
     * 0为昵称 1为头像 2为姓名 3为性别 4为邮箱...
     *
     * @param view 姓名、性别、邮箱等布局
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(PerCenterActivity.this, ModifyInfoActivity.class);
        switch (view.getId()) {
            case R.id.rl_avatar:
                showAvatarDialog();
                break;
            case R.id.rl_name:
                intent.putExtra("reqType", REQ_MOD_NAME);
                startActivityForResult(intent, REQ_MOD_NAME);
                break;
            case R.id.rl_gender:
                showGenderDialog();
                break;
            case R.id.rl_email:
                intent.putExtra("reqType", REQ_MOD_EMAIL);
                startActivityForResult(intent, REQ_MOD_EMAIL);
                break;
            case R.id.rl_gov_id:
                intent.putExtra("reqType", REQ_MOD_GOVID);
                startActivityForResult(intent, REQ_MOD_GOVID);
                break;
            case R.id.rl_education:
                showEduDialog();
                break;
            case R.id.rl_introduction:
                intent.putExtra("reqType", REQ_MOD_INTRODUCTION);
                startActivityForResult(intent, REQ_MOD_INTRODUCTION);
                break;
            case R.id.rl_birth:
                showDateDialog();
                break;
            case R.id.tv_takePhoto:
                takePhoto();
                dialog.dismiss();
                break;
            case R.id.tv_choosePhoto:
                choosePhoto();
                dialog.dismiss();
                break;
        }

    }

    /**
     * 点击头像后触发该函数
     * 从底部弹出对话框，选择照片或拍照
     */
    private void showAvatarDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_dialog_avatar, null);
        //初始化控件
        tv_choosePhoto = inflate.findViewById(R.id.tv_choosePhoto);
        tv_takePhoto = inflate.findViewById(R.id.tv_takePhoto);
        tv_choosePhoto.setOnClickListener(this);
        tv_takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    /**
     * 弹出性别的单选框
     */
    private void showGenderDialog() {
        final String items[] = {"男", "女"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.app_icon)//设置标题的图片
                .setTitle("请选择您的性别")//设置对话框的标题
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestModInfo(which, REQ_MOD_GENDER);
                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出教育经历的单选框
     */
    private void showEduDialog() {
        final String items[] = {"文盲", "小学", "初中", "高中", "大学专科", "大学本科", "硕士研究生", "博士研究生", "博士后"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.app_icon)
                .setTitle("请选择您的学历")
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestModInfo(which, REQ_MOD_EDU);
                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出日期选择的单选框
     */
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker picker = datePickerDialog.getDatePicker();
                int year = picker.getYear();
                int monthOfYear = picker.getMonth() + 1;
                int dayOfMonth = picker.getDayOfMonth();
                String dateStr = year + "-" + monthOfYear + "-" + dayOfMonth;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                requestModInfo(dateStr, REQ_MOD_BIRTH);
                datePickerDialog.dismiss();
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                datePickerDialog.dismiss();
                //放弃修改s
            }
        });
        datePickerDialog.create();
        datePickerDialog.show();
    }


    /**
     * 拍照作为头像
     */
    private void takePhoto() {
        //先判断写入外存的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，发起申请权限，用户做出决定后执行onRequestPermissionsResult方法
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.MY_PERMISSIONS_REQUEST_CALL_TAKE_PHONE);
        } else {
            //判断sd卡是否可用
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQ_TAKE_PHOTO);

            } else {
                Toast.makeText(this, "当前sd卡不可用，请检查手机设置后重试", Toast.LENGTH_SHORT).show();
            }
        }
        //先判断sd卡是否存在

    }

    /**
     * 选择相册图片作为头像
     */
    private void choosePhoto() {
        //先判断写入外存的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，发起申请权限，用户做出决定后执行onRequestPermissionsResult方法
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.MY_PERMISSIONS_REQUEST_CALL_CHOOSE_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CHOOSE_PHOTO);
        }

    }

    /**
     * 修改头像时根据用户是否同意权限做出不同响应
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstants.MY_PERMISSIONS_REQUEST_CALL_TAKE_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // 申请权限被拒绝，提示用户放弃拍照
                Toast.makeText(this, "拍照失败，请设置权限后重试", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == AppConstants.MY_PERMISSIONS_REQUEST_CALL_CHOOSE_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                // 申请权限被拒绝，提示用户放弃选择相册图片
                Toast.makeText(this, "获取相册失败，请设置权限后重试", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 修改头像 或 字符串数据
     * 获取上一页面修改后的结果,并发送请求修改
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQ_TAKE_PHOTO:
                    Bundle bundle = intent.getExtras();
                    avatar = BitmapUtil.makeRoundCorner((Bitmap) bundle.get("data"));
                    mdAvatar();
                    break;
                case REQ_CHOOSE_PHOTO:
                    if (intent.getData() != null) {
                        Bitmap bitmap = BitmapUtil.CompressBitmap(this, intent.getData());
                        avatar = BitmapUtil.makeRoundCorner(bitmap);
                        mdAvatar();
                    }
                    break;
                case REQ_MOD_NICKNAME:
                case REQ_MOD_NAME:
                case REQ_MOD_EMAIL:
                case REQ_MOD_GOVID:
                case REQ_MOD_INTRODUCTION:
                case REQ_MOD_BIRTH:
                    NoticeManager.build(this).setNoticeType(NoticeManager.NoticeType.Loading);
                    NoticeManager.build(this).show();
                    String mod_data = intent.getStringExtra("md_data");
                    requestModInfo(mod_data, requestCode);
                    break;
            }
        } else if (RESULT_CANCELED == resultCode) {
            Toast.makeText(this, "已放弃修改", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 更换缓存中的头像，并再ui显示
     * 先为bitmap生成新的文件名，再放入文件名和图像
     */
    private void mdAvatar() {

        requestModInfo(photolink, REQ_MOD_AVATAR);
    }


    /**
     * 重写监听返回按钮的点击事件,点击后结束当前activity，自动返回上一activity
     *
     * @param item 返回按钮代表的菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PerCenterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重写按下手机返回键
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PerCenterActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    /**
     * 封装修改个人信息的request请求
     */
    private ModifyInfoRequest initModInfo(String modStr, int reqType) {
        if (edu == 0) {
            edu = 1;
        }
        String Token = ACache.get(this).getAsString(CacheConstants.LOGIN_TOKEN);
        ModifyInfoRequest mir = new ModifyInfoRequest();
        mir.setToken(Token);
        mir.setAccountId(accountId);
        com.liu.jim.jobgo.entity.response.bean.Account account = new com.liu.jim.jobgo.entity.response.bean.Account();
        Account accountInfo = new Account();
        accountInfo.setGender(gender);
        accountInfo.setAppEdu(edu);
        switch (reqType) {
            case REQ_MOD_NICKNAME:   //修改 用户名
                account.setAccountName(modStr);
                account.setAccountEmail(email);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_NAME:  //修改真实名字
                account.setAccountEmail(email);
                accountInfo.setAppRealname(modStr);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_EMAIL:  //修改邮箱
                account.setAccountName(nickname);
                account.setAccountEmail(modStr);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_GOVID:  //修改身份证号
                account.setAccountName(nickname);
                account.setAccountEmail(email);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(modStr);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_INTRODUCTION:  //修改个人介绍
                account.setAccountName(nickname);
                account.setAccountEmail(email);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(modStr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_AVATAR:  //修改头像
                account.setAccountName(nickname);
                account.setAccountEmail(email);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(modStr);
                accountInfo.setAppBirth(birth);
                break;
            case REQ_MOD_BIRTH:
                account.setAccountName(nickname);
                account.setAccountEmail(email);
                accountInfo.setAppRealname(realname);
                accountInfo.setAppGovid(govid);
                accountInfo.setAppDescript(intr);
                accountInfo.setAppPhotolink(photolink);
                accountInfo.setAppBirth(modStr);
                break;

        }
        mir.setAccount(account);
        mir.setAccount(accountInfo);
        return mir;
    }

    /**
     * 修改性别时，0为男，1为女（服务器识别1为男，-1为女）
     */
    private ModifyInfoRequest initModInfo(int modInt, int reqType) {
        if (edu == 0) {
            edu = 1;
        }
        String Token = CacheManager.getCacheManager().getToken(this);
        ModifyInfoRequest mir = new ModifyInfoRequest();
        mir.setToken(Token);
        mir.setAccountId(accountId);
        com.liu.jim.jobgo.entity.response.bean.Account account = new com.liu.jim.jobgo.entity.response.bean.Account();
        Account accountInfo = new Account();
        accountInfo.setAppRealname(realname);
        account.setAccountName(nickname);
        account.setAccountEmail(email);
        accountInfo.setAppGovid(govid);
        accountInfo.setAppDescript(intr);
        accountInfo.setAppPhotolink(photolink);
        accountInfo.setAppBirth(birth);
        System.out.println("gengder: " + modInt);
        switch (reqType) {
            case REQ_MOD_GENDER:
                Byte a;
                if (modInt == 0) {
                    a = 1;
                } else {
                    a = -1;
                }
                accountInfo.setGender(a);
                accountInfo.setAppEdu(edu);
                break;
            case REQ_MOD_EDU:
                modInt += 1;
                accountInfo.setAppEdu(modInt);
                accountInfo.setGender(gender);
                break;
        }
        mir.setAccount(account);
        mir.setAccount(accountInfo);
        return mir;
    }


    /**
     * 发送网络请求,修改后台的信息
     */
    private void requestModInfo(String modStr, int reqType) {
        ModifyInfoRequest mir = initModInfo(modStr, reqType);
        modInfoPresenter.modInfo(mir, reqType);
    }

    private void requestModInfo(int modInt, int reqType) {
        ModifyInfoRequest mir = initModInfo(modInt, reqType);
        modInfoPresenter.modInfo(mir, reqType);
    }


    @Override
    public void showModInfo(ModifyInfoRequest modifyInfoRequest, int reqType) {
        com.liu.jim.jobgo.entity.response.bean.Account account = modifyInfoRequest.getAccount();
        Account accountInfo = modifyInfoRequest.getAccount();
        switch (reqType) {   //修改控件上的显示和内存中的数据,并写入缓存
            case REQ_MOD_NICKNAME:
                nickname = account.getAccountName();
                break;
            case REQ_MOD_NAME:
                realname = accountInfo.getAppRealname();
                break;
            case REQ_MOD_EMAIL:
                email = account.getAccountEmail();
                break;
            case REQ_MOD_GOVID:
                govid = accountInfo.getAppGovid();
                break;
            case REQ_MOD_INTRODUCTION:
                intr = accountInfo.getAppDescript();
                break;
            case REQ_MOD_EDU:
                edu = accountInfo.getAppEdu();
                break;
            case REQ_MOD_GENDER:
                gender = accountInfo.getGender();
                break;
            case REQ_MOD_BIRTH:
                birth = accountInfo.getAppBirth();
                break;
            case REQ_MOD_AVATAR:
                // 保存新头像在缓存文件中
                SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String FileTime = timesdf.format(new Date()).toString();//获取系统时间
                photolink = FileTime.replace(":", "");
                ModifyInfoRequest mir = initModInfo(photolink, REQ_MOD_AVATAR);
                CacheManager.getCacheManager().putPersonalInfo(mir);
                CacheManager.getCacheManager().asyWrite(photolink, avatar, CacheConstants.DIR_AVATAR);
                iv_avatar.setImageBitmap(avatar);
                break;
            default:
                break;
        }
        showInfo();
        NoticeManager.build(this).loadSuccess();
    }

    @Override
    public void modFail(int reqType) {
        NoticeManager.build(this).loadFail();
        Toast.makeText(MyApplication.getContext(),"修改失败，请检查网络设置",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (modInfoPresenter != null) {
            modInfoPresenter.onDestroy();
            modInfoPresenter = null;
            System.gc();
        }
    }
}
