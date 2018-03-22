package com.nullptr.imamusicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.nullptr.imamusicplayer.TAG.PlayerMsg;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer =  new MediaPlayer();       //媒体播放器对象
    private String path;                        //音乐文件路径
    private boolean isPause;                    //暂停状态
    private int position;                   //播放位置

    public final static int CURRENT_TIME_TYPE = 0;
    public final static int DURATION_TYPE = 1;
    public final static int NEXT_MUSIC = 2;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MusicThread extends Thread {
        @Override
        public void run() {
            //获得当前的时长
            while (mediaPlayer.isPlaying()) {
                int time = mediaPlayer.getCurrentPosition();
                Intent intent = new Intent("com.nullptr.imamusicplayer.musictime");
                intent.putExtra("time", time);
                intent.putExtra("type", CURRENT_TIME_TYPE);
                sendBroadcast(intent);
                //每隔一秒发送一次
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        path = intent.getStringExtra("path");
        position = intent.getIntExtra("position",0);
        int msg = intent.getIntExtra("MSG",0);

        if(msg == PlayerMsg.PLAY_MSG) {
            play(position);
        } else if(msg == PlayerMsg.PAUSE_MSG) {
            pause();
        } else if(msg == PlayerMsg.STOP_MSG) {
            stop();
        }
        return super.onStartCommand(intent,flags,startId);
    }

    /*
     * 播放音乐
     */
    private void play(int position) {
        try {
            mediaPlayer.reset();//把各项参数恢复到初始状态
            mediaPlayer.setDataSource(path);
            isPause = false;
            mediaPlayer.prepare();  //进行缓冲
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));//注册一个监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放完毕
                    Intent intent = new Intent("com.nullptr.imamusicplayer.musictime");
                    intent.putExtra("type", NEXT_MUSIC);
                    sendBroadcast(intent);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }else if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            isPause = false;
            MusicThread thread = new MusicThread();
            thread.start();
        }
    }

    /*
     * 停止音乐
     */
    private void stop(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /*
     *
     * 实现一个OnPrepareListener接口,当音乐准备好的时候开始播放
     *
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int positon;

        public PreparedListener(int positon) {
            this.positon = positon;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();    //开始播放
            if(positon > 0) {    //如果音乐不是从头播放
                mediaPlayer.seekTo(positon);
            }
            int time = mediaPlayer.getDuration();//获得所有的时间
            Intent intent = new Intent("com.nullptr.imamusicplayer.musictime");
            intent.putExtra("time", time);
            intent.putExtra("type", DURATION_TYPE);
            sendBroadcast(intent);
            MusicThread thread = new MusicThread();
            thread.start();
        }
    }

}
