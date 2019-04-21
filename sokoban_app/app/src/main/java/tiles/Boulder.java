package tiles;

import java.util.Random;

import game.Texture;

public class Boulder implements Tile, Movable {

    private int boulderType;
    Texture texture;

    public Boulder() {
        Random r = new Random();
        boulderType = r.nextInt(3) + 1;
        texture = new Texture("sheep" + boulderType);
    }

    public Boulder(Boulder other){
        this.boulderType = other.boulderType;
        texture = new Texture("sheep"+boulderType);
    }

    public Boulder(BoulderGoal other){
        Random r = new Random();
        switch (other.getBoulderType()){
            case 1:
                boulderType = r.nextInt(2)+1;
            case 2:
                boulderType = 3;
            case 3:
                boulderType = 3;
            default:
                boulderType = r.nextInt(3)+1;
        }
        texture = new Texture("sheep"+boulderType);
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
            return new Empty();
        }
        return new Void();
    }
}
