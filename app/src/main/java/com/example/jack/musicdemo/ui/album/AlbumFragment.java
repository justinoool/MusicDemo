package com.example.jack.musicdemo.ui.album;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.ui.adapter.TabAlbumAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * toolbar上的唱片碎片
 */
public class AlbumFragment extends Fragment {


    @Bind(R.id.tbb_layout)
    TabLayout mTbbLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.bottom_container)
    FrameLayout mBottomContainer;
    private TabAlbumAdapter mAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, inflate);
        mAdapter = new TabAlbumAdapter(getChildFragmentManager());

        mViewpager.setAdapter(mAdapter);

        mViewpager.setCurrentItem(0);

        mViewpager.setOffscreenPageLimit(mAdapter.getCount());//设置多少也viewpager

        mTbbLayout.setupWithViewPager(mViewpager);//与这个viewpager联系在一起


        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
