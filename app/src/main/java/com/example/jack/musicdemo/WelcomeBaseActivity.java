package com.example.jack.musicdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.MusicServiceHelper;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

/**
 * Created by ${justin} on 2017/8/2519: 01
 * WeChat:Justin-Tz
 * 引导页和登录界面的基类
 */

public class WelcomeBaseActivity extends AppCompatActivity {


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
           MusicServiceHelper.get(getApplicationContext()).initService();

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
}
