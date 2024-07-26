package soulessences.utils;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String texturePath) {
        Texture texture = textures.get(texturePath);

        if (texture != null)
            return texture;

        return loadTexture(texturePath);
    }

    private static Texture loadTexture(String texturePath) {
        Texture texture = new Texture(texturePath);

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.put(texturePath, texture);

        return texture;
    }
}
