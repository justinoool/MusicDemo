package com.example.jack.musicdemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import static android.R.attr.name;
import static com.example.jack.musicdemo.R.drawable.c;

/**
 * Created by ${justin} on 2017/8/2117: 14
 * WeChat:Justin-Tz
 *
 *   服务帮助类
 */

public class MusicServiceHelper {
    private Context mContext;

    private static  MusicServiceHelper sMusicServiceHelper = new MusicServiceHelper();
    public  static  MusicServiceHelper get(Context context){
        sMusicServiceHelper.mContext=context;
        return sMusicServiceHelper;
    }

 public static MusicService mMusicService;
    /**
     * 服务初始化
     *作用：在你需要开启服务的地方调用这个方法
     *
     */
    public void initService(){
        if(mMusicService == null){
            Intent intent = new Intent(mContext, MusicService.class);
            ServiceConnection conn =new ServiceConnection(){

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MusicService.MyBingder bingder= (MusicService.MyBingder) service;
                  mMusicService = bingder.getMusicService();
                    mMusicService.setUp();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };

            mContext.startService(intent);
            mContext.bindService(intent,conn,Context.BIND_ABOVE_CLIENT);
        }
    }
          public static MusicService getMusicService(){
              return mMusicService;
          }
}
