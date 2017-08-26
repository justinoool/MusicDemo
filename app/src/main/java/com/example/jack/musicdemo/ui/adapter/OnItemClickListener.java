package com.example.jack.musicdemo.ui.adapter;

import android.view.View;

/**
 *
 * Created by ${justin} on 2017/8/2010: 22
 * WeChat:Justin-Tz
 * 本地音乐下 单曲碎片中的 recyclerview 适配器 的 列表点击接口
 * 就是点击事件抽取出来做了个回调
 */

public interface OnItemClickListener<T> {
    //点击整个条目的监听
    void onItemClick(T item, int position);
     //设置按钮的监听
    void onItemSettingClick(View v, T item, int position);
}
