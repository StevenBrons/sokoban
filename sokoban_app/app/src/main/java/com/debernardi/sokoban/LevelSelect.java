package com.debernardi.sokoban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FilenameUtils;


public class LevelSelect extends AppCompatActivity {

    // numLevelsOnScreen = roughly the number of levels that will fit on a screen before scrolling
    static private int numLevelsOnScreen = 10;

    /**
     * Dynamically creates the level menu based on the level files in assets/levels/
     * @author Jelmer Firet
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        LinearLayout layLevels = findViewById(R.id.layLevels);

        // Fetch filenames of the level files
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

        // Get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        // Get highscores shared preferences
        SharedPreferences prefHighscores = this.getSharedPreferences("Highscores", MODE_PRIVATE);

        Boolean even = false;
        for (String levelFilename:levelFiles){
            // Figure out properties of the level
            String levelName, authorName;
            int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFilename),-1);
            int minMoves;
            try {
                BufferedReader levelReader = new BufferedReader(new InputStreamReader(getAssets().open("levels/"+levelFilename)));
                levelName = levelReader.readLine();
                authorName = levelReader.readLine();
                while (levelReader.read() != ' ');
                while (levelReader.read() != ' ');
                minMoves = Integer.parseInt(levelReader.readLine());
            } catch (IOException e) {
                continue;
            }

            // Make a container for a level
            RelativeLayout level = new RelativeLayout(this);
            if (even){
                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundEven));
            }
            else{
                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundOdd));
            }
            even = !even;

            // Make preview image
            String previewFilename = "levelPreviews/"+FilenameUtils.removeExtension(levelFilename)+".png";
            Log.i("previewFilename",previewFilename);
            ImageView previewView = new ImageView(this);
            previewView.setId(View.generateViewId());
            previewView.setAdjustViewBounds(true);
            try{
                InputStream previewData = getAssets().open(previewFilename);
                previewView.setImageBitmap(BitmapFactory.decodeStream(previewData));

            }
            catch (Exception e){
                previewView.setImageResource(R.color.colorPrimary);
            }

            // Make level title text
            TextView levelNameView = new TextView(this);
            levelNameView.setText(levelName);
            levelNameView.setTextSize(24);
            levelNameView.setId(View.generateViewId());

            // Make level title text
            TextView authorNameView = new TextView(this);
            authorNameView.setText(authorName);
            authorNameView.setTextSize(18);
            authorNameView.setId(View.generateViewId());

            // Make best score text
            TextView bestTextView = new TextView(this);
            bestTextView.setText(String.format("%s/%s",highscore>=0?highscore:"-",minMoves>=0?minMoves:"-"));
            bestTextView.setTextSize(18);
            bestTextView.setPadding(10,3,10,3);
            bestTextView.setId(View.generateViewId());

            // Add elements to level container
            level.addView(previewView);
            level.addView(levelNameView);
            level.addView(authorNameView);
            level.addView(bestTextView);

            // Add layout rules
            RelativeLayout.LayoutParams previewViewLp = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
            previewViewLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            previewViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
            previewViewLp.height = screenSize.y/numLevelsOnScreen;
            previewViewLp.width = screenSize.y/numLevelsOnScreen;
            RelativeLayout.LayoutParams levelNameViewLp = (RelativeLayout.LayoutParams) levelNameView.getLayoutParams();
            levelNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            levelNameViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            RelativeLayout.LayoutParams authorNameViewLp = (RelativeLayout.LayoutParams) authorNameView.getLayoutParams();
            authorNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            authorNameViewLp.addRule(RelativeLayout.BELOW,levelNameView.getId());
            RelativeLayout.LayoutParams bestTextViewLp = (RelativeLayout.LayoutParams) bestTextView.getLayoutParams();
            bestTextViewLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            bestTextViewLp.addRule(RelativeLayout.CENTER_VERTICAL);

            // Add click functionality
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

            // Add level to the screen
            layLevels.addView(level);
        }
    }
}
