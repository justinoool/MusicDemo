package com.example.jack.musicdemo.ui.album;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jack.musicdemo.R;

/**
 * 音乐专辑详情页面 与 收藏夹歌单是同一个布局
 */
public class MusicDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
    }
}
