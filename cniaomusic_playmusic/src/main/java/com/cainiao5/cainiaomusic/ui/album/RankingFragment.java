package com.cainiao5.cainiaomusic.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.RankingInfo;
import com.cainiao5.cainiaomusic.ui.adapter.RankingAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * A simple {@link Fragment} subclass.
 * 排行榜
 */
public class RankingFragment extends Fragment {

    private RecyclerView recycleViewRanking;
    private RankingAdapter mRankingAdapter;
    private RankingInfo rankingInfo;
    private int type = -1;
    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rankingView = inflater.inflate(R.layout.fragment_ranking, container, false);
        initData();
        recycleViewRanking = (RecyclerView) rankingView.findViewById(R.id.recycleView_ranking);
        recycleViewRanking.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRankingAdapter = new RankingAdapter(getActivity());
        recycleViewRanking.setAdapter(mRankingAdapter);
        mRankingAdapter.setOnItemClickLitener(new RankingAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int type) {
                RankingDetailActivity.open(getActivity(),type);
            }
        });
        return rankingView;
    }

    private void initData() {

//        Observable<RankingInfo> observable = RetrofitManager.getInstance()
//                .getAPIService(ApiStore.BASE_PARAMETERS_URL)
//                .getRankingListInfo();
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<RankingInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(RankingInfo rankingInfo) {
//                        mRankingAdapter.setData(rankingInfo.getContent());
//                    }
//                });

        OkGo.<String>get(ApiStore.BASE_PARAMETERS_URL + "cainiaomusic/ranking_last")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new GsonBuilder().create();
                        RankingInfo rankingInfo = gson.fromJson(response.body(), RankingInfo.class);
                        mRankingAdapter.setData(rankingInfo.getContent());
                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            Log.e("RankingFragment: ", "setUserVisibleHint: ");
        }
    }



}
