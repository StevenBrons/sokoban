package tiles;

import android.util.Log;

import game.Texture;

public class Water implements Tile, Connectable{
    Texture texture = new Texture("fenceCross");
    boolean leftWall = false, topWall = false, rightWall = false, bottomWall = false;

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
        if (other instanceof Water){
            leftWall = true;
        }
        else{
            leftWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectRight(Tile other) {
        if (other instanceof Water){
            rightWall = true;
        }
        else{
            rightWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectTop(Tile other) {
        if (other instanceof Water){
            topWall = true;
        }
        else{
            topWall = false;
        }
        updateTexture();
    }

    @Override
    public void connectBottom(Tile other) {
        if (other instanceof Water){
            bottomWall = true;
        }
        else{
            bottomWall = false;
        }
        updateTexture();
    }
}
