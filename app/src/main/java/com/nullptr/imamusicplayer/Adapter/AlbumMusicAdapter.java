package com.nullptr.imamusicplayer.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nullptr.imamusicplayer.Data.AlbumLab;
import com.nullptr.imamusicplayer.Data.Mp3Info;
import com.nullptr.imamusicplayer.Data.PlayingListLab;
import com.nullptr.imamusicplayer.Fragment.BottomFragment;
import com.nullptr.imamusicplayer.R;
import com.nullptr.imamusicplayer.Service.PlayerService;
import com.nullptr.imamusicplayer.TAG.PlayerMsg;
import java.util.List;

public class AlbumMusicAdapter extends RecyclerView.Adapter<AlbumMusicAdapter.ViewHolder>{

    private List<Mp3Info> musics;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView musicTitle;
        TextView musicSinger;
        TextView musicAlbum;
        TextView musicNumber;
        LinearLayout mLinearLayout;
        public ViewHolder(View view){
            super(view);
            musicTitle = (TextView)view.findViewById(R.id.music_title);
            musicSinger = (TextView)view.findViewById(R.id.music_singer);
            musicAlbum = (TextView)view.findViewById(R.id.music_album);
            musicNumber = (TextView)view.findViewById(R.id.music_number);
            mLinearLayout = (LinearLayout)view.findViewById(R.id.music_layout);
        }

    }

    public AlbumMusicAdapter(List<Mp3Info> music){
        musics = music;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(),PlayerService.class);
                intent.putExtra("path", AlbumLab.get(parent.getContext()).getMusicsByPosition(AlbumLab.album_num).get(position).getUrl());
                PlayingListLab.get().getPlayingList().setPlayingList(AlbumLab.get(parent.getContext()).getMusicsByPosition(AlbumLab.album_num));
                PlayingListLab.get().getPlayingList().setCurrentNum(position);
                PlayingListLab.isPlaying = true;
                BottomFragment.updateUI();

                PlayingListLab.get().getPlayingList().setCurrentNum(position);
                intent.putExtra("MSG", PlayerMsg.PLAY_MSG);
                parent.getContext().startService(intent);
            }
        });
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
