package com.nullptr.imamusicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SleepService extends Service {
    final static public int SLEEP_END = 0;
    private int sleepTime;
    private SleepThread thread;
    public SleepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sleepTime = intent.getIntExtra("sleep_time",1800);
        startSleep(sleepTime);
        return super.onStartCommand(intent, flags, startId);
    }

    private void startSleep(final int time){
        thread = new SleepThread(time);
        thread.start();
    }

    private class SleepThread extends Thread{
        private int time;
        public SleepThread(int _time){
            this.time = _time;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(time*1000);    //睡眠time秒后，暂停播放
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent("com.nullptr.imamusicplayer.sleep");
            intent.putExtra("type",SLEEP_END);
            sendBroadcast(intent);
        }
    }

}
