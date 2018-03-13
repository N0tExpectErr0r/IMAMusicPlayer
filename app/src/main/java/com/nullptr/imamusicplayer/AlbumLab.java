package com.nullptr.imamusicplayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AlbumLab {
    private static AlbumLab sAlbumLab;
    private List<Album> mAlbums;

    public static AlbumLab get(Context context){
        if (sAlbumLab==null){
            sAlbumLab = new AlbumLab(context);
        }
        return sAlbumLab;
    }

    private AlbumLab(Context context){
        GetAlbum getter = new GetAlbum();
        mAlbums = getter.getAlbums(Mp3Lab.get(context).getMusics());
    }

    public List<Album> getAlbums(){
       return mAlbums;
    }

    public List<Mp3Info> getMusicsByPosition(int position){
        return mAlbums.get(position).getMusics();
    }

    public List<Mp3Info> getMusicsById(int album_id){
        for (Album album:mAlbums){
            if (album.getAlbum_id() == album_id){
                return album.getMusics();
            }
        }
        return new ArrayList<>();
    }

    public int getCount(){
        return mAlbums.size();
    }
}
