package com.example.jack.musicdemo.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.net.LastFmClient;
import com.example.jack.musicdemo.common.net.callbacks.ArtistInfoListener;
import com.example.jack.musicdemo.data.ArtistInfo;
import com.example.jack.musicdemo.data.ArtistQuery;
import com.example.jack.musicdemo.data.LastfmArtist;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import magicasakura.widgets.TintImageView;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.jack.musicdemo.R.drawable.c;

/**
 * Created by ${justin} on 2017/8/3011: 41
 * WeChat:Justin-Tz
 * 歌手碎片中 recyclerview 的适配器
 */

public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArtistInfo> mList;
    private Context mContext;

    public ArtistAdapter(List<ArtistInfo> list, Context context) {
        mList = list;
        mContext = context;
    }
  //更新数据
   public void updateDataSet(List<ArtistInfo> list){
       this.mList = list;
   }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_common_item,parent,false);
        return  new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
          ArtistInfo artistData =mList.get(position);
        //设置条目信息
        ((ListItemViewHolder)holder).mainTitle.setText(artistData.artist_name);
        ((ListItemViewHolder) holder).title.setText(artistData.number_of_tracks + "首");
        //如果正在播放中的歌曲的id等于 歌手专辑条目的id 则显示小喇叭
        if(MusicPlayerManager.get().isPlaying()){
            //根据播放中歌曲的歌手id判断当前歌手专辑条目是否有播放的歌曲
            if(MusicPlayerManager.get().getPlayingSong().getArtistId()==(artistData.artist_id)){
                ((ListItemViewHolder) holder).moreOverflow.setImageResource(R.drawable.song_play_icon);
                ((ListItemViewHolder) holder).moreOverflow.setImageTintList(R.color.theme_color_primary);
            } else {
                ((ListItemViewHolder) holder).moreOverflow.setImageResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
            }
        }

        //加载歌手图片
        LastFmClient.getInstance(mContext).getArtistInfo(new ArtistQuery(artistData.artist_name.toString()), new ArtistInfoListener() {

            //这里运用到retrofit不是很懂，不懂为什么get（2）
            @Override
            public void artistInfoSucess(LastfmArtist artist) {
                if(artist != null && artist.mArtwork != null){
                    ((ListItemViewHolder)holder).draweeView.setImageURI(Uri.parse(artist.mArtwork.get(2).mUrl));
                }
            }

            @Override
            public void artistInfoFailed() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView draweeView;//歌手照片
        TextView mainTitle , title; //歌手姓名，歌曲数量
        TintImageView moreOverflow; //点点点

        public ListItemViewHolder(View itemView) {
            super(itemView);
            this.mainTitle = (TextView) itemView.findViewById(R.id.viewpager_list_toptext);
            this.title = (TextView) itemView.findViewById(R.id.viewpager_list_bottom_text);
            this.draweeView = (SimpleDraweeView) itemView.findViewById(R.id.viewpager_list_img);
            this.moreOverflow = (TintImageView) itemView.findViewById(R.id.viewpager_list_button);

            //为每个条目设置监听
            itemView.setOnClickListener(this);
        }

        //加载歌手专辑界面的fragment
        @Override
        public void onClick(View v) {

        }
    }
}
