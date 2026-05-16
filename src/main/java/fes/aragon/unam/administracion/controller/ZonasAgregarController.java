package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.GestorZonas;
import fes.aragon.unam.administracion.model.Zona;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ZonasAgregarController {

    @FXML private TextField  txtId;           // solo lectura
    @FXML private ComboBox<String> cmbDepartamento;
    @FXML private TextField  txtCp;
    @FXML private TextField  txtReferencia;
    @FXML private Label      lblTitulo;

    private ZonasController zonasController;
    private Zona zonaEditar;
    private GestorZonas gestor = GestorZonas.getInstance();

    @FXML
    public void initialize() {
        cmbDepartamento.setItems(
                FXCollections.observableArrayList(Zona.getDepartamentos()));
    }

    public void setZona(Zona zona) {
        this.zonaEditar = zona;
        if (zona == null) {
            lblTitulo.setText("Agregar Zona");
            txtId.setText(String.valueOf(gestor.generarId()));
            cmbDepartamento.setDisable(false);
        } else {
            lblTitulo.setText("Editar Zona");
            txtId.setText(String.valueOf(zona.getId()));
            cmbDepartamento.setValue(zona.getDepartamento());
            cmbDepartamento.setDisable(true); // departamento no editable
            txtCp.setText(zona.getCp());
            txtReferencia.setText(zona.getReferencia());
        }
    }

    public void setZonasController(ZonasController ctrl) {
        this.zonasController = ctrl;
    }

    @FXML
    void guardar(ActionEvent event) {
        String dep  = cmbDepartamento.getValue();
        String cp   = txtCp.getText().trim();
        String ref  = txtReferencia.getText().trim();

        if (dep == null || dep.isEmpty()) {
            mostrarAlerta("Selecciona un departamento.");
            return;
        }

        if (!cp.matches("\\d{5}")) {
            mostrarAlerta("El código postal debe tener exactamente 5 dígitos numéricos.");
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());

        if (zonaEditar == null) {
            gestor.agregarZona(new Zona(id, dep, cp, ref, "ACTIVA"));
        } else {
            gestor.editarZona(id, cp, ref);
        }

        zonasController.refrescarTabla();
        cerrar();
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        ((Stage) txtCp.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}