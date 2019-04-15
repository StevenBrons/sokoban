package com.debernardi.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        LinearLayout layLevels = findViewById(R.id.layLevels);
        String[] levelFiles;
        try {
            levelFiles = getAssets().list("levels");
            if (levelFiles == null){
                throw new IOException();
            }
        } catch (IOException e) {
            TextView levelLoadError = new TextView(this);
            levelLoadError.setText(getString(R.string.levelLoadError));
            layLevels.addView(levelLoadError);
            return;
        }
        for (String levelFilename:levelFiles){
            Log.i("levelFilename",levelFilename);
            String levelName;
            try {
                BufferedReader levelReader = new BufferedReader(new InputStreamReader(getAssets().open(levelFilename)));
                levelName = levelReader.readLine();
            } catch (IOException e) {
                continue;
            }
            LinearLayout level = new LinearLayout(this);
            level.setOrientation(LinearLayout.HORIZONTAL);

            ImageView previewView = new ImageView(this);
            previewView.setImageResource(R.color.colorPrimary);

            TextView levelNameView = new TextView(this);
            levelNameView.setText(levelName);
            levelNameView.setTextSize(18);

            TextView bestTextView = new TextView(this);
            bestTextView.setText(String.format("%d/%d",0,0));
            bestTextView.setTextSize(12);

            level.addView(previewView);
            level.addView(levelNameView);
            level.addView(bestTextView);
            level.setClickable(true);
            final String levelFilenameCpy = levelFilename;
            level.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startGame = new Intent(LevelSelect.this,Game.class);
                    startGame.putExtra("levelFileName",levelFilenameCpy);
                    startActivity(startGame);
                }
            });

            layLevels.addView(level);
        }
    }

    public void onClickGame(View view){
        Intent startGame = new Intent(this, Game.class);
        startActivity(startGame);
    }
}
