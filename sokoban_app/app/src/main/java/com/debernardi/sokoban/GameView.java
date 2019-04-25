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

    /**
     *
     * @author Steven Bronsveld
     */
    GameView(Context context, GameHandler handler) {
        super(context);
        this.handler = handler;
        donutMode = Math.random() * 20 < 1;
    }

    /**
     * Override view draw method. Draws the whole game view except the ui-buttons
     */
    @Override
    protected void onDraw(Canvas canvas) {
        updateClouds(canvas);

        Level level = handler.getLevel();
        drawBackground(canvas);
        drawClouds(canvas,false);
        drawLevel(canvas,level);
        drawClouds(canvas,true);
    }

    /**
     * Creates a low-scale version of the current level
     * @param level The level to convert
     * @return The bitmap on which the level is drawn
     */
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

    /**
     * Draw the level to the screen using a level bitmap.
     * The low-scale bitmap is scaled up to the screen size.
     * @param canvas
     * @param level
     */
    public void drawLevel(Canvas canvas,Level level) {
        Bitmap bitmap = getLevelBitmap(level);
        int screenHeight = min((canvas.getWidth() * bitmap.getHeight()) / bitmap.getWidth(),canvas.getHeight());
        int screenWidth = screenHeight*bitmap.getWidth()/bitmap.getHeight();
        int topOffset = (canvas.getHeight() / 2) - screenHeight/2;
        int leftOffset = (canvas.getWidth() / 2) - screenWidth/2;
        Rect dest = new Rect(leftOffset,topOffset,screenWidth+leftOffset,screenHeight+topOffset);
        canvas.drawBitmap(bitmap,null,dest,null);
    }

    /**
     * Update the clouds, that is, move them,
     * add new clouds and remove clouds that have moved out of the screen.
     * If the method is called for the first time, a number of start-clouds are added.
     * @param canvas
     */
    public void updateClouds(Canvas canvas) {
        if (clouds.size() == 0) {
            for (int i = 0; i < 15; i++) {
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
    }

    /**
     * Draw the clouds, either only draw the clouds with the
     * foreground property or the clouds without the foreground property
     * @param canvas
     * @param foreground Whether the foreground or non-foreground clouds should be drawn
     */
    public void drawClouds(Canvas canvas, boolean foreground) {
        for (Cloud c: clouds) {
            c.move(10);
            if (c.foreground == foreground) {
                c.draw(canvas);
            }
        }
    }

    /**
     * Draw the blue sky-background
     * @param canvas
     */
    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(255,168,211,255));
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }

}
