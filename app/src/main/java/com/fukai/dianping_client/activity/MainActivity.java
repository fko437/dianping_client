package com.fukai.dianping_client.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.fragment.FragmentHome;
import com.fukai.dianping_client.fragment.FragmentMy;
import com.fukai.dianping_client.fragment.FragmentSearch;
import com.fukai.dianping_client.fragment.FragmentTuan;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by fukai on 2015/11/24.
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;
    public FragmentManager fragmentManager; //管理fragmen类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ViewUtils.inject(this);
        fragmentManager = getFragmentManager();
        main_home.setChecked(true);
        changeFragment(new FragmentHome(), true);
        group.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_home:
            changeFragment(new FragmentHome(),true);
                break;
            case R.id.main_tuan:
                changeFragment(new FragmentTuan(),true);
                break;
            case R.id.main_search:
                changeFragment(new FragmentSearch(),true);
                break;
            case R.id.main_my:
                changeFragment(new FragmentMy(),true);
                break;
            default:
                break;
        }
    }
    //切换不同的fragment
    private void changeFragment(Fragment fragment, boolean isFirst) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace( R.id.main_content,fragment);
        //可以防止页面重影效果
        if (!isFirst){
            transaction.addToBackStack(null);
        }
        transaction.commit();

    }
}
