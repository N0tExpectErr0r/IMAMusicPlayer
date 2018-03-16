package com.nullptr.imamusicplayer.Data;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private List<Mp3Info> musics;
    private String album_name;
    private String artist;
    private long album_id;

    public Album(){
        musics = new ArrayList<>();
        album_name = "";
        artist = "";
        album_id = -1;
    }

    public List<Mp3Info> getMusics() {
        return musics;
    }

    public void setMusics(List<Mp3Info> musics) {
        this.musics = musics;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }
}
