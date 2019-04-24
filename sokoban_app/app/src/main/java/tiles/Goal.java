package tiles;

import game.Texture;

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
