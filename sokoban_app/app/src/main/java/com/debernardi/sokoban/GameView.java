package com.debernardi.sokoban;

 import android.content.Context;
 import android.graphics.Bitmap;
 import android.graphics.Canvas;
import android.graphics.Color;
 import android.graphics.Matrix;
 import android.graphics.Paint;
 import android.graphics.Rect;
 import android.text.TextUtils;
 import android.view.View;

import java.util.Random;

import game.GameHandler;
import game.Level;
 import game.Texture;

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
        int tileWidth = Texture.WIDTH;
        int tileHeight = Texture.HEIGHT;
        int left = -level.getHeight() * (tileWidth / 2);
        int right = level.getWidth() * (tileWidth / 2) + tileWidth;
        int bottom = level.getWidth() * (tileHeight / 4) + level.getHeight() * (tileHeight / 4) + tileHeight;
        Bitmap bitmap = Bitmap.createBitmap(-left + right,bottom,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                Bitmap texture = level.getTileAt(x,y).getTexture().getBitmap();

                int xAbs = x * (tileWidth / 2) - y * (tileWidth / 2) - left;
                int yAbs = x * (tileHeight / 4) + y * (tileHeight / 4);


                Rect src = new Rect(0,0,texture.getWidth(), texture.getHeight());
                Rect dest = new Rect(xAbs,yAbs,tileWidth + xAbs, tileHeight + yAbs);
                canvas.drawBitmap(texture,src,dest,null);
            }
        }
        return bitmap;
    }

    public void drawLevel(Canvas canvas,Level level) {
        Bitmap bitmap = getLevelBitmap(level);
        Rect src = new Rect(0,0,bitmap.getWidth(), bitmap.getHeight());
        int screenHeight = (canvas.getWidth() * bitmap.getHeight()) / bitmap.getWidth();
        int topOffset = (canvas.getHeight() / 2) - screenHeight;
        Rect dest = new Rect(0,topOffset,canvas.getWidth(),screenHeight+topOffset);
        canvas.drawBitmap(bitmap,src,dest,null);

    }

    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }
}