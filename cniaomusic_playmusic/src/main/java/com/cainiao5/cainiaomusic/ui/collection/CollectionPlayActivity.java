package com.cainiao5.cainiaomusic.ui.collection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.CollectionPlayIView;
import com.cainiao5.cainiaomusic.ui.album.MusicDetailActivity;

import java.util.List;

/**
 * @desciption: 收藏夹播放列表
 */
public class CollectionPlayActivity extends MusicDetailActivity implements CollectionPlayIView {

    public static void open(Context context, CollectionBean collection) {
        context.startActivity(getIntent(context, collection));
    }

    public static Intent getIntent(Context context, CollectionBean collection) {
        Intent intent = new Intent();
        intent.setClass(context, CollectionPlayActivity.class);
        intent.putExtra("collection", collection);
        return intent;
    }

    private CollectionBean collection;
    private CollectionPlayPresenter cpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        collection = (CollectionBean) getIntent().getSerializableExtra("collection");
        if (collection == null) {
            finish();
        }
        cpPresenter = new CollectionPlayPresenter(this, collection);
        cpPresenter.init();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSongChanged(Song song) {

    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public void collectionDetail(int collectionId, Spanned title, Spanned description) {
        Log.e("collectionDetail: ", "collectionDetail: ");

        setCollectionData(collection);
    }

    @Override
    public void collectionCover(Bitmap cover) {

    }

    @Override
    public void getSongs(List<Song> songs) {
        Log.e("getSongs: ", "getSongs: " + songs.size());
    }

    @Override
    public void fail(Throwable throwable) {
        Log.e("fail: ", "fail: " + throwable.getMessage());

    }

//    @Override
//    protected void showPopupMenu(final View v, final Song song, final int position) {
//        final PopupMenu menu = new PopupMenu(this, v);
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.popup_song_play:
//                        playSong(position);
//                        break;
//                    case R.id.popup_song_addto_playlist:
//                        addToPlaylist(song);
//                        break;
//                    case R.id.popup_song_delete:
//                        CollectionManager.getInstance().deleteCollectionShip(collection.getId(), (int) song.getId());
//                        refreshView.notifySwipeFinish();
//                        cpPresenter.refresh();
//                        break;
//                    case R.id.popup_song_download:
//                        downloadSong(v,song);
//                        break;
//                }
//                return false;
//            }
//        });
//        menu.inflate(R.menu.popup_collection_song_setting);
//        menu.show();
//    }
}
