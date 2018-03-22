package com.nullptr.imamusicplayer.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nullptr.imamusicplayer.Data.PlayingListLab;
import com.nullptr.imamusicplayer.Fragment.BottomFragment;
import com.nullptr.imamusicplayer.Service.PlayerService;
import com.nullptr.imamusicplayer.Service.SleepService;
import com.nullptr.imamusicplayer.TAG.PlayerMsg;
import com.nullptr.imamusicplayer.Utils.ToastUtile;

public class SleepReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", SleepService.SLEEP_END);
        switch (type){
            case SleepService.SLEEP_END:
                PlayingListLab.isPlaying= !PlayingListLab.isPlaying;
                BottomFragment.updateUI();
                Intent intent_play_pause = new Intent(context, PlayerService.class);
                intent_play_pause.putExtra("path",PlayingListLab.get().getCurrentMusic().getUrl());
                intent_play_pause.putExtra("MSG", PlayerMsg.PAUSE_MSG);
                context.startService(intent_play_pause);
                ToastUtile.showText(context,"时间到，停止播放");
                break;
        default:
        }
    }
}
