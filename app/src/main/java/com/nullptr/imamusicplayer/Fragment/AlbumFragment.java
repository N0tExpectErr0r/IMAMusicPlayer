package com.nullptr.imamusicplayer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullptr.imamusicplayer.Adapter.AlbumAdapter;
import com.nullptr.imamusicplayer.Data.AlbumLab;
import com.nullptr.imamusicplayer.R;


public class AlbumFragment extends Fragment {

    private RecyclerView albumList;
    private static AlbumAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album,container,false);
        albumList = (RecyclerView)v.findViewById(R.id.album_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumList.setLayoutManager(layoutManager);
        adapter = new AlbumAdapter(AlbumLab.get(getActivity()).getAlbums());

        albumList.setAdapter(adapter);
        return v;
    }

    public static AlbumFragment newInstance(){
        AlbumFragment frag = new AlbumFragment();
        return frag;
    }

    public static void notifyChange(){
        adapter.notifyDataSetChanged();
    }
}
