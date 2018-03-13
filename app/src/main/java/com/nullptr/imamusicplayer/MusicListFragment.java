package com.nullptr.imamusicplayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MusicListFragment extends Fragment{

    private List<Mp3Info> songs;
    private RecyclerView musicList;
    private TextView musicNums;
    private FindSongs finder;

    public static MusicListFragment newInstance(){
        MusicListFragment frag = new MusicListFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list,container,false);

        finder = new FindSongs();
        songs = finder.getMp3Infos(getActivity().getContentResolver());

        if (songs.size()<=0){
            Toast.makeText(getActivity(),"你的手机中没有音乐",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        musicNums = (TextView)v.findViewById(R.id.music_num);
        musicNums.setText("共有"+songs.size()+"首歌曲");

        musicList = (RecyclerView)v.findViewById(R.id.music_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        musicList.setLayoutManager(layoutManager);
        MusicAdapter adapter = new MusicAdapter(songs);
        musicList.setAdapter(adapter);

        return v;
    }
}
