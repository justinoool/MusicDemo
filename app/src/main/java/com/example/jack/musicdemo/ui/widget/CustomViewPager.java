package com.example.jack.musicdemo.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义viewpager
 */
public class CustomViewPager extends ViewPager {


    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    PointF mPointF = new PointF();

    OnSingleTouchListner mOnSingleTouchListner;


    /*
    重写触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //获取按下的坐标，相当于把它存在Pointf
                mPointF.x = ev.getX();
                mPointF.y = ev.getY();
                if(this.getChildCount() >1 ){ //相当于有内容，将点击事件留给viewpager处理
                    /*
                    ViewPager.getChildCount() 含义
            viewpager.getChildCount() 很容易误解成viewpager子页面的size，它和getCount还是有区别的
            getChildCount() 是表示当前可见页size
            比如：Viewpager总共3页
            当到第一页时候可见页面为2（在滑动过程，可见第一张和第二张），getChildCount() =2，
            当到第二页时候可见页为3（在左右滑动过程，可见第一张，第二张和第三张），getChildCount() =3，
            当到第三页时候可见页为2（在滑动过程，可见第二张和第三张），getChildCount() =2，
                     */
                    //通知父控件不进行拦截点击事件
                   getParent().requestDisallowInterceptTouchEvent(true);
               }
                break;
            case MotionEvent.ACTION_MOVE:
                if(this.getChildCount() > 1){
                    //通知父控件不进行拦截点击事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP: //抬起，
                //判断 按下和松开是不是同一个点
                if(PointF.length(ev.getX() - mPointF.x,ev.getY()-mPointF.y)< (float) 5.0){
                    //如果大于5就认定为是滑动
                    //单纯的点击
                    onSingleTouch(this);

                }

                break;


        }

        return super.onTouchEvent(ev);
    }
//单纯点击，点击后去回调实现接口，在接口里实现监听
    private void onSingleTouch(View view) {
        if(mOnSingleTouchListner != null){
            mOnSingleTouchListner.onSingleTouch();
        }

    }

  //创建点击回调接口
    public interface OnSingleTouchListner{
        void onSingleTouch();
    }

    //设置调用接口的方法，参数是实现接口
    public void setOnSingleTouchListner(OnSingleTouchListner onSingleTouchListner){
        this.mOnSingleTouchListner = onSingleTouchListner;
    }



}