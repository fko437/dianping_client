package com.fukai.dianping_client.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by fukai on 2015/12/2.
 */
public class Shop implements Parcelable {
//    private static final long serialVersionUID = 1L;
    private String id;//商家ID
    private String name;//商家名字
    private String address;//商家地址
    private String area;//商家城市
    private String opentime;//开始营业时间
    private String lon;//经度
    private String lat;//维度
    private String tel;//手机


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getOpentime() {
        return opentime;
    }
    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }
    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle =new Bundle();
        bundle.putString("ID",this.id);
        bundle.putString("NAME",this.name);
        bundle.putString("ADDRESS",this.address);
        bundle.putString("AREA",this.area);
        bundle.putString("OPENTIME",this.opentime);
        bundle.putString("LON",this.lon);
        bundle.putString("LAT",this.lat);
        bundle.putString("TEL",this.tel);
        dest.writeBundle(bundle);
    }
    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>()
    {
        public Shop createFromParcel(Parcel in)
        {
            Shop shop = new Shop();
            Bundle bundle = in.readBundle();
            shop.id = bundle.getString("ID");
            shop.name = bundle.getString("NAME");
            shop.address = bundle.getString("ADDRESS");
            shop.area = bundle.getString("AREA");
            shop.opentime = bundle.getString("OPRNTIME");
            shop.lon = bundle.getString("LON");
            shop.lat = bundle.getString("LAT");
            shop.tel = bundle.getString("TEL");
            return shop;
        }

        public Shop[] newArray(int size)
        {
            return new Shop[size];
        }
    };

}
