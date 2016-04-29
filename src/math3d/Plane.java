package math3d;

/**
 * An immutable plane in 3d space. There can be multiple implementations of
 * this.
 * @author vanjac
 */
public interface Plane {
    public static Plane at(final Vector3 point, final Direction3 normal) {
        return new Plane() {
            
            @Override
            public Direction3 getNormal() {
                return normal;
            }
            
            @Override
            public Vector3 getPoint() {
                return point;
            }
            
            @Override
            public double[] getPlaneCoefficients() {
                double[] c = new double[4];
                Vector3 unit = normal.getUnitVector();
                c[0] = unit.getX();
                c[1] = unit.getY();
                c[2] = unit.getZ();
                c[3] = -(unit.getX()*point.getX()
                        + unit.getY()*point.getY()
                        + unit.getZ()*point.getZ());
                return c;
            }
            
        };
    }
    
    public static Plane fromPoints(Vector3 v1, Vector3 v2, Vector3 v3) {
        return at(v1, Vector3.normal(v1, v2, v3));
    }
    
//    public static Plane fromCoefficients(double[] c) {
//        return new Plane() {
//
//            @Override
//            public Direction3 getNormal() {
//                return new Direction3(new Vector3(c[0], c[1], c[2]));
//            }
//
//            @Override
//            public Vector3 getPoint() {
//                
//            }
//
//            @Override
//            public double[] getPlaneCoefficients() {
//                return c;
//            }
//            
//        };
//    }
    
    public Direction3 getNormal();
    
    /**
     * Get any point on the plane. It doesn't matter which point is returned,
     * as long as the same point is returned by the same object each time.
     * @return the vector of a point on the plane
     */
    public Vector3 getPoint();
    
    /**
     * Get the coefficients for the equation [ ax + by + cz + d = 0 ].
     * @return an array of the 4 coefficients, in the order { a, b, c, d }
     */
    public double[] getPlaneCoefficients();
}
