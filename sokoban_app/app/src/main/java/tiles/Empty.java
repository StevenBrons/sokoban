package tiles;

import game.Texture;

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
