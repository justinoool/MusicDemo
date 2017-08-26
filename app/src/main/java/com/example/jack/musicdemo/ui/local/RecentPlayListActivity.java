package com.example.jack.musicdemo.ui.local;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.music.MusicPlaylist;
import com.example.jack.musicdemo.music.MusicRecentPlayList;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.OnSongchangeListener;
import com.example.jack.musicdemo.ui.adapter.RecentPlayAdapter;
import com.lb.materialdesigndialog.base.DialogBase;
import com.lb.materialdesigndialog.base.DialogWithTitle;
import com.lb.materialdesigndialog.impl.MaterialDialogNormal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.y;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

/**
 * 最近播放列表Activity
 */
public class RecentPlayListActivity extends AppCompatActivity implements OnSongchangeListener {

    @Bind(R.id.btnRight)
    Button mBtnRight;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ActionBar ab;
    private RecentPlayAdapter mRecentPlayAdapter;
    private MusicPlaylist mMusicPlaylist;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RecentPlayListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_play_list);

        ButterKnife.bind(this);
        setToolbar();

        MusicPlayerManager.get().registerListener(this);

        initRecyclerView();
    }

    /**
     * 初始化Recyclerview
     */
    private void initRecyclerView() {
           mBtnRight.setText("清空");
        mRecentPlayAdapter = new RecentPlayAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置排列
        mRecyclerView.setAdapter(mRecentPlayAdapter);
        mRecentPlayAdapter.setData(MusicRecentPlayList.getInstance().getQueue());
        //播放列表
        mMusicPlaylist = new MusicPlaylist(MusicRecentPlayList.getInstance().getQueue());

        //设置menu的点击监听
    }

    /**
     * 设置toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("最近播放");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
    }

    /**
     * 清空,用了一个库  compile 'com.lb:materialdesigndialog:1.0.0'
     */
    @OnClick(R.id.btnRight)
    public void onViewClicked() {
        MaterialDialogNormal d =   new MaterialDialogNormal(this);
      d.setTitle("");
        d.setMessage("清空全部所有最近播放记录？");
        d.setNegativeButton("取消", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                dialog.dismiss();
            }
        });
        d.setPositiveButton("清空", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                MusicRecentPlayList.getInstance().clearRecentPlayList();
                mRecentPlayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
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

    @Override
    public void onSongChanged(Song song) {

    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }
}
