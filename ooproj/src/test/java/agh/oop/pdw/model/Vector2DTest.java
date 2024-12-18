package agh.oop.pdw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    public void testVector2D_Adding() {
        Vector2D vector2D = new Vector2D(1,1);
        assertEquals(vector2D.addVector(new Vector2D(1,1)), new Vector2D(2,2));
        assertEquals(new Vector2D(1,1).addVector(new Vector2D(10,10)), new Vector2D(11,11));
        assertEquals(new Vector2D(5,5).addVector(new Vector2D(10,10)), new Vector2D(15,15));
    }


}