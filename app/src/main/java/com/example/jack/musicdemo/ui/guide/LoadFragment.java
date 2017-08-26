package com.example.jack.musicdemo.ui.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${justin} on 2017/8/1813: 21
 * WeChat:Justin-Tz
 */
/**
 * fragment的预加载问题的处理
 *
 * 1.可以懒加载的fragment
 */

public abstract class LoadFragment extends Fragment {
    /**
     * 视图是否已经初始化
     */
    protected  boolean isInitView = false;

    /**
     * 是否可以加载数据
     */
    protected boolean isLoadData=false;
    private View view;

    public LoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(),container,false);
        //因为是父类所以参数1不能先设布局，留给子类去实现

        /**视图是否已经初始化*/
        isInitView = true;
        isCanLoadData();
        return view;
    }

    protected void isCanLoadData() {
        if(!isInitView){//如果视图没初始化直接结束
            return;
        }
        /**视图用户可见*/
        if (getUserVisibleHint()){
            lazyLoadData();
            isLoadData = true;
        }else {
            if(isLoadData){//防止乱加载
                stopLoad();
            }
        }
    }

    /**
     * 系统方法，告诉系统法fragment的Ui可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 停止加载数据
     */
    protected void stopLoad() {

    }

    /***
     * 预加载数据，子类实现
     */
    protected abstract void lazyLoadData();

    /*
    设置视图
    由子类实现
     */
    protected abstract int setContentView();

    //获取子类传递过来的view
    protected  View  getContentView(){
        return view;
    }

    //抽取去findViewById
    protected <T extends  View> T findViewById(int id){
        return (T) getContentView().findViewById(id);  //让子类去实现
    }
    /**
     * 视图销毁的时候将Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isInitView=false;
        isLoadData=false;
    }
}
