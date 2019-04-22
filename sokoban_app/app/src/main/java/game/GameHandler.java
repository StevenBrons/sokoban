package game;

import android.content.Context;

import com.debernardi.sokoban.GameView;

import java.util.ArrayList;

public class GameHandler {

    private Thread gameLoop;
    private GameView view;
    private long time = 0;
    private Level level;
    private boolean running = false;

    private ArrayList<Level> history = new ArrayList<>();

    public GameHandler(Context context, String levelPath) {
        level = new Level(context,levelPath);
        history.add(level.copy());
    }

    public void move(Direction d) {
        history.add(level.copy());
        boolean success = level.move(d);
        if (!success) {
            //don't add to history if move was invalid
            history.remove(history.size() - 1);
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

}