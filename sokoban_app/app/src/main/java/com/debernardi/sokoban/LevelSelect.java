package com.debernardi.sokoban;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

import game.Texture;
import view.LevelCollectionItem;


public class LevelSelect extends AppCompatActivity {

    // numLevelsOnScreen = roughly the number of levels that will fit on a screen before scrolling
    static private int numLevelsOnScreen = 10;
    static private String baseFilename = "All.sok";
    LinearLayout layLevels;
    ArrayList<String> levelFiles = new ArrayList<>();

    /**
     * Dynamically creates the level menu based on the level files in assets/levels/
     * For each level it creates preview, title, author and score elements
     * @author Jelmer Firet
     * @param savedInstanceState Bundle to pass along to AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Texture.init(getAssets());
        setContentView(R.layout.activity_level_select);
        layLevels = findViewById(R.id.layLevels);

        String[] AllCollection = {"All.sok","","","0"};

        LevelCollectionItem AllLevels = new LevelCollectionItem(this,AllCollection,layLevels);
        int backgroundColor = getResources().getColor(R.color.colorPrimaryLight);
        layLevels.addView(AllLevels.getView(false,0));

        // Get screen size
//        Display display = getWindowManager().getDefaultDisplay();
//        Point screenSize = new Point();
//        display.getSize(screenSize);

        // Get highscores shared preferences
//        SharedPreferences prefHighscores = this.getSharedPreferences("Highscores", MODE_PRIVATE);

//        int previewSize = max(screenSize.x,screenSize.y)/numLevelsOnScreen;

//        boolean even = false;
//        for (String levelFilename:levelFiles){
//            // Figure out properties of the level
//            int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFilename),-1);
//            Level levelItem = new Level(this,"levels/"+levelFilename);
//
//            // Make a container for a level
//            RelativeLayout level = new RelativeLayout(this);
//            if (even){
//                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundEven));
//            }
//            else{
//                level.setBackgroundColor(getResources().getColor(R.color.levelSelectBackgroundOdd));
//            }
//            even = !even;
//
//            // Make preview image
//            ImageView previewView = new ImageView(this);
//            previewView.setId(View.generateViewId());
//            previewView.setAdjustViewBounds(true);
//            try{
//                Bitmap preview = GameView.getLevelBitmap(levelItem,false);
//                previewView.setImageBitmap(Bitmap.createScaledBitmap(preview,previewSize,
//                        preview.getHeight()*previewSize/preview.getWidth(),false));
//            }
//            catch (Exception e){
//                previewView.setImageResource(R.color.colorPrimary);
//            }
//
//            // Make level title text
//            TextView levelNameView = new TextView(this);
//            levelNameView.setText(levelItem.getLevelName());
//            levelNameView.setTextSize(20);
//            levelNameView.setId(View.generateViewId());
//            levelNameView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));
//
//            // Make level title text
//            TextView authorNameView = new TextView(this);
//            authorNameView.setText(levelItem.getAuthor());
//            authorNameView.setTextSize(16);
//            authorNameView.setId(View.generateViewId());
//            authorNameView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));
//
//            // Make best score text
//            TextView bestTextView = new TextView(this);
//            bestTextView.setText(String.format("%s/%s",highscore>=0?highscore:"-",
//		    levelItem.getBestPossibleScore()>=0?levelItem.getBestPossibleScore():"-"));
//            bestTextView.setTextSize(16);
//            bestTextView.setPadding(10,3,10,3);
//            bestTextView.setId(View.generateViewId());
//            bestTextView.setTypeface(ResourcesCompat.getFont(this,R.font.dtm_mono));
//
//            // Add elements to level container
//            level.addView(previewView);
//            level.addView(levelNameView);
//            level.addView(authorNameView);
//            level.addView(bestTextView);
//
//            // Add layout rules
//            RelativeLayout.LayoutParams previewViewLp = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
//            previewViewLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            previewViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
//            previewViewLp.height = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
//            previewViewLp.width = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
//            RelativeLayout.LayoutParams levelNameViewLp = (RelativeLayout.LayoutParams) levelNameView.getLayoutParams();
//            levelNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            levelNameViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//            RelativeLayout.LayoutParams authorNameViewLp = (RelativeLayout.LayoutParams) authorNameView.getLayoutParams();
//            authorNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            authorNameViewLp.addRule(RelativeLayout.BELOW,levelNameView.getId());
//            RelativeLayout.LayoutParams bestTextViewLp = (RelativeLayout.LayoutParams) bestTextView.getLayoutParams();
//            bestTextViewLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            bestTextViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
//
//            // Add click functionality
//            level.setClickable(true);
//            final String levelFilenameCpy = "levels/"+levelFilename;
//            level.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent startGame = new Intent(LevelSelect.this, GameActivity.class);
//                    startGame.putExtra("levelFileName",levelFilenameCpy);
//                    startActivity(startGame);
//                }
//            });
//
//            // Add level to the screen
//            layLevels.addView(level);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences prefHighscores = this.getSharedPreferences("Highscores", MODE_PRIVATE);
//        for (int levelId = 0;levelId<layLevels.getChildCount();levelId++){
//            String levelFilename = levelFiles.get(levelId);
//            RelativeLayout level = (RelativeLayout) layLevels.getChildAt(levelId);
//            TextView bestTextView = (TextView) level.getChildAt(3);
//            String bestText = (String) bestTextView.getText();
//            int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFilename),-1);
//            bestText = (highscore>=0?highscore:"-")+"/"+bestText.split("/")[1];
//            bestTextView.setText(bestText);
//        }
    }
}
