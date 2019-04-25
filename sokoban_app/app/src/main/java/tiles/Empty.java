package tiles;

import game.Texture;

/**
 * a grass tile
 * sheep can be pushed onto grass
 * players can walk over grass
 * doesn't connect
 * @author Jelmer Firet
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
