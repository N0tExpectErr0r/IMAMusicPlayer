package com.nullptr.imamusicplayer.Acitivty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nullptr.imamusicplayer.Data.AlbumLab;
import com.nullptr.imamusicplayer.Data.Mp3Lab;
import com.nullptr.imamusicplayer.Fragment.MusicListFragment;
import com.nullptr.imamusicplayer.R;

public class AlbumActivity extends AppCompatActivity {

    private MusicListFragment musicListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);
        Mp3Lab.isAlbum = true;
        AlbumLab.album_num = position;

        musicListFragment=MusicListFragment.newInstance(AlbumLab.get(this).getMusicsByPosition(position));

        android.support.v4.app.FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment,musicListFragment);
        transaction.commit();

        initToolbar(AlbumLab.get(this).getAlbums().get(position).getAlbum_name()
        ,"共"+AlbumLab.get(this).getAlbums().get(position).getMusics().size()+"首歌");
    }

    public Toolbar initToolbar(CharSequence title,CharSequence subTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setSubtitle(subTitle);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        return toolbar;
    }
}
