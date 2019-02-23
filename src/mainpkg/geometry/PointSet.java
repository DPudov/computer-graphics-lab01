package mainpkg.geometry;

import mainpkg.DoubleFilter;
import mainpkg.MainController;

import java.util.ArrayList;

public class PointSet {
    private ArrayList<Point> points;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public PointSet() {
        points = new ArrayList<>();
    }

    public boolean contains(Point point) {
        for (Point p : points) {
            if (point.getWorldX() == p.getWorldX() && point.getWorldY() == p.getWorldY()) {
                return true;
            }
        }
        return false;
    }

    public Point get(int index) {
        if (points == null) {
            return null;
        }

        return points.get(index);
    }

    public int size() {
        return points.size();
    }

    public void add(Point point) {
        points.add(point);
    }

    public void remove(int index) {
        points.remove(index);
    }

    public Triangle findMagicTriangle() {
        if (size() < 3) {
            return null;
        }

        Triangle result = null;
        Point p1, p2, p3;
        double currentMinHeight = Double.MAX_VALUE;
        for (int i = 0; i < size() - 2; i++) {
            p1 = points.get(i);
            for (int j = 1; (j < size() - 1) && (i != j); j++) {
                p2 = points.get(j);
                for (int k = 2; k < size() && (k != j) && (k != i); k++) {
                    p3 = points.get(k);
                    Triangle triangle = new Triangle(p1, p2, p3);
                    double tmp;
                    if ((tmp = triangle.getMaximumHeight()) < currentMinHeight && Math.abs(tmp) >= MainController.EPSILON) {
                        currentMinHeight = tmp;
                        result = triangle;
                    }
                }
            }
        }

        return result;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    private void updateMinMax() {
        if (points.isEmpty()) {
            minX = Double.MAX_VALUE;
            maxX = Double.MIN_VALUE;
            minY = Double.MAX_VALUE;
            maxY = Double.MIN_VALUE;
        }

        for (Point p : points) {
            double curX = p.getWorldX();
            double curY = p.getWorldY();
            if (curX < minX) {
                minX = curX;
            }

            if (curY < minY) {
                minY = curY;
            }

            if (curX > maxX) {
                maxX = curX;
            }

            if (curY > maxY) {
                maxY = curY;
            }
        }
    }

    public double getMinimumX() {
        updateMinMax();
        return minX;
    }

    public double getMinimumY() {
        updateMinMax();
        return minY;
    }

    public double getMaximumX() {
        updateMinMax();
        return maxX;
    }

    public double getMaximumY() {
        updateMinMax();
        return maxY;
    }
}
