package com.cainiao5.cainiaomusic.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 服务帮助类
 *
 */
public class MusicServiceHelper {
    Context cxt;
    private static MusicServiceHelper i = new MusicServiceHelper();

    public static MusicServiceHelper get(Context c) {
        i.cxt = c;
        return i;
    }

    private MusicServiceHelper() {
    }

    MusicService mService;

    public void initService() {
        if (mService == null) {
            Intent i = new Intent(cxt, MusicService.class);
            ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MusicService.MyBinder binder = (MusicService.MyBinder) service;
                    mService = binder.getMusicService();
                    mService.setUp();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mService = null;
                }
            };
            cxt.startService(i);
            cxt.bindService(i, conn, Context.BIND_AUTO_CREATE);
        }
    }

    public MusicService getService() {
        return mService;
    }
}
