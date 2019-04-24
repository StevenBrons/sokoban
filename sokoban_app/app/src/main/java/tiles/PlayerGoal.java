package tiles;

import game.Texture;

/**
 * @author Jelmer Firet
 * a tile that acts as a player and as a goal
 * can be used as a goal tile if no player is present
 * acts as an active player
 * doesn't connect
 */
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
