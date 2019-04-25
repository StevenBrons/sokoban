package game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.debernardi.sokoban.GameActivity;
import com.debernardi.sokoban.GameView;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

/**
 * Handles the game logic
 * @author Steven Bronsveld
 */
public class GameHandler extends AppCompatActivity {

    private Thread gameLoop;
    private GameView view;
    private long time = 0;
    private GameActivity context;
    private Level level;
    private boolean running = false;

    private ArrayList<Level> history = new ArrayList<>();

    /**
     * Initializes the game handler
     * @param context A random context, only used to get assets
     * @param levelPath The path of the level that should be loaded
     * @author Steven Bronsveld
     */
    public GameHandler(Context context, String levelPath) {
        this.context = (GameActivity) context;
        level = new Level(context,levelPath);
        history.add(level.copy());
    }

    /**
     * Moves the player in the given direction
     * @param d The direction in which the player is moved
     * @return If the move was valid
     */
    public boolean move(Direction d) {
        boolean success = level.move(d);
        if (success) {
            history.add(level.copy());
        }
        if(level.isFinished()){
            won();
        }
        return success;
    }

    /**
     * Undoes the last move, excluding invalid moves.
     * @author Thomas Berghuis
     */
    public void undo(){
        if (history.size() == 1)
            return;
        else{
            history.remove(history.size() -1);
            this.level = history.get(history.size() -1).copy();
        }

    }

    /**
     * Starts the gameloop. In the game loop, the level is drawn 10 times a second and
     * the in-game time is increased.
     * @param view2
     * @author Steven Bronsveld
     */
    public void start(GameView view2) {
        final GameView view = view2;
        this.running = true;
        gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    view.postInvalidate();
                    time++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gameLoop.start();
    }

    /**
     * @return The current level state
     * @author Steven Bronsveld
     */
    public Level getLevel() {
        return this.level;
    }

    /**
        * This function is called when a level is finished. It passes the neccessary values  to the game activity.
        * @author Bram Pulles
        */
    private void won(){
        int currentScore = history.size() - 1;
        boolean newBest = false;
        SharedPreferences prefHighscores = context.getSharedPreferences("Highscores", MODE_PRIVATE);
        String key = FilenameUtils.removeExtension(level.getHighscoreString());
        int highscore = prefHighscores.getInt(key,-1);
        System.out.println(highscore);
        SharedPreferences.Editor edit = prefHighscores.edit();
        if(highscore == -1 || currentScore < highscore){
            newBest = true;
            highscore = currentScore;
            edit.putInt(key,highscore);
            edit.commit();
        }

        Bundle b = new Bundle();
        b.putInt("currentScore", currentScore);
        b.putInt("bestScore", highscore);
        b.putInt("minimumScore", level.getBestPossibleScore());
        b.putBoolean("newBest", newBest);
        b.putString("levelFileName", level.getLevelPath());

        context.won(b);
    }

}