package mainpkg.geometry;

public class PointSetContainer {
    private final PointSet pointSet;
    private static final PointSetContainer ourInstance = new PointSetContainer();

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
