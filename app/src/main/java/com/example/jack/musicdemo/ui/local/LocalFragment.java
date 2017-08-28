package com.example.jack.musicdemo.ui.local;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.collection.CollectionCreateActivity;
import com.example.jack.musicdemo.data.CollectionBean;
import com.example.jack.musicdemo.ui.adapter.CollectionAdapter;
import com.example.jack.musicdemo.ui.adapter.OnItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 * toolbar本地音乐碎片
 */
public class LocalFragment extends Fragment {


    @Bind(R.id.local_layout)
    RelativeLayout mLocalLayout;
    @Bind(R.id.recently_layout)
    RelativeLayout mRecentlyLayout;
    @Bind(R.id.download_layout)
    RelativeLayout mDownloadLayout;
    @Bind(R.id.artist_layout)
    RelativeLayout mArtistLayout;
    @Bind(R.id.add)
    ImageView mAdd;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CollectionAdapter mCollectionAdapter;
    private CompositeSubscription allSubscription  = new CompositeSubscription();

    public static LocalFragment getInstance() {
        LocalFragment localFragment = new LocalFragment();
        return localFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCollectionAdapter = new CollectionAdapter(getActivity());

        setHeaderView(mRecyclerView);
      mRecyclerView.setAdapter(mCollectionAdapter);

        mCollectionAdapter.setItemClickListenter(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(CollectionBean item, int position) {
                //若点击哪个收藏夹就跳转

            }

            @Override
            public void onItemSettingClick(View v, CollectionBean item, int position) {
                //若点击三个点就弹出设置列表

            }
        });
        return view;
    }

    /**
     * 添加头布局，就是 我最喜欢的音乐
     * @param recyclerView
     */
    private void setHeaderView(RecyclerView recyclerView) {
        View header = LayoutInflater.from(getActivity())
                //将参1设置到参2中
                .inflate(R.layout.fragment_main_playlist_first_item,recyclerView,false);
        mCollectionAdapter.setHeaderView(header);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.local_layout, R.id.recently_layout, R.id.download_layout, R.id.artist_layout, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.local_layout:
                LocalMusicActivity.open(getActivity());
                break;
            case R.id.recently_layout:
                RecentPlayListActivity.open(getActivity());
                break;
            case R.id.download_layout:
                break;
            case R.id.artist_layout:
                break;
            case R.id.add:
                CollectionCreateActivity.open(getActivity());
                break;
        }
    }


}
