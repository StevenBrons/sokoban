package tiles;

import game.Texture;

public class Void implements Tile {
    Texture texture = new Texture("grassTile");

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
