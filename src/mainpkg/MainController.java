package mainpkg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainpkg.geometry.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class MainController {
    public static final double EPSILON = 0.000001;
    private String defaultText;

    @FXML
    Label resultLabel;

    @FXML
    Button resultButton;

    @FXML
    Button addPointButton;

    @FXML
    TextField inputX;

    @FXML
    TextField inputY;

    @FXML
    ListView pointsList;

    @FXML
    Button drawButton;

    @FXML
    public void initialize() {
        defaultText = resultLabel.getText();

        pointsList.setCellFactory(listView -> new PointCell(this));

        UnaryOperator<TextFormatter.Change> filter = DoubleFilter.getFilter();

        TextFormatter formatter = new TextFormatter(filter);
        inputX.setTextFormatter(formatter);

        TextFormatter formatter2 = new TextFormatter(filter);
        inputY.setTextFormatter(formatter2);

        addPointButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> {
            String xText = inputX.getText();
            String yText = inputY.getText();
            if (xText == null || xText.isEmpty() || yText == null || yText.isEmpty()) {
                showAlertWithError("У точки не определены координаты!");
            } else {
                double xValue = Double.parseDouble(xText);
                double yValue = Double.parseDouble(yText);
                Point newPoint = new Point(xValue, yValue);
                if (!PointSetContainer.getInstance().getPointSet().contains(newPoint)) {
                    PointSetContainer.getInstance().getPointSet().add(newPoint);
                    updateList();
                } else {
                    showAlertWithError("Такая точка уже есть!");
                }
            }

            inputX.clear();
            inputY.clear();
        });

        resultButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> calculateResult());

        drawButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> {
            if (calculateResult()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/canvas_window.fxml"));
                    Parent root = fxmlLoader.load();

                    Stage canvasStage = new Stage();
                    canvasStage.setTitle("А здесь будет построена визуализация полученного решения...");
                    canvasStage.setScene(new Scene(root, 600, 600));
                    canvasStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlertWithError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Что-то пошло не так :(");
        alert.setHeaderText("Произошла ошибка");
        Label label = new Label(error);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void updateList() {
        ObservableList<Point> items = FXCollections.observableArrayList();
        ArrayList<Point> points = PointSetContainer.getInstance().getPointSet().getPoints();
        items.addAll(points);
        pointsList.setItems(items);
    }

    private void clearMarks() {
        ArrayList<Point> points = PointSetContainer.getInstance().getPointSet().getPoints();
        for (Point p : points) {
            p.setInTriangle(false);
        }
    }

    private void markResultPoints(Triangle triangle) {
        PointSet pointSet = PointSetContainer.getInstance().getPointSet();

        int indexP1 = pointSet.indexOf(triangle.getP1());
        int indexP2 = pointSet.indexOf(triangle.getP2());
        int indexP3 = pointSet.indexOf(triangle.getP3());

        ArrayList<Point> points = PointSetContainer.getInstance().getPointSet().getPoints();
        points.get(indexP1).setInTriangle(true);
        points.get(indexP2).setInTriangle(true);
        points.get(indexP3).setInTriangle(true);
        updateList();
    }

    private boolean calculateResult() {
        clearMarks();
        if (PointSetContainer.getInstance().getPointSet().size() < 3) {
            showAlertWithError("Для построения треугольника требуется хотя бы 3 точки!");
        } else {
            Triangle triangle = PointSetContainer.getInstance().getPointSet().findMagicTriangle();
            if (triangle == null) {
                showAlertWithError("Все возможные треугольники вырождены! (являются либо точками, либо линиями)");
            } else {
                double heightLen = triangle.getMaximumHeight();
                if (Math.abs(heightLen) <= EPSILON) {
                    showAlertWithError("Все возможные треугольники вырождены! (являются либо точками, либо линиями)");
                } else {
                    DecimalFormat df = new DecimalFormat("#0.000");
                    resultLabel.setText(defaultText + " " + df.format(heightLen));
                    markResultPoints(triangle);
                    return true;
                }
            }
        }
        return false;
    }
}
