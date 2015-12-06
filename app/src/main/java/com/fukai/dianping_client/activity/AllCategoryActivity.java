package com.fukai.dianping_client.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.consts.CONSTS;
import com.fukai.dianping_client.entity.Category;
import com.fukai.dianping_client.entity.City;
import com.fukai.dianping_client.entity.ResponseObject;
import com.fukai.dianping_client.utils.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by fukai on 2015/11/30.
 */
public class AllCategoryActivity extends FragmentActivity {
    @ViewInject(R.id.home_nav_all_category)
    private ListView categoryList;
    @ViewInject(R.id.home_nav_all_back)
    private ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_index_nav_all);
        ViewUtils.inject(this);
        //categoryList.setAdapter(new AllCategoryAdapter());
        //异步从服务器获取数量数据
        new CategoryDataTask().execute();
    }
    public class CategoryDataTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String readline;
                StringBuilder sb = new StringBuilder();
                URL url = new URL(CONSTS.Catagory_Data_URI);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
                    while ((readline = bf.readLine()) != null) {
                        sb.append(readline);
                    }
                    bf.close();
                    inputStream.close();
                    parseCatagoryDataJson(sb.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        void parseCatagoryDataJson(String jsonString){
            Gson gson = new Gson();
            ResponseObject<List<Category>> responseObject = gson.fromJson(jsonString, new TypeToken<ResponseObject<List<Category>>>(){}.getType());
            for (Category category : responseObject.getDatas()){
                int i = Integer.parseInt(category.getCategoryId());
                long number = category.getCategoryNumber();
                MyUtils.allCategoryNumber[i-1] = number;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            categoryList.setAdapter(new AllCategoryAdapter());
        }
    }


    public class AllCategoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.allCategory.length;
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_index_nav_all_item,null);
                ViewUtils.inject(viewHolder,convertView);//此行代码很关键
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageResource(MyUtils.allCategoryImages[position]);
            viewHolder.textDesc.setText(MyUtils.allCategory[position]);
            viewHolder.textNumber.setText(MyUtils.allCategoryNumber[position]+"");//此处需将long转换成string类型
            return convertView;
        }

        private class ViewHolder {
            @ViewInject(R.id.home_nav_all_item_image)
            private ImageView imageView;
            @ViewInject(R.id.home_nav_all_item_desc)
            private TextView textDesc;
            @ViewInject(R.id.home_nav_all_item_number)
            private TextView textNumber;
        }
    }
    @OnClick(R.id.home_nav_all_back)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_nav_all_back:
                finish();
                break;
            default:
                break;
        }
    }
}
