module fes.aragon.unam.administracion {
    requires javafx.controls;
    requires javafx.fxml;


    opens fes.aragon.unam.administracion to javafx.fxml;
    exports fes.aragon.unam.administracion;
}