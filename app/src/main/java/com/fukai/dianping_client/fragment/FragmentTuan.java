package com.fukai.dianping_client.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.activity.GoodsDetailsActivity;
import com.fukai.dianping_client.consts.CONSTS;
import com.fukai.dianping_client.entity.Category;
import com.fukai.dianping_client.entity.Goods;
import com.fukai.dianping_client.entity.ResponseObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by fukai on 2015/11/25.
 */
public class FragmentTuan extends Fragment  {
    @ViewInject(R.id.index_listGoods)
    private PullToRefreshListView listView;
    private List<Goods> goodsList;
    MyAdapter adapter;
    private  int page = 1, size = 10, count;
    private boolean isPulldown = true;

    @OnItemClick(R.id.index_listGoods)
    public void onItemClick(AdapterView<?> parent, View view
                                ,int position, long id){
        Intent intent = new Intent(this.getActivity(), GoodsDetailsActivity.class);
        intent.putExtra("goods",goodsList.get(position-1));
        startActivity(intent);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuan_index,null);
        ViewUtils.inject(this, view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setScrollingWhileRefreshingEnabled(true);//滚动时不加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isPulldown = true;
                page = 1;
                loadGoodsDatas(isPulldown);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isPulldown = false;
                page++;
                if (page == count){
                    listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }
                loadGoodsDatas(isPulldown);

            }

        });
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                listView.setRefreshing();
                return true;
            }
        }).sendEmptyMessageDelayed(0,300);

        return view;
    }
    private void loadGoodsDatas(boolean pullDown){
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", page+"");
        params.addQueryStringParameter("size", size+"");
        httpUtils.send(HttpRequest.HttpMethod.GET, CONSTS.Goods_Data_URI, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                listView.onRefreshComplete();
                Gson gson = new Gson();
                ResponseObject<List<Goods>> listResponseObject =  gson.fromJson(responseInfo.result
                        , new TypeToken<ResponseObject<List<Goods>>>() {}.getType());
                //this.page = listResponseObject.getPage();
                size = listResponseObject.getSize();
                count = listResponseObject.getCount();
                if (isPulldown){
                    goodsList = listResponseObject.getDatas();
                    adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                }else{
                    goodsList.addAll(listResponseObject.getDatas());
                    adapter.notifyDataSetChanged();
                }
               // Log.i("TAG","page=" + page + "size=" + size + "count=" + count);
                //Log.i("TAG","goodsList="+goodsList);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                listView.onRefreshComplete();
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuan_goods_list_item,null);
                ViewUtils.inject(viewHolder,convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Goods goods = goodsList.get(position);
            Picasso.with(parent.getContext()).load(goods.getImgUrl()).placeholder(R.drawable.default_pic).into(viewHolder.imageGoods);
            viewHolder.title.setText(goods.getSortTitle()) ;
            viewHolder.titleContent.setText(goods.getTitle());
            viewHolder.price.setText("¥"+goods.getPrice());

            StringBuffer sbf = new StringBuffer("¥"+goods.getValue());
            SpannableString spannable = new SpannableString(sbf);
            spannable.setSpan(new StrikethroughSpan(),0,sbf.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            viewHolder.value.setText(spannable);
            viewHolder.count.setText(goods.getBought()+"份");

            return convertView;
        }
        private class ViewHolder{
            @ViewInject(R.id.index_gl_item_image)
            private ImageView imageGoods;
            @ViewInject(R.id.index_gl_item_title)
            private TextView title;
            @ViewInject(R.id.index_gl_item_titlecontent)
            private TextView titleContent;
            @ViewInject(R.id.index_gl_item_price)
            private TextView price;
            @ViewInject(R.id.index_gl_item_value)
            private TextView value;
            @ViewInject(R.id.index_gl_item_count)
            private TextView count;

        }
    }

}
