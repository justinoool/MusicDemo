package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.RankingInsideInfo;
import com.cainiao5.cainiaomusic.data.Song;

import java.util.ArrayList;
import java.util.List;

public class RankingInsideAdapter extends RecyclerView.Adapter<RankingInsideAdapter.MusicViewHolder> {

    private Context context;
    private List<RankingInsideInfo.SongListBean> songs;
    private long playingId;
    private OnItemClickListener<Song> songClickListener;

    public RankingInsideAdapter(Context context) {
        this.context = context;
        songs = new ArrayList<>();
    }

    public void setData(List<RankingInsideInfo.SongListBean> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_music_listitem, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MusicViewHolder holder, int position) {

        final RankingInsideInfo.SongListBean song = songs.get(position);
        holder.title.setText(song.getTitle());
        if (TextUtils.isEmpty(song.getAuthor())) {
            holder.detail.setVisibility(View.GONE);
        } else {
            holder.detail.setVisibility(View.VISIBLE);
            holder.detail.setText(song.getAuthor());
        }
        int number = position + 1;
        holder.number.setText(String.valueOf(number));
//        if (song.isStatus()) {
//            holder.title.setTextColor(context.getResources().getColor(R.color.black_normal));
//            if (song.getId() == playingId) {
//                holder.number.setVisibility(View.GONE);
//                holder.playing.setVisibility(View.VISIBLE);
//            } else {
//                holder.playing.setVisibility(View.GONE);
//                holder.number.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.title.setTextColor(context.getResources().getColor(R.color.black_alpha));
//            holder.number.setVisibility(View.GONE);
//            holder.playing.setVisibility(View.VISIBLE);
////            holder.playing.setImageResource(R.drawable.ic_volume_off);
//        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.musicLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.musicLayout,pos,song.getSong_id());
                }
            });
        }
//        holder.setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (songClickListener != null && song.isStatus()) {
//                    songClickListener.onItemSettingClick(holder.setting, song, holder.getAdapterPosition());
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public long getPlayingId() {
        return playingId;
    }

    public void setPlayingId(long playingId) {
        this.playingId = playingId;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position,String songid);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public class MusicViewHolder extends RecyclerView.ViewHolder {

        public View musicLayout;
        public TextView number, title, detail;
        public ImageView playing;
        public AppCompatImageView setting;

        public MusicViewHolder(View itemView) {
            super(itemView);
            musicLayout = itemView.findViewById(R.id.music_item);
            number = (TextView) itemView.findViewById(R.id.play_number);
            title = (TextView) itemView.findViewById(R.id.play_title);
            detail = (TextView) itemView.findViewById(R.id.play_detail);
            playing = (ImageView) itemView.findViewById(R.id.playing);
            setting = (AppCompatImageView) itemView.findViewById(R.id.play_setting);
        }
    }

}
