package com.example.jack.musicdemo.ui.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 自定义播放控件
 * Created by ${justin} on 2017/8/1412: 00
 * WeChat:Justin-Tz
 */

public class CustomView extends VideoView {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec);
    View调用此方法来确定本身和所包含内容的大小。此方法被measure(int,int)唤起，而且必须被子类重写以得到所包含内容的确切大小。
    注意：当重写此方法时，必须调用setMeasureDimension(int,int)来保存View的大小。如果没有做到，将会引发一个measure(int,int)抛出的IllegalStateException（非法状态错误）。超类onMeasure(int,int)可以被调用。
    编写基类的确认大小的方法，缺省情况下是根据其背景大小来确认，除非MeasureSepc允许有更大的高度或宽度。子类必须重写onMeasure(int,int)以得到对其内容大小的更准确的测量。
    若此方法被重写，它的子类需要确保其高度和宽度至少达到View所规定的最小值
   （可通过getSuggestedMinimumHeight()和getSuggestedMinimumWidth()得到）。
参数
        widthMeaureSpec           受上一层大小影响下的对水平空间的要求。可参看View.MeasureSpec。
        heightMeasureSpec         受上一层大小影响下的对垂直空间的要求。可参看View.MeasureSpec。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 播放视频
     * @param uri
     */
    public void playVideo(Uri uri){
        if(uri == null){
            throw new IllegalArgumentException("uri can not be null");

        }
        /**设置播放路径*/
        setVideoURI(uri);
        /**开始播放*/
        start();

        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               //设置循环播放
                mp.setLooping(true);
            }
        });

        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //错误监听
                return true;
            }
        });
    }
}
