package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;

import java.util.ArrayList;
import java.util.List;

import magicasakura.widgets.TintImageView;

/**
 * @description: 本地歌曲适配器
 */

public class LocalRecyclerAdapter extends RecyclerView.Adapter<LocalRecyclerAdapter.LocalMusicViewHolder> {

    private Context context;
    private List<Song> songs;
    private OnItemClickListener<Song> itemClickListener;
    private OnItemClickListener songClickListener;

    public LocalRecyclerAdapter(Context context) {
        this.context = context;
        songs = new ArrayList<>();
    }

    public void setData(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    public void updateAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public LocalMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_localmusic_listitem, parent, false);
        return new LocalMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocalMusicViewHolder holder, final int position) {
        final Song song = songs.get(position);
        holder.title.setText(Html.fromHtml(song.getTitle()));
        if (TextUtils.isEmpty(song.getArtistName())) {
            holder.detail.setText("unknown");
        } else {
            holder.detail.setText(song.getArtistName());
        }
        Glide.with(context)
                .load(song.getCoverUrl())
                .placeholder(R.drawable.cover)
                .into(holder.cover);
        if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING ||
                MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
            if (MusicPlayerManager.get().getMusicPlaylist().getCurrentPlay() != null && song.getId() == MusicPlayerManager.get().getPlayingSong().getId()) {
                holder.title.setTextColor(context.getResources().getColor(R.color.theme_color_primary));
            } else {
                holder.title.setTextColor(context.getResources().getColor(R.color.black_normal));
            }
            if (MusicPlayerManager.get().getPlayingSong().getId() == song.getId()) {
                holder.playstate.setVisibility(View.VISIBLE);
                holder.playstate.setImageResource(R.drawable.song_play_icon);
                holder.playstate.setImageTintList(R.color.theme_color_primary);
            } else {
                holder.playstate.setVisibility(View.GONE);
            }
//            holder.musicLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (songClickListener != null && song.isStatus()) {
//                        Log.e("onClick: ", "onClick: " + song.getArtistName());
//                        songClickListener.onItemClick(song, holder.getAdapterPosition());
//                    }
//                }
//            });

        }


    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class LocalMusicViewHolder extends RecyclerView.ViewHolder {

        public View musicLayout;
        public TextView title, detail;
        public ImageView cover;
        public AppCompatImageView setting;
        public TintImageView playstate;

        public LocalMusicViewHolder(View itemView) {
            super(itemView);
            musicLayout = itemView.findViewById(R.id.local_song_item);
            title = (TextView) itemView.findViewById(R.id.local_song_title);
            detail = (TextView) itemView.findViewById(R.id.local_song_detail);
            cover = (ImageView) itemView.findViewById(R.id.local_song_cover);
            setting = (AppCompatImageView) itemView.findViewById(R.id.local_song_setting);
            playstate = (TintImageView) itemView.findViewById(R.id.play_state);

            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Song song = songs.get(getAdapterPosition());
                    if (itemClickListener != null && song.isStatus()) {
                        itemClickListener.onItemSettingClick(setting, song, getAdapterPosition());
                    }
                }
            });
            musicLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = songs.get(getAdapterPosition());
                    if (itemClickListener != null && song.isStatus()) {
                        itemClickListener.onItemClick(song, getAdapterPosition());
                    }

                }
            });

        }
    }
}
