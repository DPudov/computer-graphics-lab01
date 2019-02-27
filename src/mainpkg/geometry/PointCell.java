package mainpkg.geometry;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import mainpkg.DoubleFilter;
import mainpkg.MainController;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public class PointCell extends ListCell<Point> {
    private static final String STYLE_LIST_CLASS = "point-list";
    private static final String STYLE_INDEX_CLASS = "point-list-index";
    private static final String STYLE_DELETE_CLASS = "point-list-delete";
    private static final String STYLE_INPUT_CLASS = "point-list-input";
    private static final String STYLE_EDIT_CLASS = "point-list-edit";

    private final MainController controller;

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
            ButtonType ok = new ButtonType("Да, хочу удалить", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = confirmDeleting(ok, cancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(cancel) == ok) {
                int index = getIndex();
                getListView().getItems().remove(index);
                PointSetContainer.getInstance().removePoint(index);
                controller.updateList();
                System.out.println("deleting " + index);
            }
        });

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.000", otherSymbols);
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

        Button editButton = new Button();
        editButton.setText("Изменить");
        editButton.getStyleClass().add(STYLE_LIST_CLASS);
        editButton.getStyleClass().add(STYLE_EDIT_CLASS);
        editButton.setOnAction(event -> {
            String xText = fieldX.getText();
            String yText = fieldY.getText();
            PointSet pointSet = PointSetContainer.getInstance().getPointSet();
            Point existing = pointSet.get(getIndex());
            if (!xText.isEmpty() && !yText.isEmpty()) {
                double newValueX = Double.parseDouble(xText);
                double newValueY = Double.parseDouble(yText);
                Point newPoint = new Point(newValueX, newValueY);

                if (!existing.isEqualTo(newPoint)) {
                    if (pointSet.contains(newPoint)) {
                        warningEditing("Такая точка уже есть! Останавливаю изменение...");
                        fieldX.setText(String.valueOf(existing.getWorldX()));
                        fieldY.setText(String.valueOf(existing.getWorldY()));
                    } else {
                        existing.setWorldX(newValueX);
                        existing.setWorldY(newValueY);
                    }
                }
            } else {
                warningEditing("У точки указаны не все координаты! Останавливаю изменение...");
                fieldX.setText(String.valueOf(existing.getWorldX()));
                fieldY.setText(String.valueOf(existing.getWorldY()));
            }
            controller.updateList();

        });

        GridPane container = new GridPane();
        container.setHgap(10);
        container.setVgap(4);
        container.setPadding(new Insets(0, 10, 0, 10));
        if (p.isInTriangle()) {
            container.setStyle("-fx-background-color: greenyellow");
        }

        container.add(indexLabel, 0, 0);
        container.add(fieldX, 1, 0);
        container.add(fieldY, 2, 0);
        container.add(editButton, 3, 0);
        container.add(deleteButton, 4, 0);
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

    private Alert confirmDeleting(ButtonType ok, ButtonType cancel) {
        Point p = PointSetContainer.getInstance().getPointSet().get(getIndex());
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "",
                ok,
                cancel);
        confirmationDialog.setTitle("Подтверждение удаления");
        confirmationDialog.setHeaderText("УДАЛИТЬ?");
        Label label = new Label("Вы действительно хотите удалить точку: " + p.toString() + " ?");
        label.setWrapText(true);
        confirmationDialog.getDialogPane().setContent(label);
        return confirmationDialog;
    }

    private void warningEditing(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Изменение невозможно");
        alert.setHeaderText("Проверьте введённые данные");
        Label label = new Label(message);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }
}
