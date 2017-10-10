package com.cainiao5.cainiaomusic.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.BitmapUtils;
import com.cainiao5.cainiaomusic.common.utils.DateUtils;
import com.cainiao5.cainiaomusic.common.utils.TransitionHelper;
import com.cainiao5.cainiaomusic.data.FavBean;
import com.cainiao5.cainiaomusic.data.WikiBean;
import com.cainiao5.cainiaomusic.ui.cnmusic.MoeDetailActivity;
import com.cainiao5.cainiaomusic.ui.cnmusic.MusicListActivity;

import java.util.List;

/**
 * @description: 歌单适配器
 */
public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int ALBUM_TYPE_NEW = 1000;
    public final static int ALBUM_TYPE_HOT = 1001;
    public final static int ALBUM_MUSIC_LEFT = 1002;
    public final static int ALBUM_MUSIC_RIGHT = 1003;

    private Context context;

    private List<WikiBean> newMusics;
    private List<WikiBean> hotMusics;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public List<WikiBean> getNewMusics() {
        return newMusics;
    }

    public void setNewMusics(List<WikiBean> newMusics) {
        this.newMusics = newMusics;
    }

    public List<WikiBean> getHotMusics() {
        return hotMusics;
    }

    public void updateWikiFav(long wikiId, boolean fav) {
        for (WikiBean bean : newMusics) {
            if (bean.getWiki_id() == wikiId) {
                if (fav) bean.setWiki_user_fav(new FavBean());
                else bean.setWiki_user_fav(null);
            }
        }

        for (WikiBean bean : hotMusics) {
            if (bean.getWiki_id() == wikiId) {
                if (fav) bean.setWiki_user_fav(new FavBean());
                else bean.setWiki_user_fav(null);
            }
        }
    }

    public void setHotMusics(List<WikiBean> hotMusics) {
        this.hotMusics = hotMusics;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ALBUM_TYPE_NEW || viewType == ALBUM_TYPE_HOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_music_type, parent, false);
            return new AlbumTypeViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_album_card, parent, false);
            if (viewType == ALBUM_MUSIC_LEFT)
                return new AlbumLeftCardViewHolder(view);
            else return new AlbumRightCardViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ALBUM_TYPE_NEW && holder instanceof AlbumTypeViewHolder) {
            AlbumTypeViewHolder newTypeHolder = (AlbumTypeViewHolder) holder;
            newTypeHolder.albumType.setText(context.getString(R.string.album_new_title));
            newTypeHolder.albumSubtype.setText(context.getString(R.string.album_new_subtitle));
        } else if (getItemViewType(position) == ALBUM_TYPE_HOT && holder instanceof AlbumTypeViewHolder) {
            AlbumTypeViewHolder newTypeHolder = (AlbumTypeViewHolder) holder;
            newTypeHolder.albumType.setText(context.getString(R.string.album_hot_title));
            newTypeHolder.albumSubtype.setText(context.getString(R.string.album_hot_subtitle));
        } else {
            final BaseAlbumCardViewHolder cardHolder;
            final WikiBean wiki;
            if (holder instanceof AlbumLeftCardViewHolder) {
                cardHolder = (AlbumLeftCardViewHolder) holder;
            } else {
                cardHolder = (AlbumRightCardViewHolder) holder;
            }
            if (position > newMusics.size() + 1) {
                wiki = hotMusics.get(position - getNewCount() - 1);
                Glide.with(context)
                        .load(wiki.getWiki_cover().getLarge())
                        .placeholder(R.drawable.cover)
                        .into(cardHolder.albumCover);
                cardHolder.albumTitle.setText(Html.fromHtml(wiki.getWiki_title()));
                cardHolder.albumDate.setText(DateUtils.convertTimeToFormat(wiki.getWiki_date()));
                cardHolder.subLayout.setVisibility(View.GONE);
                cardHolder.favLayout.setVisibility(View.VISIBLE);
                cardHolder.favCount.setText(String.valueOf(wiki.getWiki_fav_count()));
            } else {
                wiki = newMusics.get(position - 1);
                Glide.with(context)
                        .load(wiki.getWiki_cover().getLarge())
                        .placeholder(R.drawable.cover)
                        .into(cardHolder.albumCover);
                cardHolder.albumTitle.setText(Html.fromHtml(wiki.getWiki_title()));
                cardHolder.albumDate.setText(DateUtils.convertTimeToFormat(wiki.getWiki_date()));
                cardHolder.favLayout.setVisibility(View.GONE);
                cardHolder.subLayout.setVisibility(View.VISIBLE);
                cardHolder.subCount.setText(String.valueOf(wiki.getWiki_sub_count()));
            }
            cardHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        MoeDetailActivity.open(context, wiki);
                        return;
                    }
                    Intent intent = MoeDetailActivity.getIntent(context, wiki);
                    final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants((Activity) context, false,
                            new Pair<>(cardHolder.albumCover, context.getString(R.string.music_share_cover)));
                    TransitionHelper.startSharedElementActivity((Activity) context, intent, pairs);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ALBUM_TYPE_NEW;
        }
        if (position == getNewCount()) {
            return ALBUM_TYPE_HOT;
        }

        if (position > 0 && position <= newMusics.size()) {
            if ((position - 1) % 2 == 0)
                return ALBUM_MUSIC_LEFT;
            else return ALBUM_MUSIC_RIGHT;
        }

        if (position > newMusics.size() + 1 && position < getItemCount()) {
            if ((position - newMusics.size() - 2) % 2 == 0)
                return ALBUM_MUSIC_LEFT;
            else return ALBUM_MUSIC_RIGHT;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getNewCount() + getHotCount();
    }

    public int getNewCount() {
        return newMusics == null ? 0 : newMusics.size() + 1;
    }

    public int getHotCount() {
        return hotMusics == null ? 0 : hotMusics.size() + 1;
    }

    public class BaseAlbumCardViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView albumCover;
        public TextView albumTitle, albumDate;
        public TextView subCount, favCount;
        public View favLayout, subLayout;

        public BaseAlbumCardViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            albumCover = (ImageView) itemView.findViewById(R.id.cover);
            albumTitle = (TextView) itemView.findViewById(R.id.album_title);
            albumDate = (TextView) itemView.findViewById(R.id.album_date);

            subLayout = itemView.findViewById(R.id.album_sub_count_layout);
            subCount = (TextView) itemView.findViewById(R.id.album_sub_count);
            favLayout = itemView.findViewById(R.id.album_fav_layout);
            favCount = (TextView) itemView.findViewById(R.id.album_fav);
        }
    }

    public class AlbumLeftCardViewHolder extends BaseAlbumCardViewHolder {

        public AlbumLeftCardViewHolder(View itemView) {
            super(itemView);
            restoreLeftMargin();
        }

        public void restoreLeftMargin() {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) cardView.getLayoutParams();
            params.leftMargin = BitmapUtils.dp2px(8);
            params.rightMargin = BitmapUtils.dp2px(4);
            cardView.setLayoutParams(params);
        }
    }

    public class AlbumRightCardViewHolder extends BaseAlbumCardViewHolder {

        public AlbumRightCardViewHolder(View itemView) {
            super(itemView);
            restoreLeftMargin();
        }

        public void restoreLeftMargin() {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) cardView.getLayoutParams();
            params.rightMargin = BitmapUtils.dp2px(8);
            params.leftMargin = BitmapUtils.dp2px(4);
            cardView.setLayoutParams(params);
        }
    }

    public class AlbumTypeViewHolder extends RecyclerView.ViewHolder {

        public TextView albumType, albumSubtype;
        public Button moreBtn;

        public AlbumTypeViewHolder(View itemView) {
            super(itemView);
            albumType = (TextView) itemView.findViewById(R.id.type_title);
            albumSubtype = (TextView) itemView.findViewById(R.id.type_subtitle);
            moreBtn = (Button) itemView.findViewById(R.id.more);
            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MusicListActivity.open(context, WikiBean.WIKI_MUSIC);
                }
            });
        }
    }
}
