package fes.aragon.unam.administracion.controller;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableColumn<?, ?> acciones;

    @FXML
    private TextField bscBuscador;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnTrabajador;


    @FXML
    private Button btnProductos;

    @FXML
    private Button btnSalir;

    @FXML
    private AnchorPane contenedor;

    @FXML
    private Button btnZonas;

    @FXML
    private TableColumn<?, ?> cajas;

    @FXML
    private TableColumn<?, ?> conductor;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> matricula;

    @FXML
    private TableView<?> tabla;

    @FXML
    private TableColumn<?, ?> zona;

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
    void Tabla(ActionEvent event) {

    }

    @FXML
    void agregarATabla(ActionEvent event) {

    }



    @FXML
    void irTrabajador(ActionEvent event) throws IOException {
        cargarVista("/fes/aragon/unam/administracion/trabajador.fxml");
    }

    @FXML
    void dashboard(ActionEvent event) {

    }

    @FXML
    void productos(ActionEvent event) {

    }

    @FXML
    void salir(ActionEvent event) {

    }

    @FXML
    void zonas(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
