package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.dao.ProductoDAO;
import fes.aragon.unam.administracion.model.Producto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarProductosController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private ComboBox<String> cmbImagen;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtSabor;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Label lblTitulo;

    private ProductoController productoController;
    private Producto productoEditar;
    private final ProductoDAO dao = new ProductoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbImagen.setItems(FXCollections.observableArrayList("pepsi.png"));
    }

    public void setProductoController(ProductoController ctrl) {
        this.productoController = ctrl;
    }

    public void setProducto(Producto producto) {
        this.productoEditar = producto;
        if (producto == null) {
            lblTitulo.setText("Agregar Producto");
            txtId.setText(String.valueOf(generarId()));
        } else {
            lblTitulo.setText("Editar Producto");
            txtId.setText(String.valueOf(producto.getId()));
            txtNombre.setText(producto.getNombre());
            txtSabor.setText(producto.getSabor());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtDescripcion.setText(producto.getDescripcion());
            cmbImagen.setValue(producto.getRutaImagen());
        }
    }

    private int generarId() {
        var lista = dao.listar();
        int max = 0;
        for (Producto p : lista) {
            if (p.getId() > max) max = p.getId();
        }
        return max + 1;
    }

    private boolean validar() {
        String nombre = txtNombre.getText().trim();
        String precio = txtPrecio.getText().trim();

        if (nombre.isEmpty()) {
            mostrarError("El nombre del producto es obligatorio.");
            return false;
        }
        if (precio.isEmpty()) {
            mostrarError("El precio no puede estar vacío.");
            return false;
        }
        try {
            Double.parseDouble(precio);
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un número válido.");
            return false;
        }
        return true;
    }

    @FXML
    void guardar(ActionEvent event) {
        if (!validar()) return;

        String nombre = txtNombre.getText().trim();
        String sabor = txtSabor.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        String descripcion = txtDescripcion.getText().trim();
        String rutaImagen = cmbImagen.getValue() != null ? cmbImagen.getValue() : "";

        if (productoEditar == null) {
            Producto p = new Producto(0, nombre, sabor, precio, descripcion, rutaImagen);
            dao.agregar(p);
        } else {
            productoEditar.setNombre(nombre);
            productoEditar.setSabor(sabor);
            productoEditar.setPrecio(precio);
            productoEditar.setDescripcion(descripcion);
            productoEditar.setRutaImagen(rutaImagen);
            dao.editar(productoEditar);
        }

        productoController.cargarProductos();
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
