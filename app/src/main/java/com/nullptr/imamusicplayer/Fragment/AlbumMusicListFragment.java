package com.nullptr.imamusicplayer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nullptr.imamusicplayer.Adapter.AlbumMusicAdapter;
import com.nullptr.imamusicplayer.Data.Mp3Info;
import com.nullptr.imamusicplayer.Data.Mp3Lab;
import com.nullptr.imamusicplayer.R;

import java.util.List;

public class AlbumMusicListFragment extends Fragment{

    private RecyclerView musicList;
    private static List<Mp3Info> mMusics;
    private static AlbumMusicAdapter adapter;

    public static AlbumMusicListFragment newInstance(List<Mp3Info> musics){
        mMusics = musics;
        AlbumMusicListFragment frag = new AlbumMusicListFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list,container,false);

        if (Mp3Lab.get(getActivity()).getCount()<=0){
            Toast.makeText(getActivity(),"你的手机中没有音乐",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        musicList = (RecyclerView)v.findViewById(R.id.music_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        musicList.setLayoutManager(layoutManager);
        if (mMusics==null) {
            adapter = new AlbumMusicAdapter(Mp3Lab.get(getActivity()).getMusics());
            musicList.setAdapter(adapter);
        }else{
            adapter = new AlbumMusicAdapter(mMusics);
            musicList.setAdapter(adapter);
        }
        return v;
    }


    public static void notifyChange(){
        adapter.notifyDataSetChanged();
    }
}
