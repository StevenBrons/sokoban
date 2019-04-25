package tiles;

import game.Texture;

/**
 * a goal tile
 * sheep can be pushed onto this tile
 * players can walk over this tile
 * doesn't connect
 * all sheep have to be moved to goal tiles to win
 * @author Jelmer Firet
 */

public class Goal implements Tile{

    private Texture texture = new Texture("stable");

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
