package game;

import com.debernardi.sokoban.GameView;

public class GameHandler {

    private Thread gameLoop;
    private GameView view;
    private long time = 0;
    private Level level;
    private boolean running = false;

    public GameHandler(String levelData) {
        level = new Level(levelData);
        System.out.println(level);
    }

    public void move(Direction d) {

    }

    public void start(GameView view) {
        this.view = view;
        final GameHandler h = this;
        this.running = true;
        gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    h.view.invalidate();
                    time++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public Level getLevel() {
        return this.level;
    }

}

enum Direction {
    UP,DOWN,LEFT,RIGHT
}