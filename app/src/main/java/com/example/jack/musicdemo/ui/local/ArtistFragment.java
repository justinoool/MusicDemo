package com.example.jack.musicdemo.ui.local;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.utils.MusicUtils;
import com.example.jack.musicdemo.data.ArtistInfo;
import com.example.jack.musicdemo.ui.adapter.ArtistAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 本地音乐下的 歌手碎片
 */
public class ArtistFragment extends Fragment {


    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager mLayoutManager;
    private List<ArtistInfo> mArtistInfos;
    private ArtistAdapter mAdapter;
    private RecyclerView.ItemDecoration mItemDecoration;

    public ArtistFragment() {
        // Required empty public constructor
    }

    public static ArtistFragment newInstance() {
        ArtistFragment artistFragment = new ArtistFragment();
        return artistFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mAdapter = new ArtistAdapter(null,getActivity());//因为图片都是网络上获取的，这里暂时给null
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setHasFixedSize(true);//设置固定大小 当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)
        //通过更改recycleView的动画，减少动画时间可以减少item的闪烁。
        ((SimpleItemAnimator)mRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);//不修改动画
         setItemDecoration();
        reloadAdapter();
        return view;
    }
//更新adapter界面
    private void reloadAdapter() {
         new AsyncTask<Void , Void ,Void>(){
             @Override
             protected Void doInBackground(Void... params) {
                List<ArtistInfo> artList = MusicUtils.queryArtist(getActivity());
                 if(artList != null){
                     mAdapter.updateDataSet(artList);
                 }
                 return null;
             }
             @Override
             protected void onPostExecute(Void aVoid) {
                 mAdapter.notifyDataSetChanged();
             }
         }.execute();
    }

  //设置分割线
    private void setItemDecoration() {
        mItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerview.addItemDecoration(mItemDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
     mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
