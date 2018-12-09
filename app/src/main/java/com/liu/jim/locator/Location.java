package com.liu.jim.locator;

import android.os.Parcel;
import android.os.Parcelable;

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

    protected Location(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        province = in.readString();
        city = in.readString();
        district = in.readString();
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
}
