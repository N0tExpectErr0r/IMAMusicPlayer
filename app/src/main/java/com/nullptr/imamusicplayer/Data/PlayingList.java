package com.nullptr.imamusicplayer.Data;

import java.util.ArrayList;
import java.util.List;

public class PlayingList {

    private List<Mp3Info> playingList;

    private int currentNum;

    public PlayingList(){
        playingList = new ArrayList<>();
        currentNum = 0;
    }

    public List<Mp3Info> getPlayingList() {
        return playingList;
    }

    public void setPlayingList(List<Mp3Info> playingList) {
        this.playingList = playingList;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

}
