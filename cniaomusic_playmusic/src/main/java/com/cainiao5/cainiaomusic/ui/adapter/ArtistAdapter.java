package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.ArtistInfo;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import magicasakura.widgets.TintImageView;

public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ArtistInfo> mList;
        private Context mContext;
        public ArtistAdapter(List<ArtistInfo> list,Context context) {
//            if (list == null) {
//                throw new IllegalArgumentException("model Data must not be null");
//            }
            mList = list;
            mContext = context;
        }

        //更新adpter的数据
        public void updateDataSet(List<ArtistInfo> list) {
            this.mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_common_item, viewGroup, false);
            return new ListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
            ArtistInfo model = mList.get(i);
            //设置条目状态
            ((ListItemViewHolder) holder).mainTitle.setText(model.artist_name);
            ((ListItemViewHolder) holder).title.setText(model.number_of_tracks + "首");
            if(MusicPlayerManager.get().isPlaying()){
                //根据播放中歌曲的歌手名判断当前歌手专辑条目是否有播放的歌曲
                if (MusicPlayerManager.get().getPlayingSong().getArtistId() == (model.artist_id)) {
                    ((ListItemViewHolder) holder).moreOverflow.setImageResource(R.drawable.song_play_icon);
                    ((ListItemViewHolder) holder).moreOverflow.setImageTintList(R.color.theme_color_primary);
                } else {
                    ((ListItemViewHolder) holder).moreOverflow.setImageResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
                }
            }

//            //加载歌手图片
//            LastFmClient.getsInstance(mContext).getArtistInfo(new ArtistQuery(model.artist_name.toString()), new ArtistInfoListener() {
//                @Override
//                public void artistInfoSucess(LastfmArtist artist) {
//                    if(artist != null && artist.mArtwork != null){
//                        ((ListItemViewHolder) holder).draweeView.setImageURI(Uri.parse(artist.mArtwork.get(2).mUrl));
//                    }
//                }
//                @Override
//                public void artistInfoFailed() {}
//            });



        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //ViewHolder
            SimpleDraweeView draweeView;
            TextView mainTitle, title;
            TintImageView moreOverflow;

            ListItemViewHolder(View view) {
                super(view);
                this.mainTitle = (TextView) view.findViewById(R.id.viewpager_list_toptext);
                this.title = (TextView) view.findViewById(R.id.viewpager_list_bottom_text);
                this.draweeView = (SimpleDraweeView) view.findViewById(R.id.viewpager_list_img);
                this.moreOverflow = (TintImageView) view.findViewById(R.id.viewpager_list_button);

                //为每个条目设置监听
                view.setOnClickListener(this);

            }

            //加载歌手专辑界面fragment
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                ArtistDetailFragment fragment = ArtistDetailFragment.newInstance(mList.get(getAdapterPosition()).artist_id);
//                transaction.hide(((AppCompatActivity) getContext()).getSupportFragmentManager().findFragmentById(R.id.tab_container));
//                transaction.add(R.id.tab_container, fragment);
//                transaction.addToBackStack(null).commit();
            }

        }
    }