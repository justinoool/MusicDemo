package com.example.jack.musicdemo.common.util.net.callback;

import android.graphics.Bitmap;

/**
 * 图片的回调
 */
public interface ImageCallback {
    void getImageSuccess(Bitmap resource);
    void getImageFail();
}
