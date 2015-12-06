package com.fukai.dianping_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.entity.Goods;
import com.fukai.dianping_client.entity.Shop;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import java.net.URI;

/**
 * Created by fukai on 2015/12/5.
 */
public class GoodsDetailsActivity extends Activity {
    @ViewInject(R.id.goods_details_image)
    private ImageView goods_image;
    @ViewInject(R.id.goods_details_title)
    private TextView goods_title;
    @ViewInject(R.id.goods_details_desc)
    private TextView goods_desc;
    @ViewInject(R.id.goods_details_shop_number)
    private TextView goods_phone;
    @ViewInject(R.id.goods_details_price)
    private TextView goods_price;
    @ViewInject(R.id.goods_details_value)
    private TextView goods_value;
    @ViewInject(R.id.tv_more_details_web_view)
    private WebView wv_goods_more_details;
    @ViewInject(R.id.wv_goods_details_warm_prompt)
    private WebView wv_goods_more_warm_prompt;
    @ViewInject(R.id.goods_details_shop_title)
    private TextView shop_title;
    @ViewInject(R.id.goods_details_shop_number)
    private TextView shop_phone;

    private Goods goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuan_goods_details);
        ViewUtils.inject(this);
        //TextView的文字中的中划线效果
        goods_value.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //让WebView自适应屏幕
        WebSettings webSettings = wv_goods_more_details.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebSettings webSettings1 = wv_goods_more_warm_prompt.getSettings();
        webSettings1.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            goods = (Goods) bundle.get("goods");
            //更新页面上的所有内容
            updateTitleImage();
            updateGoodsInfo();
            updateShopInfo();
            updateMoreDetails();

        }


    }
    @OnClick({R.id.goods_details_shop_call,R.id.goods_details_back})
    private void onClick(View view ){
        switch (view.getId()){
            case R.id.goods_details_shop_call:
                Intent callIn = new Intent(Intent.ACTION_DIAL);
                callIn.setData(Uri.parse("tel:" + goods.getShop().getTel()));
                startActivity(callIn);
                break;
            case R.id.goods_details_back:
                finish();
                break;
            default:
                break;
        }
    }
    //更新商品的标题图片
    private void updateTitleImage() {
        Picasso.with(this).load(goods.getImgUrl())
                .placeholder(R.drawable.default_pic)
                .into(goods_image);

    }

    private void updateGoodsInfo() {
        goods_title.setText(goods.getSortTitle());
        goods_desc.setText(goods.getTip());
        goods_price.setText("￥"+goods.getPrice());
        goods_value.setText("￥" + goods.getValue());
    }
    private void updateShopInfo() {
        Shop shop = goods.getShop();
        shop_title.setText(shop.getName());
        shop_phone.setText(shop.getTel());

    }
    private void updateMoreDetails() {
        String datas[] = htmlSub(goods.getDetail());
        wv_goods_more_details.loadDataWithBaseURL("",datas[0],"text/html","UTF-8","");
        wv_goods_more_warm_prompt.loadDataWithBaseURL("",datas[1],"text/html","UTF-8","");
    }
    //对Detail的解析
    private String[] htmlSub(String html) {
        String datas[] = new String[3];
        char[] str =html.toCharArray();
        long length = html.length();
        int firIndex = 0, secIndex = 1, thiIndex = 2, n =0;
        for(int i= 0; i<length; i++){
            if (str[i]=='【'){
                n++;
                if (1 == n) firIndex = i;
                if (2 == n) secIndex = i;
                if (3 == n) thiIndex = i;
            }

        }
        if(firIndex >0 && secIndex > 1 && thiIndex >2){
            datas[0] = html.substring(firIndex,secIndex);
            datas[1] = html.substring(secIndex,thiIndex);
            datas[2] = html.substring(thiIndex, (int) (length));
        }

        return datas;
    }


}
