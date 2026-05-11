package fes.aragon.unam.administracion.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {
    @FXML
    private MenuItem AgregarCamion;

    @FXML
    private MenuItem buscarCamion;

    @FXML
    private AnchorPane contenedor;

    private void cargarVista(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent vista = loader.load();
        contenedor.getChildren().clear();
        contenedor.getChildren().add(vista);

        // que la vista ocupe todo el AnchorPane
        AnchorPane.setTopAnchor(vista, 0.0);
        AnchorPane.setBottomAnchor(vista, 0.0);
        AnchorPane.setLeftAnchor(vista, 0.0);
        AnchorPane.setRightAnchor(vista, 0.0);
    }

    @FXML
    void irCamiones(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/camion-view.fxml");
    }
    @FXML
    void irZonas(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/zonas-view.fxml");
    }

}