package tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import game.Texture;

/**
 * a tile that acts as sheep and as water
 * the sheep on this tile can only be pushed onto land tiles
 * connects to neighbouring water tiles (Water, BoulderWater, PlayerWater, Void)
 * @author Jelmer Firet
 */
public class BoulderWater implements Tile, Movable, Connectable, WaterTile {
    private Texture texture = new Texture("lakeSheep2");
    private boolean leftWater = false, topWater = false, rightWater = false, bottomWater = false;
    private boolean leftWaterFall,topWaterFall,rightWaterFall,bottomWaterFall;
    private Texture textureLeftFall = new Texture("waterfallLeft");
    private Texture textureTopFall = new Texture("waterfallTop");
    private Texture textureRightFall = new Texture("waterfallRight");
    private Texture textureBottomFall = new Texture("waterfallBottom");
    private int boulderType;

    int getBoulderType(){
        return boulderType;
    }
    boolean getLeftWater() { return leftWater; }
    boolean getTopWater() { return topWater; }
    boolean getRightWater() { return rightWater; }
    boolean getBottomWater() { return bottomWater; }
    boolean getLeftWaterFall() { return leftWaterFall; }
    boolean getTopWaterFall() { return topWaterFall; }
    boolean getRightWaterFall() { return rightWaterFall; }
    boolean getBottomWaterFall() { return bottomWaterFall; }

    /**
     * a constructor for a new random BoulderWater
     * @author Jelmer Firet
     */
    public BoulderWater(){
        Random r = new Random();
        boulderType = r.nextInt(2)+2;
        updateTexture();
    }

    /**
     * a constructor for a BoulderWater based on a Boulder and a Water
     * @param boulderTemplate a Boulder to use as a template for the number of sheep
     * @param waterTemplate a Water to use as a template for the water connections
     * @author Jelmer Firet
     */
    BoulderWater(Boulder boulderTemplate, Water waterTemplate){
        Random r = new Random();
        switch (boulderTemplate.getBoulderType()){
            case 1:
            case 2:
                boulderType = 3;break;
            case 3:
                boulderType = 2;break;
            default:
                boulderType = r.nextInt(2)+2;break;
        }
        leftWater = waterTemplate.getLeftWater();
        topWater = waterTemplate.getTopWater();
        rightWater = waterTemplate.getRightWater();
        bottomWater = waterTemplate.getBottomWater();
        leftWaterFall = waterTemplate.getLeftWaterFall();
        topWaterFall = waterTemplate.getTopWaterFall();
        rightWaterFall = waterTemplate.getRightWaterFall();
        bottomWaterFall = waterTemplate.getBottomWaterFall();
        updateTexture();
    }

    /**
     * a constructor for a BoulderWater based on a Boulder and a Water
     * @param boulderTemplate a BoulderGoal to use as a template for the number of sheep
     * @param waterTemplate a Water to use as a template for the water connections
     * @author Jelmer Firet
     */
    BoulderWater(BoulderGoal boulderTemplate, Water waterTemplate){
        Random r = new Random();
        switch (boulderTemplate.getBoulderType()){
            case 1:
            case 2:
                boulderType = 3;break;
            case 3:
                boulderType = 2;break;
            default:
                boulderType = r.nextInt(2)+2;break;
        }
        leftWater = waterTemplate.getLeftWater();
        topWater = waterTemplate.getTopWater();
        rightWater = waterTemplate.getRightWater();
        bottomWater = waterTemplate.getBottomWater();
        leftWaterFall = waterTemplate.getLeftWaterFall();
        topWaterFall = waterTemplate.getTopWaterFall();
        rightWaterFall = waterTemplate.getRightWaterFall();
        bottomWaterFall = waterTemplate.getBottomWaterFall();
        updateTexture();
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    /**
     * @return a Texture for the BoulderWater that takes waterFalls into account
     * @author Jelmer Firet
     */
    @Override
    public Texture getTexture() {
        String name = texture.getName();
        if (topWaterFall) name += "_topWaterFall";
        if (rightWaterFall) name += "_rightWaterFall";
        if (bottomWaterFall) name += "_bottomWaterFall";
        if (leftWaterFall) name += "_leftWaterFall";
        if (!Texture.hasTexture(name)){
            Bitmap result = Bitmap.createBitmap(Texture.WIDTH,Texture.HEIGHT,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            Rect dst = new Rect(0,0,Texture.WIDTH,Texture.HEIGHT);
            if (topWaterFall)
                canvas.drawBitmap(textureTopFall.getBitmap(),null,dst,null);
            if (rightWaterFall)
                canvas.drawBitmap(textureRightFall.getBitmap(),null,dst,null);
            canvas.drawBitmap(texture.getBitmap(),null,dst,null);
            if(leftWaterFall)
                canvas.drawBitmap(textureLeftFall.getBitmap(),null,dst,null);
            if (bottomWaterFall)
                canvas.drawBitmap(textureBottomFall.getBitmap(),null,dst,null);
            Texture.addTexture(name,result);
        }
        return new Texture(name);
    }

    /**
     * changes the primary texture to adapt to water connections
     * @author Jelmer Firet
     */
    private void updateTexture(){
        int val = 0;
        if (leftWater) val += 1;
        if (topWater) val += 2;
        if (rightWater) val += 4;
        if (bottomWater) val += 8;
        switch (val){
            case 0:texture = new Texture("lakeSheep"+boulderType);break;
            case 1: texture = new Texture("waterSourceLeftSheep"+boulderType);break;
            case 2: texture = new Texture("waterSourceTopSheep"+boulderType);break;
            case 3: texture = new Texture("waterTopLeftSheep"+boulderType);break;
            case 4: texture = new Texture("waterSourceRightSheep"+boulderType);break;
            case 5: texture = new Texture("waterHorizontalSheep"+boulderType);break;
            case 6: texture = new Texture("waterRightTopSheep"+boulderType);break;
            case 7: texture = new Texture("waterTTopSheep"+boulderType);break;
            case 8: texture = new Texture("waterSourceBottomSheep"+boulderType);break;
            case 9: texture = new Texture("waterLeftBottomSheep"+boulderType);break;
            case 10: texture = new Texture("waterVerticalSheep"+boulderType);break;
            case 11: texture = new Texture("waterTLeftSheep"+boulderType);break;
            case 12: texture = new Texture("waterBottomRightSheep"+boulderType);break;
            case 13: texture = new Texture("waterTBottomSheep"+boulderType);break;
            case 14: texture = new Texture("waterTRightSheep"+boulderType);break;
            case 15: texture = new Texture("waterCrossSheep"+boulderType);break;
            default: texture = new Texture("lakeSheep"+boulderType);break;
        }
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

    @Override
    public Tile moveOnto(Tile other) {
        if (other instanceof Empty){
            return new Boulder(this);
        }
        if (other instanceof Goal){
            return new BoulderGoal(this);
        }
        return new Void();
    }
}
