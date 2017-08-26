package com.example.jack.musicdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.ui.MainMusic.BottomFragment;
import com.example.jack.musicdemo.ui.play.PlayingActivity;

import static android.R.attr.fragment;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          /*    沉浸状态栏 做笔记用*/
        if (Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置不能横屏
      //开启服务初始化
     //   MusicServiceHelper.get(getApplicationContext()).initService();
        MusicPlayerManager.startServiceIfNecessary(getApplicationContext());
       showQuickControl(true);   //导航栏显示
    }
    /**
     * snackbar的显示
     * @param view
     * @param text
     */
    public void showSnakBar(View view, @Nullable String text){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
    }
    public void showSnakBar(View view, int resID){
        Snackbar.make(view,resID,Snackbar.LENGTH_SHORT).show();
    }
    public void showToast(int toastRes){
        Toast.makeText(this, getString(toastRes), Toast.LENGTH_SHORT).show();
    }


    /**
     *  启动页面代码，子类只需要用父类的这个方面，传入想要跳转的页面的class就好
     * @param activity
     */
    public void startToActivity(Class activity){
        Intent intent = new Intent();
        intent.setClass(this,activity);
        startActivity(intent);
    }

    /**
     * 跳转音乐界面
     * @return
     */
    public boolean gotoSongPlayerActivity(){
        if(MusicPlayerManager.get().getPlayingSong() == null){
            showToast(R.string.music_playing_none);//没有歌曲
            return  false;
        }
        PlayingActivity.open(this);
        return  true;
    }


    private BottomFragment fragment; //底部播放控制栏

/**
     * @param show 显示或关闭底部播放控制栏
     */

    protected void showQuickControl(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = BottomFragment.newInstance();
                ft.add(R.id.bottom_container,fragment).commitAllowingStateLoss();
            } else {
                ft.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }
    }


}
