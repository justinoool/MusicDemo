package com.cainiao5.cainiaomusic.ui.cnmusic;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends DialogFragment {


    public static MoreFragment newInstance(String id, int startFrom, String albumId, String artistId) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("albumid", albumId);
        args.putString("artistid", artistId);
        args.putInt("type", startFrom);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

}
