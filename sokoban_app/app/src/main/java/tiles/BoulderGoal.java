package tiles;

import java.util.Random;

import game.Texture;

/**
 * @author Jelmer Firet
 * a tile that acts as sheep and as a goal
 * doesn't connect
 */
public class BoulderGoal implements Tile,Movable {
    private int boulderType;
    private Texture texture;

    /**
     * @author Jelmer Firet
     * makes a new random BoulderGoal
     */

    public BoulderGoal() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("stableSheep" + boulderType);
    }

    /**
     * @author Jelmer Firet
     * @param other a Boulder to use as a reference for the number of sheep in the new BoulderGoal
     */

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

    /**
     * @author Jelmer Firet
     * @param other a BoulderGoal to make a clone of
     */

    BoulderGoal(BoulderGoal other){
        boulderType = other.boulderType;
        texture = new Texture("stableSheep" + boulderType);
    }

    /**
     * @author Jelmer Firet
     * @param other a BoulderWater to use as a reference for the number of
     *              sheep in the new BoulderGoal
     */
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
