package tiles;

import java.util.Random;

import game.Texture;

public class Boulder implements Tile, Movable {

    private int boulderType;
    private Texture texture;

    public Boulder() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("sheep" + boulderType);
    }

    Boulder(Boulder other){
        this.boulderType = other.getBoulderType();
        texture = new Texture("sheep"+boulderType);
    }

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
