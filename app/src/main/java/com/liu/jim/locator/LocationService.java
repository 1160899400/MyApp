package com.liu.jim.locator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.liu.jim.ILocationManager;
import com.liu.jim.jobgo.db.model.Location;

/**
 * @author Hongzhi.Liu
 * @date 2018/12/10
 */
public class LocationService extends Service {
    private static final String TAG = "LocationService";

    private LocationClient locationClient;

    private static Location location;

    private ILocationListener mLocationListener;

    /**
     * 传出返回的定位结果
     */
    private BDAbstractLocationListener bdLocationListener = new MyLocationListener();

    private final Binder mBinder = new ILocationManager.Stub() {

        @Override
        public void registerListener(ILocationListener locationListener) throws RemoteException {
            Log.i(TAG,"register listener");
            mLocationListener = locationListener;
        }

        @Override
        public void getLocation() throws RemoteException {
            Log.i(TAG,"start locate");
            locationClient.start();
        }

        @Override
        public void unregisterListener() throws RemoteException {
            mLocationListener = null;
        }
    };

    @Override
    public void onCreate() {
        location = new Location();
        initLocationClient();
        Log.i(TAG,"init location client");
    }

    private void initLocationClient() {
        locationClient = new LocationClient(this);
        //声明LocationClient类
        locationClient.registerLocationListener(bdLocationListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(2000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setIsNeedAddress(true);
        //设置是否需要位置信息，默认为不需要

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        locationClient.setLocOption(option);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.i(TAG,"service receive location");
            try {
                location.setLongitude(bdLocation.getLongitude());
                location.setLatitude(bdLocation.getLatitude());
                location.setProvince(bdLocation.getProvince());
                location.setCity(bdLocation.getCity());
                location.setDistrict(bdLocation.getDistrict());
                mLocationListener.onReturnLocation(location);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
