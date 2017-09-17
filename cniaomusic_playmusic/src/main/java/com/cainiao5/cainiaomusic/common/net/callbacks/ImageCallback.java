package com.cainiao5.cainiaomusic.common.net.callbacks;

import android.graphics.Bitmap;

/**
 * Created by hongkl on 17/7/24.
 */
public interface ImageCallback {
    void getImageSuccess(Bitmap resource);
    void getImageFail();
}
