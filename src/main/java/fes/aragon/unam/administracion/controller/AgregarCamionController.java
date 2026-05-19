package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.ArchivoTrabajador;
import fes.aragon.unam.administracion.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgregarCamionController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtFecha;
    @FXML
    private ComboBox<Trabajador> cmbTrabajador;
    @FXML
    private ListView<Zona> lwDisp;
    @FXML
    private ListView<Zona> lwSelect;
    @FXML
    private Label lblTitulo;

    private MainViewController mainViewController;
    private Camion camionEditar;
    private GestorCamiones gestor = GestorCamiones.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtFecha.setDisable(true);

        TextFormatter<String> matriculaFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,6}")) {
                return change;
            }
            return null;
        });
        txtMatricula.setTextFormatter(matriculaFormatter);

        cargarTrabajadores();
        cargarZonas();

        lwDisp.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Zona seleccionada = lwDisp.getSelectionModel().getSelectedItem();
                if (seleccionada != null) {
                    lwDisp.getItems().remove(seleccionada);
                    lwSelect.getItems().add(seleccionada);
                }
            }
        });

        lwSelect.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Zona seleccionada = lwSelect.getSelectionModel().getSelectedItem();
                if (seleccionada != null) {
                    lwSelect.getItems().remove(seleccionada);
                    lwDisp.getItems().add(seleccionada);
                }
            }
        });
    }

    private void cargarTrabajadores() {
        ObservableList<Trabajador> items = FXCollections.observableArrayList();
        try {
            ArrayList<Trabajador> lista = ArchivoTrabajador.leer("trabajadores.dat");
            for (Trabajador t : lista) {
                if (t.isActivo()) items.add(t);
            }
        } catch (Exception e) {
            // No hay trabajadores aún
        }
        cmbTrabajador.setItems(items);

        cmbTrabajador.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Trabajador t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? null : t.getNombre() + " " + t.getApellidoPaterno());
            }
        });
        cmbTrabajador.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Trabajador t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? null : t.getNombre() + " " + t.getApellidoPaterno());
            }
        });
    }

    private void cargarZonas() {
        ArrayList<Zona> todas = GestorZonas.getInstance().obtenerTodos();
        ObservableList<Zona> activas = FXCollections.observableArrayList();
        for (Zona z : todas) {
            if ("ACTIVA".equals(z.getEstado())) {
                activas.add(z);
            }
        }
        lwDisp.setItems(activas);
        lwDisp.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Zona z, boolean empty) {
                super.updateItem(z, empty);
                setText(empty || z == null ? null : z.getDepartamento());
            }
        });
        lwSelect.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Zona z, boolean empty) {
                super.updateItem(z, empty);
                setText(empty || z == null ? null : z.getDepartamento());
            }
        });
    }

    public void setMainViewController(MainViewController ctrl) {
        this.mainViewController = ctrl;
    }

    public void setCamion(Camion camion) {
        this.camionEditar = camion;
        if (camion == null) {
            lblTitulo.setText("Agregar Camión");
            txtId.setText(String.valueOf(gestor.generarId()));
        } else {
            lblTitulo.setText("Editar Camión");
            txtId.setText(String.valueOf(camion.getId()));
            txtMatricula.setText(camion.getMatricula());
            txtFecha.setText(camion.getFecha());

            cmbTrabajador.setValue(camion.getTrabajador());

            for (Zona z : camion.getZonas()) {
                lwDisp.getItems().remove(z);
                lwSelect.getItems().add(z);
            }
        }
    }

    private boolean validar() {
        String matricula = txtMatricula.getText().trim();

        if (matricula.isEmpty()) {
            mostrarError("La matrícula no puede estar vacía.");
            return false;
        }
        if (!matricula.matches("\\d{6}")) {
            mostrarError("La matrícula debe tener exactamente 6 dígitos numéricos.");
            return false;
        }
        if (cmbTrabajador.getValue() == null) {
            mostrarError("Debes seleccionar un conductor.");
            return false;
        }
        if (lwSelect.getItems().isEmpty()) {
            mostrarError("Debes seleccionar al menos una zona.");
            return false;
        }
        return true;
    }

    @FXML
    void guardar(ActionEvent event) {
        if (!validar()) return;

        String matricula = txtMatricula.getText().trim();
        String fecha = txtFecha.getText().trim();
        Trabajador trabajador = cmbTrabajador.getValue();
        ArrayList<Zona> zonasSeleccionadas = new ArrayList<>(lwSelect.getItems());

        if (camionEditar == null) {
            Camion c = new Camion(0, matricula);
            c.setFecha(fecha);
            c.setTrabajador(trabajador);
            for (Zona z : zonasSeleccionadas) c.agregarZona(z);
            gestor.agregarCamion(c);
        } else {
            camionEditar.setMatricula(matricula);
            camionEditar.setFecha(fecha);
            camionEditar.setTrabajador(trabajador);
            camionEditar.getZonas().clear();
            for (Zona z : zonasSeleccionadas) camionEditar.agregarZona(z);
            gestor.editarCamion(camionEditar);
        }

        if (mainViewController != null) mainViewController.refrescarTabla();
        cerrar();
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        ((Stage) txtId.getScene().getWindow()).close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error de validación");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
