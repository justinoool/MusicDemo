package com.cainiao5.cainiaomusic.common.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ObjectLoader {
 /**
   * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
   * @param observable     
   * @param <T>   
   * @return    
   */   
 protected  <T> Observable<T> observe(Observable<T> observable){
    return observable
      .subscribeOn(Schedulers.io())
      .unsubscribeOn(Schedulers.io())  
      .observeOn(AndroidSchedulers.mainThread());
  }
}