package game;

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

    Level(String content) {
        Scanner s = new Scanner(content);
        levelName = s.nextLine();
        author = s.nextLine();
        width = s.nextInt();
        height = s.nextInt();
        bestPossibleScore = s.nextInt();
        tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = readTile(s.next());
            }
        }
        for (int y = 0;y < height;y++){
            for (int x = 0; x < width; x++){
                System.out.println("a "+tiles[x][y].getClass().toString());
                if (tiles[x][y] instanceof Connectable){
                    ((Connectable)tiles[x][y]).connectLeft(getTileAt(x-1,y));
                    ((Connectable)tiles[x][y]).connectTop(getTileAt(x,y-1));
                    ((Connectable)tiles[x][y]).connectRight(getTileAt(x+1,y));
                    ((Connectable)tiles[x][y]).connectBottom(getTileAt(x,y+1));
                }
            }
        }
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

    public String toString() {
        String s = "";
        s += levelName + "\n";
        s += author + "\n";
        s += width + " " + height + " " + bestPossibleScore + " \n";


        return s;
    }
}
