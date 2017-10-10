package com.cainiao5.cainiaomusic.ui.local;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.RxBus;
import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.model.event.CollectionUpdateEvent;
import com.cainiao5.cainiaomusic.ui.adapter.CollectionAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.OnItemClickListener;
import com.cainiao5.cainiaomusic.ui.cnmusic.LocalMusicActivity;
import com.cainiao5.cainiaomusic.ui.cnmusic.RecentPlayListActivity;
import com.cainiao5.cainiaomusic.ui.collection.CollectionPlayActivity;
import com.cainiao5.cainiaomusic.ui.collection.PopupFragment;
import com.cainiao5.cainiaomusic.ui.dynamic.DynamicFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private CollectionAdapter collectionAdapter;
    private CompositeSubscription allSubscription = new CompositeSubscription();

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSubscription.add(RxBus.getDefault()
                .toObservable(CollectionUpdateEvent.class).subscribe(new Action1<CollectionUpdateEvent>() {
                    @Override
                    public void call(CollectionUpdateEvent event) {
                        onEvent(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, inflate);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        collectionAdapter = new CollectionAdapter(getActivity());
        recyclerView.setAdapter(collectionAdapter);
        collectionAdapter.setItemClickListener(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(CollectionBean item, int position) {
                CollectionPlayActivity.open(getActivity(), item);

            }

            @Override
            public void onItemSettingClick(View v, CollectionBean item, int position) {
//                showPopupMenu(v, item, position);
//                PopupFragment popupFragment = new PopupFragment(item,collectionAdapter);
                PopupFragment popupFragment = PopupFragment.newInstance(item, collectionAdapter);
                popupFragment.show(getFragmentManager(),"");
            }
        });
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.recently_layout, R.id.local_layout, R.id.download_layout, R.id.artist_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recently_layout:
                RecentPlayListActivity.open(getActivity());
                break;
            case R.id.local_layout:
                startActivity(new Intent(getActivity(), LocalMusicActivity.class));
                break;
            case R.id.download_layout:
                break;
            case R.id.artist_layout:
                startActivity(new Intent(getActivity(), LocalMusicActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!allSubscription.isUnsubscribed()) {
            allSubscription.unsubscribe();
        }
    }

    public void onEvent(CollectionUpdateEvent event) {
        collectionAdapter.update();
    }
}
