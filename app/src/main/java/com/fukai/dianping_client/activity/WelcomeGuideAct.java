package com.fukai.dianping_client.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.activity.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fukai on 2015/11/24.
 */
public class WelcomeGuideAct extends Activity {

    @ViewInject(R.id.welcome_pager_btn)
    private Button btn;
    @ViewInject(R.id.welcome_pager)
    private ViewPager viewPager;
    private List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_guide);
        ViewUtils.inject(this);
        initViewPager();
    }

    @OnClick(R.id.welcome_pager_btn)
    public void click(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }
    //初始化ViewPager
    @TargetApi(Build.VERSION_CODES.M)
    public void initViewPager(){
         list = new ArrayList<View>();
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.guide_01);
        list.add(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.guide_02);
        list.add(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.guide_03);
        list.add(iv3);
        viewPager.setAdapter(new MyPagerAdapter());
        //监听viewpager滑动效果
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //初始化item实例方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
//            return super.instantiateItem(container, position);
            return list.get(position);
        }
        //item销毁方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
//            super.destroyItem(container, position, object);
        }
    }
}
