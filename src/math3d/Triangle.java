package math3d;

import java.util.*;

/**
 * A Polygon with 3 vertices. All 3 vertices shouldn't be collinear.
 * @author vanjac
 */
public class Triangle implements Polygon {
    private final List<Vertex> vertices;
    private final Direction3 normal;
    private final VertexOrder order;
    
    /**
     * Create a triangle from 3 vertices
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     * @param order the order of the vertices
     */
    public Triangle(Vertex v1, Vertex v2, Vertex v3, VertexOrder order) {
        vertices = new ArrayList<>(3);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        
        this.order = order;
        normal = calculateNormal(
                v1.getPosition(),
                v2.getPosition(),
                v3.getPosition()
        );
    }
    
    /**
     * Create a triangle from 3 vectors. Vertices will be generated from the
     * vectors, with the same normal as the triangle itself.
     * @param v1 the first vector
     * @param v2 the second vector
     * @param v3 the third vector
     * @param order the order of the vertices
     */
    public Triangle(Vector3 v1, Vector3 v2, Vector3 v3, VertexOrder order) {
        vertices = new ArrayList<>(3);
        
        this.order = order;
        normal = calculateNormal(v1, v2, v3);
        
        vertices.add(new Vertex(v1, normal));
        vertices.add(new Vertex(v2, normal));
        vertices.add(new Vertex(v3, normal));
    }
    
    /**
     * Create a triangle from the coordinates of 3 vectors. Vertices will be
     * generated from the vectors, with the same normal as the triangle itself.
     * @param v1x the x value of the first vector
     * @param v1y the y value of the first vector
     * @param v1z the z value of the first vector
     * @param v2x the x value of the second vector
     * @param v2y etc.
     * @param v2z
     * @param v3x
     * @param v3y
     * @param v3z
     * @param order the order of the vertices
     */
    public Triangle(
            double v1x, double v1y, double v1z,
            double v2x, double v2y, double v2z,
            double v3x, double v3y, double v3z,
            VertexOrder order) {
        vertices = new ArrayList<>(3);
        Vector3 v1 = new Vector3(v1x, v1y, v1z);
        Vector3 v2 = new Vector3(v2x, v2y, v2z);
        Vector3 v3 = new Vector3(v3x, v3y, v3z);
        
        this.order = order;
        normal = calculateNormal(v1, v2, v3);
        
        vertices.add(new Vertex(v1, normal));
        vertices.add(new Vertex(v2, normal));
        vertices.add(new Vertex(v3, normal));
    }
    
    /**
     * Create a triangle from an array of 9 values representing 3 vectors.
     * Vertices will be generated from the vectors, with the same normal as the
     * triangle itself
     * @param v an array of 3 groups of x-y-z triples
     * @param order the order of the vertices
     */
    public Triangle(double[] v, VertexOrder order) {
        vertices = new ArrayList<>(3);
        Vector3 v1 = new Vector3(v[0], v[1], v[2]);
        Vector3 v2 = new Vector3(v[3], v[4], v[5]);
        Vector3 v3 = new Vector3(v[6], v[7], v[8]);
        
        this.order = order;
        normal = calculateNormal(v1, v2, v3);
        
        vertices.add(new Vertex(v1, normal));
        vertices.add(new Vertex(v2, normal));
        vertices.add(new Vertex(v3, normal));
    }
    
    @Override
    public String toString() {
        return "Triangle [ (" + vertices.get(0).toString()
                + ") (" + vertices.get(1).toString()
                + ") (" + vertices.get(2).toString()
                + ") ]";
    }
    
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Triangle))
            return false;
        if(o == this)
            return true;
        
        Triangle t = (Triangle)o;
        return vertices.containsAll(t.getVertices())
               && t.getNormal().equals(getNormal());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.normal);
        hash = 29 * hash + Objects.hashCode(this.vertices.get(0));
        hash = 29 * hash + Objects.hashCode(this.vertices.get(1));
        hash = 29 * hash + Objects.hashCode(this.vertices.get(2));
        return hash;
    }
    
    private Direction3 calculateNormal(Vector3 v1, Vector3 v2, Vector3 v3) {
        Direction3 tempNormal = Vector3.normal(v1, v2, v3);
        if(order == VertexOrder.CLOCKWISE)
            tempNormal = tempNormal.inverse();
        return tempNormal;
    }

    @Override
    public PolygonType getType() {
        return PolygonType.TRI;
    }

    @Override
    public VertexOrder getVertexOrder() {
        return order;
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public short getVertexCount() {
        return 3;
    }

    @Override
    public Direction3 getNormal() {
        return normal;
    }

    @Override
    public Collection<Polygon> getTriangles() {
        Set<Polygon> set = new HashSet<>(1);
        set.add(this);
        return set;
    }
}
