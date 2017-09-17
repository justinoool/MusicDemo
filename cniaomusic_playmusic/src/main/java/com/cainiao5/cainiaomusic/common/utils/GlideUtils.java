package com.cainiao5.cainiaomusic.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cainiao5.cainiaomusic.common.net.callbacks.ImageCallback;

/**
 * Created by hongkl on 17/5/7.
 */
public class GlideUtils {
    private RequestManager glideRequest;

    /***
     * 图片加载
     * @param mContext
     * @param coverUrl
     * @param resID
     * @param circleCover
     */
    public static void with(Context mContext, String coverUrl, @NonNull int resID, final ImageView circleCover){
        Glide.with(mContext)
                .load(coverUrl)
                .placeholder(resID)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        circleCover.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        circleCover.setImageDrawable(errorDrawable);
                        super.onLoadFailed(e, errorDrawable);
                    }
                });


    }

    public static void withUri(Context context, Uri uri){
        Glide.with(context)
                .load(uri);

    }

    public static void withRoundImage(Context context, String url,ImageView imageView){
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void getBitmapWithUrl(Context context, String url, final ImageCallback callback){
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if(resource !=null){
                    callback.getImageSuccess(resource);
                }else
                    callback.getImageFail();
            }
        });
    }

}
