package com.fukai.dianping_client.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.fukai.dianping_client.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by fukai on 2015/12/6.
 */
public class NearByMapActivity extends Activity implements LocationSource,LocationSource.OnLocationChangedListener{
    @ViewInject(R.id.search_mymap)
    MapView mapView;
    private AMap aMap;
    private double longtitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_map_act);
        ViewUtils.inject(this);
        mapView.onCreate(savedInstanceState);// 必须要写
        if (mapView != null){
            aMap = mapView.getMap();
            aMap.setLocationSource(this);//设置定位监听
            aMap.setMyLocationEnabled(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!=null){
            longtitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }
}
