package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.dao.ProductoDAO;
import fes.aragon.unam.administracion.model.Caja;
import fes.aragon.unam.administracion.model.Camion;
import fes.aragon.unam.administracion.model.GestorCamiones;
import fes.aragon.unam.administracion.model.Producto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarCajasController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private ComboBox<Producto> cmbProductos;
    @FXML
    private TextField txtCajas;
    @FXML
    private Label lblTitulo;

    private MainViewController mainViewController;
    private Camion camion;
    private GestorCamiones gestor = GestorCamiones.getInstance();
    private final ProductoDAO dao = new ProductoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Producto> productos = dao.listar();
        cmbProductos.setItems(FXCollections.observableArrayList(productos));

        cmbProductos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getNombre() + " - " + p.getSabor());
            }
        });
        cmbProductos.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getNombre() + " - " + p.getSabor());
            }
        });

        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,9}")) {
                return change;
            }
            return null;
        });
        txtCajas.setTextFormatter(formatter);
    }

    public void setMainViewController(MainViewController ctrl) {
        this.mainViewController = ctrl;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
        int idCaja = camion.getCajas().stream().mapToInt(Caja::getId).max().orElse(0) + 1;
        txtId.setText(String.valueOf(idCaja));
    }

    private boolean validar() {
        Producto producto = cmbProductos.getValue();
        String cajasStr = txtCajas.getText().trim();

        if (producto == null) {
            mostrarError("Debes seleccionar un producto.");
            return false;
        }
        if (cajasStr.isEmpty()) {
            mostrarError("La cantidad de cajas no puede estar vacía.");
            return false;
        }
        int cantidad = Integer.parseInt(cajasStr);
        if (cantidad <= 0) {
            mostrarError("La cantidad debe ser mayor a 0.");
            return false;
        }
        for (Caja c : camion.getCajas()) {
            if (c.getTipo().getId() == producto.getId()) {
                mostrarError("El producto \"" + producto.getNombre() + "\" ya está agregado a este camión.");
                return false;
            }
        }
        return true;
    }

    @FXML
    void guardar(ActionEvent event) {
        if (!validar()) return;

        Producto producto = cmbProductos.getValue();
        int cantidad = Integer.parseInt(txtCajas.getText().trim());
        int idCaja = Integer.parseInt(txtId.getText().trim());

        Caja caja = new Caja(producto, cantidad);
        caja.setId(idCaja);
        camion.agregarCaja(caja);
        gestor.editarCamion(camion);

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
