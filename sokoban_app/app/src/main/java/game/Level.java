package game;

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

    Level(String fileName) {
        //todo read line
        String content = "";
        Scanner s = new Scanner(content);
        levelName = s.nextLine();
        width = s.nextInt();
        height = s.nextInt();
        tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = readTile(s.next());
            }
        }
    }

    private Tile readTile(String tileName) {
        switch (tileName) {
            case "g":
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
            return tiles[x][y];
        }else {
            return new Void();
        }
    }
}
