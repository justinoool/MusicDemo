package com.example.jack.musicdemo.ui.local;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.jack.musicdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    public LocalFragment() {
        // Required empty public constructor
    }

    public static LocalFragment getInstance() {
        LocalFragment localFragment = new LocalFragment();
        return localFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.local_layout, R.id.recently_layout, R.id.download_layout, R.id.artist_layout})
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
        }
    }
}
