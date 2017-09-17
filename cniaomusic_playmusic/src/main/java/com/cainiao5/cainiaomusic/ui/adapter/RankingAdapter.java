package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.GlideUtils;
import com.cainiao5.cainiaomusic.data.RankingInfo;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    private List<RankingInfo.ContentBean> result = new ArrayList<>();

    public RankingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.online_ranklist_item, parent,
                false));
        return holder;
    }

    public void setData(List<RankingInfo.ContentBean> data){
        this.result = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //排行榜的实例
        final RankingInfo.ContentBean contentBean = result.get(position);
        holder.ranklistTitle.setText(contentBean.getName());
        //图片下载
        GlideUtils.with(context,contentBean.getPic_s192(),R.drawable.a8y,holder.ranklistIcon);
        //三首歌
        holder.songlistTitle0.setText(result.get(position).getContent().get(0).getTitle() + "-" + result.get(position).getContent().get(0).getAuthor());
        holder.songlistTitle1.setText(result.get(position).getContent().get(1).getTitle() + "-" + result.get(position).getContent().get(1).getAuthor());
        holder.songlistTitle2.setText(result.get(position).getContent().get(2).getTitle() + "-" + result.get(position).getContent().get(2).getAuthor());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = contentBean.getType();
                    mOnItemClickLitener.onItemClick(holder.itemView, type);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ranklistIcon;
        TextView ranklistTitle;
        TextView songlistTitle0;
        TextView songlistTitle1;
        TextView songlistTitle2;

        public MyViewHolder(View itemView) {
            super(itemView);
            ranklistIcon = (ImageView) itemView.findViewById(R.id.ranklist_item_icon);
            ranklistTitle = (TextView) itemView.findViewById(R.id.ranklist_item_title);
            songlistTitle0 = (TextView) itemView.findViewById(R.id.ranklist_item_songlist_top4_title_0);
            songlistTitle1 = (TextView) itemView.findViewById(R.id.ranklist_item_songlist_top4_title_1);
            songlistTitle2 = (TextView) itemView.findViewById(R.id.ranklist_item_songlist_top4_title_2);
        }
    }


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
