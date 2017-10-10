package com.cainiao5.cainiaomusic.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.TransitionHelper;
import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.db.CollectionManager;
import com.cainiao5.cainiaomusic.ui.collection.CollectionCreateActivity;
import com.cainiao5.cainiaomusic.ui.collection.CollectionPlayActivity;

import java.util.List;

/**
 * @desciption: 收藏夹适配器
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<CollectionBean> collectionList;
    private Context context;
    private OnItemClickListener<CollectionBean> itemClickListener;
    private boolean inPopupMenu;

/*    MaterialDialog dialog = new MaterialDialog.Builder(this)
            .title(R.string.collection_dialog_selection_title)
            .adapter(new CollectionAdapter(this), new LinearLayoutManager(this))
            .build();
    dialog.show();*/

    public CollectionAdapter(Context context) {
        this.context = context;
        inPopupMenu = false;
        update();
    }

    public CollectionAdapter(Context context, boolean inPopupMenu) {
        this.context = context;
        this.inPopupMenu = inPopupMenu;
        update();
    }

    public void update() {
        collectionList = CollectionManager.getInstance().getCollectionList();
        for (CollectionBean bean : collectionList) {
            if (bean.getId() == -1) {
                collectionList.remove(bean);
            }
        }
        collectionList.add(createDefault());
        notifyDataSetChanged();
    }

    public void deleteCollection(CollectionBean bean) {
        if (collectionList.contains(bean)) {
            collectionList.remove(bean);
        }
        notifyDataSetChanged();
    }

    private CollectionBean createDefault() {
        CollectionBean bean = new CollectionBean();
        bean.setId(-1);
        return bean;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_collection_listitem, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectionViewHolder holder, int position) {
        final CollectionBean bean = collectionList.get(position);
        if (bean.getId() == -1) {
            holder.setting.setVisibility(View.GONE);
            holder.count.setVisibility(View.GONE);
            holder.title.setText(R.string.collection_create);
            holder.cover.setImageResource(R.drawable.ah1);
            holder.collectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(null, -1);
                    }
                    CollectionCreateActivity.open(context);
                }
            });

        } else {
            holder.setting.setVisibility(View.VISIBLE);
            holder.count.setVisibility(View.VISIBLE);
            holder.title.setText(bean.getTitle());
            String count = String.format(context.getString(R.string.song), bean.getCount());
            holder.count.setText(count);
            Glide.with(context)
                    .load(bean.getCoverUrl())
                    .placeholder(R.drawable.ah1)
                    .into(holder.cover);
            holder.collectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(bean, holder.getAdapterPosition());
                    }
                    if (inPopupMenu) {
                        return;
                    }
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        CollectionPlayActivity.open(context, bean);
                        return;
                    }
                    Intent intent = CollectionPlayActivity.getIntent(context, bean);
                    final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants((Activity) context, false,
                            new Pair<>(holder.cover, context.getString(R.string.music_share_cover)));
                    TransitionHelper.startSharedElementActivity((Activity) context, intent, pairs);
                }
            });
            if (inPopupMenu) {
                holder.setting.setVisibility(View.GONE);
            }
            holder.setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemSettingClick(holder.setting, bean, holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class CollectionViewHolder extends RecyclerView.ViewHolder {

        private View collectLayout;
        private ImageView cover;
        private TextView title, count;
        private AppCompatImageView setting;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            collectLayout = itemView.findViewById(R.id.collection_item);
            cover = (ImageView) itemView.findViewById(R.id.collection_cover);
            count = (TextView) itemView.findViewById(R.id.collection_count);
            title = (TextView) itemView.findViewById(R.id.collection_title);
            setting = (AppCompatImageView) itemView.findViewById(R.id.collection_setting);
        }
    }
}
