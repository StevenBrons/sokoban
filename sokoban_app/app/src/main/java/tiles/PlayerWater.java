package tiles;

import game.Texture;

public class PlayerWater extends Player implements Connectable, WaterTile{
    private Texture texture = new Texture("lakeShepherdDog");
    private boolean leftWall, topWall, rightWall, bottomWall;

    boolean getLeftWall() { return leftWall; }
    boolean getTopWall() { return topWall; }
    boolean getRightWall() { return rightWall; }
    boolean getBottomWall() { return bottomWall; }

    PlayerWater(Water other){
        leftWall = other.getLeftWall();
        topWall = other.getTopWall();
        rightWall = other.getRightWall();
        bottomWall = other.getBottomWall();
        updateTexture();
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
}
