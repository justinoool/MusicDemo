package com.example.jack.musicdemo.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * 歌曲排名碎片
 */
public class RankingFragment extends Fragment {


    public RankingFragment() {
        // Required empty public constructor
    }
   public   static  RankingFragment getInstance(){
       RankingFragment rankingFragment = new RankingFragment();
       return rankingFragment;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

}
