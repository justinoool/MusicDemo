package com.example.jack.musicdemo.ui.guide;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.WelcomeBaseActivity;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

public class WelcomeActivity extends WelcomeBaseActivity {

    private ImageView mLogo;

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
        setContentView(R.layout.activity_welcome);


        mLogo = (ImageView) findViewById(R.id.welcome_Image);
        startAnimation();
    }

    /**
     * 启动也动画
     */
    private void startAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogo, "scaleX", 1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogo, "scaleY", 1f, 1f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLogo, "alpha", 1f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimator).with(scaleX).with(scaleY);
        animatorSet.setDuration(1500);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //当有app用户之后，可以在此判断是否登录

                //需要跳转界面
               startToActivity(GuideActivity.class);
             //   startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }
}
