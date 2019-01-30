// ILocationService.aidl
package com.liu.jim;

// Declare any non-default types here with import statements
import com.liu.jim.jobgo.db.model.Location;
import com.liu.jim.locator.ILocationListener;

interface ILocationManager {

   //注册定位监听器
   void registerListener(ILocationListener locationListener);

   //尝试获取定位
   void getLocation();

   void unregisterListener();


}
