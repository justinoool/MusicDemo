package com.example.jack.musicdemo.ui.guide;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.ui.widget.CustomView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {


    //构造出一个自定义控件
    private CustomView mCustomView;

    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustomView = new CustomView(getContext());
        int index = getArguments().getInt("index");
        Uri uri;
        if(index == 1){
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_1);
        }else if(index ==2){
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_2);
        }else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_3);
        }
        mCustomView.playVideo(uri);//播放
        return mCustomView;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    if(mCustomView != null){
        mCustomView.stopPlayback();
    }
    }

    //懒加载
    /*
    @Override
    protected void lazyLoadData() {
        mCustomView = findViewById(R.id.cv);
        int index = getArguments().getInt("index");//拿到下标
        Uri uri;
        if(index == 1){
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_1);
        }else if(index ==2){
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_2);
        }else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_3);
        }
        mCustomView.playVideo(uri);//播放
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if(mCustomView != null){
            mCustomView.stopPlayback();//停止播放
        }
    }*/
}
