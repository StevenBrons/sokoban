package tiles;

import java.util.Random;

import game.Texture;

public class BoulderGoal implements Tile, Movable {
    private int boulderType;
    Texture texture;

    public BoulderGoal() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("stableSheep" + boulderType);
    }

    public BoulderGoal(Boulder other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 1:
                boulderType = 1;
            case 2:
                boulderType = 1;
            case 3:
                boulderType = r.nextInt(2)+2;
            default:
                boulderType = r.nextInt(3)+1;
        }
        texture = new Texture("stableSheep" + boulderType);
    }

    public BoulderGoal(BoulderGoal other){
        boulderType = other.boulderType;
        texture = new Texture("stableSheep" + boulderType);
    }

    public int getBoulderType(){
        return boulderType;
    }

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
            return new Boulder(this);
        }
        if (other instanceof Goal){
            return new BoulderGoal(this);
        }
        return new Void();
    }
}
