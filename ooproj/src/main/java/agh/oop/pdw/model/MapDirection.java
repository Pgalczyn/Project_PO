package agh.oop.pdw.model;

import java.util.Random;

public enum MapDirection {
    NORTH,NORTH_EAST,NORTH_WEST,SOUTH_EAST,SOUTH_WEST,SOUTH,WEST,EAST;

    @Override
    public String toString() {
        return switch (this){
            case NORTH -> "N";
            case NORTH_EAST -> "N_E";
            case NORTH_WEST -> "N_W";
            case SOUTH_EAST -> "S_E";
            case SOUTH_WEST -> "S_W";
            case SOUTH -> "S";
            case WEST -> "W";
            case EAST -> "E";
            default -> "";
        };
    }

    public Vector2D toVector(){
        return switch (this){
            case NORTH -> new Vector2D(0,1);
            case NORTH_EAST -> new Vector2D(1,1);
            case NORTH_WEST -> new Vector2D(-1,1);
            case SOUTH_EAST -> new Vector2D(1,-1);
            case SOUTH_WEST -> new Vector2D(-1,-1);
            case SOUTH -> new Vector2D(0,-1);
            case WEST -> new Vector2D(-1,0);
            case EAST -> new Vector2D(1,0);

        };
    }

    public MapDirection nextDirection(){
        return switch (this){
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case NORTH_WEST -> NORTH;
            case SOUTH_EAST -> SOUTH;
            case SOUTH_WEST -> WEST;
            case SOUTH -> SOUTH_WEST;
            case WEST -> NORTH_WEST;
            case EAST -> SOUTH_WEST;
        };
    }


    public static MapDirection randomDirection(){
        Random random = new Random();
        MapDirection[] directions = values();
        return directions[random.nextInt(directions.length)];
    }
}
