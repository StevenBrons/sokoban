package tiles;

import game.Texture;

public class Tile {

    private Texture texture = new Texture("arrowDown");

    public boolean isSolid() {
        return true;
    }

    public boolean isMovable() {
        return false;
    }

    public Texture getTexture() {
        return texture;
    }

}
