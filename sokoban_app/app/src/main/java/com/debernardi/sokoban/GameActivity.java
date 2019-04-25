package com.debernardi.sokoban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.View;
import android.view.LayoutInflater;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import java.io.IOException;
import java.util.Random;

import game.Direction;
import game.GameHandler;
import game.Texture;

public class GameActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, OnGestureListener {

    private GestureDetector gDetector;
    GameView view;
    GameHandler handler;
    private static MediaPlayer audioIntro,audioMiddle,sheep1,sheep2,sheep3,sheep4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Texture.init(getAssets());
        super.onCreate(savedInstanceState);
        try {
            handler = new GameHandler(this,intent.getStringExtra("levelFileName"));
            view = new GameView(this,handler);

            handler.start(view);

            FrameLayout frame = new FrameLayout(this);
            frame.addView(view);
            LayoutInflater factory = LayoutInflater.from(this);
            View UI = factory.inflate(R.layout.ui, null);
            frame.addView(UI);
            setContentView(frame);
            gDetector = new GestureDetector(this, this);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onFling(MotionEvent ev1, MotionEvent ev2, float X, float Y) {
        if (ev1.getX()-ev2.getX()< 0 && ev1.getY()-ev2.getY() >= 0) {
            moveRight(view);
            return true;
        }

        if (ev1.getX()-ev2.getX() < 0 && ev1.getY()-ev2.getY() < 0) {
            moveDown(view);
            return true;
        }

        if (ev1.getX()-ev2.getX() >= 0 && ev1.getY()-ev2.getY() >= 0) {
            moveUp(view);
            return true;
        }

        if (ev1.getX()-ev2.getX() >= 0 && ev1.getY()-ev2.getY() < 0) {
            moveLeft(view);
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    /**
     * catch move down button click
     * The direction is reversed because the game is drawn in isometric view
     * @author Steven Bronsveld
     */
    public void moveDown(View v){
        move(Direction.UP);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gDetector.onTouchEvent(motionEvent);
    }


    /**
     * catch move down button click
     * @author Steven Bronsveld
     */
    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    public void move(Direction d){
        if(handler.move(d)){
            int n_steps = this.getSharedPreferences("statprefs", MODE_PRIVATE).getInt("n_steps", 0);
            n_steps++;
            SharedPreferences.Editor editor = this.getSharedPreferences("statprefs", MODE_PRIVATE).edit();
            editor.putInt("n_steps", n_steps);
            editor.commit();
        }
    }

    /**
     * catch move left button click
     * @author Steven Bronsveld
     */
    public void moveLeft(View v){
        move(Direction.LEFT);
    }

    /**
     * catch move up button click
     * The direction is reversed because the game is drawn in isometric view
     * @author Steven Bronsveld
     */
    public void moveUp(View v){
        move(Direction.DOWN);
    }

    public void moveRight(View v){
        move(Direction.RIGHT);
    }

    public void undo(View v){
        handler.undo();
    }

    public void onCompletion(MediaPlayer player){
        audioMiddle.start();
    }

    /**
     * handler to load and start music on activity start
     * @author Jelmer Firet
     */
    @Override
    protected void onStart() {
        super.onStart();
        audioIntro = MediaPlayer.create(this,R.raw.soundtrack1_intro);
        audioIntro.start();
        audioIntro.setOnCompletionListener(this);
        audioMiddle = MediaPlayer.create(this,R.raw.soundtrack1_middle);
        audioMiddle.setLooping(true);
        sheep1 = MediaPlayer.create(this,R.raw.sheep1);
        sheep2 = MediaPlayer.create(this,R.raw.sheep2);
        sheep3 = MediaPlayer.create(this,R.raw.sheep3);
        sheep4 = MediaPlayer.create(this,R.raw.sheep4);

    }

    /**
     * handler to pause music when activity is paused
     * @author Jelmer Firet
     */
    @Override
    protected void onPause() {
        super.onPause();
        audioIntro.pause();
        audioMiddle.pause();
        sheep1.stop();
        sheep2.stop();
        sheep3.stop();
        sheep4.stop();
    }

    /**
     * handler to resume music when activity resumes
     * @author Jelmer Firet
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (audioIntro.getCurrentPosition() != audioIntro.getDuration()){
            audioIntro.start();
        }
        else{
            audioMiddle.start();
        }
    }

    /**
     * handler to stop and release music when activity stops
     * @author Jelmer Firet
     */
    @Override
    protected void onStop() {
        super.onStop();
        audioMiddle.release();
        audioIntro.release();
    }

    /**
     * Start the winlose activity.
     * @param b
     * @author Bram Pulles
     */
    public void won(Bundle b){
        Intent startWinLose = new Intent(this, WinLose.class);
        startWinLose.putExtras(b);
        startActivity(startWinLose);
    }

    /**
     * play random blèèrgh sound of a sheep
     * @author Jelmer Firet
     */
    public static void playAudioSheep(){
        Random r = new Random();
        switch(r.nextInt(4)){
            case 0: sheep1.start();
            case 1: sheep2.start();
            case 2: sheep3.start();
            case 3: sheep4.start();
        }
    }

}
