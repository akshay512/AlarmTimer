package com.example.alarmtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtview;
    SeekBar alarmTimeBar;
    Boolean isCounterActive=false;
    Button startbtn;
    CountDownTimer cntdowntimer;

    public void resetTimer() {
        txtview.setText("0:30");
        alarmTimeBar.setProgress(30);
        alarmTimeBar.setEnabled(true);
        cntdowntimer.cancel();
        startbtn.setText("START");
        isCounterActive = false;
    }

    public void buttonClicked(View view) {

        if(isCounterActive){
            resetTimer();

        } else {

            isCounterActive = true;

            alarmTimeBar.setEnabled(false);

            startbtn.setText("STOP");

            cntdowntimer = new CountDownTimer(alarmTimeBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished", "Timer Finished");

                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if(seconds<=9) {
            secondString="0"+secondString;
        }

        txtview.setText(Integer.toString(minutes) +":"+ secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTimeBar = findViewById(R.id.seekBar);
        txtview = findViewById(R.id.textView);
        startbtn = findViewById(R.id.btn1);


        alarmTimeBar.setMax(600);

        alarmTimeBar.setProgress(30);

        alarmTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}