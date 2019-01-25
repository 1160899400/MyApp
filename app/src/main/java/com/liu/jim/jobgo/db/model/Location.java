package com.liu.jim.jobgo.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * @author HZLI02
 */

@Entity
public class Location implements Parcelable {

    @Id
    private long id;

    //纬度
    private double latitude;

    //经度
    private double longitude;

    private String province;

    private String city;

    private String district;

    /**
     * 具体地址
     */
    private String address;

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
        dest.writeLong(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
    }

    public void readFromParcel(Parcel src) {
        id = src.readLong();
        latitude = src.readDouble();
        longitude = src.readDouble();
        province = src.readString();
        city = src.readString();
        district = src.readString();
    }


    @Override
    public String toString(){
        return province + city + district;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
