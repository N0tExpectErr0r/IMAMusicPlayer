package com.nullptr.imamusicplayer.Utils;

import com.nullptr.imamusicplayer.Data.Album;
import com.nullptr.imamusicplayer.Data.Mp3Info;

import java.util.ArrayList;
import java.util.List;

public class GetAlbum {
    int i=1;
    private List<Album> mAlbums;
    public List<Album> getAlbums(List<Mp3Info> musics){
        mAlbums = new ArrayList<>();
        for (Mp3Info music:musics){
            int num=findedAlbum(music.getAlbum());
            if (num==-1){
                //没有找到专辑,创建专辑并把此歌加入
                Album currentAlbum = new Album();
                currentAlbum.setAlbum_id(music.getAlbum_id());
                currentAlbum.setAlbum_name(music.getAlbum());
                currentAlbum.setArtist(music.getArtist());
                currentAlbum.getMusics().add(music);
                mAlbums.add(currentAlbum);
                i++;
            }else{
                //找到专辑的话,把这首歌加入
                mAlbums.get(num).getMusics().add(music);
            }
        }
        return mAlbums;
    }

    public int findedAlbum(String name){
        for (int i=0;i<mAlbums.size();i++){
            if (mAlbums.get(i).getAlbum_name().equals(name)){
                return i;
            }
            i++;
        }
        return -1;
    }

}
