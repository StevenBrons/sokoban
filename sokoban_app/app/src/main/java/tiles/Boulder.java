package tiles;

import java.util.Random;

import game.Texture;

public class Boulder extends Tile{

    private int boulderType;
    private Texture texture;

    public Boulder() {
        Random r = new Random();
        boulderType = r.nextInt(4) + 1;
        texture = new Texture("sheep" + boulderType);
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
