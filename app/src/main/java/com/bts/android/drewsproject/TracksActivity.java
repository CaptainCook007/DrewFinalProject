package com.bts.android.drewsproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TracksActivity extends AppCompatActivity {
    public static final String TRACK_URI = "TRACK_URI";
    public static final String BASE_URI = "android.resource://com.bts.android.drewsproject/";
    private TracksAdapter tracksAdapter;
    private RecyclerView tracksList;
    private List<Track> tracks;
    private Messenger mService;
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tracksList = findViewById(R.id.rv_tracks);
        tracksList.setHasFixedSize(true);
        tracksList.setLayoutManager(new LinearLayoutManager(this));
        tracks = new ArrayList<>(10);
        tracks.add(new Track(R.raw.bensoundbrazilsamba, 1, "Samba"));
        tracks.add(new Track(R.raw.bensoundcountryboy, 1, "Country"));
        tracks.add(new Track(R.raw.bensoundindia, 1, "India"));
        tracks.add(new Track(R.raw.bensoundlittleplanet, 1, "LittlePlanet"));
        tracks.add(new Track(R.raw.bensoundpsychedelic, 1, "Psychedelic"));
        tracks.add(new Track(R.raw.bensoundrelaxing, 1, "Relaxing"));
        tracks.add(new Track(R.raw.bensoundtheelevatorbossanova, 1, "theelevatorbossanova"));

        tracksAdapter = new TracksAdapter(tracks, new TracksAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (mBound) {
                    Message msg = Message.obtain(null, MediaService.START,
                            tracks.get(position).getDir(), 0);
                    try {
                        mService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent srvIntent = new Intent(TracksActivity.this, MediaService.class);
                    srvIntent.putExtra(TRACK_URI, tracks.get(position).getDir());
                    bindService(srvIntent, mConnection, Context.BIND_AUTO_CREATE);
                }
            }
        });
        tracksList.setAdapter(tracksAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to the service
        bindService(new Intent(this, MediaService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
