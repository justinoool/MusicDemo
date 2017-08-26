package com.example.jack.musicdemo.ui.guide;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.WelcomeBaseActivity;
import com.example.jack.musicdemo.ui.MainMusic.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视屏引导页
 */
public class GuideActivity extends WelcomeBaseActivity {

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
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initView() {
        mVp.setOffscreenPageLimit(3);
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));//
        mVp.addOnPageChangeListener(new MyPagerListener());//viewpager监听
    }

    /**
     * 初始化数据，添加三个fragment
     */
    private void initData() {
        mFragments = new ArrayList<>();
        GuideFragment fragment1 = new GuideFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);  //指定一个下标，好让滑动是执行下一个fragment
        fragment1.setArguments(bundle1);//注意要将bundel存入，不让在GuideActivity获取下标会出现空指针异常
        mFragments.add(fragment1);

        GuideFragment fragment2 = new GuideFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("index", 2);  //指定一个下标，好让滑动是执行下一个fragment
        fragment2.setArguments(bundle2);
        mFragments.add(fragment2);

        GuideFragment fragment3 = new GuideFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("index", 3);  //指定一个下标，好让滑动是执行下一个fragment
        fragment3.setArguments(bundle3);
        mFragments.add(fragment3);


    }

    //立即体验按钮
    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //由于逻辑性并不是很强所以没抽取到data
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //viewpager监听
    public class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 切换下标点，改变下标点颜色
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
