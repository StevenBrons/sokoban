package com.debernardi.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import game.GameHandler;
import game.Level;
import game.Texture;

import static java.lang.Math.max;

public class GameView extends View {

    GameHandler handler;

    GameView(Context context, GameHandler handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Level level = handler.getLevel();
        drawBackground(canvas);
        drawLevel(canvas,level);
    }

    public static Bitmap getLevelBitmap(Level level) {
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        int tileWidth = Texture.WIDTH;
        int tileHeight = Texture.HEIGHT;
        int width = (level.getWidth()+level.getHeight())*tileWidth/2;
        int height = (max(level.getWidth(),level.getHeight())+2)*tileHeight/2;
        int startheight = (level.getWidth())*tileHeight/4;
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int x = level.getWidth()-1; x >= 0; x--) {
            for (int y = 0; y < level.getHeight(); y++) {
                Bitmap texture = level.getTileAt(x,y).getTexture().getBitmap();

                int xAbs = x * (tileWidth / 2) + y * (tileWidth / 2);
                int yAbs = -x * (tileHeight / 4) + y * (tileHeight / 4)+startheight;


                Rect src = new Rect(0,0,texture.getWidth(), texture.getHeight());
                Rect dest = new Rect(xAbs,yAbs,tileWidth + xAbs, tileHeight + yAbs);
                canvas.drawBitmap(texture,src,dest,paint);
            }
        }
        return bitmap;
    }

    public void drawLevel(Canvas canvas,Level level) {
        Bitmap bitmap = getLevelBitmap(level);
        Rect src = new Rect(0,0,bitmap.getWidth(), bitmap.getHeight());
        int screenHeight = (canvas.getWidth() * bitmap.getHeight()) / bitmap.getWidth();
        int topOffset = (canvas.getHeight() / 2) - screenHeight/2;
        Rect dest = new Rect(0,topOffset,canvas.getWidth(),screenHeight+topOffset);
        canvas.drawBitmap(bitmap,src,dest,null);


    }

    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }

    public void moveDown() {

    }
}
