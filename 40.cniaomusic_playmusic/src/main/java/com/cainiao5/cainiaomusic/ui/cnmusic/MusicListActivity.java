package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cainiao5.cainiaomusic.BaseActivity;
import com.cainiao5.cainiaomusic.R;

/**
 * @author: cpacm
 * @date: 2016/8/23
 * @desciption: 专辑，电台列表界面
 */
public class MusicListActivity extends BaseActivity {

    public static void open(Context context, String musicType) {
        Intent intent = new Intent(context, MusicListActivity.class);
        intent.putExtra("MusicType", musicType);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_more);
    }


}
