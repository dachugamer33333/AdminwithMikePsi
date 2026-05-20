package fes.aragon.unam.administracion.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.unam.administracion.ArchivoTrabajador;
import fes.aragon.unam.administracion.model.GestorTrabajadores;
import fes.aragon.unam.administracion.model.Trabajador;
import fes.aragon.unam.administracion.model.Zona;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TrabajadorController implements Initializable {

    Logger logger = Logger.getLogger(TrabajadorController.class.getName());

    @FXML
    private Button btnEstado;
    private boolean estadoTemporal = false;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnSubirFoto;

    @FXML
    private TableColumn<Trabajador, String> clmApellido;

    @FXML
    private TableColumn<Trabajador, String> clmEstatus;

    @FXML
    private TableColumn<Trabajador, Integer> clmId;

    @FXML
    private TableColumn<Trabajador, String> clmNombre;

    @FXML
    private TableView<Trabajador> tblTrabajadores;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private TextField txtNombre;

    @FXML
    private ImageView imgTrabajador;

    private File fotoSeleccionada;


    @FXML
    private TableColumn<Trabajador, Void> clmAcciones;

    private ArrayList<Trabajador> trabajadores = new ArrayList<>();

    private boolean validar() {
        if (txtNombre.getText().trim().isEmpty()){
            mostrarError("El nombre no puede estar vacío");
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()){
            mostrarError("El apellido no puede estar vacío");
            return  false;
        }
        return true;
    }
    private void mostrarError(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(("Error de validación"));
        alert.setHeaderText("Datos invalidos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private int generarIdConsecutivo() {
        if (GestorTrabajadores.getInstance().getListaTrabajadores().isEmpty()) {
            return 1; //primer ID
        }
        return GestorTrabajadores.getInstance().getListaTrabajadores().stream().mapToInt(Trabajador::getId).max().orElse(0) + 1;
    }

    @FXML
    void anadir(ActionEvent event) {
        if (!validar()) {

            return;
        }
            Trabajador trabajador = new Trabajador();
            int id = generarIdConsecutivo();
            trabajador.setId(id);

            txtIdEmpleado.setText(String.valueOf(id));
            txtIdEmpleado.setEditable(false);
            txtIdEmpleado.setStyle("-fx-opacity: 0.5;");

            trabajador.setNombre(txtNombre.getText());
            trabajador.setApellidoPaterno(txtApellido.getText());
            trabajador.setActivo(estadoTemporal);

            GestorTrabajadores.getInstance().agregarTrabajador(trabajador);
            tblTrabajadores.refresh();

            if (fotoSeleccionada != null) {
                trabajador.setFotoEmpleado(fotoSeleccionada.getAbsolutePath());
            }
            try {
                ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
            } catch (Exception e) {
                System.out.println("Error al guardar: " + e.getMessage());
            }
            limpiar();
            txtIdEmpleado.setText(String.valueOf(generarIdConsecutivo()));

        try {
            ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "Archivo_trabajadores.pepsi");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(("Aviso"));
            alert.setHeaderText("Notificación");
            alert.setContentText("Se almaceno con éxito");
            alert.showAndWait();
        } catch (IOException e) {
            logger.warning(e.getMessage());
            mostrarError("No se pudo guardar el archivo");
        }
    }


    @FXML
    void cambiarEstado (ActionEvent event) {
        //Trabajador seleccionado = tblTrabajadores.getSelectionModel().getSelectedItem();
        estadoTemporal = !estadoTemporal;
        if (estadoTemporal){
                btnEstado.setText("Activo");;
                btnEstado.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            } else {
                btnEstado.setText("Inactivo");
                btnEstado.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            }
            tblTrabajadores.refresh();
        }


    private void limpiar() {

        txtNombre.setText("");
        txtApellido.setText("");
        fotoSeleccionada = null;
        imgTrabajador.setImage(null);
        estadoTemporal = false;
        btnEstado.setText("Inactivo");
        btnEstado.setStyle("-fx-background-color:red; -fx-text-fill: white");
    }


    @FXML
    private void limpiar(ActionEvent event) {

        txtNombre.setText("");
        txtApellido.setText("");
        fotoSeleccionada = null;
        imgTrabajador.setImage(null);
        estadoTemporal = false;
        btnEstado.setText("Inactivo");
        btnEstado.setStyle("-fx-background-color:red; -fx-text-fill: white");
    }

    @FXML
    void subirFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto del Trabajador");
        FileChooser.ExtensionFilter filtroImagen = new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jppeg");
        fileChooser.getExtensionFilters().add(filtroImagen);
        File file = fileChooser.showOpenDialog(btnSubirFoto.getScene().getWindow());
        if (file != null) {
            fotoSeleccionada = file;
            Image image = new Image(file.toURI().toString());
            imgTrabajador.setImage(image);
        }
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        Trabajador trabajador = tblTrabajadores.getSelectionModel().getSelectedItem();
        System.out.println("Boton presionado");
        if (trabajador != null) {
            System.out.println(trabajador.getNombre());
            // Actualiza los datos con lo que esta en el formulario
            trabajador.setNombre(txtNombre.getText());
            trabajador.setApellidoPaterno(txtApellido.getText());
            trabajador.setActivo(estadoTemporal);

            if (fotoSeleccionada != null) {
                trabajador.setFotoEmpleado(fotoSeleccionada.getAbsolutePath());
            }
            tblTrabajadores.refresh();
            System.out.println("Tabla refresca");

            try {
                ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(("Confirmación"));
                alert.setHeaderText(null);
                alert.setContentText("Los datos del trabajador se actualizaron correctamente");
                alert.showAndWait();
            } catch (Exception e) {
                System.out.println("Error al guardar: " + e.getMessage());

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo guardar");
                alert.setContentText("Ocurrio un problema al guardar los cambios");
                alert.showAndWait();
            }
            btnGuardarCambios.setVisible(false);
            cancelarEdicion();
        }
    }
    private void cancelarEdicion() {
        limpiar();
        btnGuardarCambios.setVisible(false);
        imgTrabajador.setImage(null);
        btnAnadir.setText("Agregar");
        btnAnadir.setOnAction(this::anadir);
        btnAnadir.setStyle("-fx-background-color: #005995; -fx-text-fill: white;");
        txtIdEmpleado.setText(String.valueOf(generarIdConsecutivo()));
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GestorTrabajadores gestor = GestorTrabajadores.getInstance();
        tblTrabajadores.setItems(GestorTrabajadores.getInstance().getListaTrabajadores());
        try {
            ArrayList<Trabajador> datos = ArchivoTrabajador.leer("trabajadores.dat");
            GestorTrabajadores.getInstance().getListaTrabajadores().setAll(datos);
        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo, se inicia lista vacía");
        }

        clmEstatus.setCellValueFactory(cellData -> {
            boolean activo = cellData.getValue().isActivo();
            return new ReadOnlyStringWrapper(activo ? "Activo" : "Inactivo");
        });

        clmAcciones.setCellFactory(param -> new TableCell<Trabajador, Void>() {
            private final Button iconoEditar = new Button("🖉");   // lápiz
            private final Button iconoBorrar = new Button("🗑");   // eliminar
            private final HBox contenedor = new HBox(6, iconoEditar, iconoBorrar);

            {
                String baseStyle =
                        "-fx-font-size: 11px; -fx-font-weight: bold;" +
                                "-fx-cursor: hand; -fx-text-fill: white;" +
                                "-fx-min-width: 28px; -fx-max-width: 28px;" +
                                "-fx-min-height: 24px; -fx-max-height: 24px;";

                iconoEditar.setStyle(baseStyle + "-fx-background-color: #51C68E;");
                iconoBorrar.setStyle(baseStyle + "-fx-background-color: #FF1436;");
                // Tooltip para que el usuario sepa qué hace cada botón
                iconoEditar.setTooltip(new Tooltip("Editar trabajador"));
                iconoBorrar.setTooltip(new Tooltip("Eliminar trabajador"));

                {
                    iconoBorrar.setOnMouseClicked(event -> {
                        Trabajador trabajador = getTableView().getItems().get(getIndex());
                        GestorTrabajadores.getInstance().eliminarTrabajador(trabajador);
                        getTableView().refresh();
                        try {
                            ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
                        } catch (Exception e) {
                            System.out.println("Error al guardar: " + e.getMessage());
                        }
                    });

                    iconoEditar.setOnMouseClicked(event -> {
                        Trabajador trabajador = getTableView().getItems().get(getIndex());
                        tblTrabajadores.getSelectionModel().select(trabajador);

                        txtIdEmpleado.setText(String.valueOf(trabajador.getId()));
                        txtNombre.setText(trabajador.getNombre());
                        txtApellido.setText(trabajador.getApellidoPaterno());
                        estadoTemporal = trabajador.isActivo();
                        if (trabajador.isActivo()) {
                            btnEstado.setText("Activo");
                            btnEstado.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        } else {
                            btnEstado.setText("Inactivo");
                            btnEstado.setStyle("-fx-background-color: red; -fx-text-fill: white");
                        }
                        if (trabajador.getFotoEmpleado() != null) {
                            Image image = new Image(new File(trabajador.getFotoEmpleado()).toURI().toString());
                            imgTrabajador.setImage(image);
                        } else {
                            imgTrabajador.setImage(null);
                        }
                        btnGuardarCambios.setVisible(true);
                        btnAnadir.setText("Cancelar");
                        btnAnadir.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        btnAnadir.setOnAction(e -> cancelarEdicion());

                        try {
                            ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
                        } catch (Exception e) {
                            System.out.println("Error al guardar: " + e.getMessage());
                        }
                    });
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }

        });

        tblTrabajadores.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Trabajador trabajador = tblTrabajadores.getSelectionModel().getSelectedItem();
                if (trabajador != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalles del trabajador");
                    alert.setHeaderText(trabajador.getNombre() + " " + trabajador.getApellidoPaterno());

                    String detalles = "ID: " + trabajador.getId() + "\n" + "Nombre: " + trabajador.getNombre() + "\n" + "Apellido: " + trabajador.getApellidoPaterno() + "\n" + "Estatus: " + (trabajador.isActivo() ? "Activo" : "Inactivo");
                    alert.setContentText(detalles);


                    if (trabajador.getFotoEmpleado() != null) {
                        File file = new File(trabajador.getFotoEmpleado());
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(120);
                            imageView.setFitWidth(120);
                            imageView.setPreserveRatio(true);
                            alert.setGraphic(imageView);
                        }
                    }
                    alert.showAndWait();
                }
            }
        });


        txtIdEmpleado.setEditable(false);
        txtIdEmpleado.setStyle("-fx-opacity: 0.7;"); // para opacarlo
        int siguienteID = generarIdConsecutivo();
        txtIdEmpleado.setText(String.valueOf(siguienteID));

        btnEstado.setText("Inactivo");
        btnEstado.setStyle("-Fx-background-color: red; -fx-text-fill: white;");
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        clmEstatus.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isActivo() ? "Activo" : "Inactivo"));
        btnGuardarCambios.setVisible(false);

    }
}