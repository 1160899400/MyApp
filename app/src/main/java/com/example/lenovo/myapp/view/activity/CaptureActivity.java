package com.example.lenovo.myapp.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.util.Validator;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by lenovo on 2018/4/10.
 */

public class CaptureActivity extends AppCompatActivity {
    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置结果处理
        CaptureActivity.mDelegate mDlg = new mDelegate();
        mQRCodeView.setDelegate(mDlg);
        //开始读取二维码
        mQRCodeView.startSpot();
        //打开摄像头预览，但未开始识别
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);  打开指定摄像头预览
        //延迟1.5秒后识别
        mQRCodeView.showScanRect();
    }

    private class mDelegate implements QRCodeView.Delegate {
        private void vibrate() {
            //调用手机震动功能
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        }

        @Override
        public void onScanQRCodeSuccess(String result) {
            //发出震动
            vibrate();
            Validator validator = Validator.getValidator();
            if (validator.valHttpUrl(result)) {      //如果解析结果为一串url，则直接访问
                Toast.makeText(CaptureActivity.this, "扫描成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri url = Uri.parse(result);
                intent.setData(url);
                startActivity(intent);
            }else {
                Toast.makeText(CaptureActivity.this, "签到码： " + result, Toast.LENGTH_SHORT).show();
                Toast.makeText(CaptureActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                CaptureActivity.this.finish();
            }

        }

        @Override
        public void onScanQRCodeOpenCameraError() {
//        Log.e(TAG, "打开相机出错");
        }
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

}
