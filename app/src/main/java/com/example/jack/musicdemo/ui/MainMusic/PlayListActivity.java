package com.example.jack.musicdemo.ui.MainMusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.utils.CommonUtils;
import com.example.jack.musicdemo.common.utils.HandlerUtil;
import com.example.jack.musicdemo.common.utils.IConstants;
import com.example.jack.musicdemo.common.utils.NetworkUtils;
import com.example.jack.musicdemo.data.MusicInfo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

public class PlayListActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    private String playlistId;
    private String albumPath, playlistName, playlistDetail;
    private ArrayList<MusicInfo> adapterList = new ArrayList<>();

    private SimpleDraweeView albumArtSmall;
    private ImageView albumArt;
    private TextView playlistTitleView, playlistDetailView;
    private boolean isLocalPlaylist;

    private PlayListDetailAdapter mAdapter;
    private Toolbar toolbar;
    private FrameLayout loadFrameLayout;
    private int musicCount;
    private Handler mHandler;
    private View loadView;
    private int mFlexibleSpaceImageHeight;
    private ActionBar actionBar;
    private int mActionBarSize;
    private int mStatusSize;
    private TextView tryAgain;
    private TextView playlistCountView;
    private String playlistCount;
    private FrameLayout headerViewContent; //上部header
    private RelativeLayout headerDetail; //上部header信息
    private Context mContext;
    private boolean mCollected;
    private TextView collectText;
    private ImageView collectView;
    private FrameLayout favLayout;
    private LinearLayout share;
    private String TAG = "PlaylistActivity";
    private boolean d = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从前面的点击事件传递数据过来
        if (getIntent().getExtras() != null) {
            isLocalPlaylist = getIntent().getBooleanExtra("islocal", false);
            playlistId  = getIntent().getStringExtra("playlistid");
            albumPath = getIntent().getStringExtra("albumart"); //专辑图片路径
            playlistName = getIntent().getStringExtra("playlistname");
            playlistDetail = getIntent().getStringExtra("playlistDetail");
            playlistCount = getIntent().getStringExtra("playlistcount");
        }

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_play_list);

        loadFrameLayout= (FrameLayout) findViewById(R.id.state_container);

        headerViewContent = (FrameLayout) findViewById(R.id.headerView);
        headerDetail = (RelativeLayout) findViewById(R.id.headerdetail);
        favLayout = (FrameLayout) findViewById(R.id.playlist_fav);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mHandler = HandlerUtil.getInstance(this);

        //高
        mFlexibleSpaceImageHeight=getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize= CommonUtils.getActionBarHeight(this);
        mStatusSize=CommonUtils.getStatusHeight(this);

        tryAgain = (TextView) findViewById(R.id.try_again);  //网络链接
        collectText= (TextView) findViewById(R.id.playlist_collect_state);
        collectView = (ImageView) findViewById(R.id.playlist_collect_view);
        share= (LinearLayout) findViewById(R.id.playlist_share);

        setUpEverything();
    }

    private void setUpEverything() {
        setupToolbar();
        setHeaderView();
        setAlbumart();
        setList();
        loadAllLists();
    }

    private void loadAllLists() {
        if(isLocalPlaylist){
            loadView =LayoutInflater.from(this).inflate(R.layout.loading,loadFrameLayout,false);
            loadFrameLayout.addView(loadView);
            return;
        }
        if(NetworkUtils.isConnectInternet(this)){//是否连接到网络
            tryAgain.setVisibility(View.GONE);
            loadView = LayoutInflater.from(mContext).inflate(R.layout.loading,loadFrameLayout,false);
            loadFrameLayout.addView(loadView);

        }else{
            tryAgain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setList() {
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setScrollViewCallbacks(PlayListActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlayListActivity.this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new PlayListDetailAdapter(PlayListActivity.this,adapterList);
        recyclerView.setAdapter(mAdapter);
        //定义每个item的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(PlayListActivity.this,DividerItemDecoration.VERTICAL));
    }


    private void setAlbumart() {
        playlistTitleView.setText(playlistName);
        albumArtSmall.setImageURI(Uri.parse(albumPath));
    }

    private void setHeaderView() {
        albumArt= (ImageView) findViewById(R.id.album_art);
        playlistTitleView= (TextView) findViewById(R.id.album_title);
        playlistDetailView= (TextView) findViewById(R.id.album_details);
        SpannableString spanString;
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.mipmap.index_icn_earphone);
        ImageSpan imgSpan = new ImageSpan(this,b,ImageSpan.ALIGN_BASELINE);
      /*  通常情况下，我们都是使用字符串类型的变量，直接放置在TextView.setText()的参数里面的
        。这样只能单纯的输出字符串。如果要给TextView加上特殊的文本效果，就要使用SpannableString这个类了*/
        spanString = new SpannableString("icon");
        //参数1：为需要设置的样式，有很多类可以选 参2：开始位置，0表示第一个字符，
        // 参3：是结束位置，参4：是表示替换的位置是否会影响开头和结尾
        spanString.setSpan(imgSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        playlistCountView = (TextView) findViewById(R.id.playlist_listen_count);
        playlistCountView.setText(spanString);

        if(playlistCount == null){
            playlistCount = "0";
        }
        int count = Integer.parseInt(playlistCount);
        if(count > 10000){
            count= count/10000;
            playlistCountView.append(" "+count+"万");
        }else{
            playlistCountView.append(" "+ playlistCount);
        }
        LinearLayout downAll = (LinearLayout) headerViewContent.findViewById(R.id.playlist_down);
        final LinearLayout addToplaylist = (LinearLayout) headerViewContent.findViewById(R.id.playlist_collect);

        //分享
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,
                        Uri.parse("http://music.baidu.com/songlist/"+ playlistId));
                shareIntent.setType("html/*");
                //Intent.createChooser自定义对话框
                startActivity(Intent.createChooser(shareIntent,getResources().getString(R.string.shared_to)));
            }
        });

        if(!isLocalPlaylist){
            headerDetail.setVisibility(View.GONE);
        }
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllLists();
            }
        });
        if(Integer.parseInt(playlistId)== IConstants.FAV_PLAYLIST){
            favLayout.setVisibility(View.VISIBLE);
        }
    }



    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("歌单");
        toolbar.setPadding(0,mStatusSize,0,0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(!isLocalPlaylist){
            //设置标题子文本
            toolbar.setSubtitle(playlistDetail);
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
    class PlayListDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        final  static  int FIRST_ITEM = 0;
        final  static  int ITEM=1;
        private ArrayList<MusicInfo> mArrayList;
        private Activity mContext;

        public PlayListDetailAdapter(Activity context ,ArrayList<MusicInfo> mList){
            this.mArrayList = mList;
            this.mContext=context;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == FIRST_ITEM){//第一item项
                return  new CommonItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_common_item,parent,false));
            }else{
                return  new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_playlist_detail_item,parent,false));
            }
        }

        //判断布局类型
        @Override
        public int getItemViewType(int position) {
            return position == FIRST_ITEM ? FIRST_ITEM : ITEM;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
                if(itemHolder instanceof ItemViewHolder){
                    final MusicInfo localItem = mArrayList.get(position - 1);//这里不懂为什么 -1
                    ((ItemViewHolder) itemHolder).trackNumber.setText(position + ""); //编号
                    ((ItemViewHolder) itemHolder).title.setText(localItem.musicName); //歌名
                    ((ItemViewHolder) itemHolder).artist.setText(localItem.artist); //歌手
                    //更多菜单
                    ((ItemViewHolder) itemHolder).menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else if(itemHolder instanceof CommonItemViewHolder){
                    ((CommonItemViewHolder) itemHolder).mTextView.setText("(共" + mArrayList.size() + "首)");

                    ((CommonItemViewHolder) itemHolder).select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
        }

        @Override
        public int getItemCount() {
            return mArrayList == null ? 0 : mArrayList.size() + 1;
        }

        public void updateDataSet(ArrayList<MusicInfo> arrayList){
            this.mArrayList = arrayList;
            this.notifyDataSetChanged();
        }

        public class CommonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mTextView;
            ImageView select;
            RelativeLayout mLayout;
            public CommonItemViewHolder(View itemView) {
                super(itemView);
                this.mTextView = (TextView) itemView.findViewById(R.id.play_all_number);
                this.select = (ImageView) itemView.findViewById(R.id.select);
                this.mLayout= (RelativeLayout) itemView.findViewById(R.id.play_all_layout);
                mLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }

        //每首歌曲
        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
           protected  TextView title,artist,trackNumber;
            protected ImageView menu;//更多
            public ItemViewHolder(View itemView) {
                super(itemView);
                this.title = (TextView) itemView.findViewById(R.id.song_title);
                this.artist = (TextView) itemView.findViewById(R.id.song_artist);
                this.trackNumber = (TextView) itemView.findViewById(R.id.trackNumber);
                this.menu = (ImageView) itemView.findViewById(R.id.popup_menu);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //播放歌曲
            }
        }
    }

}
