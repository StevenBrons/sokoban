package tiles;

import game.Texture;

public class Player implements Tile, Movable {

    Texture texture = new Texture("shepherdDogGrass");

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
        return new Empty();
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