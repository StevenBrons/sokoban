package com.debernardi.sokoban;

 import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

import game.GameHandler;
import game.Level;

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

    public void drawLevel(Canvas canvas,Level level) {
        Paint p = new Paint();
        Random rand = new Random();
        int tileWidth = canvas.getWidth() / level.getWidth();
        int tileHeight = 70;
        for (int x = level.getWidth() + 1; x >= 0; x--) {
            for (int y = level.getHeight() + 1; y >= 0; y--) {
                int r = rand.nextInt();
                int g = rand.nextInt();
                int b = rand.nextInt();
                p.setColor(Color.rgb(r,g,b));
                canvas.drawRect(x * tileWidth, y * tileHeight,tileWidth,tileHeight,p);
            }
        }

    }

    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),p);
    }
}
