package com.liu.jim.locator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author HZLI02
 */
public class Location implements Parcelable {

    //纬度
    public double latitude;

    //经度
    public double longitude;

    //省
    public String province;

    //市
    public String city;

    //区
    public String district;

    public Location() {
    }

    protected Location(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
    }

    public void readFromParcel(Parcel src) {
        latitude = src.readDouble();
        longitude = src.readDouble();
        province = src.readString();
        city = src.readString();
        district = src.readString();
    }

    public static boolean isNull(Location location){
        if (null == location.province || null == location.city || location.district == null){
            return true;
        }
        return false;
    }

}
