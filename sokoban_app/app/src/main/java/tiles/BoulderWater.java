package tiles;

import java.util.Random;

import game.Texture;

public class BoulderWater implements Tile, Movable, Connectable, WaterTile {
    Texture texture = new Texture("fenceCross");
    boolean leftWall = false, topWall = false, rightWall = false, bottomWall = false;
    private int boulderType;

    public BoulderWater(Boulder boulderTemplate,Water waterTemplate){
        this.boulderType = boulderTemplate.getBoulderType();
        leftWall = waterTemplate.getLeftWall();
        topWall = waterTemplate.getTopWall();
        rightWall = waterTemplate.getRightWall();
        bottomWall = waterTemplate.getBottomWall();
        updateTexture();
    }

    public BoulderWater(BoulderGoal boulderTemplate,Water waterTemplate){
        Random r = new Random();
        switch (boulderTemplate.getBoulderType()){
            case 1:
                boulderType = r.nextInt(2)+1;break;
            case 2:
                boulderType = 3;break;
            case 3:
                boulderType = 3;break;
            default:
                boulderType = r.nextInt(3)+1;break;
        }
        leftWall = waterTemplate.getLeftWall();
        topWall = waterTemplate.getTopWall();
        rightWall = waterTemplate.getRightWall();
        bottomWall = waterTemplate.getBottomWall();
        updateTexture();
    }

    public BoulderWater(BoulderWater boulderTemplate,Water waterTemplate){
        boulderType = boulderTemplate.getBoulderType();
        leftWall = waterTemplate.getLeftWall();
        topWall = waterTemplate.getTopWall();
        rightWall = waterTemplate.getRightWall();
        bottomWall = waterTemplate.getBottomWall();
        updateTexture();
    }

    public int getBoulderType(){
        return boulderType;
    }
    public boolean getLeftWall() { return leftWall; }
    public boolean getTopWall() { return topWall; }
    public boolean getRightWall() { return rightWall; }
    public boolean getBottomWall() { return bottomWall; }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public void updateTexture(){
        int val = 0;
        if (leftWall) val += 1;
        if (topWall) val += 2;
        if (rightWall) val += 4;
        if (bottomWall) val += 8;
        switch (val){
            case 0:texture = new Texture("waterCross");break;
            case 1: texture = new Texture("waterCross");break;
            case 2: texture = new Texture("waterCross");break;
            case 3: texture = new Texture("waterTopLeft");break;
            case 4: texture = new Texture("waterCross");break;
            case 5: texture = new Texture("waterHorizontal");break;
            case 6: texture = new Texture("waterRightTop");break;
            case 7: texture = new Texture("waterTTop");break;
            case 8: texture = new Texture("waterCross");break;
            case 9: texture = new Texture("waterLeftBottom");break;
            case 10: texture = new Texture("waterVertical");break;
            case 11: texture = new Texture("waterTLeft");break;
            case 12: texture = new Texture("waterBottomRight");break;
            case 13: texture = new Texture("waterTBottom");break;
            case 14: texture = new Texture("waterTRight");break;
            case 15: texture = new Texture("fenceCross");break;
            default: texture = new Texture("fenceCross");break;
        }
    }

    @Override
    public void connectLeft(Tile other) {
        if (other instanceof WaterTile){
            leftWall = true;
        }
        else{
            leftWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectRight(Tile other) {
        if (other instanceof WaterTile){
            rightWall = true;
        }
        else{
            rightWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectTop(Tile other) {
        if (other instanceof WaterTile){
            topWall = true;
        }
        else{
            topWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectBottom(Tile other) {
        if (other instanceof WaterTile){
            bottomWall = true;
        }
        else{
            bottomWall = false;
        }
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
