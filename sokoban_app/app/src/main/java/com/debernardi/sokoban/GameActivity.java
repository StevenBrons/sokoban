package com.debernardi.sokoban;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Scanner;

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
            Scanner levelReader = new Scanner(getAssets().open(intent.getStringExtra("levelFileName")));
            String levelData = "";
            while (levelReader.hasNextLine()) {
                levelData += levelReader.nextLine() + "\n";
            }

            handler = new GameHandler(levelData);

            handler.start(view);

            view = new GameView(this,handler);
            view.setBackgroundColor(Color.RED);
            setContentView(view);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
