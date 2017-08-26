package com.example.jack.musicdemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jack.musicdemo.ui.local.ArtistFragment;
import com.example.jack.musicdemo.ui.local.LocalAlbumFragment;
import com.example.jack.musicdemo.ui.local.LocalFragment;
import com.example.jack.musicdemo.ui.local.LocalMusicActivity;
import com.example.jack.musicdemo.ui.local.LocalMusicFragment;

/**
 * Created by ${justin} on 2017/8/1914: 56
 * WeChat:Justin-Tz
 * 本地音乐界面的适配器 作用将tab与viewpager联系
 */

public class LocalMusicAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"单曲", "歌手", "专辑"};
    private LocalMusicFragment mLocalMusicFragment;
    private LocalAlbumFragment mLocalAlbumFragment;
    private ArtistFragment mArtistFragment;

    public LocalMusicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (mLocalMusicFragment == null) {
                    mLocalMusicFragment = mLocalMusicFragment.newInstance();
                }
                return mLocalMusicFragment;
            case 1:
                if (mArtistFragment == null) {
                    mArtistFragment = mArtistFragment.newInstance();
                }
                return mArtistFragment;
            case 2:
                if (mLocalAlbumFragment == null) {
                    mLocalAlbumFragment = mLocalAlbumFragment.newInstance();
                }
                return mLocalAlbumFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
