package com.example.jack.musicdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Song;

import java.util.ArrayList;
import java.util.List;

import static com.example.jack.musicdemo.MyApplication.context;

/**
 * Created by ${justin} on 2017/8/2010: 22
 * WeChat:Justin-Tz
 * 本地音乐下 单曲碎片中的 recyclerview 适配器
 */

public class LocalRecyclerAdapter extends RecyclerView.Adapter<LocalRecyclerAdapter.LocalMusicViewHolder>{

    private Context mContext;
    private List<Song> mSongs;
    private OnItemClickListener<Song> mItemClickListener;

    public LocalRecyclerAdapter(Context context) {
        mContext = context;
        mSongs = new ArrayList<>();
    }
    //给适配器设置数据，并更新
    public void setData( List<Song> songs){
        this.mSongs=songs;
        notifyDataSetChanged();
    }
    @Override
    public LocalMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_localmusic_listitem,parent,false);
       return new LocalMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalMusicViewHolder holder, int position) {
              Song song = mSongs.get(position);
        holder.title.setText(Html.fromHtml(song.getTitle()));//html.fromhtml将文本用html格式化显示在TextView上
        if(TextUtils.isEmpty(song.getArtistName())){
           holder.detail.setText("unknown");
        }else {
            holder.detail.setText(song.getArtistName());
        }
        //解析图片
        Glide.with(context)
                .load(song.getCoverUrl())
                .placeholder(R.drawable.cover)
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public OnItemClickListener getItemClickListener()
    {
        return  mItemClickListener;
    }
    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }


    public class LocalMusicViewHolder extends RecyclerView.ViewHolder{

        public View musicLayout;
        public TextView title, detail;
        public ImageView cover;
        public AppCompatImageView setting;

        public LocalMusicViewHolder(View itemView) {
            super(itemView);
            musicLayout=itemView.findViewById(R.id.local_song_item);
            title = (TextView) itemView.findViewById(R.id.local_song_title);
            detail = (TextView) itemView.findViewById(R.id.local_song_detail);
            cover = (ImageView) itemView.findViewById(R.id.local_song_cover);
            setting= (AppCompatImageView) itemView.findViewById(R.id.local_song_setting);

            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getAdapterPosition());
                    if (mItemClickListener != null && song.isStatus()){
                        mItemClickListener.onItemSettingClick(setting,song,getAdapterPosition());
                    }
                }
            });
            musicLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getAdapterPosition());
                    if(mItemClickListener != null && song.isStatus()){
                        mItemClickListener.onItemClick(song,getAdapterPosition());
                    }
                }
            });
        }
    }
}
