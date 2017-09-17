package com.cainiao5.cainiaomusic.ui.local;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.MusicUtils;
import com.cainiao5.cainiaomusic.data.ArtistInfo;
import com.cainiao5.cainiaomusic.ui.adapter.ArtistAdapter;
import com.cainiao5.cainiaomusic.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<ArtistInfo> artistInfos;
    private RecyclerView.ItemDecoration itemDecoration;
    private ArtistAdapter mAdapter;

    public ArtistFragment() {
        // Required empty public constructor
    }

    public static ArtistFragment newInstance() {
        ArtistFragment fragment = new ArtistFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArtistAdapter(null,getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        setItemDecoration();
        reloadAdapter();

        return view;
    }

    //更新adapter界面
    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                List<ArtistInfo> artList = MusicUtils.queryArtist(getActivity());
                if (artList != null)
                    mAdapter.updateDataSet(artList);
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

        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
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
