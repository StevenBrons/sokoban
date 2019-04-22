package com.debernardi.sokoban;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.View;
import java.util.Scanner;
import android.view.LayoutInflater;

import game.Direction;
import game.GameHandler;
import game.Texture;

public class GameActivity extends AppCompatActivity {

    GameView view;
    GameHandler handler;


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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveDown(View v){
        //compensate for drawing
        handler.move(Direction.UP);
    }

    public void moveLeft(View v){
        handler.move(Direction.LEFT);
    }

    public void moveUp(View v){
        //compensate for drawing
        handler.move(Direction.DOWN);
    }

    public void moveRight(View v){
        handler.move(Direction.RIGHT);
    }
}
