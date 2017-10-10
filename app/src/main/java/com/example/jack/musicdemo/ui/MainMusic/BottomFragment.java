package com.example.jack.musicdemo.ui.MainMusic;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.musicdemo.R;

import com.example.jack.musicdemo.common.utils.ImageUtils;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.OnSongchangeListener;
import com.example.jack.musicdemo.ui.local.BaseFragment;
import com.example.jack.musicdemo.ui.play.PlayingActivity;
import com.facebook.drawee.view.SimpleDraweeView;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import magicasakura.widgets.TintImageView;
import rx.Subscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends BaseFragment implements OnSongchangeListener {


    @Bind(R.id.playbar_img)
    SimpleDraweeView mPlaybarImg;
    @Bind(R.id.playbar_info)
    TextView mTitle;
    @Bind(R.id.playbar_singer)
    TextView mArtist;
    @Bind(R.id.play_list)
    TintImageView mPlayList;
    @Bind(R.id.control)
    TintImageView mPlayPause;
    @Bind(R.id.play_next)
    TintImageView mPlayNext;
    @Bind(R.id.linear)
    LinearLayout mLinear;
    @Bind(R.id.song_progress_normal)
    SeekBar mSongProgressNormal;
    @Bind(R.id.nav_play)
    LinearLayout mNavPlay;
    private View rootView;

    private boolean duetoplaypause = false;
    private Song song;
    private Subscription progressSub, timerSub;
    private boolean isPaused;

    public static BottomFragment newInstance() {
        return new BottomFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_nav, container, false);
        this.rootView = rootView;

        ButterKnife.bind(this, rootView);
        //音乐信息的更新的监听注册
        MusicPlayerManager.get().registerListener(this);

        mSongProgressNormal.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
        mSongProgressNormal.setProgress(MusicPlayerManager.get().getCurrentProgressInSong());
        //点击跳转
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song = MusicPlayerManager.get().getPlayingSong();
                if(song == null){
                    Toast.makeText(getActivity(), "当前无歌曲哦", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                Intent intent = new Intent(getActivity(), PlayingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                }
            }
        });
    // seekbar
        mSongProgressNormal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    MusicPlayerManager.get().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }




    @OnClick({R.id.play_list, R.id.control, R.id.play_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_list://播放列表
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PlayQueueFragment playQueueFragment = new PlayQueueFragment();
                        //由于是dialog所以只需要调用show()，参数1 Manager,参数2: 标签
                       playQueueFragment.show(getFragmentManager(),"playQueueFragment");
                    }
                },60);//数字是延迟，就是60毫秒显示
                break;
            case R.id.play_next:
         MusicPlayerManager.get().playNext();
                break;
            case R.id.control:
                if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {
                    MusicPlayerManager.get().pause();
                    mPlayPause.setImageResource(R.drawable.playbar_btn_play);
                } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
                    MusicPlayerManager.get().play();
                    mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
                }
                break;
        }
    }

    /***
     * 更新数据
     */
    private void updateData() {

        //歌曲封面
        String coverUrl = song.getCoverUrl();
        if (!isPaused) {
           // GlideUtils.with(getActivity(), coverUrl, R.drawable.ah1, mPlaybarImg);
            ImageUtils.GlideWith(getActivity(),coverUrl,R.drawable.ah1,mPlaybarImg);
        }

        //设置标题
        if (!TextUtils.isEmpty(song.getTitle())) {
            String title = song.getTitle();
            mTitle.setText(title);
        }
        //设置歌手
        if (!TextUtils.isEmpty(song.getArtistName())) {
            String artist = song.getArtistName();
            mArtist.setText(artist);
        }
         //获取当前播放歌曲用来设置播放按钮--但是这个getplayingsong不妥，会导致图标有bug
        if (MusicPlayerManager.get().getPlayingSong() != null) {
            mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
        }
    }

    private void updatePlayStatus() {
        if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {

            mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
        } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
            mPlayPause.setImageResource(R.drawable.playbar_btn_play);

        }
    }
    //歌曲改变
    @Override
    public void onSongChanged(Song song) {
        this.song = song;
        updateData();
    }

    //播放状态改变
    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {
        updatePlayStatus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
//        updateData();
    }

    @Override
    public void onPause() {
        isPaused = true;
        super.onPause();
    }


}
