package tiles;

import game.Texture;

public class Water implements Tile, Connectable, WaterTile{
    private Texture texture = new Texture("lake");
    private boolean leftWall = false, topWall = false, rightWall = false, bottomWall = false;

    public Water(){}

    public Water(BoulderWater other){
        leftWall = other.getLeftWall();
        topWall = other.getTopWall();
        rightWall = other.getRightWall();
        bottomWall = other.getBottomWall();
        updateTexture();
    }

    public Water(PlayerWater other){
        leftWall = other.getLeftWall();
        topWall = other.getTopWall();
        rightWall = other.getRightWall();
        bottomWall = other.getBottomWall();
        updateTexture();
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    boolean getLeftWall() { return leftWall; }
    boolean getTopWall() { return topWall; }
    boolean getRightWall() { return rightWall; }
    boolean getBottomWall() { return bottomWall; }

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
            case 0:texture = new Texture("lake");break;
            case 1: texture = new Texture("waterSourceLeft");break;
            case 2: texture = new Texture("waterSourceTop");break;
            case 3: texture = new Texture("waterTopLeft");break;
            case 4: texture = new Texture("waterSourceRight");break;
            case 5: texture = new Texture("waterHorizontal");break;
            case 6: texture = new Texture("waterRightTop");break;
            case 7: texture = new Texture("waterTTop");break;
            case 8: texture = new Texture("waterSourceBottom");break;
            case 9: texture = new Texture("waterLeftBottom");break;
            case 10: texture = new Texture("waterVertical");break;
            case 11: texture = new Texture("waterTLeft");break;
            case 12: texture = new Texture("waterBottomRight");break;
            case 13: texture = new Texture("waterTBottom");break;
            case 14: texture = new Texture("waterTRight");break;
            case 15: texture = new Texture("waterCross");break;
            default: texture = new Texture("waterCross");break;
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
}
