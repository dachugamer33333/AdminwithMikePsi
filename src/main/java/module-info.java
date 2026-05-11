module fes.aragon.unam.administracion {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.base;

    opens fes.aragon.unam.administracion to javafx.fxml, javafx.graphics;
    opens fes.aragon.unam.administracion.controller to javafx.fxml, javafx.graphics;
    opens fes.aragon.unam.administracion.model to javafx.base, javafx.fxml;

}