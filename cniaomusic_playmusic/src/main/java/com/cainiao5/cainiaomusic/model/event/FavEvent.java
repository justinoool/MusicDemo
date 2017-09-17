package com.cainiao5.cainiaomusic.model.event;

public class FavEvent {
    long wikiId;
    boolean fav;

    public FavEvent(long wikiId, boolean fav) {
        this.wikiId = wikiId;
        this.fav = fav;
    }

    public long getWikiId() {
        return wikiId;
    }

    public boolean isFav() {
        return fav;
    }
}