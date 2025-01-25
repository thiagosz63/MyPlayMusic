package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private static double xOffset = 0;
    private static double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainView.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/MainCSS.css")).toExternalForm());
        stage.setTitle("My Play Music");
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/simboloMusical.png")));
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}