package com.example.jack.musicdemo.model.event;

/**
 * Created by ${justin} on 2017/8/2810: 54
 * WeChat:Justin-Tz
 * 收集收藏夹更新时间
 * 作用：用于保存按钮监听
 */

public class CollectionUpdateEvent {
    private boolean update;
    public CollectionUpdateEvent(boolean update){
        this.update = update;
    }

    public  boolean isUpdate(){
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
