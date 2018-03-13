package com.nullptr.imamusicplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AlbumFragment extends Fragment {

    private RecyclerView albumList;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album,container,false);
        albumList = (RecyclerView)v.findViewById(R.id.album_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumList.setLayoutManager(layoutManager);
        AlbumAdapter adapter = new AlbumAdapter(AlbumLab.get(getActivity()).getAlbums());

        albumList.setAdapter(adapter);
        return v;
    }

    public static AlbumFragment newInstance(){
        AlbumFragment frag = new AlbumFragment();
        return frag;
    }
}
