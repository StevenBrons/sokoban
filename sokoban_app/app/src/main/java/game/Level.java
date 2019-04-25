package game;

import android.content.Context;

import com.debernardi.sokoban.GameActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import tiles.*;
import tiles.Void;

/**
 *
 * Contains the current level state
 *
 * @author Steven Bronsveld
 */
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

    /**
     * The level is initialized from the file, properties of the level are set,
     * including the playerX and playerY which are calculated.
     *
     * @param context a random context, just used for obtaining
     * @param levelPath the path of the level file starting in the assets folder (so including level/).
     *                  An example level name is level/00.sok
     * @author Steven Bronsveld
     */
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

    /**
     * This constructor creates the level not from a file, but from a bunch of parameters
     *
     * @param levelName The title of the level
     * @param author The creator of the level
     * @param playerX The current player x position of this level state
     * @param playerY The current player y position of this level state
     * @param bestPossibleScore The minimum amount of steps the level can be completed in
     * @param width The width of the level
     * @param height The height of the level
     * @param tiles A 2D array containing the tiles of the current level state
     * @param levelPath The path of the original level file from which the level was first created
     */
    public Level(String levelName, String author, int playerX, int playerY, int bestPossibleScore, int width, int height, Tile[][] tiles, String levelPath) {
        this.levelName = levelName;
        this.author = author;
        this.bestPossibleScore = bestPossibleScore;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.playerY = playerY;
        this.playerX = playerX;
        this.levelPath = levelPath;
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

    /**
     *
     * @return The width of the level
     */
    public int getWidth() {
        return width;
    }


    /**
     *
     * @return The height of the level
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param x The x coordinate to get the tile at
     * @param y The y coordinate to get the tile at
     * @return The tile at the given x,y coordinate.
     * If a coordinate outside of the level bounds is requested, an new void tile is returned
     */
    public Tile getTileAt(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return tiles[x][y];
        }else {
            return new Void();
        }
    }

    /**
     *
     * @return The level name
     */
    public String getLevelName(){
        return levelName;
    }

    /**
     *
     * @return The autor of the level
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Returns a copy of the level object
     *
     */
    public int getBestPossibleScore(){
        return bestPossibleScore;
    }

    /**
     * @return A copy of the level object
     */
    public Level copy() {
        Tile[][] tilesCopy = new Tile[width][height];
        for (int x = 0; x < width;x++) {
            tilesCopy[x] = Arrays.copyOf(tiles[x],tiles[x].length);
        }
        return new Level(levelName,author,playerX,playerY,bestPossibleScore,width,height,tilesCopy, levelPath);
    }

    /**
     * If possible move the player and if necessary push the boulders
     *
     * @param d The direction to move in
     * @return If the move was valid
     */
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
        if (moveAfter instanceof Goal && (moveTo instanceof Boulder ||
                moveTo instanceof BoulderWater || moveTo instanceof BoulderGoal)){
            GameActivity.playAudioSheep();
        }
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

    /**
     *
     * @return The level path
     */
    public String getLevelPath(){
        return levelPath;
    }

    public String getHighscoreString(){
        return levelPath.substring(7);
    }

}
