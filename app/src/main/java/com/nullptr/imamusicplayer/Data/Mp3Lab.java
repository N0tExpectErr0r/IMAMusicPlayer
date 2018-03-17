package com.nullptr.imamusicplayer.Data;

import android.content.Context;

import com.nullptr.imamusicplayer.Utils.MediaUtils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
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
        mMusics = MediaUtils.getMp3Infos(context.getContentResolver());
        sort_title();
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

    public void sort_title(){
        Collections.sort(mMusics,new Comparator<Mp3Info>(){
            public int compare(Mp3Info arg0, Mp3Info arg1) {
                Collator collator = Collator.getInstance();
                return collator.getCollationKey(arg0.getTitle()).compareTo(
                        collator.getCollationKey(arg1.getTitle()));
            }
        });
    }

    public void sort_artist(){
        Collections.sort(mMusics,new Comparator<Mp3Info>(){
            public int compare(Mp3Info arg0, Mp3Info arg1) {
                Collator collator = Collator.getInstance();
                return collator.getCollationKey(arg0.getArtist()).compareTo(
                        collator.getCollationKey(arg1.getArtist()));
            }
        });
    }
}
