package com.example.jack.musicdemo.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * 新歌碎片
 */
public class NewSongFragment extends Fragment {


    public NewSongFragment() {
        // Required empty public constructor
    }
   //用于创建对象
    public static NewSongFragment getInstance(){
        NewSongFragment newSongFragment = new NewSongFragment();
        return  newSongFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_song, container, false);
    }

}
