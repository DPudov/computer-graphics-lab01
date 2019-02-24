package mainpkg;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {
    public ResizableCanvas() {
        // Redraw canvas when size changes.
//        widthProperty().addListener(evt -> draw());
//        heightProperty().addListener(evt -> draw());
    }

//    private void draw() {
//        double width = getWidth();
//        double height = getHeight();
//
//    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
