package com.example.jack.musicdemo.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by ${justin} on 2017/8/2217: 00
 * WeChat:Justin-Tz
 * 图片加载工具类
 * 将Glide封装成工具
 */

public class ImageUtils {
    /**
     * Glide加载图片
     */
    public static void GlideWith(Context context , String coverUrl
    , @NonNull int resID, final ImageView imageView){
        Glide.with(context)
                .load(coverUrl)
                .placeholder(resID)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                      imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setImageDrawable(errorDrawable);
                    }
                });
    }

}
