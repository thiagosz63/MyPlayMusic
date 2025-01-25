package utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Utils {
   public static Stage currentStage(MouseEvent event) {
       return (Stage) ((Node) event.getSource()).getScene().getWindow();
   }
}
