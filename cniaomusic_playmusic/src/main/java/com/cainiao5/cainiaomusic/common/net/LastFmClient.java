/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.cainiao5.cainiaomusic.common.net;

import android.content.Context;

import com.cainiao5.cainiaomusic.common.net.callbacks.ArtistInfoListener;
import com.cainiao5.cainiaomusic.data.AlbumInfo;
import com.cainiao5.cainiaomusic.data.AlbumQuery;
import com.cainiao5.cainiaomusic.data.ArtistInfo;
import com.cainiao5.cainiaomusic.data.ArtistQuery;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LastFmClient {

    public static final String BASE_API_URL = "http://ws.audioscrobbler.com/2.0";
    private static final Object sLock = new Object();
    private static LastFmClient sInstance;
    private ApiStore mRestService;

    public static LastFmClient getsInstance(Context context){
        synchronized (sLock){
            if(sInstance == null){
                sInstance = new LastFmClient();
                sInstance.mRestService = RestServiceFactory.create(context,BASE_API_URL,ApiStore.class);
            }
            return sInstance;
        }
    }

    /****
     * 获取歌手信息
     * @param artistQuery
     * @param listener
     */
    public void getArtistInfo(ArtistQuery artistQuery, final ArtistInfoListener listener){
        mRestService.getArtistInfo(artistQuery.mArtist, new Callback<ArtistInfo>() {
            @Override
            public void onResponse(Call<ArtistInfo> call, Response<ArtistInfo> response) {
                listener.artistInfoSucess(response.body().mArtist);

            }

            @Override
            public void onFailure(Call<ArtistInfo> call, Throwable t) {
                listener.artistInfoFailed();
            }
        });

    }

    public void getAlbumInfo(AlbumQuery albumQuery){
        mRestService.getAlbumInfo(albumQuery.mArtist, albumQuery.mALbum, new Callback<AlbumInfo>() {
            @Override
            public void onResponse(Call<AlbumInfo> call, Response<AlbumInfo> response) {

            }

            @Override
            public void onFailure(Call<AlbumInfo> call, Throwable t) {

            }
        });
    }




}
