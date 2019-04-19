package tiles;

import game.Texture;

public class Goal extends Tile{

    Texture stable = new Texture("stable");

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public Texture getTexture() {
        return stable;
    }
}
