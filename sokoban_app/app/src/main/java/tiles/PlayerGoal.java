package tiles;

import game.Texture;

public class PlayerGoal extends Player{
    private Texture texture = new Texture("shepherdDogStable");

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Tile moveLeftOver() {
        return new Goal();
    }

}
