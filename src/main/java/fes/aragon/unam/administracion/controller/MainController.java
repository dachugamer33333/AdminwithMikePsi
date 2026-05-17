package fes.aragon.unam.administracion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnTrabajador;

    @FXML
    private Button btnZonas;

    @FXML
    private AnchorPane contenedor;
    //Reinicia los botones permitiendo hacer el efecto de seleccion de botone
    private void resetBotton(Button btnActual)
    {
        btnDashboard.setStyle("-fx-background-color: #313a46");
        btnProductos.setStyle("-fx-background-color: #313a46");
        btnSalir.setStyle("-fx-background-color: #313a46");
        btnTrabajador.setStyle("-fx-background-color: #313a46");
        btnZonas.setStyle("-fx-background-color: #313a46");
        btnActual.setStyle("-fx-background-color: #4a90d9");
    }

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
        resetBotton(btnDashboard);
        cargarVista("/fes/aragon/unam/administracion/main-view.fxml");

    }
    @FXML
    void irTrabajador(ActionEvent event) throws IOException {
        resetBotton(btnTrabajador);
        cargarVista("/fes/aragon/unam/administracion/trabajador.fxml");
    }


    @FXML
    void productos(ActionEvent event) throws IOException {
        resetBotton(btnProductos);
        cargarVista("/fes/aragon/unam/administracion/productos-view.fxml");
    }
    @FXML
    void zonas(ActionEvent event) throws IOException {
        resetBotton(btnZonas);
        cargarVista("/fes/aragon/unam/administracion/zonas-view.fxml");
    }


    @FXML
    void salir(ActionEvent event) {
        resetBotton(btnSalir);
        Stage stage = (Stage) contenedor.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            resetBotton(btnDashboard);
            cargarVista("/fes/aragon/unam/administracion/main-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}