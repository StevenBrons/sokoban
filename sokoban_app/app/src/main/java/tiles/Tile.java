package tiles;

import game.Texture;

/**
 * @author Jelmer Firet
 */

public interface Tile {
    /**
     * @return boolean whether a player can move trough this tile
     */
    boolean isSolid();

    /**
     * @return a game.Texture with the bitmap of this tile
     */
    Texture getTexture();
}
