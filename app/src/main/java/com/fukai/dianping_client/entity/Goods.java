package com.fukai.dianping_client.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by fukai on 2015/12/2.
 */
public class Goods implements Parcelable{
//        private static final long serialVersionUID = 1L;
        private String id;//商品ID
        private String categoryId;//分类ID
        private String shopId;//商家ID
        private String CityId;//城市ID
        private String title;//商品的名称
        private String sortTitle;//商品描述
        private String imgUrl;//图片路径
        private String startTime;//开始时间
        private String value;//商品原价
        private String price;//商品销售价
        private String ribat;//商品折扣
        private String bought;//购买量
        private String maxQuota;//最大存货量
        private String post;
        private String soldOut;
        private String tip;
        private String endTime;//结束的时间
        private String detail;//描述
        private boolean isRefund;
        private boolean isOverTime;
        private String minquota;
        private Shop shop;//所属商家
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getCategoryId() {
            return categoryId;
        }
        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
        public String getShopId() {
            return shopId;
        }
        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
        public String getCityId() {
            return CityId;
        }
        public void setCityId(String cityId) {
            CityId = cityId;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getSortTitle() {
            return sortTitle;
        }
        public void setSortTitle(String sortTitle) {
            this.sortTitle = sortTitle;
        }
        public String getImgUrl() {
            return imgUrl;
        }
        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        public String getStartTime() {
            return startTime;
        }
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }
        public String getRibat() {
            return ribat;
        }
        public void setRibat(String ribat) {
            this.ribat = ribat;
        }
        public String getBought() {
            return bought;
        }
        public void setBought(String bought) {
            this.bought = bought;
        }
        public String getMaxQuota() {
            return maxQuota;
        }
        public void setMaxQuota(String maxQuota) {
            this.maxQuota = maxQuota;
        }
        public String getPost() {
            return post;
        }
        public void setPost(String post) {
            this.post = post;
        }
        public String getSoldOut() {
            return soldOut;
        }
        public void setSoldOut(String soldOut) {
            this.soldOut = soldOut;
        }
        public String getTip() {
            return tip;
        }
        public void setTip(String tip) {
            this.tip = tip;
        }
        public String getEndTime() {
            return endTime;
        }
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
        public String getDetail() {
            return detail;
        }
        public void setDetail(String detail) {
            this.detail = detail;
        }
        public boolean isRefund() {
            return isRefund;
        }
        public void setRefund(boolean isRefund) {
            this.isRefund = isRefund;
        }
        public boolean isOverTime() {
            return isOverTime;
        }
        public void setOverTime(boolean isOverTime) {
            this.isOverTime = isOverTime;
        }
        public String getMinquota() {
            return minquota;
        }
        public void setMinquota(String minquota) {
            this.minquota = minquota;
        }
        public Shop getShop() {
            return shop;
        }
        public void setShop(Shop shop) {
            this.shop = shop;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.categoryId);
        dest.writeString(this.shopId);
        dest.writeString(this.CityId);
        dest.writeString(this.title);
        dest.writeString(this.sortTitle);
        dest.writeString(this.imgUrl);
        dest.writeString(this.startTime);
        dest.writeString(this.value);
        dest.writeString(this.price);
        dest.writeString(this.ribat);
        dest.writeString(this.bought);
        dest.writeString(this.maxQuota);
        dest.writeString(this.post);
        dest.writeString(this.soldOut);
        dest.writeString(this.tip);
        dest.writeString(this.endTime);
        dest.writeString(this.detail);
        dest.writeByte((byte) (this.isRefund ? 1 : 0));
        dest.writeString(this.minquota);
        if (this.shop instanceof Parcelable){
            Bundle bundle =new Bundle();
            bundle.putParcelable("shop",this.shop);
            dest.writeBundle(bundle);
        }


    }
    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>()
    {
        public Goods createFromParcel(Parcel in)
        {
            Goods goods = new Goods();
            goods.id = in.readString();
            goods.categoryId = in.readString();
            goods.shopId = in.readString();
            goods.CityId = in.readString();
            goods.title = in.readString();
            goods.sortTitle = in.readString();
            goods.imgUrl = in.readString();
            goods.startTime = in.readString();
            goods.value = in.readString();
            goods.price = in.readString();
            goods.ribat = in.readString();
            goods.bought = in.readString();
            goods.maxQuota = in.readString();
            goods.post = in.readString();
            goods.soldOut = in.readString();
            goods.tip = in.readString();
            goods.endTime = in.readString();
            goods.detail = in.readString();
            goods.isRefund = in.readByte()!= 0;
            goods.isOverTime = in.readByte()!= 0;
            goods.minquota = in.readString();
            Bundle bundle = new Bundle();
            bundle = in.readBundle();
            Log.i("TAG",bundle.toString());
            goods.shop = in.readBundle().getParcelable("shop");
            return goods;
        }

        public Goods[] newArray(int size)
        {
            return new Goods[size];
        }
    };



}
