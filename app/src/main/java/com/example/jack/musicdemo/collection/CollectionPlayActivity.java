package com.example.jack.musicdemo.collection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;

import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.CollectionBean;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.model.CollectionPlayIView;
import com.example.jack.musicdemo.ui.album.MusicDetailActivity;

import java.util.List;

public class CollectionPlayActivity extends MusicDetailActivity implements CollectionPlayIView {

    //提供一个外部打开接口，但要传入 收藏夹实体类让其获取到信息
    public static void open(Context context , CollectionBean collectionBean){
        context.startActivity(getIntent(context,collectionBean));
    }

    public  static Intent getIntent(Context context , CollectionBean collectionBean){
        Intent intent = new Intent();
        intent.setClass(context,CollectionPlayActivity.class);
        intent.putExtra("collection",collectionBean);
        return  intent;
    }

    private CollectionBean mCollectionBean;
    private CollectionPlayPresenter cpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取出传过来的收藏夹实体类
        mCollectionBean = (CollectionBean) getIntent().getSerializableExtra("collection");
        if(mCollectionBean == null){
            finish();
        }
        cpPresenter = new CollectionPlayPresenter(this,mCollectionBean);
        cpPresenter.init();
    }
     //收藏夹详情
    @Override
    public void collectionDetail(int collectionId, Spanned title, Spanned description) {
         setCollectionData(mCollectionBean);
    }
  //收藏夹封面
    @Override
    public void collectionCover(Bitmap cover) {

    }
  //获取歌曲
    @Override
    public void getSongs(List<Song> songs) {

    }

    @Override
    public void fail(Throwable throwable) {

    }
}
