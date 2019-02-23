package mainpkg.geometry;

public class PointSetContainer {
    private PointSet pointSet;
    private static PointSetContainer ourInstance = new PointSetContainer();

    public static PointSetContainer getInstance() {
        return ourInstance;
    }

    private PointSetContainer() {
        pointSet = new PointSet();
    }

    public PointSet getPointSet() {
        return pointSet;
    }

    public void removePoint(int index) {
        pointSet.remove(index);
    }
}
