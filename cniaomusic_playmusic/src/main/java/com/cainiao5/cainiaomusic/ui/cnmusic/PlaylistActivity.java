package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.BaseActivity;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.CommonUtils;
import com.cainiao5.cainiaomusic.common.utils.HandlerUtil;
import com.cainiao5.cainiaomusic.common.utils.IConstants;
import com.cainiao5.cainiaomusic.common.utils.NetworkUtils;
import com.cainiao5.cainiaomusic.data.MusicInfo;
import com.cainiao5.cainiaomusic.ui.widget.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

//歌单
public class PlaylistActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private String playlsitId;
    private String albumPath, playlistName, playlistDetail;
    private ArrayList<MusicInfo> adapterList = new ArrayList<>();

    private SimpleDraweeView albumArtSmall;
    private ImageView albumArt;
    private TextView playlistTitleView, playlistDetailView;
    private boolean isLocalPlaylist;

    private PlaylistDetailAdapter mAdapter;
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
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //从前面的点击事件传递数据过来
        if (getIntent().getExtras() != null) {
            isLocalPlaylist = getIntent().getBooleanExtra("islocal", false);
            playlsitId = getIntent().getStringExtra("playlistid");
            albumPath = getIntent().getStringExtra("albumart");
            playlistName = getIntent().getStringExtra("playlistname");
            playlistDetail = getIntent().getStringExtra("playlistDetail");
            playlistCount = getIntent().getStringExtra("playlistcount");

        }
        mContext = this;
        setContentView(R.layout.activity_playlist);
        loadFrameLayout = (FrameLayout) findViewById(R.id.state_container);

        headerViewContent = (FrameLayout) findViewById(R.id.headerview);
        headerDetail = (RelativeLayout) findViewById(R.id.headerdetail);
        favLayout = (FrameLayout) findViewById(R.id.playlist_fav);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mHandler = HandlerUtil.getInstance(this);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize = CommonUtils.getActionBarHeight(this);
        mStatusSize = CommonUtils.getStatusHeight(this);


        tryAgain = (TextView) findViewById(R.id.try_again);
        collectText = (TextView) findViewById(R.id.playlist_collect_state);
        collectView = (ImageView) findViewById(R.id.playlist_collect_view);
        share = (LinearLayout) findViewById(R.id.playlist_share);

        setUpEverything();

    }

    private void setUpEverything() {
        setupToolbar();
        setHeaderView();
        setAlbumart();
        setList();
        loadAllLists();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("歌单");
        toolbar.setPadding(0, mStatusSize, 0, 0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!isLocalPlaylist) {
            toolbar.setSubtitle(playlistDetail);
        }

    }


    private void setHeaderView() {
        albumArt = (ImageView) findViewById(R.id.album_art);
        playlistTitleView = (TextView) findViewById(R.id.album_title);
        playlistDetailView = (TextView) findViewById(R.id.album_details);
        SpannableString spanString;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.index_icn_earphone);
        ImageSpan imgSpan = new ImageSpan(this, b, ImageSpan.ALIGN_BASELINE);
        spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        playlistCountView = (TextView) findViewById(R.id.playlist_listen_count);
        playlistCountView.setText(spanString);
        if (playlistCount == null) {
            playlistCount = "0";
        }
        int count = Integer.parseInt(playlistCount);
        if (count > 10000) {
            count = count / 10000;
            playlistCountView.append(" " + count + "万");
        } else {
            playlistCountView.append(" " + playlistCount);
        }
        LinearLayout downAll = (LinearLayout) headerViewContent.findViewById(R.id.playlist_down);

        final LinearLayout addToplaylist = (LinearLayout) headerViewContent.findViewById(R.id.playlist_collect);

        //分享,简单分享
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://music.baidu.com/songlist/" + playlsitId));
                shareIntent.setType("html/*");
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.shared_to)));
            }
        });

        if (!isLocalPlaylist)
            headerDetail.setVisibility(View.GONE);


        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllLists();
            }
        });

        if(Integer.parseInt(playlsitId) == IConstants.FAV_PLAYLIST){
            favLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setList() {
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setScrollViewCallbacks(PlaylistActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaylistActivity.this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new PlaylistDetailAdapter(PlaylistActivity.this, adapterList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(PlaylistActivity.this, DividerItemDecoration.VERTICAL_LIST));
    }


    protected void updateViews(int scrollY, boolean animated) {

        ViewHelper.setTranslationY(headerViewContent, getHeaderTranslationY(scrollY));

    }
    //获取坐标
    protected float getHeaderTranslationY(int scrollY) {
        final int headerHeight = headerViewContent.getHeight();
        int headerTranslationY = mActionBarSize + mStatusSize - headerHeight;
        if (mActionBarSize + mStatusSize <= -scrollY + headerHeight) {
            headerTranslationY = -scrollY;
        }
        return headerTranslationY;
    }


    private void loadAllLists() {

        if (isLocalPlaylist) {
            loadView = LayoutInflater.from(this).inflate(R.layout.loading, loadFrameLayout, false);
            loadFrameLayout.addView(loadView);
            return;
        }

        if (NetworkUtils.isConnectInternet(this)) {
            tryAgain.setVisibility(View.GONE);
            loadView = LayoutInflater.from(this).inflate(R.layout.loading, loadFrameLayout, false);
            loadFrameLayout.addView(loadView);
        } else {
            tryAgain.setVisibility(View.VISIBLE);

        }

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setAlbumart() {
        playlistTitleView.setText(playlistName);
        albumArtSmall.setImageURI(Uri.parse(albumPath));
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        updateViews(scrollY, false);

        if (scrollY > 0 && scrollY < mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize) {
            toolbar.setTitle(playlistName);
            toolbar.setSubtitle(playlistDetail);
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_background));
        }
        if (scrollY == 0) {
            toolbar.setTitle("歌单");
            actionBar.setBackgroundDrawable(null);
        }
        if (scrollY > mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize) {

//            if(mBlurDrawable != null){
//                mBlurDrawable.setColorFilter(Color.parseColor("#79000000"), PorterDuff.Mode.SRC_OVER);
//                actionBar.setBackgroundDrawable(mBlurDrawable);
//            }
        }

        float a = (float) scrollY / (mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize);
        headerDetail.setAlpha(1f - a);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.playlit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    class PlaylistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;
        private ArrayList<MusicInfo> arraylist;
        private Activity mContext;

        public PlaylistDetailAdapter(Activity context, ArrayList<MusicInfo> mList) {
            this.arraylist = mList;
            this.mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == FIRST_ITEM) {
                return new CommonItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_common_item, viewGroup, false));
            } else {
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_playlist_detail_item, viewGroup, false));
            }
        }

        //判断布局类型
        @Override
        public int getItemViewType(int position) {
            return position == FIRST_ITEM ? FIRST_ITEM : ITEM;

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder itemHolder, final int i) {
            if (itemHolder instanceof ItemViewHolder) {
                final MusicInfo localItem = arraylist.get(i - 1);
                ((ItemViewHolder) itemHolder).trackNumber.setText(i + "");
                ((ItemViewHolder) itemHolder).title.setText(localItem.musicName);
                ((ItemViewHolder) itemHolder).artist.setText(localItem.artist);
                ((ItemViewHolder) itemHolder).menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            } else if (itemHolder instanceof CommonItemViewHolder) {

                ((CommonItemViewHolder) itemHolder).textView.setText("(共" + arraylist.size() + "首)");

                ((CommonItemViewHolder) itemHolder).select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return arraylist == null ? 0 : arraylist.size() + 1;
        }

        public void updateDataSet(ArrayList<MusicInfo> arraylist) {
            this.arraylist = arraylist;
            this.notifyDataSetChanged();
        }

        public class CommonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView textView;
            ImageView select;
            RelativeLayout layout;

            CommonItemViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.play_all_number);
                this.select = (ImageView) view.findViewById(R.id.select);
                this.layout = (RelativeLayout) view.findViewById(R.id.play_all_layout);
                layout.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

            }
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected TextView title, artist, trackNumber;
            protected ImageView menu;

            public ItemViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.song_title);
                this.artist = (TextView) view.findViewById(R.id.song_artist);
                this.trackNumber = (TextView) view.findViewById(R.id.trackNumber);
                this.menu = (ImageView) view.findViewById(R.id.popup_menu);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //播放歌曲
            }

        }
    }

}
