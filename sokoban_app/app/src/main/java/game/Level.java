package game;

import android.content.Context;

import com.debernardi.sokoban.GameActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import tiles.*;
import tiles.Void;

public class Level {

    private Tile[][] tiles;
    private int width;
    private int height;
    private String levelName;
    private String author;
    private int bestPossibleScore;
    private int playerX = -1;
    private int playerY = -1;
    private String levelPath;

    public Level(Context context, String levelPath) {
        this.levelPath = levelPath;

        Scanner s = null;
        try {
            s = new Scanner(context.getAssets().open(levelPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelName = s.nextLine();
        author = s.nextLine();
        width = s.nextInt();
        height = s.nextInt();
        bestPossibleScore = s.nextInt();
        tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile t = readTile(s.next());
                if (t instanceof Player || t instanceof PlayerGoal) {
                    playerX = x;
                    playerY = y;
                }
                tiles[x][y] = t;
            }
        }
        for (int y = 0;y < height;y++){
            for (int x = 0; x < width; x++){
                updateConnections(x,y);
            }
        }
    }

    public Level(String levelName, String author, int playerX, int playerY, int bestPossibleScore, int width, int height, Tile[][] tiles) {
        this.levelName = levelName;
        this.author = author;
        this.bestPossibleScore = bestPossibleScore;
        this.width = width;
        this.tiles = tiles;
        this.playerY = playerY;
        this.playerX = playerX;
    }

    private Tile readTile(String tileName) {
        switch (tileName) {
            case ".":
                return new Empty();
            case "s":
                return new Boulder();
            case "S":
                return new BoulderGoal();
            case "f":
                return new Wall();
            case "p":
                return new Player();
            case "P":
                return new PlayerGoal();
            case "g":
                return new Goal();
            case "w":
                return new Water();
            case "W":
                return new BoulderWater();
            default:
                return new Void();
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTileAt(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return tiles[x][y];
        }else {
            return new Void();
        }
    }

    public String getLevelName(){
        return levelName;
    }

    public String getAuthor(){
        return author;
    }

    public int getBestPossibleScore(){
        return bestPossibleScore;
    }

    public Level copy() {
        Tile[][] tilesCopy = new Tile[width][height];
        for (int x = 0; x < width;x++) {
            tilesCopy[x] = Arrays.copyOf(tiles[x],tiles.length);
        }
        return new Level(levelName,author,playerX,playerY,bestPossibleScore,width,height,tiles);
    }

    public boolean move(Direction d) {
        Movable player = (Movable) getTileAt(playerX,playerY);
        int dx = 0;
        int dy = 0;
        switch (d) {
            case UP:
                dy = 1;
                break;
            case DOWN:
                dy = -1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
        }
        Tile moveTo = getTileAt(playerX + dx,playerY + dy);
        if (!moveTo.isSolid()) {
            tiles[playerX][playerY] = player.moveLeftOver();
            tiles[playerX + dx][playerY + dy] = player.moveOnto(tiles[playerX + dx][playerY + dy]);
            playerX += dx;
            playerY += dy;
            return true;
        }
        Tile moveAfter = getTileAt(playerX + (dx * 2),playerY + (dy * 2));
        if (moveTo instanceof Movable && !(((Movable) moveTo).moveOnto(moveAfter) instanceof Void)) {
            tiles[playerX][playerY] = player.moveLeftOver();
            playerX += dx;
            playerY += dy;
            tiles[playerX][playerY] = player.moveOnto(((Movable) moveTo).moveLeftOver());
            tiles[playerX + dx][playerY + dy] = ((Movable) moveTo).moveOnto(moveAfter);
            return true;
        }
        return false;
    }

    public void updateConnections(int x, int y){
        Tile tile = getTileAt(x,y);
        if (tile instanceof Connectable){
            ((Connectable)tile).connectLeft(getTileAt(x-1,y));
            ((Connectable)tile).connectTop(getTileAt(x,y-1));
            ((Connectable)tile).connectRight(getTileAt(x+1,y));
            ((Connectable)tile).connectBottom(getTileAt(x,y+1));
        }
    }

    public boolean isFinished(){
        for(int i = 0; i < tiles.length; i++)
            for(int j = 0; j < tiles[i].length; j++)
                if(tiles[i][j] instanceof Goal || tiles[i][j] instanceof PlayerGoal)
                    return false;
        return true;
    }

    public String getLevelPath(){
        return levelPath;
    }

    public String getHighscoreString(){
        return levelPath.substring(7);
    }

}
