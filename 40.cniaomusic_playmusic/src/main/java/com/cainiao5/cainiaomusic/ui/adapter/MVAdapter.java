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
import com.cainiao5.cainiaomusic.data.MVListInfo;

import java.util.ArrayList;
import java.util.List;

public class MVAdapter extends RecyclerView.Adapter<MVAdapter.MyViewHolder> {
    private String BASE_VEDIO_URL = "http://orp6z38cm.bkt.clouddn.com/cainiaomusic/";
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    private List<MVListInfo.ResultBean.MvListBean> result = new ArrayList<>();

    public MVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_mvview, parent,
                false));
        return holder;
    }

    public void setData(List<MVListInfo.ResultBean.MvListBean> data){
        this.result = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MVListInfo.ResultBean.MvListBean resultBean = result.get(position);
//        holder.jcVideoPlayer.setUp(BASE_VEDIO_URL + resultBean.getMv_id()+".mp4",
//                JCVideoPlayer.SCREEN_LAYOUT_LIST,resultBean.getTitle());
        GlideUtils.with(context,resultBean.getThumbnail(),
                R.drawable.a8p,holder.thumb);

        holder.mvName.setText(resultBean.getTitle());
        holder.mvSinger.setText(resultBean.getArtist());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos,result.get(pos).getMv_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
//        JCVideoPlayerStandard jcVideoPlayer;
        ImageView thumb;
        TextView mvName;
        TextView mvSinger;

        public MyViewHolder(View itemView) {
            super(itemView);
//            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            mvName = (TextView) itemView.findViewById(R.id.mv_name);
            mvSinger = (TextView) itemView.findViewById(R.id.mv_singer);
        }
    }


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position,String mvID);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
