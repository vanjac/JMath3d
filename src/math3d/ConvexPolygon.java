package math3d;

import java.util.*;
import static math3d.Polygon.PolygonType.*;

/**
 * A polygon implementation for convex polygons. All ConvexPolygon constructors
 * expect that:
 *  -all vertices are coplanar
 *  -no consecutive vertices are collinear
 *  -the vertices are given in the order matching the specified VertexOrder
 *  -the polygon created by the vertices is convex
 * These rules aren't checked by ConvexPolygon, but if they aren't followed
 * incorrect results or errors could be produced.
 * @author vanjac
 */
public class ConvexPolygon implements Polygon {
    private final List<Vertex> vertices;
    private final Direction3 normal;
    private final VertexOrder order;
    private final PolygonType type;
    
    /**
     * Create a ConvexPolygon from an ordered list of vertices.
     * @param v the vertices
     * @param order the order of the vertices
     */
    public ConvexPolygon(List<Vertex> v, VertexOrder order) {
        vertices = v;
        this.order = order;
        normal = calculateNormal(
                v.get(0).getPosition(),
                v.get(1).getPosition(),
                v.get(2).getPosition()
        );
        
        type = calculateType();
    }
    
    /**
     * Create a ConvexPolygon from an array of vectors. Vertices will be
     * generated from the vectors, with the same normal as the polygon itself.
     * @param vectors the vectors
     * @param order the order of the vertices
     */
    public ConvexPolygon(Vector3[] vectors, VertexOrder order) {
        this.order = order;
        normal = calculateNormal(vectors[0], vectors[1], vectors[2]);
        
        vertices = new ArrayList<>(vectors.length);
        for(Vector3 v : vectors)
            vertices.add(new Vertex(v, normal));
        
        type = calculateType();
    }
    
    @Override
    public String toString() {
        StringBuilder stringValue = new StringBuilder("Polygon [ ");
        for(Vertex v : vertices) {
            stringValue.append("(");
            stringValue.append(v.toString());
            stringValue.append(") ");
        }
        stringValue.append("]");
        return stringValue.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof ConvexPolygon))
            return false;
        if(o == this)
            return true;
        
        ConvexPolygon p = (ConvexPolygon)o;
        return vertices.containsAll(p.getVertices())
               && p.getNormal().equals(getNormal());
    }
    
    //TODO: doesn't allow vertices in different orders
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.vertices);
        hash = 37 * hash + Objects.hashCode(this.normal);
        return hash;
    }
    
    private Direction3 calculateNormal(Vector3 v1, Vector3 v2, Vector3 v3) {
        Direction3 tempNormal = Vector3.normal(v1, v2, v3);
        if(order == VertexOrder.CLOCKWISE)
            tempNormal = tempNormal.inverse();
        return tempNormal;
    }
    
    private PolygonType calculateType() {
        int size = vertices.size();
        switch(size) {
            case 3:
                return TRI;
            case 4:
                return QUAD;
            default:
                return MIXED;
        }
    }
    
    
    @Override
    public PolygonType getType() {
        return type;
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
        return (short)vertices.size();
    }

    @Override
    public Direction3 getNormal() {
        return normal;
    }

    @Override
    public Collection<Polygon> getTriangles() {
        //split into polygons by drawing diagonals from one vertex (startVertex)
        //to all other vertices
        
        int verticesSize = vertices.size();
        Vertex startVertex = vertices.get(0);
        Set<Polygon> triangles = new HashSet<>(verticesSize - 2);
        
        //order will be preserved
        for(int i = 2; i < verticesSize; i++) {
            Polygon p = new Triangle(
                    startVertex,
                    vertices.get(i-1),
                    vertices.get(i),
                    order
            );
            triangles.add(p);
        }
        
        return triangles;
    }
}
