package com.example.material;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    float windowX;
    float windowY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startTimer() {
        final ProgressBar progressBar = findViewById(R.id.progressTime);
        final int totalTime = 60000;
        new CountDownTimer(totalTime, 1000) {
            public void onFinish() {
                progressBar.setProgress(100);
                Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
            }

            public void onTick(long millisUntilFinished) {
                int timeLeft = (int) millisUntilFinished;
                int progress = (int) (((float) (totalTime - timeLeft) / totalTime) * 100);
                progressBar.setProgress(progress);
            }
        }.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup layout = findViewById(R.id.gameFrame);

        if (hasFocus) {
            int width = layout.getWidth();
            int height = layout.getHeight();
            setWindowSize(width, height);
            startTimer();
            spawnDuck();
        }
    }

    public void setWindowSize(int width, int height) {
        this.windowX = width;
        this.windowY = height;
        Toast.makeText(getApplicationContext(), "New Window Focus: " + windowX + " " + windowY, Toast.LENGTH_SHORT).show();
    }

    private void spawnDuck() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.duck_yellow);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // get layout
        ViewGroup layout = findViewById(R.id.gameFrame);
        layout.addView(imageView);
        // get params
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // size
        params.width = 200;
        params.height = 200;
        imageView.setLayoutParams(params);
        // pos
        imageView.setX((windowX-200)/2);
        imageView.setY((windowY-200)/2);

    }




}