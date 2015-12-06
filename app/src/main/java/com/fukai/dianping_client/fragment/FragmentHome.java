package com.fukai.dianping_client.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fukai.dianping_client.activity.AllCategoryActivity;
import com.fukai.dianping_client.activity.CityActivity;
import com.fukai.dianping_client.R;
import com.fukai.dianping_client.utils.MyUtils;
import com.fukai.dianping_client.utils.SharedUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.IOException;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by fukai on 2015/11/25.
 */
public class FragmentHome extends Fragment implements LocationListener {
    @ViewInject(R.id.index_top_city)
    private TextView topCity;
    private String cityName;//当前城市名称
    private LocationManager locationManager;
    @ViewInject(R.id.home_nav_sort)
    private GridView navSort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_index, null);
        ViewUtils.inject(this, view);//注意后一个参数为view
        //获取数据并且显示
        topCity.setText(SharedUtils.getCityName(getActivity()));
        navSort.setAdapter(new NavAdapter());
        return view;
    }
    public class NavAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.navsort.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_index_nav_item,null);
                ViewUtils.inject(viewHolder,convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageResource(MyUtils.navSortImages[position]);
            viewHolder.textView.setText(MyUtils.navsort[position]);
            if (position == MyUtils.navsort.length - 1){
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),AllCategoryActivity.class));
                    }
                });
            }
            return convertView;
        }
        private class ViewHolder{
            @ViewInject(R.id.home_nav_item_image)
            private ImageView imageView;
            @ViewInject(R.id.home_nav_item_desc)
            private TextView textView;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        checkGPSIsOpen();
    }

    @OnClick(R.id.index_top_city)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.index_top_city:
                startActivityForResult(new Intent(getActivity(),CityActivity.class), MyUtils.RequestCityCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyUtils.RequestCityCode && resultCode == Activity.RESULT_OK){
            cityName = data.getStringExtra("cityName");
            topCity.setText(cityName);
            SharedUtils.putCityName(getActivity(),cityName);
        }
    }

    //检查是否打开GPS
    private void checkGPSIsOpen() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpen) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
        //开始定位
        startLocation();
    }

    //使用GPS定位方法
    private void startLocation() {
        if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);


    }

    //接收并处理消息
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {

            }

            return false;
        }
    });

    //位置信息更新执行的方法
    @Override
    public void onLocationChanged(Location location) {
        //更新当前的位置信息
        updateWithLocation(location);

    }

    private void updateWithLocation(Location location) {
        double lat = 0.0, lng = 0.0;
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i("TAG", "经度：" + lat + ",纬度：" + lng);
        } else {
            cityName = "无法获取定位";
        }
        //通过经纬度获取地址,地址可能有多个
        List<Address> list = null;
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            list = geocoder.getFromLocation(lat, lng, 2);

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Address add = list.get(i);
                cityName = add.getLocality();//获取城市信息
            }
        }
        //发送一条空消息
        handler.sendEmptyMessage(1);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存城市
        SharedUtils.putCityName(getActivity(),cityName);
        //停止定位
//        stopLocation();
    }

    //停止定位
    private void stopLocation() {
        if (checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }
}
