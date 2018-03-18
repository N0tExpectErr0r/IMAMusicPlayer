package com.nullptr.imamusicplayer.Data;

import java.util.Random;

public class PlayingListLab {
    final public static int ORDER = 0;
    final public static int REPEAT_ONCE = 1;
    final public static int RANDOM = 2;

    private static PlayingListLab sPlayingListLab;
    private PlayingList mPlayingList;
    public static int playStatus;

    public static boolean isPlaying;

    public static int duration;

    public static PlayingListLab get(){
        if (sPlayingListLab == null){
            sPlayingListLab = new PlayingListLab();
        }
        return sPlayingListLab;
    }

    private PlayingListLab(){
        mPlayingList = new PlayingList();
        playStatus = ORDER;
    }

    public PlayingList getPlayingList(){
        return mPlayingList;
    }

    public Mp3Info getCurrentMusic(){
        return mPlayingList.getPlayingList().get(mPlayingList.getCurrentNum());
    }

    public void prev(){
        int num = mPlayingList.getCurrentNum();
        switch (playStatus){
            case ORDER:
                if (num-1<0){
                    num = 0;
                }else{
                    num--;
                }
                break;
            case REPEAT_ONCE:
                break;
            case RANDOM:
                Random random = new Random();
                num = random.nextInt((mPlayingList.getPlayingList().size()-1));
                break;
            default:
        }
        mPlayingList.setCurrentNum(num);
    }

    public void next(){
        int num = mPlayingList.getCurrentNum();
        switch (playStatus){
            case ORDER:
                if (num+1>mPlayingList.getPlayingList().size()-1){
                    num = getPlayingList().getPlayingList().size()-1;
                }else{
                    num++;
                }
                break;
            case REPEAT_ONCE:
                break;
            case RANDOM:
                Random random = new Random();
                num = random.nextInt((mPlayingList.getPlayingList().size()-1));
                break;
            default:
        }
        mPlayingList.setCurrentNum(num);
    }

    public void setPlayStatus(int status){
        playStatus = status;
    }
}
