package mainpkg.geometry;

class Side {
    private final Point p1;
    private final Point p2;

    public Side(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
