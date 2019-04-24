package tiles;

import game.Texture;

public class Player implements Tile, Movable {

    private Texture texture = new Texture("shepherdDogGrass");

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Tile moveLeftOver() {
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
        if (other instanceof Water){
            return new PlayerWater((Water)other);
        }
        return new Void();
    }
}