package com.debernardi.sokoban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import game.Level;
import game.Texture;

import static java.lang.Math.max;


public class LevelSelect extends AppCompatActivity {

    // numLevelsOnScreen = roughly the number of levels that will fit on a screen before scrolling
    static private int numLevelsOnScreen = 10;
    LinearLayout layLevels;
    String[] levelFiles;

    /**
     * Dynamically creates the level menu based on the level files in assets/levels/
     * For each level it creates preview, title, author and score elements
     * @author Jelmer Firet
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Texture.init(getAssets());
        setContentView(R.layout.activity_level_select);
        layLevels = findViewById(R.id.layLevels);

        // Fetch filenames of the level files
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

        int previewSize = max(screenSize.x,screenSize.y)/numLevelsOnScreen;

        Boolean even = false;
        for (String levelFilename:levelFiles){
            // Figure out properties of the level
            String levelName, authorName;
            int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFilename),-1);
            int minMoves;
            Level levelItem = new Level(this,"levels/"+levelFilename);

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
            ImageView previewView = new ImageView(this);
            previewView.setId(View.generateViewId());
            previewView.setAdjustViewBounds(true);
            try{
                Bitmap preview = GameView.getLevelBitmap(levelItem);
                previewView.setImageBitmap(Bitmap.createScaledBitmap(preview,previewSize,
                        preview.getHeight()*previewSize/preview.getWidth(),false));
            }
            catch (Exception e){
                previewView.setImageResource(R.color.colorPrimary);
            }

            // Make level title text
            TextView levelNameView = new TextView(this);
            levelNameView.setText(levelItem.getLevelName());
            levelNameView.setTextSize(20);
            levelNameView.setId(View.generateViewId());
            levelNameView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));

            // Make level title text
            TextView authorNameView = new TextView(this);
            authorNameView.setText(levelItem.getAuthor());
            authorNameView.setTextSize(16);
            authorNameView.setId(View.generateViewId());
            authorNameView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));

            // Make best score text
            TextView bestTextView = new TextView(this);
            bestTextView.setText(String.format("%s/%s",highscore>=0?highscore:"-",
		    levelItem.getBestPossibleScore()>=0?levelItem.getBestPossibleScore():"-"));
            bestTextView.setTextSize(16);
            bestTextView.setPadding(10,3,10,3);
            bestTextView.setId(View.generateViewId());
            bestTextView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));

            // Add elements to level container
            level.addView(previewView);
            level.addView(levelNameView);
            level.addView(authorNameView);
            level.addView(bestTextView);

            // Add layout rules
            RelativeLayout.LayoutParams previewViewLp = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
            previewViewLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            previewViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
            previewViewLp.height = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
            previewViewLp.width = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
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
                    Intent startGame = new Intent(LevelSelect.this, GameActivity.class);
                    startGame.putExtra("levelFileName",levelFilenameCpy);
                    startActivity(startGame);
                }
            });

            // Add level to the screen
            layLevels.addView(level);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefHighscores = this.getSharedPreferences("Highscores", MODE_PRIVATE);
        for (int levelId = 0;levelId<layLevels.getChildCount();levelId++){
            String levelFilename = levelFiles[levelId];
            RelativeLayout level = (RelativeLayout) layLevels.getChildAt(levelId);
            TextView bestTextView = (TextView) level.getChildAt(3);
            String bestText = (String) bestTextView.getText();
            int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFilename),-1);
            bestText = (highscore>=0?highscore:"-")+"/"+bestText.split("/")[1];
            bestTextView.setText(bestText);
        }
    }
}
