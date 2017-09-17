package com.cainiao5.cainiaomusic.ui.collection;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

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
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    @Bind(R.id.pop_layout)
    LinearLayout mPopLayout;
    private Context mContext;
    private Handler mHandler;
    private LinearLayoutManager layoutManager;
    private List<OverFlowItem> mlistInfo = new ArrayList<>();  //声明一个list，动态存储要显示的信息
    private MusicFlowAdapter musicflowAdapter;
    private MusicInfo adapterMusicInfo;
    private CollectionAdapter collectionAdapter;
    private CollectionBean bean;
    private int mPosition;

    public PopupFragment() {
        // Required empty public constructor
    }

    public static PopupFragment newInstance(int position, CollectionBean item, CollectionAdapter collectionAdapter1) {

        PopupFragment fragment = new PopupFragment();
        fragment.bean = item;
        fragment.collectionAdapter = collectionAdapter1;
        fragment.mPosition = position;
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
        musicflowAdapter = new MusicFlowAdapter(getActivity(), mlistInfo);

        recyclerView.setAdapter(musicflowAdapter);
        musicflowAdapter.setOnItemClickListener(new MusicFlowAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int index = Integer.valueOf(data);
                switch (index) {
                    case TYPE_DOWNLOAD:
                        break;
                    case TYPE_SHARE:
                        testShare();

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

    /***
     * 分享测试
     */
    private void testShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getActivity());

    }


    //设置分割线
    private void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }


    //设置音乐overflow条目
    private void setMusicInfo() {
        if (mPosition == 0) {
            setInfo("下载", R.drawable.ic_red_download);
            setInfo("分享", R.drawable.ic_red_share);
            return;
        } else {
            //设置mlistInfo，listview要显示的内容
            setInfo("下载", R.drawable.ic_red_download);
            setInfo("分享", R.drawable.ic_red_share);
            setInfo("编辑歌单信息", R.drawable.ic_red_editor);
            setInfo("删除", R.drawable.ic_red_delete);
        }


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
        ViewTreeObserver vto = mPopLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //不移除的话会调用多次,但是使用该方法的话需要api16
//                mPopLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measuredHeight = mPopLayout.getMeasuredHeight();
                getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, measuredHeight);
                getDialog().setCanceledOnTouchOutside(true);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
