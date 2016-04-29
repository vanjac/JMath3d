/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math3d;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

/**
 *
 * @author jacob
 */
public class Vector2Test {
    
    public Vector2Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testDirectionConversionsMatch() {
        double direction = 0.85;
        Vector2 v = Vector2.unitVector(direction);
        assertTrue(v.direction() == direction);
    }
    
    @Test
    public void testAddition() {
        Vector2 a = new Vector2(5.4, 8.2);
        Vector2 b = new Vector2(2.7, 9.6);
        Vector2 expectedResult = new Vector2(5.4+2.7, 8.2+9.6);
        Vector2 result = a.add(b);
        assertTrue(expectedResult.equals(result));
    }
    
    @Test
    public void testEquals() {
        Vector2 a = new Vector2(1,3);
        Vector2 b = new Vector2(2,3);
        Vector2 c = new Vector2(1,2);
        Vector2 d = new Vector2(1,3);
        Object o = new Object();
        Vector2 e = new Vector2(1,3) {
            public void doNothing() { }
        };
        
        assertTrue(a.equals(d));
        assertTrue(d.equals(a));
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertFalse(a.equals(c));
        assertFalse(b.equals(c));
        
        assertFalse(a.equals(o));
        assertTrue(a.equals(e)); //should allow subclasses
    }
    
    @Test
    public void testNormalize() {
        Random r = new Random();
        for(int i = 0; i < 100; i++) {
            Vector2 v = new Vector2(r.nextDouble(), r.nextDouble());
            v = v.normalize();
            assertTrue(approximatelyEqual(v.magnitude(), 1));
        }
        assertFalse(approximatelyEqual(1,2));
    }
    
    @Test
    public void testOrthogonal() {
        Vector2 a = new Vector2(4, 7);
        Vector2 b = a.rotate(Math.PI / 2);
        assertTrue(a.orthogonal(b));
        assertTrue(b.orthogonal(a));
        
        Vector2 c = a.rotate(Math.PI / 2 + 0.1);
        assertFalse(a.orthogonal(c));
        assertFalse(c.orthogonal(a));
    }
    
    @Test
    public void testDirection() {
        Vector2 a = new Vector2( 3, 0);
        Vector2 b = new Vector2( 3, 3);
        Vector2 c = new Vector2( 0, 3);
        Vector2 d = new Vector2(-3, 3);
        Vector2 e = new Vector2(-3, 0);
        Vector2 f = new Vector2(-3,-3);
        Vector2 g = new Vector2( 0,-3);
        Vector2 h = new Vector2( 3,-3);
        
        System.out.println(Math.toDegrees(a.direction()));
        System.out.println(Math.toDegrees(b.direction()));
        System.out.println(Math.toDegrees(c.direction()));
        System.out.println(Math.toDegrees(d.direction()));
        System.out.println(Math.toDegrees(e.direction()));
        System.out.println(Math.toDegrees(f.direction()));
        System.out.println(Math.toDegrees(g.direction()));
        System.out.println(Math.toDegrees(h.direction()));
    }
    
    @Test
    public void testRotate() {
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            Vector2 a =
                    new Vector2(random.nextDouble()*10, random.nextDouble()*10);
            Vector2 b = a.rotate(0);
            assertTrue(a.equals(b));
        }
    }
    
    @Test
    public void testAngleBetween() {
        Vector2 a = new Vector2(8, 5);
        double angle = 0.7;
        Vector2 b = a.rotate(angle);
        assertTrue(approximatelyEqual(a.angleBetween(b), angle));
        assertTrue(approximatelyEqual(b.angleBetween(a), angle));
    }
    
    @Test
    public void testFixRotation() {
        Random r = new Random();
        for(int i = 0; i < 100; i++) {
            double rotation = r.nextDouble() * 1000;
            double fixed = Vector2.fixRotation(rotation);
            assertTrue(fixed >= 0 && fixed < Math.PI*2);
        }
    }
    
    private boolean approximatelyEqual(double a, double b) {
        double difference = Math.abs(a - b);
        return difference < 1E-7;
    }
}
