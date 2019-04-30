package levelgenerator;
import java.util.Arrays;
import levelgenerator.tiles.*;
import levelgenerator.tiles.Void;

/**
 * class to represent a level
 * @author Jelmer Firet
 */

public class Level {
    public enum Direction{
        UP, RIGHT, DOWN, LEFT;
    }
    private final Tile[][] board;
    private final String levelName;
    private final String authorName;
    private final int width;
    private final int height;
    private int playerX;
    private int playerY;
    
    public Level(int width, int height, String name, String author){
        board = new Tile[height][width];
        for (int y = 0;y<height;y++){
            for (int x = 0;x<width;x++){
                board[y][x] = new Void();
            }
        }
        this.width = width;
        this.height = height;
        this.levelName = name;
        this.authorName = author;
        playerX = -1;
        playerY = -1;
    }
    
    public Level(Level other){
        this.width = other.width;
        this.height = other.height;
        this.board = new Tile[height][width];
        for (int y = 0;y<height;y++){
            for (int x = 0;x<width;x++){
                this.board[y][x] = other.board[y][x];
                this.board[y][x].setOriginal(other.board[y][x].getOriginal());
            }
        }
        this.levelName = other.levelName;
        this.authorName = other.authorName;
        this.playerX = other.playerX;
        this.playerY = other.playerY;
    }
    
    @Override
    public String toString(){
        StringBuilder levelString = new StringBuilder("");
        levelString.append(levelName).append('\n');
        levelString.append(authorName).append('\n');
        levelString.append(Integer.toString(width)).append(' ');
        levelString.append(Integer.toString(height)).append(" -1\n");
        for (int y = 0;y<height;y++){
            for (int x = 0;x<width;x++){
                if (x>0){
                    levelString.append(' ');
                }
                levelString.append(board[y][x].toString());
            }
            levelString.append('\n');
        }       
        return levelString.toString();
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public boolean hasPlayer(){
        return playerX >= 0;
    }
    
    public Tile getTileAt(int x,int y){
        if (0 <= x && x< width && 0<=y && y<height){
            return board[y][x];
        }
        return new Void();
    }
    
    public void placeTileAt(int x,int y,Tile tile){
        if (0 <= x && x < width && 0 <= y && y < height){
            board[y][x] = tile;
            if (tile instanceof Player || tile instanceof PlayerGoal || 
                                          tile instanceof PlayerWater){
                playerX = x;
                playerY = y;
            }
        }
    }
    
    public void move(Direction dir){
        int dx = 0,dy = 0;
        switch(dir){
            case UP: dy=-1;break;
            case RIGHT: dx=1;break;
            case DOWN: dy=1;break;
            case LEFT: dx=-1;break;
        }
        Tile forward = getTileAt(playerX+dx,playerY+dy);
        Tile forward2 = getTileAt(playerX+2*dx,playerY+2*dy);
        if (!(forward.moveOnto(forward2) instanceof Void)){
            placeTileAt(playerX+dx,playerY+dy,forward.moveLeftOver());
            placeTileAt(playerX+2*dx,playerY+2*dy,forward.moveOnto(forward2));
        }
        
        Tile player = getTileAt(playerX,playerY);
        forward = getTileAt(playerX+dx,playerY+dy);
        if (!(player.moveOnto(forward) instanceof Void)){
            placeTileAt(playerX,playerY,player.moveLeftOver());
            placeTileAt(playerX+dx,playerY+dy,player.moveOnto(forward));
        }
    }
    
    public void undoMove(Direction dir,boolean pull){
        int dx = 0,dy = 0;
        switch(dir){
            case UP: dy=-1;break;
            case RIGHT: dx=1;break;
            case DOWN: dy=1;break;
            case LEFT: dx=-1;break;
        }
        Tile player = getTileAt(playerX,playerY);
        Tile backward = getTileAt(playerX-dx,playerY-dy);
        if (!(player.moveOnto(backward) instanceof Void)){
            placeTileAt(playerX,playerY,player.moveLeftOver());
            placeTileAt(playerX-dx,playerY-dy,player.moveOnto(backward));
            Tile forward = getTileAt(playerX+dx,playerY+dy);
            Tile forward2 = getTileAt(playerX+2*dx,playerY+2*dy);
            if (pull && !(forward2.moveOnto(forward) instanceof Void)){
                placeTileAt(playerX+2*dx,playerY+2*dy,forward2.moveLeftOver());
                placeTileAt(playerX+dx,playerY+dy,forward2.moveOnto(forward));
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Level other = (Level) obj;
        return other.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Arrays.deepHashCode(this.board);
        return hash;
    }
}
