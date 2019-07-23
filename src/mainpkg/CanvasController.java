package mainpkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mainpkg.geometry.Point;
import mainpkg.geometry.PointSet;
import mainpkg.geometry.PointSetContainer;
import mainpkg.geometry.Triangle;

public class CanvasController {
    @FXML
    ResizableCanvas canvas;

    @FXML
    StackPane pane;

    @FXML
    public void initialize() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            canvas.widthProperty().bind(pane.widthProperty());
            canvas.heightProperty().bind(pane.heightProperty());
            PointSet set = PointSetContainer.getInstance().getPointSet();
            drawGrid();
            if (set != null) {
                Triangle triangle = set.findMagicTriangle();
                drawTriangle(triangle);
                for (Point p : set.getPoints()) {
                    if (!triangle.contains(p))
                        drawPoint(p);
                }
            } else {
                System.out.println("Point set is null :P");
            }
        });
    }

    private void drawTriangle(Triangle triangle) {
        Point p1 = triangle.getP1();
        Point p2 = triangle.getP2();
        Point p3 = triangle.getP3();

        drawLine(p1, p2);
        drawLine(p2, p3);
        drawLine(p1, p3);
    }

    private void drawLine(Point p1, Point p2) {
        PointSet set = PointSetContainer.getInstance().getPointSet();
        double maxX = set.getMaximumX();
        double minX = set.getMinimumX();
        double maxY = set.getMaximumY();
        double minY = set.getMinimumY();
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        Point screenPoint1 = p1.getScreenPoint(minX, maxX, width, minY, maxY, height);
        Point screenPoint2 = p2.getScreenPoint(minX, maxX, width, minY, maxY, height);
        double x1 = screenPoint1.getWorldX();
        double y1 = screenPoint1.getWorldY();
        double x2 = screenPoint2.getWorldX();
        double y2 = screenPoint2.getWorldY();;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeLine(x1, y1, x2, y2);
        gc.setLineWidth(1);
        gc.strokeText(p1.toString(), x1, y1);
        gc.strokeText(p2.toString(), x2, y2);
    }

    private void drawPoint(Point point) {
        PointSet set = PointSetContainer.getInstance().getPointSet();
        Point p = point.getScreenPoint(set.getMinimumX(), set.getMaximumX(), canvas.getWidth(), set.getMinimumY(), set.getMaximumY(), canvas.getHeight());
        double x = p.getWorldX();
        double y = p.getWorldY();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(x, y, x, y);
        gc.setLineWidth(1);
        gc.strokeText(point.toString(), x, y);
    }

    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.DIMGREY);
        gc.setLineWidth(0.5);
        double step = 10;
        double begin = 0;
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        while (begin < height) {
            begin += step;
            gc.strokeLine(0, begin, width, begin);
        }

        begin = 0;
        while (begin < width) {
            begin += step;
            gc.strokeLine(begin, 0, begin, height);
        }
    }
}
