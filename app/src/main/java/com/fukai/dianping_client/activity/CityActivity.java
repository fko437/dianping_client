package com.fukai.dianping_client.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.consts.CONSTS;
import com.fukai.dianping_client.entity.City;
import com.fukai.dianping_client.entity.ResponseObject;
import com.fukai.dianping_client.utils.MyUtils;
import com.fukai.dianping_client.widget.SideBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fukai on 2015/11/28.
 */
public class CityActivity extends FragmentActivity implements SideBar.OnTouchingLetterChangeListener{
    @ViewInject(R.id.city_list)
    private ListView listDatas;
    private List<City> cityList;
    @ViewInject(R.id.city_side_bar)
    private SideBar sideBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);
        ViewUtils.inject(this);
        View view = LayoutInflater.from(this).inflate(R.layout.home_city_search,null);
        listDatas.addHeaderView(view);
        //执行异步任务
        new CityDataTask().execute();
        sideBar.setOnTouchingLetterChangeListener(this);
    }


    @OnClick({R.id.index_city_back,R.id.index_city_flushcity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.index_city_back://返回
                finish();
                break;
            case R.id.index_city_flushcity://刷新
                new CityDataTask().execute();
                break;
            default:
                break;
        }

    }
    @OnItemClick(R.id.city_list)
    public void onItemClick(AdapterView<?> parent,View view,
                            int position, long id){
        Intent intent = new Intent();
        TextView textView = (TextView) view.findViewById(R.id.city_list_item_name);
        intent.putExtra("cityName",textView.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void OnTouchingLetterChanged(String s) {
        //找到listView中显示的索引位置
        listDatas.setSelection(findIndex(cityList, s));
    }
    //根据s值找到对应的s位置
    public int findIndex(List<City> list, String s){
        if (list != null){
            for (int i = 0; i < list.size(); i++){
                City city = list.get(i);
                if (s.equals(city.getSortKey())){
                    return i;
                }
            }
        }else {
            Toast.makeText(this, "暂无信息",Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public class CityDataTask extends AsyncTask<Void, Void, List<City>> {

        @Override
        protected List<City> doInBackground(Void... params) {
            StringBuilder sb = new StringBuilder();
            String readline = null;
            List<City> list = null;
            try {
                URL url = new URL(CONSTS.City_Data_URI);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
                    while ((readline = bf.readLine())!= null){
                        sb.append(readline);
                    }
                    bf.close();
                    inputStream.close();
                    return parseCityDatasJson(sb.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            cityList = cities;
            //适配显示
            listDatas.setAdapter(new MyAdapter(cityList));
        }
    }

    private List<City> parseCityDatasJson(String json) {

        Gson gson = new Gson();
        ResponseObject<List<City>> responseObject = gson.fromJson(json,new TypeToken<ResponseObject<List<City>>>(){}.getType());

        return responseObject.getDatas();
    }

    //用来第一次保存首字母的索引
    private StringBuffer buffer = new StringBuffer();
    //用来保存索引值对象的城市名称
    private List<String> firstList = new ArrayList<String>();
    public class MyAdapter extends BaseAdapter{

        private List<City> listCityDatas;

        MyAdapter(List<City> list){
            this.listCityDatas = list;
        }
        @Override
        public int getCount() {
            return listCityDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return listCityDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null){
                holder = new Holder();
                convertView =LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_list_item,null);
                ViewUtils.inject(holder,convertView);
                convertView.setTag(holder);
            }else{
                holder = (Holder)convertView.getTag();
            }
            //数据显示处理
            City city = listCityDatas.get(position);
            String sort = city.getSortKey();
            String name = city.getName();
            if (buffer.indexOf(sort) == -1){
                buffer.append(sort);
                firstList.add(name);
            }
            if (firstList.contains(name)){
                holder.sortKey.setText(sort);
                holder.sortKey.setVisibility(View.VISIBLE);
            }else {
                holder.sortKey.setVisibility(View.GONE);
            }
            holder.cityName.setText(name);
            return convertView;
        }

    }
    public class Holder{
        @ViewInject(R.id.city_list_item_sort)
        private TextView sortKey;
        @ViewInject(R.id.city_list_item_name)
        private TextView cityName;

    }
}
