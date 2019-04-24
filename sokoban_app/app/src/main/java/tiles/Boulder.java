package tiles;

import java.util.Random;

import game.Texture;

/**
 * @author Jelmer Firet
 * a sheep tile
 * sheep can be pushed on land tiles that aren't fences and into water
 * doesn't connect
 */
public class Boulder implements Tile, Movable {

    private int boulderType;
    private Texture texture;

    /**
     * @author Jelmer Firet
     * makes a new random boulder
     */
    public Boulder() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("sheep" + boulderType);
    }

    /**
     * @author Jelmer Firet
     * @param other a Boulder to make a copy of
     */
    Boulder(Boulder other){
        this.boulderType = other.getBoulderType();
        texture = new Texture("sheep"+boulderType);
    }

    /**
     * @author Jelmer Firet
     * @param other a BoulderGoal to use as template for the new boulder, preserves
     *              the number of sheep on the tile
     */
    Boulder(BoulderGoal other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 1:
                boulderType = r.nextInt(2)+1;break;
            case 2:
                boulderType = 3;break;
            case 3:
                boulderType = 3;break;
            default:
                boulderType = r.nextInt(3)+1;break;
        }
        texture = new Texture("sheep"+boulderType);
    }

    /**
     * @author Jelmer Firet
     * @param other a BoulderWater to use as a template for the new boulder, preserves
     *              the number of sheep on the tile
     */
    Boulder(BoulderWater other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 2:
                boulderType = 3;break;
            case 3:
                boulderType = r.nextInt(2)+1;break;
            default:
                boulderType = r.nextInt(3)+1;break;
        }
        texture = new Texture("sheep" + boulderType);
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
        return new Empty();
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
