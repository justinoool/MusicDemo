package com.cainiao5.cainiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.GlideUtils;
import com.cainiao5.cainiaomusic.data.DynamicInfo;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<RecyclerViewVideoAdapter.MyViewHolder> {
    private String BASE_VEDIO_URL = "http://orp6z38cm.bkt.clouddn.com/cainiaomusic/";
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    private List<DynamicInfo.ResultBean> result = new ArrayList<>();

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

    public void setData(List<DynamicInfo.ResultBean> data){
        this.result = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DynamicInfo.ResultBean resultBean = result.get(position);
        holder.jcVideoPlayer.setUp(BASE_VEDIO_URL + resultBean.getVideoid()+".mp4",
                JCVideoPlayer.SCREEN_LAYOUT_LIST,resultBean.getTitle());
        GlideUtils.with(holder.jcVideoPlayer.getContext(),resultBean.getThumburl(),
                R.drawable.a8p,holder.jcVideoPlayer.thumbImageView);


    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;


        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }

}
