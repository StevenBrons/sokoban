package tiles;

import game.Texture;

public interface Tile {
    boolean isSolid();
    Texture getTexture();
}
