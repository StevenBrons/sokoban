package game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.debernardi.sokoban.GameActivity;
import com.debernardi.sokoban.GameView;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

public class GameHandler extends AppCompatActivity {

    private Thread gameLoop;
    private GameView view;
    private long time = 0;
    private GameActivity context;
    private Level level;
    private boolean running = false;

    private ArrayList<Level> history = new ArrayList<>();

    public GameHandler(Context context, String levelPath) {
        this.context = (GameActivity) context;
        level = new Level(context,levelPath);
        history.add(level.copy());
    }

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

    public void undo(){
        if (history.size() == 1)
            return;
        else{
            history.remove(history.size() -1);
            this.level = history.get(history.size() -1).copy();
        }

    }

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

    public Level getLevel() {
        return this.level;
    }

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