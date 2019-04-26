package tiles;

import android.graphics.Bitmap;

import game.Texture;

/**
 * this tile type is returned if a tile outside the level is queried
 * sheep can't be pushed into void
 * players can walk over void
 * doesn't connect
 * @author Steven Bronsveld
 */
public class Void implements Tile {
    private Texture texture = new Texture("grassTile");

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Texture getTexture() {
        if (!Texture.hasTexture("void")){
            texture.addTexture("void",Bitmap.createBitmap(Texture.WIDTH, Texture.HEIGHT,
                    Bitmap.Config.ARGB_8888));
        }
        return new Texture("void");
    }
}
