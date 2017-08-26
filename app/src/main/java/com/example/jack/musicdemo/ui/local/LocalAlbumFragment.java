package com.example.jack.musicdemo.ui.local;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * A simple {@link Fragment} subclass.
 * 本地音乐下的 专辑 碎片
 */
public class LocalAlbumFragment extends Fragment {


    public LocalAlbumFragment() {
        // Required empty public constructor
    }
   public static LocalAlbumFragment newInstance(){
       LocalAlbumFragment localAlbumFragment = new LocalAlbumFragment();
       return  localAlbumFragment;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_album, container, false);
    }

}
