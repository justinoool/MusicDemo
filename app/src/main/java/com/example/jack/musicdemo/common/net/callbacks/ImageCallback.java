package com.example.jack.musicdemo.common.net.callbacks;

import android.graphics.Bitmap;

//图片加载成功或失败的回调接口
public interface ImageCallback {
    void getImageSuccess(Bitmap resource);
    void getImageFail();
}
