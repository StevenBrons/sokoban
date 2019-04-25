package game;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Hold cloud data, including type (texture), position and velocity.
 * @author Steven Bronsveld
 */
public class Cloud {

    private int x;
    private int y;
    private int v;
    private Texture texture;
    public boolean foreground;

    private static Random rnd = new Random();

    /**
     * Select a random cloud texture
     * @return A texture object with a random cloud type
     * @author Steven Bronsveld
     */
    private static Texture getRandomCloud() {
        if (rnd.nextInt(100) == 2) {
            return new Texture("cloudSheep");
        }
        return new Texture("cloud" + (rnd.nextInt(7) + 1));
    }

    /**
     * Select a random donut
     * @return A texture object with a random donut texture
     * @author Steven Bronsveld
     */
    private static Texture getRandomDonut() {
        return new Texture("donut" + (rnd.nextInt(10) + 1));
    }

    /**
     * Creates randomly selected cloud/donut with a random start position.
     * @param height The height of the screen on which the clouds are drawn
     * @param init Whether the cloud is initialized at startup or during the game.
     *             If init is set to true the x position of the cloud will be initialized
     *             positive. Otherwise it is made negative to make sure the clouds don't randomly appear.
     * @param donut Whether the cloud should be a donut
     */
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

    /**
     * Display the cloud with the right type and on the right position
     * @param canvas The canvas to draw the cloud on
     */
    public void draw(Canvas canvas) {
        Rect dest = new Rect(x,y,x + texture.getWidth(),y + texture.getHeight());
        canvas.drawBitmap(texture.getBitmap(),null,dest,null);
    }

    /**
     *
     * @param width
     * @return True if the cloud has surpassed (by a large margin) the maximal x position, meaning
     * the cloud has moved outside of the screen
     */
    public boolean isOutsideBounds(int width) {
        return this.x - 100 > width;
    }

    /**
     * Move the cloud according to its velocity
     */
    public void move() {
        this.x += this.v;
    }
}
