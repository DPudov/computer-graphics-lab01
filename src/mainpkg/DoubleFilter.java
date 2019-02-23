package mainpkg;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class DoubleFilter {
    public static UnaryOperator<TextFormatter.Change> getFilter() {
        return t -> {
            if (t.getText().equals("-")) {
                if (t.getControlText().isEmpty()) {
                    return t;
                } else {
                    return null;
                }
            }
            if (t.isReplaced()) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                }
            }

            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
    }
}
