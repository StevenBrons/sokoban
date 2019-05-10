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
import game.Direction;
import game.GameHandler;
import game.Level;
import game.Texture;

import static java.lang.Math.min;

public class GameView extends View {

    GameHandler handler;
    ArrayList<Cloud> clouds = new ArrayList<>();
    ArrayList<Direction> movesToLocation = new ArrayList<>();
    int bitmapWidth;
    int bitmapHeight;
    int screenHeight;
    int screenWidth;
    int topOffset;
    int leftOffset;
    int movesToLocationIdx;
    boolean donutMode;

    /**
     *
     * @author Steven Bronsveld
     */
    GameView(Context context, GameHandler handler, boolean donutmode) {
        super(context);
        this.handler = handler;
        this.donutMode = donutmode;
        if(Math.random()<1.0/20.0){
            this.donutMode = true;
        }
    }

    /**
     * Override view draw method. Draws the whole game view except the ui-buttons
     */
    @Override
    protected void onDraw(Canvas canvas) {
        updateClouds(canvas);
        if (movesToLocation.size()>0){
            handler.move(movesToLocation.get(movesToLocationIdx));
            movesToLocationIdx++;
            if (movesToLocationIdx == movesToLocation.size()){
                movesToLocationIdx = 0;
                movesToLocation.clear();
            }
        }
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
        int height = (level.getWidth()+level.getHeight()+2)*tileHeight/4;
        int startHeight = (level.getWidth()-1)*tileHeight/4;
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int x = level.getWidth()-1; x >= 0; x--) {
            for (int y = 0; y < level.getHeight(); y++) {
                Bitmap texture = level.getTileAt(x,y).getTexture().getBitmap();

                int xAbs = x * (tileWidth / 2) + y * (tileWidth / 2);
                int yAbs = -x * (tileHeight / 4) + y * (tileHeight / 4)+startHeight;

                Rect dest = new Rect(xAbs,yAbs,tileWidth + xAbs, tileHeight + yAbs);
                canvas.drawBitmap(texture,null,dest,paint);
            }
        }
        return bitmap;
    }

    /**
     * Draw the level to the screen using a level bitmap.
     * The low-scale bitmap is scaled up to the screen size.
     * @param canvas Canvas to draw on
     * @param level Level to determine what to draw
     */
    public void drawLevel(Canvas canvas,Level level) {
        Bitmap bitmap = getLevelBitmap(level);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        screenHeight = min((canvas.getWidth() * bitmap.getHeight()) / bitmap.getWidth(),canvas.getHeight());
        screenWidth = screenHeight*bitmap.getWidth()/bitmap.getHeight();
        topOffset = (canvas.getHeight() / 2) - screenHeight/2;
        leftOffset = (canvas.getWidth() / 2) - screenWidth/2;
        Rect dest = new Rect(leftOffset,topOffset,screenWidth+leftOffset,screenHeight+topOffset);
        canvas.drawBitmap(bitmap,null,dest,null);
    }

    /**
     * Update the clouds, that is, move them,
     * add new clouds and remove clouds that have moved out of the screen.
     * If the method is called for the first time, a number of start-clouds are added.
     * @param canvas The canvas to determine width and height
     */
    public void updateClouds(Canvas canvas) {
        if (clouds.size() == 0) {
            for (int i = 0; i < 15; i++) {
                clouds.add(new Cloud(canvas.getHeight(),true,donutMode));
                clouds.get(i).move();
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
     * @param canvas The canvas to draw clouds on
     * @param foreground Whether the foreground or non-foreground clouds should be drawn
     */
    public void drawClouds(Canvas canvas, boolean foreground) {
        for (Cloud c: clouds) {
            c.move();
            if (c.foreground == foreground) {
                c.draw(canvas);
            }
        }
    }

    /**
     * Draw the blue sky-background
     * @param canvas Canvas to draw onto
     */
    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(255,168,211,255));
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }

    public void moveToPixels(float x,float y){
        x -= leftOffset; y -= topOffset;
        x /= screenWidth; y /= screenHeight;
        if (x < 0 || x > 1 || y < 0 || y > 1) return;
        //20/48 from the top the tile starts
        x *= bitmapWidth; y *= bitmapHeight;
        y -= Texture.HEIGHT/4f*(handler.getLevel().getWidth()-1);
        y -= Texture.HEIGHT*20f/48f;
        float tileX = x/Texture.WIDTH - 2f*y/Texture.HEIGHT;
        float tileY = x/Texture.WIDTH + 2f*y/ Texture.HEIGHT;
        movesToLocation = handler.getLevel().getMovesTo((int)tileX,(int)tileY);
        movesToLocationIdx = 0;
    }

}
