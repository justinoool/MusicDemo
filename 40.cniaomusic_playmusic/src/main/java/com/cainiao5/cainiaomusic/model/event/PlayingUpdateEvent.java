package com.cainiao5.cainiaomusic.model.event;

public class PlayingUpdateEvent {
    private boolean isPlaying;

    public PlayingUpdateEvent(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}