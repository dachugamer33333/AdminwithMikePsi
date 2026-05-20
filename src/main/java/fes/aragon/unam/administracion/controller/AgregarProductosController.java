package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.dao.ProductoDAO;
import fes.aragon.unam.administracion.model.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AgregarProductosController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtImagen;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtSabor;
    @FXML private TextField txtDescripcion;
    @FXML private Label lblTitulo;

    private ProductoController productoController;
    private Producto productoEditar;
    private final ProductoDAO dao = new ProductoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

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
            txtImagen.setText(producto.getRutaImagen() != null ? producto.getRutaImagen() : "");
        }
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File archivo = fileChooser.showOpenDialog(txtImagen.getScene().getWindow());
        if (archivo != null) {
            txtImagen.setText(archivo.toURI().toString());
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
        String sabor = txtSabor.getText().trim();
        String precio = txtPrecio.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return false;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mostrarError("El nombre solo puede contener letras.");
            return false;
        }
        if (sabor.isEmpty()) {
            mostrarError("El sabor es obligatorio.");
            return false;
        }
        if (!sabor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mostrarError("El sabor solo puede contener letras.");
            return false;
        }
        if (descripcion.isEmpty()) {
            mostrarError("La descripción es obligatoria.");
            return false;
        }
        if (precio.isEmpty()) {
            mostrarError("El precio es obligatorio.");
            return false;
        }
        try {
            double p = Double.parseDouble(precio);
            if (p <= 0) {
                mostrarError("El precio debe ser mayor a 0.");
                return false;
            }
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
        String rutaImagen = txtImagen.getText().trim();

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