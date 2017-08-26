package com.example.jack.musicdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.service.MusicPlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import magicasakura.widgets.TintImageView;

/**
 * @description: 播放列表适配器
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private List<Song> songs;
    private OnItemClickListener songClickListener;
    private int currentlyPlayingPosition;

    public PlayListAdapter(Context context) {
        this.context = context;
        songs = new ArrayList<>();
    }

    //获取外部传入的数据并刷新页面
    public void setData(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    public void clearAll() {
        //获取songs的数量
        int itemCount = getItemCount();
        songs.clear();
        //在以显示的区域删除，就是点开看的见的都删除了
        notifyItemRangeRemoved(0, itemCount);
    }

    @Override
    public PlayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_playqueue_item, parent, false);
        return new PlayListHolder(view); //传入布局
    }

    //绑定布局，就是设置布局
    @Override
    public void onBindViewHolder(final PlayListHolder holder, int position) {
        final Song song = songs.get(position);
        holder.MusicName.setText(Html.fromHtml(song.getTitle()));//设置歌名
        Log.e("onBindViewHolder: ", "onBindViewHolder: " + Html.fromHtml(song.getTitle()));
        if (TextUtils.isEmpty(song.getArtistName())) { //如果为空就不现实作者名称
            holder.Artist.setVisibility(View.GONE);
        } else {
            holder.Artist.setVisibility(View.VISIBLE);
            holder.Artist.setText(song.getArtistName());
        }
        //如果当前歌曲不为空并且当前列表的歌曲等于正在播放的歌曲就将歌名改为红色
        if (MusicPlayerManager.get().getPlayingSong() != null && song.getId() == MusicPlayerManager.get().getPlayingSong().getId()) {
            holder.MusicName.setTextColor(context.getResources().getColor(R.color.theme_color_primary));
        } else {//否则黑色
            holder.MusicName.setTextColor(context.getResources().getColor(R.color.black_normal));
        }
        //当前列表内的歌曲等于正在播放的歌曲，在歌曲名前面设置一个红色小喇叭
        if (MusicPlayerManager.get().getPlayingSong().getId() == song.getId()) {
            holder.playstate.setVisibility(View.VISIBLE);
            holder.playstate.setImageResource(R.drawable.song_play_icon);
            holder.playstate.setImageTintList(R.color.theme_color_primary);
            currentlyPlayingPosition = position;
        } else {
            holder.playstate.setVisibility(View.GONE);
        }
        //选中那条歌曲
        holder.musiclayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songClickListener != null && song.isStatus()) {
                    songClickListener.onItemClick(song, holder.getAdapterPosition());
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songClickListener != null && song.isStatus()) {
                    songClickListener.onItemSettingClick(holder.delete, song, holder.getAdapterPosition());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public OnItemClickListener getSongClickListener() {
        return songClickListener;
    }

    public void setSongClickListener(OnItemClickListener songClickListener) {
        this.songClickListener = songClickListener;
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(songs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    //删除播放列表中的某条歌曲
    @Override
    public void onItemDismiss(int position) {
        songs.remove(position);
        notifyItemRemoved(position);
    }

    //实现布局初始化
    public class PlayListHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        ImageView delete;
        TextView MusicName, Artist;
        TintImageView playstate;
        RelativeLayout musiclayout;

        public PlayListHolder(View itemView) {
            super(itemView);
            this.playstate = (TintImageView) itemView.findViewById(R.id.play_state);
            this.delete = (ImageView) itemView.findViewById(R.id.play_list_delete);
            this.MusicName = (TextView) itemView.findViewById(R.id.play_list_musicname);
            this.Artist = (TextView) itemView.findViewById(R.id.play_list_artist);
            this.musiclayout = (RelativeLayout) itemView.findViewById(R.id.musiclayout);
            this.delete.setOnClickListener(new View.OnClickListener() {   //删除的监听
                @Override
                public void onClick(View v) {


                }
            });


        }

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }
    }
}
