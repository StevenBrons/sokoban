package game;

import android.content.Context;

import com.debernardi.sokoban.GameActivity;

import java.io.IOException;
import java.util.ArrayList;
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
                if (t instanceof Player) {
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
            case "q":
                return new PlayerWater();
            case "g":
                return new Goal();
            case "w":
                return new Water();
            case "W":
                return new BoulderWater();
            case "#":
                return new Void();
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
                dy = -1;
                break;
            case DOWN:
                dy = 1;
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

    /**
        * @author Bram Pulles
        * @return if the level is solved.
        */
    public boolean isFinished(){
        for(int i = 0; i < tiles.length; i++)
            for(int j = 0; j < tiles[i].length; j++)
                if(tiles[i][j] instanceof Goal || tiles[i][j] instanceof PlayerGoal)
                    return false;
        return true;
    }


    /**
    * @author Bram Pulles
    * @return the path to this level file.
    */
    public String getLevelPath(){
        return levelPath;
    }

    /**
    * @author Bram Pulles
    * @return the name of this level file.
    */
    public String getHighscoreString(){
        return levelPath.substring(7);
    }

    /**
     * @author Jelmer Firet
     * @param x,y   The location to move to.
     * @return Direction[] a set of moves to move player to this tile this tile, empty array if impossible or already there.
     */
    public ArrayList<Direction> getMovesTo(int x,int y){
        if (x<0 || x>=width || y<0 || y >= height || getTileAt(x,y).isSolid()){
            return new ArrayList<>();
        }
        ArrayList<int[]> todo = new ArrayList<>();
        int[] start = new int[2];
        start[0] = x; start[1] = y;
        todo.add(start);
        Direction[][] bestDir = new Direction[height][width];
        for (int y2 = 0;y2<height;y2++){
            for (int x2=0;x2<width;x2++){
                bestDir[y2][x2] = Direction.NONE;
            }
        }
        for (int idx = 0;idx < todo.size();idx++){
            int[] pos = todo.get(idx);
            int[] newPos = new int[2];newPos[0] = pos[0]-1;newPos[1] = pos[1];
            if (!getTileAt(newPos[0],newPos[1]).isSolid() && bestDir[newPos[1]][newPos[0]] == Direction.NONE){
                bestDir[newPos[1]][newPos[0]] = Direction.RIGHT;
                todo.add(newPos);
            }
            newPos = new int[2];newPos[0] = pos[0]+1;newPos[1] = pos[1];
            if (!getTileAt(newPos[0],newPos[1]).isSolid() && bestDir[newPos[1]][newPos[0]] == Direction.NONE){
                bestDir[newPos[1]][newPos[0]] = Direction.LEFT;
                todo.add(newPos);
            }
            newPos = new int[2];newPos[0] = pos[0];newPos[1] = pos[1]-1;
            if (!getTileAt(newPos[0],newPos[1]).isSolid() && bestDir[newPos[1]][newPos[0]] == Direction.NONE){
                bestDir[newPos[1]][newPos[0]] = Direction.DOWN;
                todo.add(newPos);
            }
            newPos = new int[2];newPos[0] = pos[0];newPos[1] = pos[1]+1;
            if (!getTileAt(newPos[0],newPos[1]).isSolid() && bestDir[newPos[1]][newPos[0]] == Direction.NONE){
                bestDir[newPos[1]][newPos[0]] = Direction.UP;
                todo.add(newPos);
            }
        }

        ArrayList<Direction> moves = new ArrayList<>();
        int[] pos = new int[2];pos[0] = playerX;pos[1]=playerY;
        while ((pos[0] != x || pos[1] != y) && bestDir[pos[1]][pos[0]] != Direction.NONE){
            moves.add(bestDir[pos[1]][pos[0]]);
            switch(bestDir[pos[1]][pos[0]]){
                case UP: pos[1]--;break;
                case RIGHT: pos[0]++;break;
                case DOWN: pos[1]++;break;
                case LEFT: pos[0]--;break;
            }
        }
        if (pos[0] != x || pos[1] != y){
            return new ArrayList<>();
        }
        return moves;
    }

}
