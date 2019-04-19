package game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;

public class Texture {

    static HashMap<String, Bitmap> textures = new HashMap<>();
    public static final int WIDTH = 48;
    public static final int HEIGHT = 32;
    private static AssetManager manager;

    private String name;

    public static void init(AssetManager manager) {
        Texture.manager = manager;
    }

    public Texture(String name) {
        this.name = name;
        if (!textures.containsKey(name)) {
            try {
                Bitmap b = BitmapFactory.decodeStream(manager.open("sprites/" + name + ".png"));
                textures.put(name,b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmap() {
        return textures.get(this.name);
    }
}