package com.fukai.dianping_client.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fukai.dianping_client.R;

/**
 * Created by fukai on 2015/11/28.
 */
//绘制对应的英文字母
public class SideBar extends View {
    //new 对象时调用
    public SideBar(Context context) {
        super(context);
    }
    //xml文件创建控件对象时调用
    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private Paint paint = new Paint();
    public static String[] sideBar = {"热门", "A", "B", "C", "D", "E", "F", "G"
                                        , "H", "I", "J", "K","L","M","N","O","P"
                                        ,"Q","R","S","T","U","V","W","X","Y","Z"};

    private OnTouchingLetterChangeListener letterChangeListener;
    private int choose;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setTextSize(20);

        int height = getHeight();
        int width = getWidth();
        int each_heigth = height/sideBar.length;

        for (int i = 0; i < sideBar.length; i++){
            float x = width/2-paint.measureText(sideBar[i])/2;
            float y = (1 + i) * each_heigth;
            canvas.drawText(sideBar[i], x, y, paint);
        }
    }
    //定义监听事件
    public interface OnTouchingLetterChangeListener{
        //s 代表字母索引
        void OnTouchingLetterChanged(String s);
    }

    public void setOnTouchingLetterChangeListener(OnTouchingLetterChangeListener onTouchingLetterChangeListener){
        this.letterChangeListener = onTouchingLetterChangeListener;
    }

    //分发对应的touch事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();//获取对应的动作
        final float y = event.getY();//点击的y坐标
        final OnTouchingLetterChangeListener listener = letterChangeListener;
        final int c = (int) (y/getHeight()*sideBar.length);//获取点击y轴坐标所占总高度的比例*数组的长度就是数组中字母索引
        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundResource(android.R.color.transparent);
                invalidate();
                break;
            default:
                setBackgroundResource(R.drawable.side_bar_bg);
                if (c > 0 && c <sideBar.length){
                    if (listener != null) {
                        listener.OnTouchingLetterChanged(sideBar[c]);
                    }
                    choose = c;
                    invalidate();
                }
                break;

        }
        return true;
    }
}
