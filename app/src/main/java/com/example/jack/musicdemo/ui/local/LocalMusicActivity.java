package com.example.jack.musicdemo.ui.local;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.ui.adapter.LocalMusicAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalMusicActivity extends BaseActivity {


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;

    private LocalMusicAdapter mLocalMusicAdapter;
    private ActionBar ab;


    //向外面提供方法开启页面
    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LocalMusicActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        ButterKnife.bind(this);
        setToolbar();

        mLocalMusicAdapter = new LocalMusicAdapter(getSupportFragmentManager());

        mViewpager.setAdapter(mLocalMusicAdapter);
        mViewpager.setCurrentItem(0);

        mViewpager.setOffscreenPageLimit(mLocalMusicAdapter.getCount());
        mTabLayout.setupWithViewPager(mViewpager);
    }

    /**
     * 设置toobal
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        ab.setTitle("本地音乐");
    }

    //返回按钮监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
