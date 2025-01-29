package utils;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class Utils {
    public static Stage currentStage(MouseEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str.replaceAll(Pattern.quote("."), ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
