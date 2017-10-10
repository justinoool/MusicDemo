package com.cainiao5.cainiaomusic.ui.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cainiao5.cainiaomusic.BaseActivity;
import com.cainiao5.cainiaomusic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GuideActivity extends BaseActivity {
    @Bind(R.id.vp)
    ViewPager mVp;
    @Bind(R.id.iv1)
    ImageView mIv1;
    @Bind(R.id.iv2)
    ImageView mIv2;
    @Bind(R.id.iv3)
    ImageView mIv3;
    @Bind(R.id.bt_start)
    Button mBtStart;
    private ArrayList<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initView() {
        mVp.setOffscreenPageLimit(3);
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mVp.addOnPageChangeListener(new MyPagerListner());
    }

    /***
     * 初始化数据,添加三个fragment
     */
    private void initData() {
        fragments = new ArrayList<>();
        GuideFragment fragment1 = new GuideFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

        GuideFragment fragment2 = new GuideFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("index", 2);
        fragment2.setArguments(bundle2);
        fragments.add(fragment2);

        GuideFragment fragment3 = new GuideFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("index", 3);
        fragment3.setArguments(bundle3);
        fragments.add(fragment3);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    public class MyPagerListner implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /***
         * 切换下标点
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            mBtStart.setVisibility(View.GONE);
            mIv1.setImageResource(R.mipmap.dot_normal);
            mIv2.setImageResource(R.mipmap.dot_normal);
            mIv3.setImageResource(R.mipmap.dot_normal);

            if (position == 0) {
                mIv1.setImageResource(R.mipmap.dot_focus);
            } else if (position == 1) {
                mIv2.setImageResource(R.mipmap.dot_focus);
            } else {
                mIv3.setImageResource(R.mipmap.dot_focus);
                mBtStart.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
