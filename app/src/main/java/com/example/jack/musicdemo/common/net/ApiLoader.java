package com.example.jack.musicdemo.common.net;

/**
 * Created by ${justin} on 2017/9/1516: 28
 * WeChat:Justin-Tz
 */

import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVListInfo;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;


/**
 * 添加网络访问获取MV列表
 */
public class  ApiLoader extends ObjectLoader{
    private ApiStore mApiStore;

    public ApiLoader(String baseurl) {
       //实例
        mApiStore = RetrofitServiceManager.getInstance(baseurl).create(ApiStore.class);
    }


   public Observable<List<MVFromKG.DataBean.InfoBean>> getMVFromKG(){
        return observe(mApiStore.getMVFromKGInfo()) //通过api接口获取mv信息
                .map(new Func1<MVFromKG, List<MVFromKG.DataBean.InfoBean>>() {
                    @Override
                    public List<MVFromKG.DataBean.InfoBean> call(MVFromKG mvFromKG) {
                        return mvFromKG.getData().getInfo();
                    }
                });
    }


}
