package com.example.jack.musicdemo.ui.adapter;

import android.content.Context;
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
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Song;

import java.util.ArrayList;
import java.util.List;

import static com.example.jack.musicdemo.R.string.song;

/**
 * Created by ${justin} on 2017/8/2421: 39
 * WeChat:Justin-Tz
 * 最近播放的适配器
 */

public class RecentPlayAdapter extends RecyclerView.Adapter<RecentPlayAdapter.RecentViewHolder> {

    private List<Song> songs; //歌曲数据
    private Context mContext;  //获取上下文

    public RecentPlayAdapter(Context context) {
        this.mContext = context;
        songs = new ArrayList<>();
    }

    //外面的数据可以传入适配器
    public void setData(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    //创建视图
    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_recently_listitem, parent, false);
        return new RecentViewHolder(view); //将布局传入viewholder
    }

    //绑定viewholder的方法，这里执行控件的具体逻辑操作
    @Override
    public void onBindViewHolder(RecentViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.title.setText(Html.fromHtml(song.getTitle()));//转成HTML格式
        if(TextUtils.isEmpty(song.getArtistName())){  //如果作者为空
        holder.detail.setText(R.string.music_unknown);
        }else{
            holder.detail.setText(song.getArtistName());
        }

        Glide.with(mContext)
                .load(song.getCoverUrl())
                .placeholder(R.drawable.cover)//设置默认图片
                .into(holder.cover);//将压缩后的图片设置到布局中

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {
        //在这里面编写控件设置，比如初始化
        public View songItem;
        public TextView title, detail;  //detail细节

        public ImageView cover;
        public AppCompatImageView setting;

        public RecentViewHolder(View itemView) {
            super(itemView);
            songItem = itemView.findViewById(R.id.song_item);
            title = (TextView) itemView.findViewById(R.id.song_title);
            detail = (TextView) itemView.findViewById(R.id.song_detail);
            cover = (ImageView) itemView.findViewById(R.id.song_cover);
            setting = (AppCompatImageView) itemView.findViewById(R.id.song_setting);
            songItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Song song = songs.get(position);

                }
            });
        }
    }
}
