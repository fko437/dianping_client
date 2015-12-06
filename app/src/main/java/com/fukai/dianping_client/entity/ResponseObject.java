package com.fukai.dianping_client.entity;

/**
 * Created by fukai on 2015/11/28.
 */
public class ResponseObject<T> {
    private String state;
    private T datas;
    private int page;
    private int size;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ResponseObject(){
        super();
    }
    public ResponseObject(String state, T datas){
        this.state = state;
        this.datas = datas;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }
}
