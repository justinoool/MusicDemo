package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.GlideUtils;
import com.cainiao5.cainiaomusic.data.SongListInfo;

import java.util.ArrayList;
import java.util.List;

public class NewSongAdapter extends RecyclerView.Adapter<NewSongAdapter.MyViewHolder> {
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    private List<SongListInfo.ContentBean> resultData = new ArrayList<>();

    public NewSongAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_newsong, parent,
                false));
        return holder;
    }

    public void setData(List<SongListInfo.ContentBean> data) {
        this.resultData = data;
        Log.e("setData: ", "setData: " + resultData.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SongListInfo.ContentBean dataBean = resultData.get(position);
        holder.heardNum.setText("" + dataBean.getListenum());

        holder.subTitle.setText(dataBean.getTag());
        holder.title.setText(dataBean.getTitle());
        GlideUtils.with(context, dataBean.getPic_300(), R.drawable.a8y, holder.avatar);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos,dataBean.getListid());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return resultData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView title;
        TextView subTitle;
        ImageView playAll;
        TextView heardNum;


        public MyViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.songsAvatar);
            playAll = (ImageView) itemView.findViewById(R.id.play_allsongs);
            title = (TextView) itemView.findViewById(R.id.songs_title);
            subTitle = (TextView) itemView.findViewById(R.id.songs_subtitle);
            heardNum = (TextView) itemView.findViewById(R.id.heard_number);
        }
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position,String listid);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
