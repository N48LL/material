package com.example.material;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float windowX;
    float windowY;
    int duckSize = 200;
    boolean unpause = true;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startTimer() {
        final ProgressBar progressBar = findViewById(R.id.progressTime);
        final int totalTime = 10000;
        new CountDownTimer(totalTime, 1000) {
            public void onFinish() {
                progressBar.setProgress(100);
                Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
                unpause = false;
                scoreView();
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
        //Toast.makeText(getApplicationContext(), "New Window Focus: " + windowX + " " + windowY, Toast.LENGTH_SHORT).show();
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
        params.width = duckSize;
        params.height = duckSize;
        imageView.setLayoutParams(params);
        // pos
        imageView.setX((windowX - duckSize) / 2);
        imageView.setY((windowY - duckSize) / 2);


            // touchitouchi
            imageView.setOnClickListener(v -> {
                if (unpause) {
                    duckSize -= 2;
                    params.width = duckSize;
                    params.height = duckSize;
                    imageView.setLayoutParams(params);
                    imageView.setX(randomNumber(0, (windowX - (duckSize * 2))));
                    imageView.setY(randomNumber(0, (windowY - (duckSize * 2))));
                    score++;
                    //Toast.makeText(getApplicationContext(), "Duck Clicked! New Pos: X:" + imageView.getX() + ", Y: " + imageView.getY(), Toast.LENGTH_SHORT).show();
                    System.out.println("Duck Clicked! New Pos: X:" + imageView.getX() + ", Y: " + imageView.getY());
                }
            });
        }

    private float randomNumber(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    public void scoreView() {
        TextView scoreView = new TextView(this);
        scoreView.setText("Score: " + score);
        scoreView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // get layout
        ViewGroup layout = findViewById(R.id.gameFrame);
        layout.addView(scoreView);
        layout.bringChildToFront(scoreView);
        scoreView.setTextSize(30);
        scoreView.setTypeface(null, Typeface.BOLD);
        //set center
        scoreView.setX((windowX / 3) - scoreView.getWidth());
        scoreView.setY((windowY / 3) - scoreView.getHeight());
    }
}