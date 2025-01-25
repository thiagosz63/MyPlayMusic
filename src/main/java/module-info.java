module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;

    exports application;
    opens application to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;

}