// LocationListener.aidl
package com.liu.jim.locator;

// Declare any non-default types here with import statements
import com.liu.jim.jobgo.db.model.Location;
interface LocationListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onReturnLocation(out Location location);
}
