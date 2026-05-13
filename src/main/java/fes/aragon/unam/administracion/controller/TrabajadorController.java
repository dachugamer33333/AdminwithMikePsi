package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.ArchivoTrabajador;
import fes.aragon.unam.administracion.model.Trabajador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TrabajadorController implements Initializable {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnEstado;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnSubirFoto;

    @FXML
    private TableColumn<Trabajador, String> clmApellido;

    @FXML
    private TableColumn<Trabajador, String> clmEstatus;

    @FXML
    private TableColumn<Trabajador, String> clmId;

    @FXML
    private TableColumn<Trabajador, String> clmNombre;

    @FXML
    private TableView<Trabajador> tablaEmpleado;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private TextField txtNombre;

    private ArrayList<Trabajador> trabajadores = new ArrayList<>();

    @FXML
    void actualizar(ActionEvent event) {

    }

    @FXML
    void anadir(ActionEvent event) {
        if (validar()) {
            Trabajador trabajador = new Trabajador();
            trabajador.setId(Integer.parseInt(this.txtIdEmpleado.getText()));
            trabajador.setNombre(this.txtNombre.getText());
            trabajador.setApellidoPaterno(this.txtApellido.getText());
            trabajadores.add(trabajador);
            this.limpiar();
        }
        try {
            ArchivoTrabajador.escribir(trabajadores, "Archivo_trabajadores.pepsi");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(("Aviso"));
            alert.setHeaderText("Notificación");
            alert.setContentText("Se almaceno con éxito");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(("Aviso"));
            alert.setHeaderText("Notificación");
            alert.setContentText("Hay algo inesperado,consulta al programador");
            alert.showAndWait();
        }
    }

    @FXML
    void borrar(ActionEvent event) {

    }

    @FXML
    void cambiarEstado(ActionEvent event) {

    }

    private void limpiar() {
        this.txtIdEmpleado.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
    }

    private boolean validar() {
        return true;
    }

    @FXML
    private void limpiar(ActionEvent event) {
        this.txtIdEmpleado.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
    }

    @FXML
    void subirFoto(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.matches("\\d{1,3}")) {
                return change;
            }
            return null;
        });
        this.txtIdEmpleado.setTextFormatter(formatter);
        this.clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clmApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
    }
}
