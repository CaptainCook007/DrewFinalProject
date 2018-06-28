package com.bts.android.drewsproject;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;

import java.io.IOException;

import static com.bts.android.drewsproject.TracksActivity.BASE_URI;
import static com.bts.android.drewsproject.TracksActivity.TRACK_URI;

/**
 * @author ZIaDo on 6/23/18.
 */
public class MediaService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static final int START = 1;
    private MediaPlayer mp;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playTrack(intent.getIntExtra(TRACK_URI, 0));
        return Service.START_STICKY;
    }

    private void playTrack(int trackId) {
        if (mp != null && !mp.isPlaying()) {
            mp = new MediaPlayer();
            mp.setOnCompletionListener(this);
            mp.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mp.setDataSource(MediaService.this, Uri.parse(BASE_URI + trackId));
                mp.setOnPreparedListener(MediaService.this);
                mp.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public class LocalBinder extends Binder {
        MediaService getService() {
            return MediaService.this;
        }
    }

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    playTrack(msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
