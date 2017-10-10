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

package com.example.jack.musicdemo.common.net;


import android.content.Context;

import com.example.jack.musicdemo.common.net.callbacks.ArtistInfoListener;
import com.example.jack.musicdemo.data.AlbumInfo;
import com.example.jack.musicdemo.data.AlbumQuery;
import com.example.jack.musicdemo.data.ArtistInfo;
import com.example.jack.musicdemo.data.ArtistQuery;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LastFmClient {

    public static final String BASE_API_URL = "http://ws.audioscrobbler.com/2.0";
    private static final Object sLock = new Object();
    private static LastFmClient sInstance;
    private LastFmRestService mRestService;

   public static LastFmClient getInstance (Context context){
       synchronized (sLock){
           if(sInstance == null){
               sInstance =new LastFmClient();
               sInstance.mRestService
                       = RestServiceFactory
                       .create(context,BASE_API_URL,LastFmRestService.class);
           }
           return  sInstance;
       }
   }

    /**
     * 获取歌手信息
     * @param artistQuery
     * @param listener
     */
    public void getArtistInfo(ArtistQuery artistQuery ,final ArtistInfoListener listener){
       mRestService.getArtistInfo(artistQuery.mArtist, new Callback<ArtistInfo>() {
            @Override
            public void success(ArtistInfo artistInfo, Response response) {
                listener.artistInfoSucess(artistInfo.mArtist);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.artistInfoFailed();
            }
        });
    }

    public void getAlbumInfo(AlbumQuery albumQuery){

        mRestService.getAlbumInfo(albumQuery.mALbum, albumQuery.mALbum, new Callback<AlbumInfo>() {
            @Override
            public void success(AlbumInfo albumInfo, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


}
