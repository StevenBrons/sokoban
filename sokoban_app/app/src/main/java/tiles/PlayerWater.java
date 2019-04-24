package tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import game.Texture;

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

    private void updateTexture(){
        int val = 0;
        if (leftWater) val += 1;
        if (topWater) val += 2;
        if (rightWater) val += 4;
        if (bottomWater) val += 8;
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
