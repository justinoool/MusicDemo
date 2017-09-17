package com.cainiao5.cainiaomusic.ui.guide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment的预加载问题的处理(参考)
 *
 * 1.可以懒加载的fragment
 */
public abstract class LoadFragment extends Fragment {

    /***
     * 1.视图是否已经初始化
     */
    protected boolean isInitView =false;

    /***
     * 2.是否可以加载数据
     */
    protected boolean isLoadData = false;

    private View view;
    public LoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(setContentView(),container,false);

        /**视图是否已经初始化**/
        isInitView = true;
        isCanLoadData();


        return view;
    }

    protected void isCanLoadData(){
        if(!isInitView){
            return;
        }
        /**视图用户可见*/
        if(getUserVisibleHint()){
            lazyLoadData();
            isLoadData = true;
        }else{
            if(isLoadData){
                stopLoad();
            }
        }


    }

    /****
     * 系统方法,告诉系统,fragment的UI可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /***
     * 停止加载数据
     */
    protected void stopLoad() {

    }

    /***
     * 预加载数据,子类实现
     */
    protected abstract void lazyLoadData();

    /***
     * 设置视图
     * @return
     */
    protected abstract int setContentView();


    /***
     * 子类传递过来的view视图
     * @return
     */
    protected View getContentView(){
        return view;
    }


    protected <T extends View> T findViewById(int id){


        return (T)getContentView().findViewById(id);
    }


    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInitView = false;
        isLoadData = false;

    }

}
