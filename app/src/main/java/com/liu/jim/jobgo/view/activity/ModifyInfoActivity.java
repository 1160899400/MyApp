package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.util.Validator;

/**
 * 修改个人信息
 * Created by jim on 2018/4/9.
 */

public class ModifyInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private int reqType;
    private EditText et_modify_Info;
    private Button submit_modify_info;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        bindView();
        initReqType();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("LoginActivity", this);



    }

    public void bindView(){
        et_modify_Info = findViewById(R.id.et_modify_info);
        submit_modify_info = findViewById(R.id.btn_submit_modify);
        submit_modify_info.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
    }

    /**
     * 接收上一个页面的请求参数
     */
    private void initReqType(){
        Intent intent = getIntent();
        reqType = intent.getIntExtra("reqType",-1);
        switch (reqType){
            case 2:
                et_modify_Info.setHint("请输入昵称");
                toolbar.setTitle("修改昵称");
                break;
            case 3:
                et_modify_Info.setHint("请输入真实姓名");
                toolbar.setTitle("修改姓名");
                break;
            case 4:
                et_modify_Info.setHint("请输入邮箱");
                toolbar.setTitle("修改邮箱");
                break;
            case 5:
                et_modify_Info.setHint("请输入身份证号");
                toolbar.setTitle("修改身份证号");
                break;
            case 7:
                et_modify_Info.setHint("请输入个人介绍");
                toolbar.setTitle("修改个人介绍");
                et_modify_Info.setSingleLine(false);
                et_modify_Info.setLines(7);
                et_modify_Info.setGravity(Gravity.START|Gravity.TOP);
                break;
        }
    }

    /**
     * 重写点击监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit_modify:
                String subData = et_modify_Info.getText().toString();
                if (checkFormat(reqType , subData)){
                    Intent intent = new Intent();
                    intent.putExtra("md_data",subData);  //将修改的结果返回
                    setResult(RESULT_OK,intent);   //第一个参数resultcode表示修改类型，第二个修改结果,返回到上一页面
                    finish();
                }else{
                    Toast.makeText(this,"输入内容格式错误，请检查重填",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /**
     * 检查输入框对应格式
     * @param reqType
     * @param data
     */
    private boolean checkFormat(int reqType , String data){
        Validator validator = Validator.getValidator();
        boolean result;
        switch (reqType){
            case 4:
                result = validator.valEmail(data);
                break;
            case 5:
                result = validator.valGovid(data);
                break;
            default:
                result = true;
                break;
        }
        validator = null;
        return result;
    }


    public void val(int reqTp, String data){

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
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
