package math3d;

import java.util.*;

/**
 * A polygon with 3 or more vertices.
 * @author vanjac
 */
public interface Polygon {
    /**
     * A polygon vertex with a normal vector.
     */
    public class Vertex {
        private final Vector3 position;
        private final Direction3 normal;
        
        public Vertex(Vector3 position, Direction3 normal) {
            this.position = position;
            this.normal = normal;
        }
        
        @Override
        public String toString() {
            return position.toString();
        }
        
        @Override
        public boolean equals(Object o) {
            if(o == null)
                return false;
            if(!(o instanceof Vertex))
                return false;
            if(o == this)
                return true;
            
            Vertex v = (Vertex)o;
            return v.getPosition().equals(position)
                    && v.getNormal().equals(normal);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 73 * hash + Objects.hashCode(this.position);
            hash = 73 * hash + Objects.hashCode(this.normal);
            return hash;
        }
        
        public Vector3 getPosition() {
            return position;
        }
        
        public Direction3 getNormal() {
            return normal;
        }
    }
    
    /**
     * Specifies the number of sides of the polygon. Some types are easier to
     * draw than others.
     * MIXED only applies to meshes, which have multiple polygons.
     */
    public enum PolygonType {
        TRI, QUAD, POLY, MIXED
    }
    
    /**
     * Specifies the order of the vertices in the polygon, assuming you are
     * looking towards the front of the polygon. Used for computing the normal.
     * DOUBLE_SIDED means that the polygon can be viewed from both sides, so it
     * doesn't matter what order the vertices are in.
     * MIXED only applies to meshes, which have multiple polygons.
     */
    public enum VertexOrder {
        CLOCKWISE, COUNTER_CLOCKWISE, DOUBLE_SIDED, MIXED;
        
        public VertexOrder reverse() {
            switch(this) {
                case CLOCKWISE:
                    return COUNTER_CLOCKWISE;
                case COUNTER_CLOCKWISE:
                    return CLOCKWISE;
                default:
                    return this;
            }
        }
        
    }
    
    /**
     * Get the polygon type.
     * @return this polygon's PolygonType
     */
    public PolygonType getType();
    
    /**
     * Get the order of the vertices in this polygon when they are returned by
     * getVertices();
     * @return the VertexOrder of this polygon
     */
    public VertexOrder getVertexOrder();
    
    /**
     * Get the vertices of this polygon.
     * @return a list of vertices, in the order specified by this polygon's
     * VertexOrder.
     */
    public List<Vertex> getVertices();
    
    /**
     * Get the number of vertices in this polygon.
     * @return the number of vertices
     */
    public short getVertexCount();
    
    /**
     * Get the normal vector of this polygon.
     * @return the direction of the normal
     */
    public Direction3 getNormal();
    
    /**
     * Divide this polygon into triangles.
     * @return a collection of TRI polygons that make up this one, in no
     * particular order
     */
    public Collection<Polygon> getTriangles();
}
