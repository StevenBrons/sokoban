package tiles;

import java.util.Random;

import game.Texture;

public class BoulderWater implements Tile, Movable, Connectable, WaterTile {
    private Texture texture = new Texture("lakeSheep2");
    private boolean leftWall = false, topWall = false, rightWall = false, bottomWall = false;
    private int boulderType;

    public BoulderWater(){
        Random r = new Random();
        boulderType = r.nextInt(2)+2;
        updateTexture();
    }

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
        leftWall = waterTemplate.getLeftWall();
        topWall = waterTemplate.getTopWall();
        rightWall = waterTemplate.getRightWall();
        bottomWall = waterTemplate.getBottomWall();
        updateTexture();
    }

    BoulderWater(BoulderGoal boulderTemplate, Water waterTemplate){
        Random r = new Random();
        switch (boulderTemplate.getBoulderType()){
            case 1:
                boulderType = 3;break;
            case 2:
            case 3:
                boulderType = 2;break;
            default:
                boulderType = r.nextInt(2)+2;break;
        }
        leftWall = waterTemplate.getLeftWall();
        topWall = waterTemplate.getTopWall();
        rightWall = waterTemplate.getRightWall();
        bottomWall = waterTemplate.getBottomWall();
        updateTexture();
    }

    int getBoulderType(){
        return boulderType;
    }
    boolean getLeftWall() { return leftWall; }
    boolean getTopWall() { return topWall; }
    boolean getRightWall() { return rightWall; }
    boolean getBottomWall() { return bottomWall; }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    private void updateTexture(){
        int val = 0;
        if (leftWall) val += 1;
        if (topWall) val += 2;
        if (rightWall) val += 4;
        if (bottomWall) val += 8;
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
        leftWall = other instanceof WaterTile;
        updateTexture();
    }

    @Override
    public void connectRight(Tile other) {
        rightWall = other instanceof WaterTile;
        updateTexture();
    }

    @Override
    public void connectTop(Tile other) {
        topWall = other instanceof WaterTile;
        updateTexture();
    }

    @Override
    public void connectBottom(Tile other) {
        bottomWall = other instanceof WaterTile;
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
