package com.cainiao5.cainiaomusic.ui.widget.dialog;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.RxBus;
import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.db.CollectionManager;
import com.cainiao5.cainiaomusic.model.event.CollectionUpdateEvent;
import com.cainiao5.cainiaomusic.ui.adapter.CollectionAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.OnItemClickListener;
import com.cainiao5.cainiaomusic.ui.widget.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import rx.functions.Action1;


/**
 */
public class AddPlaylistDialog extends DialogFragment {
    private RecyclerView recyclerView;
    private long musicId;
    private Song mSong;
    private Context mContext;
    private CollectionBean collectionBean;

    public static AddPlaylistDialog newInstance(Song song, Context context) {
        AddPlaylistDialog dialog = new AddPlaylistDialog();
        dialog.musicId = song.getId();
        dialog.mContext = context;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.fragment_add_playlist, container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.create_new_playlist);
        recyclerView = (RecyclerView) view.findViewById(R.id.add_playlist_recyclerview);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setView((getActivity().getLayoutInflater().inflate(R.layout.dialog, null)));
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.dialog);
                final EditText editText = (EditText) (window.findViewById(R.id.message));
                editText.requestFocus();
                (window.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                (window.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(editText.getText())) {
                            return;
                        }
                        dismiss();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {

//                                String albumart = null;
//                                for (long id : musicId) {
//                                    albumart = MusicUtils.getAlbumdata(MainApplication.context, id);
//                                    if (albumart != null) {
//                                        break;
//                                    }
//                                }
//                                //String albumart = MusicUtils.getMusicInfo(getContext(), musicId[0]).albumData;
//                                playlistInfo.addPlaylist(editText.getText().hashCode(), editText.getText().toString(),
//                                        musicId.length, "file://" + albumart, "local");
//                                for (int i = 0; i < musicId.length; i++) {
//                                    playlistsManager.insert(MainApplication.context, editText.getText().hashCode(), musicId[i], i);
//                                }
//                                Intent intent = new Intent(IConstants.PLAYLIST_COUNT_CHANGED);
//                                MainApplication.context.sendBroadcast(intent);

//                                collectionBean = new CollectionBean(-1, editText.getText().toString(), "", 1, "");
//                                CollectionManager.getInstance().setCollectionAsync(collectionBean);
//                                CollectionManager.getInstance().insertCollectionShip(collectionBean, mSong);
//                                RxBus.getDefault().post(new CollectionUpdateEvent(true));

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                showCollectionDialog(mSong);
                            }
                        });


//                            }
//                        }).start();

                        alertDialog.dismiss();
                    }
                });
            }
        });
//        ArrayList<Playlist> playlists = playlistInfo.getPlaylist();
        List<CollectionBean> collectionList = CollectionManager.getInstance().getCollectionList();
        recyclerView.setLayoutManager(layoutManager);
        AddPlaylistAdapter adapter = new AddPlaylistAdapter(collectionList);
        recyclerView.setAdapter(adapter);
        setItemDecoration();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.65);
        int dialogWidth = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.77);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }

    //设置分割线
    private void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private class AddPlaylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<CollectionBean> playlists;

        public AddPlaylistAdapter(List<CollectionBean> collectionList) {
            playlists = collectionList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_playlist_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CollectionBean playlist = playlists.get(position);
            //((ViewHolder)holder).imageView.setImageURI();
            ((ViewHolder) holder).title.setText(playlist.getTitle());
            ((ViewHolder) holder).count.setText(playlist.getCount() + "");
            Uri uri = Uri.parse(playlist.getCoverUrl());
            ((ViewHolder) holder).imageView.setImageURI(uri);

        }

        @Override
        public int getItemCount() {
            return playlists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            SimpleDraweeView imageView;
            TextView title, count;

            public ViewHolder(View v) {
                super(v);
                this.imageView = (SimpleDraweeView) v.findViewById(R.id.add_playlist_img);
                this.title = (TextView) v.findViewById(R.id.add_playlist_toptext);
                this.count = (TextView) v.findViewById(R.id.add_playlist_bottom_text);
                v.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
//                Playlist playlist = playlists.get(getAdapterPosition());
                //playlistInfo.updatePlaylist(playlist.id, musicId.length);
//                ArrayList<MusicTrack> musicTracks = playlistsManager.getPlaylist(playlist.id);
//
//                if (getAdapterPosition() == 0 && musicTracks.size() == 0) {
//                    for (int i = 0; i < musicId.length; i++) {
//                        playlistsManager.insert(getContext(), playlist.id, musicId[i], 0);
//                    }
//                }
//
//                for (int i = 0; i < musicId.length; i++) {
//
//                    for (int j = 0; j < musicTracks.size(); j++) {
//                        if (musicId[i] != musicTracks.get(j).mId) {
//                            playlistsManager.insert(getContext(), playlist.id, musicId[i], 0);
//                        }
//                    }
//
//                }
//                Intent intent = new Intent(IConstants.PLAYLIST_COUNT_CHANGED);
//                getActivity().sendBroadcast(intent);
//                dismiss();
            }
        }
    }

    public void showCollectionDialog(final Song song) {
        CollectionAdapter collectionAdapter = new CollectionAdapter(mContext);
        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.collection_dialog_selection_title)
                .adapter(collectionAdapter, new LinearLayoutManager(mContext))
                .build();
        collectionAdapter.setItemClickListener(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(CollectionBean item, int position) {
                if (item == null) {
                    dialog.dismiss();
                    return;
                }
                CollectionManager.getInstance().insertCollectionShipAsync(item, song, new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        dialog.dismiss();
//                        showToast(aBoolean ? R.string.collect_song_success : R.string.collect_song_fail);
                        RxBus.getDefault().post(new CollectionUpdateEvent(aBoolean));//通知首页收藏夹数据变化
                    }
                });
            }

            @Override
            public void onItemSettingClick(View v, CollectionBean item, int position) {

            }
        });
        dialog.show();
    }

}
