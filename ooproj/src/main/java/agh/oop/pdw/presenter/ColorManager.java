package agh.oop.pdw.presenter;

public class ColorManager {
    private final String startColor = "#ff8585";
    private final String endColor = "#ff0000";

    public static String getColor(double percentage) {
        int r = (int) (255 * (1 - percentage));
        int g = (int) (255 * percentage);
        return String.format("#%02x%02x%02x", r, g, 0);
    }
}
