/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math3d;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jacob
 */
public class Vector3Test {
    
    public Vector3Test() { }
    
    @Test
    public void testTriangleNormal() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(1, 0, 0);
        Vector3 c = new Vector3(0, 1, 0);
        
        Vector3 normal = Vector3.normal(a, b, c).getUnitVector();
        //should be counter-clockwise
        assertTrue(normal.equals(new Vector3(0, 0, 1)));
    }
    
    @Test
    public void testRotate() {
        Vector3 a = new Vector3(3, 4, 5);
        
        Vector3 b = a.rotate(new Rotation3(0, 0, 0));
        assertTrue(a.equals(b));
        
        Direction3 direction = new Direction3(Math.PI/4, Math.PI/4);
        Vector3 c = Direction3.BASE_ROTATION
                .rotate(new Rotation3(direction, Math.PI/4));
        Direction3 cDirection = c.direction();
        assertTrue(cDirection.equals(direction));
        assertTrue(approximatelyEqual(
                cDirection.getYRotation(),
                direction.getYRotation()
        ));
        assertTrue(approximatelyEqual(
                cDirection.getZRotation(),
                direction.getZRotation()
        ));
        
        Random random = new Random();
        
        Direction3 d = new Direction3(new Vector3(
                random.nextDouble(), random.nextDouble(), random.nextDouble()
        ).normalize());
        System.out.println(d.getUnitVector());
        Direction3 e =
                d.getUnitVector().rotate(new Rotation3(0, 0, 0)).direction();
        System.out.println(e.getUnitVector());
        assertTrue(approximatelyEqual(d.getYRotation(), e.getYRotation()));
        assertTrue(approximatelyEqual(d.getZRotation(), e.getZRotation()));
        assertTrue(approximatelyEqual(d.getUnitVector(), e.getUnitVector()));
    }
    
    @Test
    public void testDirectionConversion() {
        Direction3 a = new Direction3(new Vector3(3,4,5).normalize());
        Direction3 b = new Direction3(a.getYRotation(), a.getZRotation());
        Direction3 c = new Direction3(b.getUnitVector());
        Direction3 d = new Direction3(c.getYRotation(), c.getZRotation());
        Direction3 e = new Direction3(d.getUnitVector());
        System.out.println(a);
        System.out.println(e);
    }
    
    @Test
    public void testNormalDirection() {
        //should be counterclockwise
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(1, 0, 0);
        Vector3 c = new Vector3(0, 1, 0);
        Vector3 normal = Vector3.normal(a, b, c).getUnitVector();
        assertTrue(normal.equals(new Vector3(0, 0, 1)));
    }
    
    private boolean approximatelyEqual(double a, double b) {
        double difference = Math.abs(a - b);
        return difference < 1E-7;
    }
    
    private boolean approximatelyEqual(Vector3 a, Vector3 b) {
        return approximatelyEqual(a.getX(), b.getX())
                && approximatelyEqual(a.getY(), b.getY())
                && approximatelyEqual(a.getZ(), b.getZ());
    }
}
