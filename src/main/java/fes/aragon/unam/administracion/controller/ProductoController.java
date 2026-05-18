package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.dao.ProductoDAO;
import fes.aragon.unam.administracion.model.Producto;
import fes.aragon.unam.administracion.model.Zona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductoController implements Initializable {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colSabor;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, String> colDescripcion;
    @FXML private TableColumn<Producto, Void> colAcciones;

    private final ProductoDAO dao = new ProductoDAO();
    private ObservableList<Producto> datos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarProductos();

        // Doble clic en fila para ver detalle
        tablaProductos.setRowFactory(tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    mostrarDetalle(row.getItem());
                }
            });
            return row;
        });
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Columna de botones
        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar   = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox box = new HBox(6, btnEditar, btnEliminar);

            {
                box.setAlignment(Pos.CENTER);
                btnEditar.setStyle("-fx-background-color:#f39c12; -fx-text-fill:white; -fx-cursor:hand;");
                btnEliminar.setStyle("-fx-background-color:#e74c3c; -fx-text-fill:white; -fx-cursor:hand;");

                btnEditar.setOnAction(e -> abrirVentana(getTableView().getItems().get(getIndex())));
                btnEliminar.setOnAction(e -> eliminar(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : box);
            }
        });

        tablaProductos.setItems(datos);
    }

    public void cargarProductos() {
        datos.setAll(dao.listar());
    }

    @FXML
    private void onAgregar() {
        abrirVentana(null);
    }

    private void abrirVentana(Producto producto) {
        boolean esEdicion = producto != null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fes/aragon/unam/administracion/productos-agregar.fxml"));
            Parent root = loader.load();
            AgregarProductosController ctrl = loader.getController();
            ctrl.setProducto(producto);
            ctrl.setProductoController(this);



            Stage stage = new Stage();
            stage.setTitle(producto == null ? "Agregar Producto" : "Editar Producto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error abriendo ventana: " + e.getMessage());
        }
    }
/*
    private void abrirFormulario(Producto existente) {
        boolean esEdicion = existente != null;

        TextField txtNombre      = new TextField();
        TextField txtSabor       = new TextField();
        TextField txtPrecio      = new TextField();
        TextField txtDescripcion = new TextField();
        ComboBox<String> cmbImagen = new ComboBox<>();
        cmbImagen.getItems().addAll("pepsi.png", "manzanita.png");
        cmbImagen.setPromptText("Seleccionar imagen");

        if (esEdicion) {
            txtNombre.setText(existente.getNombre());
            txtSabor.setText(existente.getSabor());
            txtPrecio.setText(String.valueOf(existente.getPrecio()));
            txtDescripcion.setText(existente.getDescripcion());
            cmbImagen.setValue(existente.getRutaImagen());

        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(esEdicion ? "Editar Producto" : "Agregar Producto");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20));

        grid.add(new Label("Nombre:"),      0, 0); grid.add(txtNombre,      1, 0);
        grid.add(new Label("Sabor:"),       0, 1); grid.add(txtSabor,       1, 1);
        grid.add(new Label("Precio:"),      0, 2); grid.add(txtPrecio,      1, 2);
        grid.add(new Label("Descripción:"), 0, 3); grid.add(txtDescripcion, 1, 3);
        grid.add(new Label("Imagen:"), 0, 4); grid.add(cmbImagen, 1, 4);

        javafx.scene.layout.VBox contenido = new javafx.scene.layout.VBox(grid);
        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> resultado = dialog.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (txtNombre.getText().trim().isEmpty()) {
                mostrarAlerta("El nombre es obligatorio.");
                return;
            }
            double precio;
            try {
                precio = Double.parseDouble(txtPrecio.getText().trim());
            } catch (NumberFormatException e) {
                mostrarAlerta("El precio debe ser un número.");
                return;
            }

            Producto p = esEdicion ? existente : new Producto(0, "", "", 0, "", "");
            p.setNombre(txtNombre.getText().trim());
            p.setSabor(txtSabor.getText().trim());
            p.setPrecio(precio);
            p.setDescripcion(txtDescripcion.getText().trim());
            p.setRutaImagen(cmbImagen.getValue() != null ? cmbImagen.getValue() : "");

            if (esEdicion) dao.editar(p);
            else           dao.agregar(p);

            cargarProductos();
        }
    }
        */
    private void eliminar(Producto p) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setContentText("¿Eliminar el producto \"" + p.getNombre() + "\"?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            dao.eliminar(p.getId());
            cargarProductos();
        }
    }

    private void mostrarDetalle(Producto p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle del Producto");
        alert.setHeaderText(p.getNombre());

        ImageView imgView = new ImageView();
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);
        if (p.getImagen() != null) imgView.setImage(p.getImagen());

        alert.setGraphic(imgView);
        alert.setContentText(
                "ID: "          + p.getId()          + "\n" +
                        "Nombre: "      + p.getNombre()       + "\n" +
                        "Sabor: "       + p.getSabor()        + "\n" +
                        "Precio: $"     + p.getPrecio()       + "\n" +
                        "Descripción: " + p.getDescripcion()
        );
        alert.showAndWait();
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(msg);
        a.showAndWait();
    }
}