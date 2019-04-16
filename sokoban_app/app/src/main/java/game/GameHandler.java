package game;

public class GameHandler {

    private Thread gameLoop;
    private long time = 0;

    GameHandler(String fileName) {


    }

    public void move(Direction d) {

    }

    public void init() {

    }

    public void start() {
        gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                draw();
                time++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void draw() {
        //todo
    }

}

enum Direction {
    UP,DOWN,LEFT,RIGHT
}