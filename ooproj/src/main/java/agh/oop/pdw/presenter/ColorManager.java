package agh.oop.pdw.presenter;

import agh.oop.pdw.model.Exceptions.WrongColorFormatException;

public class ColorManager {
    public static String getColor(double percentage) throws WrongColorFormatException {
        if (percentage < 0 || percentage > 1) {
            throw new WrongColorFormatException("Percentage must be between 0 and 1");
        }
        int r = (int) (255 * (1 - percentage));
        int g = (int) (255 * percentage);
        return String.format("#%02X%02X%02X", r, g, 0);
    }
}
