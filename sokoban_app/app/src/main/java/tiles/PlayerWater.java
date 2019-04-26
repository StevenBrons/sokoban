package tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import game.Texture;

/**
 * a tile that acts as a player and as water
 * acts as an active player that can be moved around
 * connects to neighbouring water tiles (Water, BoulderWater, PlayerWater, Void)
 * @author Jelmer Firet
 */
public class PlayerWater extends Player implements Connectable, WaterTile{
    private Texture texture = new Texture("lakeShepherdDog");
    private boolean leftWater, topWater, rightWater, bottomWater;
    private boolean leftWaterFall,topWaterFall,rightWaterFall,bottomWaterFall;
    private Texture textureLeftFall = new Texture("waterfallLeft");
    private Texture textureTopFall = new Texture("waterfallTop");
    private Texture textureRightFall = new Texture("waterfallRight");
    private Texture textureBottomFall = new Texture("waterfallBottom");

    boolean getLeftWater() { return leftWater; }
    boolean getTopWater() { return topWater; }
    boolean getRightWater() { return rightWater; }
    boolean getBottomWater() { return bottomWater; }
    boolean getLeftWaterFall() { return leftWaterFall; }
    boolean getTopWaterFall() { return topWaterFall; }
    boolean getRightWaterFall() { return rightWaterFall; }
    boolean getBottomWaterFall() { return bottomWaterFall; }

    /**
     * Add unconnected PlayerWater
     * @author Jelmer Firet
     */
    public PlayerWater(){
        updateTexture();
    }

    /**
     * constructs a new PlayerWater based on another Water instance
     * @param other a Water to use as a reference for the water Connections
     * @author Jelmer Firet
     */
    PlayerWater(Water other){
        leftWater = other.getLeftWater();
        topWater = other.getTopWater();
        rightWater = other.getRightWater();
        bottomWater = other.getBottomWater();
        leftWaterFall = other.getLeftWaterFall();
        topWaterFall = other.getTopWaterFall();
        rightWaterFall = other.getRightWaterFall();
        bottomWaterFall = other.getBottomWaterFall();
        updateTexture();
    }

    /**
     * @return a Texture for the PlayerWater that takes waterFalls into account
     * @author Jelmer Firet
     */
    public Texture getTexture() {
        int numWaterConnection = (topWater?1:0)+(rightWater?1:0)+(bottomWater?1:0)+(leftWater?1:0);
        int numWaterFalls = (topWaterFall?1:0)+(rightWaterFall?1:0)+
                (bottomWaterFall?1:0)+(leftWaterFall?1:0);
        boolean makeTopWaterFall = topWaterFall && numWaterConnection < 2;
        boolean makeRightWaterFall = rightWaterFall && numWaterConnection < 2;
        boolean makeBottomWaterFall = bottomWaterFall && numWaterConnection < 2;
        boolean makeLeftWaterFall = leftWaterFall && numWaterConnection < 2;
        if (numWaterFalls > 1){
            makeTopWaterFall &= bottomWater;
            makeRightWaterFall &= leftWater;
            makeBottomWaterFall &= topWater;
            makeLeftWaterFall &= rightWater;
        }
        updateTexture(topWater||makeTopWaterFall,rightWater||makeRightWaterFall,
                bottomWater||makeBottomWaterFall,leftWater||makeLeftWaterFall);

        String name = texture.getName();
        if (makeTopWaterFall) {name += "_topWaterFall";}
        if (makeRightWaterFall) name += "_rightWaterFall";
        if (makeBottomWaterFall) name += "_bottomWaterFall";
        if (makeLeftWaterFall) name += "_leftWaterFall";
        if (!Texture.hasTexture(name)){
            Bitmap result = Bitmap.createBitmap(Texture.WIDTH,Texture.HEIGHT,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            Rect dst = new Rect(0,0,Texture.WIDTH,Texture.HEIGHT);
            if (makeTopWaterFall)
                canvas.drawBitmap(textureTopFall.getBitmap(),null,dst,null);
            if (makeRightWaterFall)
                canvas.drawBitmap(textureRightFall.getBitmap(),null,dst,null);
            canvas.drawBitmap(texture.getBitmap(),null,dst,null);
            if(makeLeftWaterFall)
                canvas.drawBitmap(textureLeftFall.getBitmap(),null,dst,null);
            if (makeBottomWaterFall)
                canvas.drawBitmap(textureBottomFall.getBitmap(),null,dst,null);
            Texture.addTexture(name,result);
        }
        return new Texture(name);
    }

    /**
     * changes the primary texture to adapt to water connections
     * @author Jelmer Firet
     */
    private void updateTexture(boolean top,boolean right, boolean bottom, boolean left){
        int val = 0;
        if (left) val += 1;
        if (top) val += 2;
        if (right) val += 4;
        if (bottom) val += 8;
        switch (val){
            case 0:texture = new Texture("lakeShepherdDog");break;
            case 1: texture = new Texture("waterShepherdDogSourceLeft");break;
            case 2: texture = new Texture("waterShepherdDogSourceTop");break;
            case 3: texture = new Texture("waterShepherdDogTopLeft");break;
            case 4: texture = new Texture("waterShepherdDogSourceRight");break;
            case 5: texture = new Texture("waterShepherdDogHorizontal");break;
            case 6: texture = new Texture("waterShepherdDogRightTop");break;
            case 7: texture = new Texture("waterShepherdDogTTop");break;
            case 8: texture = new Texture("waterShepherdDogSourceBottom");break;
            case 9: texture = new Texture("waterShepherdDogLeftBottom");break;
            case 10: texture = new Texture("waterShepherdDogVertical");break;
            case 11: texture = new Texture("waterShepherdDogTLeft");break;
            case 12: texture = new Texture("waterShepherdDogBottomRight");break;
            case 13: texture = new Texture("waterShepherdDogTBottom");break;
            case 14: texture = new Texture("waterShepherdDogTRight");break;
            case 15: texture = new Texture("waterShepherdDogCross");break;
            default: texture = new Texture("waterShepherdDogCross");break;
        }
    }

    /**
     * update texture without given water directions
     */
    private void updateTexture(){
        updateTexture(topWater,rightWater,bottomWater,leftWater);
    }

    @Override
    public void connectLeft(Tile other) {
        leftWaterFall = other instanceof Void;
        leftWater = other instanceof WaterTile || leftWaterFall;
        updateTexture();
    }

    @Override
    public void connectRight(Tile other) {
        rightWaterFall = other instanceof Void;
        rightWater = other instanceof WaterTile || rightWaterFall;
        updateTexture();
    }

    @Override
    public void connectTop(Tile other) {
        topWaterFall = other instanceof Void;
        topWater = other instanceof WaterTile || topWaterFall;
        updateTexture();
    }

    @Override
    public void connectBottom(Tile other) {
        bottomWaterFall = other instanceof Void;
        bottomWater = other instanceof WaterTile || bottomWaterFall;
        updateTexture();
    }

    @Override
    public Tile moveLeftOver() {
        return new Water(this);
    }
}
