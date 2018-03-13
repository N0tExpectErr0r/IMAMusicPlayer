package com.nullptr.imamusicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> mAlbums;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView albumTitle;
        TextView albumSinger;
        TextView albumSize;
        public ViewHolder(View view){
            super(view);
            albumTitle = (TextView)view.findViewById(R.id.album_title);
            albumSinger = (TextView)view.findViewById(R.id.album_singer);
            albumSize = (TextView)view.findViewById(R.id.album_size);
        }
    }



    public AlbumAdapter(List<Album> albums){
        mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = mAlbums.get(position);
        holder.albumTitle.setText(album.getAlbum_name());
        holder.albumSize.setText("共有"+album.getMusics().size()+"首歌");
        holder.albumSinger.setText(album.getArtist());
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }
}
