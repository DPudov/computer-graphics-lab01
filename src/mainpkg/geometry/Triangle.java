package mainpkg.geometry;

public class Triangle {
    private final Point p1;
    private final Point p2;
    private final Point p3;
    private final double xA;
    private final double xB;
    private final double xC;
    private final double yA;
    private final double yB;
    private final double yC;

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.xA = p1.getWorldX();
        this.xB = p2.getWorldX();
        this.xC = p3.getWorldX();
        this.yA = p1.getWorldY();
        this.yB = p2.getWorldY();
        this.yC = p3.getWorldY();
    }

    private double getSquare() {
        return Math.abs((xB - xA) * (yC - yA) - (xC - xA) * (yB - yA)) / 2;
    }

    private double getMinimumSide() {
        double a = Side.getSideLength(xA, yA, xB, yB);
        double b = Side.getSideLength(xB, yB, xC, yC);
        double c = Side.getSideLength(xA, yA, xC, yC);
        return Math.min(Math.min(a, b), c);
    }

    public double getMaximumHeight() {
        double square = getSquare();
        double minSide = getMinimumSide();
        return minSide != 0 ? 2 * square / minSide : 0;
    }

    public boolean contains(Point p) {
        return p.isEqualTo(p1) || p.isEqualTo(p2) || p.isEqualTo(p3);
    }
}
