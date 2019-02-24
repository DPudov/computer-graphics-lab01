package mainpkg.geometry;

import java.text.DecimalFormat;

public class Point {
    private double worldX;
    private double worldY;
    private boolean inTriangle;
    private static final double PADDING_SCALE_LEFT_BOTTOM = 0.8;
    private static final double PADDING_SCALE_RIGHT_TOP = 0.1;

    public Point(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.inTriangle = false;
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public int getScreenX(double minX, double maxX, double screenWidth) {
        if (screenWidth == 0 || (maxX - minX) == 0) {
            return 0;
        }

        double nonFormatted = (worldX - minX) * screenWidth / Math.abs(maxX - minX);

        return (int) (nonFormatted * PADDING_SCALE_LEFT_BOTTOM + PADDING_SCALE_RIGHT_TOP * screenWidth);
    }

    public int getScreenY(double minY, double maxY, double screenHeight) {
        if (screenHeight == 0 || maxY - minY == 0) {
            return 0;
        }

        double nonFormatted = (maxY - worldY) * screenHeight / Math.abs(maxY - minY);

        return (int) (nonFormatted * PADDING_SCALE_LEFT_BOTTOM + PADDING_SCALE_RIGHT_TOP * screenHeight);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.000");
        return "( " + df.format(worldX) + " ; " + df.format(worldY) + " )";
    }

    public boolean isEqualTo(Point other) {
        return worldX == other.getWorldX() && worldY == other.getWorldY();
    }

    public void setInTriangle(boolean inTriangle) {
        this.inTriangle = inTriangle;
    }

    public boolean isInTriangle() {
        return inTriangle;
    }

    public void setWorldX(double worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(double worldY) {
        this.worldY = worldY;
    }
}
