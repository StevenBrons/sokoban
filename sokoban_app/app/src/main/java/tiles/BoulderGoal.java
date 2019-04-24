package tiles;

import java.util.Random;

import game.Texture;

public class BoulderGoal implements Tile,Movable {
    private int boulderType;
    private Texture texture;

    public BoulderGoal() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("stableSheep" + boulderType);
    }

    BoulderGoal(Boulder other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 1:
                boulderType = 1;break;
            case 2:
                boulderType = 1;break;
            case 3:
                boulderType = r.nextInt(2)+2;break;
            default:
                boulderType = r.nextInt(3)+1;break;
        }
        texture = new Texture("stableSheep" + boulderType);
    }

    BoulderGoal(BoulderGoal other){
        boulderType = other.boulderType;
        texture = new Texture("stableSheep" + boulderType);
    }

    BoulderGoal(BoulderWater other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 2:
                boulderType = r.nextInt(2)+2;break;
            case 3:
                boulderType = 1;break;
            default:
                boulderType = r.nextInt(3)+1;break;
        }
        texture = new Texture("stableSheep" + boulderType);
    }

    int getBoulderType(){
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
    public Tile moveLeftOver() {
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
        if (other instanceof Water){
            return new BoulderWater(this,(Water)other);
        }
        return new Void();
    }
}
