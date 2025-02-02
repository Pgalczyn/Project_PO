package agh.oop.pdw.model.util;

import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;

import java.util.*;

public class RandomUtils {
    public static final Random RANDOM = new Random();

    public static Vector2D getGrassSpawnPosition(WorldMap worldMap){
        List<Vector2D> possibleFields = worldMap.getEmptyFields();
        if(possibleFields.isEmpty()) return null;
        List<Vector2D> jungleFields = possibleFields.stream()
                .filter(p -> worldMap.getJungleBoundary().contains(p)).toList();
        if (RANDOM.nextFloat() < 0.8 && !jungleFields.isEmpty()) {
            return jungleFields.get(RANDOM.nextInt(jungleFields.size()));
        }
        return possibleFields.get(RANDOM.nextInt(possibleFields.size()));
    }


    public static Vector2D getRandomPosition(Boundary boundary){
        return new Vector2D(
                RANDOM.nextInt(boundary.topRight().getX() - boundary.bottomLeft().getX()) + boundary.bottomLeft().getX(),
                RANDOM.nextInt(boundary.topRight().getY() - boundary.bottomLeft().getY()) + boundary.bottomLeft().getY()
        );
    }

    public static int[] genotype(int n){
        int[] genotype = new int[n];
        for(int i = 0; i < n; i++){
            genotype[i] = RANDOM.nextInt(8);
        }
        return genotype;
    }
}
