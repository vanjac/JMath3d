package math3d;

import java.util.Objects;

/**
 * A rotation in 3d space.
 * @author vanjac
 */
public class Rotation3 {
    public static final Rotation3 ZERO = new Rotation3(0, 0, 0);
    
    private final Direction3 direction;
    private final double roll;
    
    /**
     * Create a new Rotation3 from x-, y-, and z-axis rotation values.
     * @param xRot rotation around x axis, in radians
     * @param yRot rotation around y axis, in radians
     * @param zRot rotation around z axis, in radians
     */
    public Rotation3(double xRot, double yRot, double zRot) {
        direction = new Direction3(yRot, zRot);
        roll = xRot;
    }
    
    /**
     * Create a new Rotation3 from the direction of a unit vector, and rotation
     * around that vector.
     * @param unitVector the unit vector to use. Must be a vector of magnitude
     * 1; this will not be checked, but it is assumed
     * @param roll rotation around the unit vector, in radians
     */
    public Rotation3(Vector3 unitVector, double roll) {
        direction = new Direction3(unitVector);
        this.roll = roll;
    }
    
    /**
     * Create a new Rotation3 from a direction, and rotation around the axis of
     * the direction
     * @param direction the direction to use
     * @param roll rotation around the direction, in radians
     */
    public Rotation3(Direction3 direction, double roll) {
        if(direction == null)
            throw new NullPointerException();
        this.direction = direction;
        this.roll = roll;
    }
    
    @Override
    public String toString() {
        return Double.toString(getXRotation()) + ", " +
                Double.toString(getYRotation()) + ", " +
                Double.toString(getZRotation());
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Rotation3))
            return false;
        if(o == this)
            return true;
        
        Rotation3 r = (Rotation3)o;
        return r.getXRotation() == getXRotation()
                && r.getYRotation() == getYRotation()
                && r.getZRotation() == getZRotation();
    }
    
    /**
     * Check if this Direction3 is the "base rotation," equivalent to no
     * rotations.
     * @return true if this Direction3 is zero
     */
    public boolean isZero() {
        return direction.isZero() && roll == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.direction);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.roll) ^ (Double.doubleToLongBits(this.roll) >>> 32));
        return hash;
    }
    
    /**
     * Get the unit vector of this rotation: a vector with a magnitude of 1,
     * pointing in the direction of this Rotation3.
     * @return a unit vector
     */
    public Vector3 getUnitVector() {
        return direction.getUnitVector();
    }
    
    public double getXRotation() {
        return roll;
    }
    
    public double getYRotation() {
        return direction.getYRotation();
    }
    
    public double getZRotation() {
        return direction.getZRotation();
    }
    
    /**
     * Get the direction of this Rotation3, without the "roll" rotation
     * component.
     * @return the direction of this rotation
     */
    public Direction3 getDirection() {
        return direction;
    }
    
    public Rotation3 add(Rotation3 r) {
        Direction3 newDir = direction.add(r.getDirection());
        double newRoll = getXRotation() + r.getXRotation();
        newRoll = Vector2.fixRotation(newRoll);
        return new Rotation3(newDir, newRoll);
    }
    
    public Rotation3 subtract(Rotation3 r) {
        Direction3 newDir = direction.subtract(r.getDirection());
        double newRoll = getXRotation() - r.getXRotation();
        newRoll = Vector2.fixRotation(newRoll);
        return new Rotation3(newDir, newRoll);
    }
}
