package controller;

import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class MainController {


    private @FXML ImageView audio;
    private @FXML Label currentTime;
    private @FXML Label musicName;
    private @FXML Slider musicTime;
    private @FXML AnchorPane telaAPP;
    private @FXML Label timeTotal;
    private @FXML Slider volumeSelector;

    @FXML
    private void minimize(MouseEvent event) {
        ((Stage) telaAPP.getScene().getWindow()).toBack();
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
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