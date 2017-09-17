package com.cainiao5.cainiaomusic.ui.cnmusic;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.ui.play.PlayingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

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

    /**
     * 跳转到音乐播放界面
     * @return
     */
    public boolean gotoSongPlayerActivity() {
        if (MusicPlayerManager.get().getPlayingSong() == null) {
            showToast(R.string.music_playing_none);
            return false;
        }
        PlayingActivity.open(getActivity());
        return true;
    }
}
