package fes.aragon.unam.administracion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane contenedor;

    private void cargarVista(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent vista = loader.load();
        contenedor.getChildren().clear();
        contenedor.getChildren().add(vista);

        AnchorPane.setTopAnchor(vista, 0.0);
        AnchorPane.setBottomAnchor(vista, 0.0);
        AnchorPane.setLeftAnchor(vista, 0.0);
        AnchorPane.setRightAnchor(vista, 0.0);
    }

    @FXML
    void dashboard(ActionEvent event) throws IOException {
        // aquí puedes cargar una vista de dashboard cuando la tengas
    }
    @FXML
    void irTrabajador(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/trabajador.fxml");
    }


    @FXML
    void productos(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/productos-view.fxml");
    }
    @FXML
    void zonas(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/zonas-view.fxml");
    }


    @FXML
    void salir(ActionEvent event) {
        Stage stage = (Stage) contenedor.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarVista("/fes/aragon/unam/administracion/main-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}