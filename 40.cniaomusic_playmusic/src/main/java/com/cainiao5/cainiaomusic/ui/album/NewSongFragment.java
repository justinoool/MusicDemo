package com.cainiao5.cainiaomusic.ui.album;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.MyApplication;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.ui.cnmusic.BaseFragment;
import com.cainiao5.cainiaomusic.ui.widget.RefreshRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * 新曲
 */
public class NewSongFragment extends BaseFragment implements RefreshRecyclerView.RefreshListener {
    public static final String TITLE = MyApplication.getInstance().getString(R.string.album);

    public NewSongFragment() {
        // Required empty public constructor
    }

    public static NewSongFragment newInstance() {
        NewSongFragment fragment = new NewSongFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View parentView = inflater.inflate(R.layout.fragment_new_song, container, false);

        return parentView;
    }

//    private void initRefreshView() {
//        albumAdapter = new AlbumAdapter(getActivity());
//        refreshView.setAdapter(albumAdapter);
//        refreshView.setHeaderView(headerView);
//        refreshView.setRefreshListener(this);
//        refreshView.setLoadEnable(false);
//        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        refreshView.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch (refreshView.getRefreshRecycleAdapter().getItemViewType(position)) {
//                    case RefreshRecyclerView.RefreshRecycleAdapter.HEADER:
//                    case RefreshRecyclerView.RefreshRecycleAdapter.LOADMORE:
//                    case AlbumAdapter.ALBUM_TYPE_NEW:
//                    case AlbumAdapter.ALBUM_TYPE_HOT:
//                        return gridLayoutManager.getSpanCount();
//                    default:
//                        return 1;
//                }
//            }
//        });
//        refreshView.startSwipeAfterViewCreate();
//    }


    @Override
    public void onSwipeRefresh() {

    }

    @Override
    public void onLoadMore() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
