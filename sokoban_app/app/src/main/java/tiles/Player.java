package tiles;

import game.Texture;

public class Player extends Tile{

    Texture player = new Texture("player");

    @Override
    public Texture getTexture() {
        return player;
    }
}