// ILocationService.aidl
package com.liu.jim;

// Declare any non-default types here with import statements
import com.liu.jim.locator.Location;
import android.content.Context;

interface ILocationService {

   Location getLocation();


}
