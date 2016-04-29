package math3d;

/**
 * A direction in 3d space. Preferable to using unit vectors.
 * @author vanjac
 */
public class Direction3 {
    public static final Vector3 BASE_ROTATION = new Vector3(1, 0, 0);
    public static final Direction3 ZERO = new Direction3(new Vector3(1, 0, 0));
    
    private final boolean unitVectorCalculated;
    private final Vector3 unitVector;
    
    private final boolean orientationCalculated;
    private final double yRot;
    private final double zRot;
    
    /**
     * Create a new Direction3 from the y- and z-axis rotation values
     * @param yRot rotation around y axis, in radians
     * @param zRot rotation around z axis, in radians
     */
    public Direction3(double yRot, double zRot) {
        this.yRot = Vector2.fixRotation(yRot);
        this.zRot = Vector2.fixRotation(zRot);
        unitVector = null;
        orientationCalculated = true;
        unitVectorCalculated = false;
    }
    
    /**
     * Create a new Direction3 from the direction of a unit vector
     * @param unitVector the unit vector to use. Must be a vector of magnitude
     * 1; this will not be checked, but it is assumed
     */
    public Direction3(Vector3 unitVector) {
        this.unitVector = unitVector;
        yRot = zRot = 0;
        unitVectorCalculated = true;
        orientationCalculated = false;
    }
    
    @Override
    public String toString() {
        return Double.toString(getYRotation()) + ", " +
                Double.toString(getZRotation());
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Direction3))
            return false;
        if(o == this)
            return true;
        
        Direction3 d = (Direction3)o;
        if(orientationCalculated)
            return d.getYRotation() == getYRotation()
                    && d.getZRotation() == getZRotation();
        
        return d.getUnitVector().equals(getUnitVector());
    }
    
    /**
     * Check if this Direction3 is the "base rotation," equivalent to no
     * rotations.
     * @return true if this Direction3 is zero
     */
    public boolean isZero() {
        if(orientationCalculated)
            return getYRotation() == 0 && getZRotation() == 0;
        return getUnitVector().normalize().equals(BASE_ROTATION);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (Double.doubleToLongBits(getYRotation()) ^ (Double.doubleToLongBits(getYRotation()) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(getZRotation()) ^ (Double.doubleToLongBits(getZRotation()) >>> 32));
        return hash;
    }
    
    /**
     * Get the unit vector of this direction: a vector with a magnitude of 1,
     * pointing in the direction of this Direction3.
     * @return a unit vector
     */
    public Vector3 getUnitVector() {
        if(unitVectorCalculated)
            return unitVector;
        
        return BASE_ROTATION.rotate(new Rotation3(this, 0));
    }
    
    public double getYRotation() { //pitch
        if(orientationCalculated)
            return yRot;
        Vector2 xy = new Vector2(unitVector.getX(), unitVector.getY());
        Vector2 xyz = new Vector2(xy.magnitude(), unitVector.getZ());
        return xyz.direction();
    }
    
    public double getZRotation() { //yaw
        if(orientationCalculated)
            return zRot;
        Vector2 xy = new Vector2(unitVector.getX(), unitVector.getY());
        return xy.direction();
    }
    
    /**
     * Get the inverse of this direction, pointing in the opposite direction
     * @return the inverse of this direction
     */
    public Direction3 inverse() {
        if(orientationCalculated) { //add 180 degrees to each angle
            double newY = getYRotation() + Math.PI;
            double newZ = getZRotation() + Math.PI;
            newY = Vector2.fixRotation(newY);
            newZ = Vector2.fixRotation(newZ);
            return new Direction3(newY, newZ);
        }
        return new Direction3(getUnitVector().inverse());
    }
    
    public Direction3 add(Direction3 d) {
        double newY = getYRotation() + d.getYRotation();
        double newZ = getZRotation() + d.getZRotation();
        newY = Vector2.fixRotation(newY);
        newZ = Vector2.fixRotation(newZ);
        return new Direction3(newY, newZ);
    }
    
    public Direction3 subtract(Direction3 d) {
        double newY = getYRotation() - d.getYRotation();
        double newZ = getZRotation() - d.getZRotation();
        newY = Vector2.fixRotation(newY);
        newZ = Vector2.fixRotation(newZ);
        return new Direction3(newY, newZ);
    }
    
    /**
     * Find the angle between this direction and another.
     * @param d the other direction
     * @return the angle between the two directions, in radians
     */
    public double angleBetween(Direction3 d) {
        Vector3 a = this.getUnitVector();
        Vector3 b = d.getUnitVector();
        double result = Math.acos(
                a.dot(b) / (a.magnitude() * b.magnitude())
        );
        //return Vector2.fixRotation(result);
        return result;
    }
}
