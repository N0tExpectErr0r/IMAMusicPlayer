package com.nullptr.imamusicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    private List<Mp3Info> musics;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView musicTitle;
        TextView musicSinger;
        TextView musicAlbum;
        TextView musicNumber;
        public ViewHolder(View view){
            super(view);
            musicTitle = (TextView)view.findViewById(R.id.music_title);
            musicSinger = (TextView)view.findViewById(R.id.music_singer);
            musicAlbum = (TextView)view.findViewById(R.id.music_album);
            musicNumber = (TextView)view.findViewById(R.id.music_number);
        }

    }

    public MusicAdapter(List<Mp3Info> music){
        musics = music;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mp3Info music = musics.get(position);
        holder.musicTitle.setText(music.getTitle());
        holder.musicSinger.setText(music.getArtist());
        holder.musicAlbum.setText("-"+music.getAlbum());
        holder.musicNumber.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
}
