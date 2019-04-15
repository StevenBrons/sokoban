package com.debernardi.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        Boolean even = false;
        for (String levelFilename:levelFiles){
            String levelName;
            try {
                BufferedReader levelReader = new BufferedReader(new InputStreamReader(getAssets().open("levels/"+levelFilename)));
                levelName = levelReader.readLine();
            } catch (IOException e) {
                continue;
            }
            RelativeLayout level = new RelativeLayout(this);
            if (even){
                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundEven));
            }
            else{
                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundOdd));
            }
            even = !even;

            ImageView previewView = new ImageView(this);
            previewView.setImageResource(R.color.colorPrimary);
            RelativeLayout.LayoutParams previewViewLp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            previewViewLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            previewViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
            previewView.setLayoutParams(previewViewLp);

            TextView levelNameView = new TextView(this);
            levelNameView.setText(levelName);
            levelNameView.setTextSize(24);
            RelativeLayout.LayoutParams levelNameViewLp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            levelNameViewLp.addRule(RelativeLayout.RIGHT_OF,previewView.getId());
            levelNameViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            levelNameView.setLayoutParams(levelNameViewLp);

            TextView bestTextView = new TextView(this);
            bestTextView.setText(String.format("%d/%d",0,0));
            bestTextView.setTextSize(18);
            bestTextView.setPadding(10,3,10,3);
            RelativeLayout.LayoutParams bestTextViewLp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            bestTextViewLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            bestTextViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
            bestTextView.setLayoutParams(bestTextViewLp);


            level.addView(previewView);
            level.addView(levelNameView);
            level.addView(bestTextView);
            level.setClickable(true);
            final String levelFilenameCpy = "levels/"+levelFilename;
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
