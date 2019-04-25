package game;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Cloud {

    private int x;
    private int y;
    private int v;
    private Texture texture;
    public boolean foreground;

    private static Random rnd = new Random();


    private static Texture getRandomCloud() {
        if (rnd.nextInt(100) == 2) {
            return new Texture("cloudSheep");
        }
        return new Texture("cloud" + (rnd.nextInt(7) + 1));
    }

    private static Texture getRandomDonut() {
        return new Texture("donut" + (rnd.nextInt(10) + 1));
    }

    public Cloud(int height, boolean init, boolean donut) {
        v = rnd.nextInt(3) + 5;
        if (init) {
            x = rnd.nextInt(2000) - 1000;
        } else {
            x = -rnd.nextInt(800) - 300;
        }
        y = rnd.nextInt(height + 100) - 100;
        foreground = rnd.nextInt( 5) == 0;
        if (donut) {
            texture = getRandomDonut();
        }else {
            texture = getRandomCloud();
        }
    }

    public void draw(Canvas canvas) {
        Rect dest = new Rect(x,y,x + texture.getWidth(),y + texture.getHeight());
        canvas.drawBitmap(texture.getBitmap(),null,dest,null);
    }

    public boolean isOutsideBounds(int width) {
        return this.x - 100 > width;
    }

    public void move(int width) {
        this.x += this.v;
    }
}
