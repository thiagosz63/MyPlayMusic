package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import utils.Utils;

public class MainController {

    private static double xOffset = 0;
    private static double yOffset = 0;

    private @FXML ImageView audio;
    private @FXML Label currentTime;
    private @FXML Label musicName;
    private @FXML Slider musicTime;
    private @FXML AnchorPane telaAPP;
    private @FXML Label timeTotal;
    private @FXML Slider volumeSelector;

    @FXML
    private void minimize(MouseEvent mouseEvent) {
        Stage stage = Utils.currentStage(mouseEvent);
        stage.setIconified(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    private void telaAPPDragged(MouseEvent mouseEvent) {
        Stage stage = Utils.currentStage(mouseEvent);
        stage.setX(mouseEvent.getScreenX() - xOffset);
        stage.setY(mouseEvent.getScreenY() - yOffset);
    }

    @FXML
    private void telaAppPressed(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    public void preview(MouseEvent mouseEvent) {
    }

    public void player(MouseEvent mouseEvent) {
    }

    public void pause(MouseEvent mouseEvent) {
    }

    public void stop(MouseEvent mouseEvent) {
    }

    public void next(MouseEvent mouseEvent) {
    }

    public void mute(MouseEvent mouseEvent) {
    }
}