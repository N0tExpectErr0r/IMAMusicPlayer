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

import com.nullptr.imamusicplayer.Data.Mp3Info;
import com.nullptr.imamusicplayer.Data.PlayingListLab;
import com.nullptr.imamusicplayer.R;
import com.nullptr.imamusicplayer.Service.PlayerService;
import com.nullptr.imamusicplayer.TAG.PlayerMsg;
import com.nullptr.imamusicplayer.Utils.ToastUtile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BottomFragment extends Fragment implements View.OnClickListener{
    private static TextView playingName;
    private static TextView mTextViewDuration;
    private static TextView mTextViewCurrentTime;

    private static ImageButton prev;
    private static ImageButton playPause;
    private static ImageButton next;
    private static ImageButton playStatus;

    private static Context mContext;

    private static SeekBar mSeekBar;

    public MusicBroadcast musicBroadcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicBroadcast = new MusicBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.nullptr.imamusicplayer.musictime");
        if (getActivity().getClass().getSimpleName().equals("MainActivity")) {
            getActivity().registerReceiver(musicBroadcast, filter);
        }
        mContext = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getClass().getSimpleName().equals("MainActivity")) {
            getActivity().unregisterReceiver(musicBroadcast);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom,container,false);
        playingName = (TextView)v.findViewById(R.id.playing_music_name);

        prev = (ImageButton) v.findViewById(R.id.prev_button);
        playPause = (ImageButton)v.findViewById(R.id.play_pause_button);
        next = (ImageButton)v.findViewById(R.id.next_button);
        playStatus = (ImageButton)v.findViewById(R.id.play_status);

        mSeekBar = (SeekBar)v.findViewById(R.id.seekbar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int position = seekBar.getProgress();
                String path = PlayingListLab.get().getCurrentMusic().getUrl();
                PlayingListLab.isPlaying = true;
                updateUI();
                Intent intent = new Intent(seekBar.getContext(),PlayerService.class);

                intent.putExtra("path",path);
                intent.putExtra("position",position);
                intent.putExtra("MSG", PlayerMsg.PLAY_MSG);
                seekBar.getContext().startService(intent);
            }
        });

        mTextViewDuration = (TextView)v.findViewById(R.id.music_time);
        mTextViewCurrentTime = (TextView)v.findViewById(R.id.current_time);

        if (PlayingListLab.get().getPlayingList().getPlayingList().size() == 0){
            Mp3Info music = new Mp3Info();
            music.setTitle("歌曲名称");
            music.setArtist("歌手名");
            PlayingListLab.get().getPlayingList().getPlayingList().add(music);
        }

        updateUI();


        prev.setOnClickListener(this);
        playPause.setOnClickListener(this);
        next.setOnClickListener(this);
        playStatus.setOnClickListener(this);
        return v;
    }

    public static void updateUI(){
        playingName.setText(PlayingListLab.get().getCurrentMusic().getTitle());

        if (PlayingListLab.isPlaying){
            playPause.setBackgroundResource(R.drawable.ic_pause_white);
        }else{
            playPause.setBackgroundResource(R.drawable.ic_play_white);
        }
        switch (PlayingListLab.playStatus){
            case PlayingListLab.ORDER:
                playStatus.setBackgroundResource(R.drawable.ic_order);
                break;
            case PlayingListLab.REPEAT_ONCE:
                playStatus.setBackgroundResource(R.drawable.ic_repeat);
                break;
            case PlayingListLab.RANDOM:
                playStatus.setBackgroundResource(R.drawable.ic_random);
                break;
        }
        int time = PlayingListLab.duration;
        Date dateDuration = new Date(time);
        SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
        mSeekBar.setMax(time);

        mTextViewDuration.setText(formatDuration.format(dateDuration));
    }

    @Override
    public void onClick(View view) {
        if (PlayingListLab.get().getPlayingList().getPlayingList().get(0).getTitle().equals("歌曲名称")){
            ToastUtile.showText(getActivity(),"播放列表中没有歌曲");
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
            case R.id.play_status:
                if (++PlayingListLab.playStatus>2){
                    PlayingListLab.playStatus = 0;
                }
                updateUI();

                switch (PlayingListLab.playStatus){
                    case PlayingListLab.ORDER:
                        ToastUtile.showText(getActivity(),"顺序播放");
                        break;
                    case PlayingListLab.REPEAT_ONCE:
                        ToastUtile.showText(getActivity(),"单曲循环");
                        break;
                    case PlayingListLab.RANDOM:
                        ToastUtile.showText(getActivity(),"随机播放");
                        break;
                }
                break;
        }
    }

    private void nextMusic(){

    }

    public static class MusicBroadcast extends BroadcastReceiver {
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
                    Date dateCurrentTime = new Date(currenttime);
                    SimpleDateFormat formatCurrent = new SimpleDateFormat("mm:ss");
                    mSeekBar.setProgress(currenttime);
                    mTextViewCurrentTime.setText(formatCurrent.format(dateCurrentTime));
                    break;
                case PlayerService.NEXT_MUSIC:
                    PlayingListLab.get().next();
                    PlayingListLab.isPlaying = true;
                    updateUI();
                    Intent intent_next = new Intent(mContext, PlayerService.class);
                    intent_next.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                    intent_next.putExtra("MSG", PlayerMsg.PLAY_MSG);
                    mContext.startService(intent_next);
                    break;
                default:
                    break;
            }
        }
    }
}
