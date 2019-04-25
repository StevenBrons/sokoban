package com.debernardi.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import game.Cloud;
import game.GameHandler;
import game.Level;
import game.Texture;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameView extends View {

    GameHandler handler;
    ArrayList<Cloud> clouds = new ArrayList<>();
    boolean donutMode;

    GameView(Context context, GameHandler handler) {
        super(context);
        this.handler = handler;
        donutMode = Math.random() * 20 < 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Level level = handler.getLevel();
        drawBackground(canvas);
        drawClouds(canvas);
        drawLevel(canvas,level);
        drawForegroundClouds(canvas);
    }

    public static Bitmap getLevelBitmap(Level level) {
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        int tileWidth = Texture.WIDTH;
        int tileHeight = Texture.HEIGHT;
        int width = (level.getWidth()+level.getHeight())*tileWidth/2;
        int height = (level.getWidth()+level.getHeight()+3)*tileHeight/4;
        int startheight = (level.getWidth())*tileHeight/4;
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int x = level.getWidth()-1; x >= 0; x--) {
            for (int y = 0; y < level.getHeight(); y++) {
                Bitmap texture = level.getTileAt(x,y).getTexture().getBitmap();

                int xAbs = x * (tileWidth / 2) + y * (tileWidth / 2);
                int yAbs = -x * (tileHeight / 4) + y * (tileHeight / 4)+startheight;

                Rect dest = new Rect(xAbs,yAbs,tileWidth + xAbs, tileHeight + yAbs);
                canvas.drawBitmap(texture,null,dest,paint);
            }
        }
        return bitmap;
    }

    public void drawLevel(Canvas canvas,Level level) {
        Bitmap bitmap = getLevelBitmap(level);
        int screenHeight = min((canvas.getWidth() * bitmap.getHeight()) / bitmap.getWidth(),canvas.getHeight());
        int screenWidth = screenHeight*bitmap.getWidth()/bitmap.getHeight();
        int topOffset = (canvas.getHeight() / 2) - screenHeight/2;
        int leftOffset = (canvas.getWidth() / 2) - screenWidth/2;
        Rect dest = new Rect(leftOffset,topOffset,screenWidth+leftOffset,screenHeight+topOffset);
        canvas.drawBitmap(bitmap,null,dest,null);
    }

    public void drawClouds(Canvas canvas) {
        if (clouds.size() == 0) {
            for (int i = 0; i < 20; i++) {
                clouds.add(new Cloud(canvas.getHeight(),true,donutMode));
                clouds.get(i).move(100);
            }
        }
        for (int i = clouds.size() - 1; i >= 0; i--) {
            if (clouds.get(i).isOutsideBounds(canvas.getWidth())) {
                clouds.remove(i);
                clouds.add(new Cloud(canvas.getHeight(),false, donutMode));
            }
        }
        for (Cloud c: clouds) {
            c.move(10);
            if (!c.foreground) {
                c.draw(canvas);
            }
        }
    }

    public void drawForegroundClouds(Canvas canvas) {
        for (Cloud c: clouds) {
            if (c.foreground) {
                c.draw(canvas);
            }
        }
    }

    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(255,168,211,255));
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }

}
