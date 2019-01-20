// ILocationService.aidl
package com.liu.jim;

// Declare any non-default types here with import statements
import com.liu.jim.locator.Location;
import com.liu.jim.locator.LocationListener;

interface ILocationManager {

   void registerListener(LocationListener locationListener);

   void getLocation();

   void unregisterListener();


}
