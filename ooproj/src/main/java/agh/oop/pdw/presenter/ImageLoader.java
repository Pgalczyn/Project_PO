package agh.oop.pdw.presenter;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static final Map<String, Image> images = new HashMap<String, Image>();

    public static Image GetImage(String path) {
       return images.computeIfAbsent(path, x -> new Image(path));
    }

}
