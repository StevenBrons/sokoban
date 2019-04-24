package tiles;

import java.util.Random;

import game.Texture;

public class Wall implements Tile, Connectable{
    private Texture texture = new Texture("fenceCross");
    private boolean leftWall = false, topWall = false, rightWall = false, bottomWall = false;
    private int Type = new Random().nextInt(5);

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
            case 0:
                switch(Type){
                    case 0:
                    case 1:
                        texture = new Texture("moreSunflowers");break;
                    case 2:
                    case 3:
                        texture = new Texture("cherryBlossomTree");break;
                    case 4:
                        texture = new Texture("deadTree");break;

                }break;
            case 1: texture = new Texture("fenceLeft");break;
            case 2: texture = new Texture("fenceTop");break;
            case 3: texture = new Texture("fenceTopLeft");break;
            case 4: texture = new Texture("fenceRight");break;
            case 5: texture = new Texture("fenceLeftRight");break;
            case 6: texture = new Texture("fenceRightTop");break;
            case 7: texture = new Texture("fenceTTop");break;
            case 8: texture = new Texture("fenceBottom");break;
            case 9: texture = new Texture("fenceLeftBottom");break;
            case 10: texture = new Texture("fenceBottomTop");break;
            case 11: texture = new Texture("fenceTLeft");break;
            case 12: texture = new Texture("fenceBottomRight");break;
            case 13: texture = new Texture("fenceTBottom");break;
            case 14: texture = new Texture("fenceTRight");break;
            case 15: texture = new Texture("fenceCross");break;
            default: texture = new Texture("fenceCross");break;
        }
    }

    @Override
    public void connectLeft(Tile other) {
        leftWall = other instanceof Wall;
        updateTexture();
    }

    @Override
    public void connectRight(Tile other) {
        rightWall = other instanceof Wall;
        updateTexture();
    }

    @Override
    public void connectTop(Tile other) {
        topWall = other instanceof Wall;
        updateTexture();
    }

    @Override
    public void connectBottom(Tile other) {
        bottomWall = other instanceof Wall;
        updateTexture();
    }
}
