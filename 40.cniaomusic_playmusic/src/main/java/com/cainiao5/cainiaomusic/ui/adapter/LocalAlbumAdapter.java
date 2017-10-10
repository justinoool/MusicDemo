package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.Album;

import java.util.ArrayList;
import java.util.List;


/**
 * @description: 本地专辑适配器
 */
public class LocalAlbumAdapter extends RecyclerView.Adapter<LocalAlbumAdapter.LocalAlbumViewHolder> {

    private List<Album> albumList;
    private Context context;

    public LocalAlbumAdapter(Context context) {
        this.context = context;
        albumList = new ArrayList<>();
    }

    public void setData(List<Album> alba) {
        this.albumList = alba;
        notifyDataSetChanged();
    }

    @Override
    public LocalAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.recycler_localalbum_listitem, parent, false);
        return new LocalAlbumViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final LocalAlbumViewHolder itemHolder, int position) {
        Album album = albumList.get(position);
        itemHolder.title.setText(album.title);
        itemHolder.artist.setText(album.artistName);
//        Glide.with(context)
//                .load(album.cover)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        itemHolder.cover.setImageBitmap(resource);
//                        new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
//                            @Override
//                            public void onGenerated(Palette palette) {
//                                Palette.Swatch swatch = palette.getVibrantSwatch();
//                                if (swatch != null) {
//                                    int color = swatch.getRgb();
//                                    itemHolder.footerView.setBackgroundColor(color);
//                                    int textColor = swatch.getTitleTextColor();
//                                    itemHolder.title.setTextColor(textColor);
//                                    itemHolder.artist.setTextColor(textColor);
//                                } else {
//                                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
//                                    if (mutedSwatch != null) {
//                                        int color = mutedSwatch.getRgb();
//                                        itemHolder.footerView.setBackgroundColor(color);
//                                        int textColor = mutedSwatch.getTitleTextColor();
//                                        itemHolder.title.setTextColor(textColor);
//                                        itemHolder.artist.setTextColor(textColor);
//                                    }
//                                }
//                            }
//                        });
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class LocalAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View content;
        private ImageView cover;
        private TextView title, artist;
        private LinearLayout footerView;

        public LocalAlbumViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.card_view);
            footerView = (LinearLayout) itemView.findViewById(R.id.album_footer);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            title = (TextView) itemView.findViewById(R.id.album_title);
            artist = (TextView) itemView.findViewById(R.id.album_artist);
            content.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Album album = albumList.get(position);
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                LocalAlbumDetailActivity.open(context, album);
//                return;
//            }
//            Intent intent = LocalAlbumDetailActivity.getIntent(context, album);
//            final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants((Activity) context, false,
//                    new Pair<>(cover, context.getString(R.string.music_share_cover)));
//            TransitionHelper.startSharedElementActivity((Activity) context, intent, pairs);
        }
    }

}



