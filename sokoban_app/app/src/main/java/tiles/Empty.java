package tiles;

import game.Texture;

/**
 * @author Jelmer Firet
 * a grass tile
 * sheep can be pushed onto grass
 * players can walk over grass
 * doesn't connect
 */
public class Empty implements Tile{
    private Texture texture = new Texture("grassTile");

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
