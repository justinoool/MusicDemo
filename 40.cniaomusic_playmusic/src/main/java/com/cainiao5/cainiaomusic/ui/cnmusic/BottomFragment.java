package com.cainiao5.cainiaomusic.ui.cnmusic;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.GlideUtils;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.play.PlayingActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import magicasakura.widgets.TintImageView;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends BaseFragment implements OnSongChangedListener{


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_nav, container, false);
        this.rootView = rootView;

        ButterKnife.bind(this, rootView);
        MusicPlayerManager.get().registerListener(this);

        mSongProgressNormal.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
        mSongProgressNormal.setProgress(MusicPlayerManager.get().getCurrentProgressInSong());
//        mSongProgressNormal.setProgressTintList(ThemeUtils.getThemeColorStateList(getContext(), R.color.theme_color_primary));
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song = MusicPlayerManager.get().getPlayingSong();
                if (song == null) {
                    Toast.makeText(getActivity(), "当前无歌曲播放", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), PlayingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

        //seekbar
        mSongProgressNormal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /**进度条拖拉**/
                if (fromUser) {
                    MusicPlayerManager.get().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

//        updateProgress();
//        updateData();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.play_list, R.id.play_next,R.id.control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_list:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PlayQueueFragment playQueueFragment = new PlayQueueFragment();
                        playQueueFragment.show(getFragmentManager(), "playqueueframent");
                    }
                }, 60);

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

//    /****
//     * 更新进度条
//     */
//    private void updateProgress() {
//        progressSub = Observable.interval(400, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        mSongProgressNormal.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
//                        mSongProgressNormal.setProgress(MusicPlayerManager.get().getCurrentPosition());
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e("progressSub: ", throwable.toString());
//                    }
//                });
//    }


    /***
     * 更新数据
     */
    private void updateData() {
        song = MusicPlayerManager.get().getPlayingSong();

        //歌曲封面
        String coverUrl = song.getCoverUrl();
        if (!isPaused) {
            GlideUtils.with(getActivity(), coverUrl, R.drawable.ah1, mPlaybarImg);
        }

        if (!TextUtils.isEmpty(song.getTitle())) {
            String title = song.getTitle();
            mTitle.setText(title);
        }
        if (!TextUtils.isEmpty(song.getArtistName())) {
            String artist = song.getArtistName();
            mArtist.setText(artist);
        }

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

    @Override
    public void onSongChanged(Song song) {
        Log.e("onSongChanged: ", "onSongChanged: ");
        this.song = song;
        updateData();
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {
        Log.e("onPlayBackState ", "onPlayBackStateChanged: ");
        updatePlayStatus();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicPlayerManager.get().unregisterListener(this);
//        progressSub.unsubscribe();
    }
}
