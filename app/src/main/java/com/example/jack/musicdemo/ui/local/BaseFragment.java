package com.example.jack.musicdemo.ui.local;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.ui.play.PlayingActivity;



/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * snackbar的显示
     *
     * @param toast
     */
    public void showSnackBar(String toast) {
        Snackbar.make(getActivity().getWindow().getDecorView(), toast, Snackbar.LENGTH_SHORT).show();
    }

    public void showToast(int toastRes) {
        Toast.makeText(getActivity(), getString(toastRes), Toast.LENGTH_SHORT).show();
    }


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }
    /**
     * 跳转音乐界面
     * @return
     */
    public boolean gotoSongPlayerActivity(){
        if(MusicPlayerManager.get().getPlayingSong() == null){
            showToast(R.string.music_playing_none);//没有歌曲
            return  false;
        }
        PlayingActivity.open(getActivity());
        return  true;
    }
}
