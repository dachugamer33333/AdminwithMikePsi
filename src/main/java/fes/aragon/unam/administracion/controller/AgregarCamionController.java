package fes.aragon.unam.administracion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AgregarCamionController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<?> cmbTrabajador;

    @FXML
    private Label lblTitulo;

    @FXML
    private ListView<?> lwDisp;

    @FXML
    private ListView<?> lwSelect;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMatricula;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
