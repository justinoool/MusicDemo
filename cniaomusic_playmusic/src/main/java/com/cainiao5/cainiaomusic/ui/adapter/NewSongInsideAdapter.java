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
import com.cainiao5.cainiaomusic.data.SongListInsideInfo;

public class NewSongInsideAdapter extends RecyclerView.Adapter<NewSongInsideAdapter.MusicViewHolder> {

    private Context context;
    private SongListInsideInfo songs;
    private long playingId;

    public NewSongInsideAdapter(Context context) {
        this.context = context;
    }

    public void setData(SongListInsideInfo songs) {
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

        final SongListInsideInfo.ContentBean song = songs.getContent().get(position);
        holder.title.setText(song.getTitle());
        if (TextUtils.isEmpty(song.getAuthor())) {
            holder.detail.setVisibility(View.GONE);
        } else {
            holder.detail.setVisibility(View.VISIBLE);
            holder.detail.setText(song.getAuthor());
        }
        int number = position + 1;
        holder.number.setText(String.valueOf(number));

        //TODO 设置音乐的状态



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


    }

    @Override
    public int getItemCount() {
        return songs.getContent().size();
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
