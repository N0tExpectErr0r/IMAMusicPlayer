package com.nullptr.imamusicplayer.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nullptr.imamusicplayer.Acitivty.AlbumActivity;
import com.nullptr.imamusicplayer.Data.Album;
import com.nullptr.imamusicplayer.R;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private List<Album> mAlbums;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView albumTitle;
        TextView albumSinger;
        TextView albumSize;
        private LinearLayout mLinearLayout;
        public ViewHolder(View view){
            super(view);
            albumTitle = (TextView)view.findViewById(R.id.album_title);
            albumSinger = (TextView)view.findViewById(R.id.album_singer);
            albumSize = (TextView)view.findViewById(R.id.album_size);
            mLinearLayout = (LinearLayout)view.findViewById(R.id.album_layout);
        }

    }



    public AlbumAdapter(List<Album> albums){
        mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(),AlbumActivity.class);
                intent.putExtra("position",position);
                parent.getContext().startActivity(intent);
            }
        });
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
