package game;

import android.graphics.Bitmap;
import java.util.HashMap;

public class Texture {

    static HashMap<String, Bitmap> textures = new HashMap<>();
    private String name;

    Texture(String name) {
        this.name = name;
        if (!textures.containsKey(name)) {
        }
    }

    public Bitmap getTexture() {
        return textures.get(this.name);
    }
}