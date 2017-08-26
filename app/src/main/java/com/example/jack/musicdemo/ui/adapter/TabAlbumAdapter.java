package com.example.jack.musicdemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jack.musicdemo.ui.album.MVFragment;
import com.example.jack.musicdemo.ui.album.NewSongFragment;
import com.example.jack.musicdemo.ui.album.RankingFragment;

/**
 * Created by ${justin} on 2017/8/1912: 36
 * WeChat:Justin-Tz
 * 唱片页面适配器,用于加载唱片页面
 */


public class TabAlbumAdapter extends FragmentPagerAdapter{
    private  String[] titles = new String[]{"歌单","MV","排行榜"};
    private NewSongFragment mNewSongFragment;
    private MVFragment mMVFragment;
    private RankingFragment mRankingFragment;
    public TabAlbumAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               if(mNewSongFragment ==null){
                   mNewSongFragment =  mNewSongFragment.getInstance();
               }
               return  mNewSongFragment;
           case 1:
               if(mMVFragment == null){
                   mMVFragment = mMVFragment.getInstance();
               }
            return  mMVFragment;
           case 2:
               if(mRankingFragment ==null){
                   mRankingFragment = mRankingFragment.getInstance();
               }
             return mRankingFragment;
       }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    //让字符串加载进去tab
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
