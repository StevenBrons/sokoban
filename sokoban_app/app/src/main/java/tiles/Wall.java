package tiles;

import game.Texture;

public class Wall extends Tile{

    private Texture texture = new Texture("fenceCross");

    @Override
    public Texture getTexture() {
        return texture;
    }
}
