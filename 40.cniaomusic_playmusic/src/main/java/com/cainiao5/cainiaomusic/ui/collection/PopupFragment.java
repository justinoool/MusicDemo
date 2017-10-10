package com.cainiao5.cainiaomusic.ui.collection;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.HandlerUtil;
import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.data.MusicInfo;
import com.cainiao5.cainiaomusic.data.OverFlowItem;
import com.cainiao5.cainiaomusic.db.CollectionManager;
import com.cainiao5.cainiaomusic.ui.adapter.CollectionAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.MusicFlowAdapter;
import com.cainiao5.cainiaomusic.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopupFragment extends DialogFragment {
    public static final int TYPE_DOWNLOAD = 0;  //说明是带有Header的
    public static final int TYPE_SHARE = 1;  //说明是带有Footer的
    public static final int TYPE_EDITOR = 2;  //说明是不带有header和footer的
    public static final int TYPE_DELETE = 3;  //说明是不带有header和footer的


    @Bind(R.id.pop_list)
    RecyclerView recyclerView;
    private Context mContext;
    private Handler mHandler;
    private LinearLayoutManager layoutManager;
    private List<OverFlowItem> mlistInfo = new ArrayList<>();  //声明一个list，动态存储要显示的信息
    private MusicFlowAdapter musicflowAdapter;
    private MusicInfo adapterMusicInfo;
    private CollectionAdapter collectionAdapter;
    private CollectionBean bean;
    public PopupFragment() {
        // Required empty public constructor
    }

    public static PopupFragment newInstance(CollectionBean item, CollectionAdapter collectionAdapter1) {

        PopupFragment fragment = new PopupFragment();
        fragment.bean = item;
        fragment.collectionAdapter = collectionAdapter1;
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
        mContext = getContext();
        mHandler = HandlerUtil.getInstance(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);

        View inflate = inflater.inflate(R.layout.fragment_popup, container, false);
        ButterKnife.bind(this, inflate);

        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        setMusicInfo();
        Log.e("mlistInfo: ", "mlistInfo: " + mlistInfo.size());
        musicflowAdapter = new MusicFlowAdapter(getActivity(), mlistInfo);

        recyclerView.setAdapter(musicflowAdapter);
        musicflowAdapter.setOnItemClickListener(new MusicFlowAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int index = Integer.valueOf(data);
                switch (index){
                    case TYPE_DOWNLOAD:
                        break;
                    case TYPE_SHARE:
                        break;
                    case TYPE_EDITOR:
                        CollectionCreateActivity.open(getActivity(), bean.getId());
                        break;
                    case TYPE_DELETE:
                        CollectionManager.getInstance().deleteCollection(bean);
                        collectionAdapter.deleteCollection(bean);
                        break;
                }
                dismiss();
            }
        });
        setItemDecoration();
        return inflate;
    }


    //设置分割线
    private void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }


    //设置音乐overflow条目
    private void setMusicInfo() {
        //设置mlistInfo，listview要显示的内容
//        setInfo("下一首播放", R.drawable.lay_icn_next);
//        setInfo("收藏到歌单", R.drawable.lay_icn_fav);
//        setInfo("分享", R.drawable.lay_icn_share);
//        setInfo("删除", R.drawable.lay_icn_delete);
//        setInfo("设为铃声", R.drawable.lay_icn_ring);
//        setInfo("查看歌曲信息", R.drawable.lay_icn_document);

        setInfo("下载",R.drawable.ic_red_download);
        setInfo("分享",R.drawable.ic_red_share);
        setInfo("编辑歌单信息",R.drawable.ic_red_editor);
        setInfo("删除",R.drawable.ic_red_delete);

    }

    //为info设置数据，并放入mlistInfo
    public void setInfo(String title, int id) {
        // mlistInfo.clear();
        OverFlowItem information = new OverFlowItem();
        information.setTitle(title);
        information.setAvatar(id);
        mlistInfo.add(information); //将新的info对象加入到信息列表中
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.4);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
