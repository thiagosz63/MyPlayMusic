module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    exports application;
    opens application to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}