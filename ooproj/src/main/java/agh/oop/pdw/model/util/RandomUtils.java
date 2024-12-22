package agh.oop.pdw.model.util;

import agh.oop.pdw.model.Grass;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;

import java.util.Map;
import java.util.Random;

public class RandomUtils {
    public static final Random RANDOM = new Random();

    public Vector2D getGrassSpawnPosition(WorldMap worldMap){
        Map<Vector2D, Grass> elements = worldMap.getGrasses();
        if (RANDOM.nextInt() < 80){
            Boundary boundary = new Boundary(new Vector2D(30,30), new Vector2D(69,69));
            Vector2D position = getRandomPosition(boundary);
            while (elements.containsKey(position)){
                position = getRandomPosition(boundary);
            }
            return position;
        }
        else return new Vector2D(-1, -1);
    }

    private Vector2D getRandomPosition(Boundary boundary){
        return new Vector2D(
                RANDOM.nextInt(boundary.topRight().getX() - boundary.bottomLeft().getX()) + boundary.bottomLeft().getX(),
                RANDOM.nextInt(boundary.topRight().getY() - boundary.bottomLeft().getY()) + boundary.bottomLeft().getY()
        );
    }
}
