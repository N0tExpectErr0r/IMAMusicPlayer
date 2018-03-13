package com.nullptr.imamusicplayer;

import android.content.Context;

import java.util.List;

public class Mp3Lab {
    private static Mp3Lab sMp3Lab;
    private List<Mp3Info> mMusics;

    public static Mp3Lab get(Context context){
        if (sMp3Lab == null){
            sMp3Lab = new Mp3Lab(context);
        }
        return sMp3Lab;
    }

    private Mp3Lab(Context context){
        FindSongs finder = new FindSongs();
        mMusics = finder.getMp3Infos(context.getContentResolver());
    }

    public List<Mp3Info> getMusics(){
        return mMusics;
    }

    public Mp3Info getMusic(int position){
        return mMusics.get(position);
    }

    public int getCount(){
        return mMusics.size();
    }
}
