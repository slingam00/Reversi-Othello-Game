/**
 * Point.java:
 * Abstraction for a (x,y) point on a two-dimensional coordinate space
 */
public class Point {

    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point)o;
            return p.x == this.x && p.y == this.y;
        }
        return false;
    }
}
