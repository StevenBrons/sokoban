package tiles;

import game.Texture;

public class PlayerGoal implements Tile, Movable {
    Texture texture = new Texture("shepherdDogStable");

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Tile MoveLeftOver() {
        return new Goal();
    }

    @Override
    public Tile moveOnto(Tile other) {
        if (other instanceof Empty){
            return new Player();
        }
        if (other instanceof Goal){
            return new PlayerGoal();
        }
        return new Void();
    }
}
