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
import com.cainiao5.cainiaomusic.data.DynamicInfo;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<RecyclerViewVideoAdapter.MyViewHolder> {
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    private List<DynamicInfo.BodyBean.DetailBean> result = new ArrayList<>();

    public RecyclerViewVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
        return holder;
    }

    public void setData(List<DynamicInfo.BodyBean.DetailBean> data) {
        this.result = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DynamicInfo.BodyBean.DetailBean resultBean = result.get(position);
        holder.jcVideoPlayer.setUp(resultBean.getMp4Url(),
                JCVideoPlayer.SCREEN_LAYOUT_LIST);
        holder.jcVideoPlayer.thumbImageView.getAdjustViewBounds();
        holder.jcVideoPlayer.backButton.setVisibility(View.GONE);
        holder.jcVideoPlayer.thumbImageView.setImageResource(R.drawable.a8p);
        GlideUtils.with(holder.jcVideoPlayer.getContext(), resultBean.getUrl(),
                R.drawable.a8p, holder.jcVideoPlayer.thumbImageView);
        GlideUtils.withRoundImage(holder.jcVideoPlayer.getContext(), resultBean.getPhoto(),
                holder.up_avatar);
        holder.up_user.setText(resultBean.getNickname());
        holder.up_decs.setText(resultBean.getTitle());
        holder.up_time.setText(resultBean.getCreatetime());

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;
        ImageView up_avatar;
        TextView up_user;
        TextView up_decs;
        TextView up_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
            up_avatar = (ImageView) itemView.findViewById(R.id.up_avtar);
            up_user = (TextView) itemView.findViewById(R.id.up_user);
            up_decs = (TextView) itemView.findViewById(R.id.up_decs);
            up_time = (TextView) itemView.findViewById(R.id.up_time);
        }
    }

}
