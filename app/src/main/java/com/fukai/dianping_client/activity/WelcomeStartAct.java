package com.fukai.dianping_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fukai.dianping_client.R;
import com.fukai.dianping_client.utils.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fukai on 2015/11/24.
 */
//延时可以使用handler
public class WelcomeStartAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

//        new Handler(new Handler.Callback() {
//            //处理接受消息的方法
//            @Override
//            public boolean handleMessage(Message msg) {
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                return false;
//            }
//        }).sendEmptyMessageDelayed(0,3000);

        //还可以使用java中的定时器进行处理
        Timer timer = new Timer();
        timer.schedule(new Task(),3000);
    }
    class Task extends TimerTask{

        @Override
        public void run() {
            //实现页面跳转
            if(SharedUtils.getWelcomeBoolean(getApplicationContext())) {
                SharedUtils.putWelcomeBoolean(getApplicationContext(),false);
                startActivity(new Intent(WelcomeStartAct.this, WelcomeGuideAct.class));

            }else{
                startActivity(new Intent(WelcomeStartAct.this,MainActivity.class));
            }
            finish();
        }
    }
}
