package game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.debernardi.sokoban.GameActivity;
import com.debernardi.sokoban.GameView;
import com.debernardi.sokoban.WinLose;

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
        history.add(level.copy());
        boolean success = level.move(d);
        if (!success) {
            //don't add to history if move was invalid
            history.remove(history.size() - 1);
        }
        if(level.isFinished()){
            won();
        }
        return success;
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
        Bundle b = new Bundle();
        b.putInt("currentScore", history.size());
        b.putInt("bestScore", 1);
        b.putInt("minimumScore", level.getBestPossibleScore());
        b.putBoolean("newBest", true);

        context.won(b);
    }

}