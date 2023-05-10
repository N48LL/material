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
    int bombSize = 200;
    boolean bombSpawned = false;
    boolean bombClicked = false;
    boolean unpause = true;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startTimer() {
        final ProgressBar progressBar = findViewById(R.id.progressTime);
        final int totalTime = 30000;
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
                if (progress > 50 && !bombSpawned) {
                    spawnBomb();
                    bombSpawned = true;
                }
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
                    //get bomb imageview
                    if (bombSpawned) {
                        ImageView bomb = getBombImageView();
                        bomb.setX(randomNumber(0, (windowX - (bombSize * 2))));
                        bomb.setY(randomNumber(0, (windowY - (bombSize * 2))));

                    }
                    //Toast.makeText(getApplicationContext(), "Duck Clicked! New Pos: X:" + imageView.getX() + ", Y: " + imageView.getY(), Toast.LENGTH_SHORT).show();
                    System.out.println("Duck Clicked! New Pos: X:" + imageView.getX() + ", Y: " + imageView.getY());
                }
            });
        }

    private void spawnBomb() {
        ImageView imageView = new ImageView(this);
        setBombImageView(imageView);
        imageView.setImageResource(R.drawable.bomb);
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
        params.width = bombSize;
        params.height = bombSize;
        imageView.setLayoutParams(params);
        // pos
        imageView.setX(randomNumber(0, (windowX - (bombSize * 2))));
        imageView.setY(randomNumber(0, (windowY - (bombSize * 2))));

        // touch
        imageView.setOnClickListener(v -> {
            if (unpause) {
                bombClicked = true;
                unpause = false;
                scoreView();
                imageView.setImageResource(R.drawable.explosion);
            }
        });
    }

    ImageView bomb;
    private void setBombImageView(ImageView bomb) {
        this.bomb = bomb;
    }
    private ImageView getBombImageView() {
        return this.bomb;
    }

    private float randomNumber(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    public void scoreView() {

        //gameover
        TextView gameOver = new TextView(this);
        gameOver.setText(R.string.game_over);
        gameOver.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        //score txt
        TextView scoreView = new TextView(this);
        scoreView.setText("Score: " + score);
        scoreView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // get parent layout ???
        ViewGroup layout = findViewById(R.id.gameFrame);

        //format
        layout.bringChildToFront(scoreView);
        layout.bringChildToFront(gameOver);
        scoreView.setTextSize(30);
        scoreView.setTypeface(null, Typeface.BOLD);
        gameOver.setTextColor(getResources().getColor(R.color.red));
        gameOver.setTextSize(50);
        gameOver.setTypeface(null, Typeface.BOLD);

        //spawn + set center
        layout.addView(scoreView);
        scoreView.setX((windowX / 3) - scoreView.getWidth());
        scoreView.setY((windowY / 3) - scoreView.getHeight());
        layout.addView(gameOver);
        gameOver.setX((windowX / 6) - gameOver.getWidth());
        gameOver.setY((windowY / 4) - gameOver.getHeight());
    }

}