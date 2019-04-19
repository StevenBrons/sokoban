package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import tiles.Boulder;
import tiles.Empty;
import tiles.Goal;
import tiles.Tile;
import tiles.Void;
import tiles.Wall;

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
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = readTile(s.next());
            }
        }
    }

    private Tile readTile(String tileName) {
        switch (tileName) {
            case ".":
                return new Empty();
            case "s":
                return new Boulder();
            case "f":
                return new Wall();
            case "v":
                return new Void();
            case "g":
                return new Goal();
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
            System.out.println(tiles[x][y]);
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
