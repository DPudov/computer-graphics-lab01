package mainpkg.geometry;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import mainpkg.DoubleFilter;
import mainpkg.MainController;

import java.text.DecimalFormat;

public class PointCell extends ListCell<Point> {
    private static final String STYLE_LIST_CLASS = "point-list";
    private static final String STYLE_INDEX_CLASS = "point-list-index";
    private static final String STYLE_DELETE_CLASS = "point-list-delete";
    private static final String STYLE_INPUT_CLASS = "point-list-input";

    private MainController controller;

    public PointCell(MainController controller) {
        this.controller = controller;
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void addContent() {
        Label indexLabel = new Label();
        indexLabel.setText(Integer.toString(getIndex() + 1));
        indexLabel.getStyleClass().add(STYLE_LIST_CLASS);
        indexLabel.getStyleClass().add(STYLE_INDEX_CLASS);

        Button deleteButton = new Button();
        deleteButton.setText("Удалить");
        deleteButton.getStyleClass().add(STYLE_LIST_CLASS);
        deleteButton.getStyleClass().add(STYLE_DELETE_CLASS);
        deleteButton.setOnAction(event -> {
            int index = getIndex();
            getListView().getItems().remove(index);
            PointSetContainer.getInstance().removePoint(index);
            controller.updateList();
            System.out.println("deleting " + index);
        });

        DecimalFormat df = new DecimalFormat("#0.000");
        int index = getIndex();
        Point p = PointSetContainer.getInstance().getPointSet().get(index);

        TextField fieldX = new TextField();
        TextField fieldY = new TextField();

        fieldX.setTextFormatter(new TextFormatter<>(DoubleFilter.getFilter()));
        fieldY.setTextFormatter(new TextFormatter<>(DoubleFilter.getFilter()));

        fieldX.setText(df.format(p.getWorldX()));
        fieldY.setText(df.format(p.getWorldY()));

        fieldX.getStyleClass().add(STYLE_LIST_CLASS);
        fieldX.getStyleClass().add(STYLE_INPUT_CLASS);

        fieldY.getStyleClass().add(STYLE_LIST_CLASS);
        fieldY.getStyleClass().add(STYLE_INPUT_CLASS);

        GridPane container = new GridPane();
        container.setHgap(10);
        container.setVgap(4);
        container.setPadding(new Insets(0, 10, 0, 10));

        container.add(indexLabel, 0, 0);
        container.add(fieldX, 1, 0);
        container.add(fieldY, 2, 0);
        container.add(deleteButton, 3, 0);
        setGraphic(container);
    }


    @Override
    public void updateItem(Point obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            clearContent();
        } else {
            clearContent();
            addContent();
        }
    }
}
