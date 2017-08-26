package com.example.jack.musicdemo.ui.local;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;

import static com.example.jack.musicdemo.R.id.a;

/**
 * A simple {@link Fragment} subclass.
 * 本地音乐下的 歌手碎片
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
    }
  public static ArtistFragment newInstance(){
       ArtistFragment artistFragment =  new ArtistFragment() ;
      return artistFragment;
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

}
