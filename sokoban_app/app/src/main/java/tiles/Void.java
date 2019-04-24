package tiles;

import game.Texture;

/**
 * @author Steven Bronsveld
 * this tile type is returned if a tile outside the level is queried
 * sheep can't be pushed into void
 * players can walk over void
 * doesn't connect
 */
public class Void implements Tile {
    private Texture texture = new Texture("grassTile");

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
