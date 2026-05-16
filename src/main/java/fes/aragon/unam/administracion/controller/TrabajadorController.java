package fes.aragon.unam.administracion.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.unam.administracion.ArchivoTrabajador;
import fes.aragon.unam.administracion.model.GestorTrabajadores;
import fes.aragon.unam.administracion.model.Trabajador;
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
        try {
            int id = Integer.parseInt(txtIdEmpleado.getText());
            if (id <= 0){
                mostrarError("El ID debe ser mayor que 0");
                return false;
            }
        } catch (NumberFormatException e){
            mostrarError ("El ID debe ser un número válido");
            return false;
        }
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

    @FXML
    void anadir(ActionEvent event) {
        if (validar()) {
            Trabajador trabajador = new Trabajador();
            trabajador.setId(Integer.parseInt(txtIdEmpleado.getText()));
            trabajador.setNombre(txtNombre.getText());
            trabajador.setApellidoPaterno(txtApellido.getText());
            trabajador.setActivo(estadoTemporal);

            GestorTrabajadores.getInstance().agregarTrabajador(trabajador);
            tblTrabajadores.refresh();

            if (fotoSeleccionada != null) {
                trabajador.setFotoEmpleado(fotoSeleccionada.getAbsolutePath());
            }
            limpiar();
            try {
                ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
            } catch (Exception e) {
                System.out.println("Error al gaurdar: " + e.getMessage());
            }
        }
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
        txtIdEmpleado.setText("");
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
        txtIdEmpleado.setText("");
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
        if (trabajador != null) {
            trabajador.setId(Integer.parseInt(txtIdEmpleado.getText()));
            trabajador.setNombre(txtNombre.getText());
            trabajador.setApellidoPaterno(txtApellido.getText());
            trabajador.setActivo(estadoTemporal);

            if (fotoSeleccionada != null) {
                trabajador.setFotoEmpleado(fotoSeleccionada.getAbsolutePath());
            }
            tblTrabajadores.refresh();

            try {
                ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
            } catch (Exception e) {
                System.out.println("Error al gaurdar: " + e.getMessage());
            }
            btnGuardarCambios.setVisible(false);
            limpiar();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GestorTrabajadores gestor = GestorTrabajadores.getInstance();
        tblTrabajadores.setItems(GestorTrabajadores.getInstance().getListaTrabajadores());
        try {
            ArrayList<Trabajador> datos = ArchivoTrabajador.leer("trabajadores.dat");
            GestorTrabajadores.getInstance().getListaTrabajadores().setAll(datos);
        } catch (Exception e){
            System.out.println("No se pudo leer el archivo, se inicia lista vacía");
        }


        TextFormatter<String> idFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d{0,4}")){
                return change;
            }
            return null;
        });
        this.txtIdEmpleado.setTextFormatter(idFormatter);

        clmEstatus.setCellValueFactory(cellData -> {
            boolean activo = cellData.getValue().isActivo();
            return new ReadOnlyStringWrapper(activo ? "Activo" : "Inactivo");
        });

        clmAcciones.setCellFactory(param -> new TableCell<Trabajador, Void>() {
            private final FontAwesomeIconView iconoBorrar = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            private final FontAwesomeIconView iconoEditar = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            private final HBox contenedor = new HBox(10, iconoBorrar, iconoEditar);

            {
                iconoBorrar.setStyle("-fx-fill:red; -fx-cursor:hand;");
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
                iconoEditar.setStyle("-fx-fill:green; -fx-cursor:hand;");
                iconoEditar.setOnMouseClicked(event -> {
                    Trabajador trabajador = getTableView().getItems().get(getIndex());
                    txtIdEmpleado.setText(String.valueOf(trabajador.getId()));
                    txtNombre.setText(trabajador.getNombre());
                    txtApellido.setText(trabajador.getApellidoPaterno());
                    estadoTemporal = trabajador.isActivo();
                    if (trabajador.isActivo()) {
                        btnEstado.setText("Activo");
                        btnEstado.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    } else {
                        btnEstado.setText("Inactivo");
                        btnEstado.setStyle("-fx-background-color:red; -fx-text-fill: white");
                    }
                    if (trabajador.getFotoEmpleado() != null) {
                        Image image = new Image(new File(trabajador.getFotoEmpleado()).toURI().toString());
                        imgTrabajador.setImage(image);
                    } else {
                        imgTrabajador.setImage(null);
                    }
                    btnGuardarCambios.setVisible(true);
                    try {
                        ArchivoTrabajador.escribir(new ArrayList<>(GestorTrabajadores.getInstance().getListaTrabajadores()), "trabajadores.dat");
                    } catch (Exception e) {
                        System.out.println("Error al guardar: " + e.getMessage());
                    }
                });
            }
            protected  void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);;
                } else {
                    setGraphic(contenedor);
                }
            }
        });

        btnEstado.setText("Inactivo");
        btnEstado.setStyle("-Fx-background-color: red; -fx-text-fill: white;");
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        clmEstatus.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isActivo() ? "Activo" : "Inactivo") );
        btnGuardarCambios.setVisible(false);
    }
}
