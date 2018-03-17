package com.nullptr.imamusicplayer.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nullptr.imamusicplayer.Data.Mp3Lab;
import com.nullptr.imamusicplayer.Data.PlayingListLab;
import com.nullptr.imamusicplayer.R;
import com.nullptr.imamusicplayer.Service.PlayerService;
import com.nullptr.imamusicplayer.TAG.PlayerMsg;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BottomFragment extends Fragment implements View.OnClickListener{
    private static TextView playingName;
    private static TextView playingArtist;
    private static TextView mTextViewDuration;
    private static TextView mTextViewCurrentTime;

    private static ImageButton prev;
    private static ImageButton playPause;
    private static ImageButton next;

    private static SeekBar mSeekBar;

    MusicBroadcast musicBroadcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicBroadcast = new MusicBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.nullptr.imamusicplayer.musictime");
        getActivity().registerReceiver(musicBroadcast,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(musicBroadcast);//取消注册广播
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom,container,false);
        playingName = (TextView)v.findViewById(R.id.playing_music_name);
        playingArtist = (TextView)v.findViewById(R.id.playing_music_artist);

        prev = (ImageButton) v.findViewById(R.id.prev_button);
        playPause = (ImageButton)v.findViewById(R.id.play_pause_button);
        next = (ImageButton)v.findViewById(R.id.next_button);

        mSeekBar = (SeekBar)v.findViewById(R.id.seekbar);

        mTextViewDuration = (TextView)v.findViewById(R.id.music_time);
        mTextViewCurrentTime = (TextView)v.findViewById(R.id.current_time);

        if (PlayingListLab.get().getPlayingList().getPlayingList().size() == 0){
            PlayingListLab.get().getPlayingList().setPlayingList(Mp3Lab.get(getActivity()).getMusics());
            PlayingListLab.get().getPlayingList().setCurrentNum(0);
        }


        updateUI();

        prev.setOnClickListener(this);
        playPause.setOnClickListener(this);
        next.setOnClickListener(this);
        return v;
    }

    public static void updateUI(){
        playingName.setText(PlayingListLab.get().getCurrentMusic().getTitle());
        playingArtist.setText(PlayingListLab.get().getCurrentMusic().getArtist());
        if (PlayingListLab.isPlaying){
            playPause.setBackgroundResource(R.drawable.ic_pause);
        }else{
            playPause.setBackgroundResource(R.drawable.ic_play);
        }
        int time = PlayingListLab.duration;
        Date dateDuration = new Date(time);
        SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
        mSeekBar.setMax(time);
        mTextViewDuration.setText(formatDuration.format(dateDuration));
    }

    @Override
    public void onClick(View view) {
        if (PlayingListLab.get().getPlayingList().getPlayingList().size() == 0){
            Toast.makeText(getActivity(),"播放列表中没有歌曲",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.prev_button:
                PlayingListLab.get().prev();
                PlayingListLab.isPlaying = true;
                updateUI();
                Intent intent_prev = new Intent(getActivity(), PlayerService.class);
                intent_prev.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                intent_prev.putExtra("MSG", PlayerMsg.PLAY_MSG);
                getActivity().startService(intent_prev);
                break;
            case R.id.next_button:
                PlayingListLab.get().next();
                PlayingListLab.isPlaying = true;
                updateUI();
                Intent intent_next = new Intent(getActivity(), PlayerService.class);
                intent_next.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                intent_next.putExtra("MSG", PlayerMsg.PLAY_MSG);
                getActivity().startService(intent_next);
                break;
            case R.id.play_pause_button:
                PlayingListLab.isPlaying= !PlayingListLab.isPlaying;
                updateUI();
                Intent intent_play_pause = new Intent(getActivity(), PlayerService.class);
                intent_play_pause.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                intent_play_pause.putExtra("MSG", PlayerMsg.PAUSE_MSG);
                getActivity().startService(intent_play_pause);
                break;
        }
    }

    public class MusicBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type= intent.getIntExtra("type", 0);
            switch (type){
                case PlayerService.DURATION_TYPE:
                    int time = intent.getIntExtra("time", 0);
                    //将时间转化为分钟秒
                    Date dateDuration = new Date(time);
                    PlayingListLab.duration = time;
                    SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
                    mSeekBar.setMax(time);
                    mTextViewDuration.setText(formatDuration.format(dateDuration));
                    break;
                case PlayerService.CURRENT_TIME_TYPE:
                    int currenttime=intent.getIntExtra("time", 0);
                    boolean nextMusic = intent.getBooleanExtra("next_music",false);
                    Date dateCurrentTime = new Date(currenttime);
                    SimpleDateFormat formatCurrent = new SimpleDateFormat("mm:ss");
                    mSeekBar.setProgress(currenttime);
                    mTextViewCurrentTime.setText(formatCurrent.format(dateCurrentTime));
                    if (nextMusic){
                        PlayingListLab.get().next();
                        PlayingListLab.isPlaying = true;
                        updateUI();
                        Intent intent_next = new Intent(getActivity(), PlayerService.class);
                        intent_next.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                        intent_next.putExtra("MSG", PlayerMsg.PLAY_MSG);
                        getActivity().startService(intent_next);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
