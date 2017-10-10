package com.example.jack.musicdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.utils.GlideUtils;
import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVListInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.jack.musicdemo.MyApplication.context;

/**
 * Created by ${justin} on 2017/9/1515: 49
 * WeChat:Justin-Tz
 */

public class MVAdapter extends RecyclerView.Adapter<MVAdapter.MyViewHolder> {

    private Context mContext;
    private List<MVListInfo.ResultBean.MvListBean> result = new ArrayList<>();

    public MVAdapter(Context context) {
        mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_mvview,parent,false));
        return holder;
    }
    public void setData(List<MVListInfo.ResultBean.MvListBean> data){
        this.result = data;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MVListInfo.ResultBean.MvListBean resultBean = result.get(position);
        //新版API直接可以下载图片了,不需要再修改------.replaceAll("\\{[^}]*\\}","240")
        GlideUtils.with(context,resultBean.getThumbnail2(),
                R.drawable.a8p,holder.thumb);
        if(!TextUtils.isEmpty(resultBean.getTitle())){
            holder.mvName.setText(resultBean.getTitle());
        }else
            holder.mvName.setText(resultBean.getSubtitle());
        holder.mvSinger.setText(resultBean.getArtist());

        //如果有设置监听,则设置点击事件
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
         int pos = holder.getLayoutPosition();
                    //拿到视屏id
                    mOnItemClickListener.onItemClick(holder.itemView, pos,result.get(pos).getMvId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView mvName;
        TextView mvSinger;


        public MyViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            mvName = (TextView) itemView.findViewById(R.id.mv_name);
            mvSinger = (TextView) itemView.findViewById(R.id.mv_singer);
        }
    }
    //监听回调
    public interface OnItemClickListener{
        void onItemClick(View view, int position,String mvID);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}

