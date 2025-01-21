package agh.oop.pdw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    @Test
    public void testAddVector() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        Vector2D result = v1.addVector(v2);

        assertEquals(new Vector2D(4, 6), result);
    }

    @Test
    public void testPrecedes() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(2, 3);
        Vector2D v3 = new Vector2D(0, 1);


        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v1));
        assertTrue(v3.precedes(v1));
    }

    @Test
    public void testFollows() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(2, 3);
        Vector2D v3 = new Vector2D(0, 1);

        assertTrue(v2.follows(v1));
        assertFalse(v1.follows(v2));
        assertTrue(v1.follows(v3));
    }

    @Test
    public void testToString() {
        Vector2D v1 = new Vector2D(1, 2);

        assertEquals("Vector2D [x=1, y=2]", v1.toString());
    }


}
