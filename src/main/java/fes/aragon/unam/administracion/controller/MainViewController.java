package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private DatePicker pkFecha;

    @FXML
    private TextField bscBuscador;

    @FXML
    private Button btnAgregar;

    @FXML
    private TableColumn<Camion, Void> colAcciones;

    @FXML
    private TableColumn<Camion, String> colCajas;

    @FXML
    private TableColumn<Camion, String> colConductor;

    @FXML
    private TableColumn<Camion, String> colFecha;

    @FXML
    private TableColumn<Camion, Integer> colId;

    @FXML
    private TableColumn<Camion, String> colMatricula;

    @FXML
    private TableColumn<Camion, String> colZona;

    @FXML
    private TableView<Camion> tabla;

    private ObservableList<Camion> datosTabla;
    private GestorCamiones gestor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestor = GestorCamiones.getInstance();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        colConductor.setCellValueFactory(cellData -> {
            Trabajador t = cellData.getValue().getTrabajador();
            if (t == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(t.getNombre() + " " + t.getApellidoPaterno());
        });

        colZona.setCellValueFactory(cellData -> {
            Camion camion = cellData.getValue();
            ArrayList<Zona> zonas = camion.getZonas();
            if (zonas == null || zonas.isEmpty()) {
                return new SimpleStringProperty("");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < zonas.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(zonas.get(i).getDepartamento());
            }
            return new SimpleStringProperty(sb.toString());
        });

        colCajas.setCellValueFactory(cellData -> {
            Camion camion = cellData.getValue();
            ArrayList<Caja> cajas = camion.getCajas();
            if (cajas == null || cajas.isEmpty()) {
                return new SimpleStringProperty("");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cajas.size(); i++) {
                if (i > 0) sb.append(", ");
                Producto p = cajas.get(i).getTipo();
                sb.append(p != null ? p.getNombre() : "?");
            }
            return new SimpleStringProperty(sb.toString());
        });

        colAcciones.setCellFactory(col -> new TableCell<>() {
            String baseStyle =
                    "-fx-font-size: 12px; -fx-font-weight: bold;" +
                            "-fx-cursor: hand; -fx-text-fill: white;" +
                            "-fx-min-width: 30px; -fx-max-width: 30px;" +
                            "-fx-min-height: 24px; -fx-max-height: 24px;";
            private final Button btnEditar = new Button("🖉");
            private final Button btnEliminar = new Button("🗑");
            private final Button btnAgregarCaja= new Button("+");
            private final HBox box = new HBox(6, btnEditar, btnEliminar,btnAgregarCaja);

            {
                box.setAlignment(Pos.CENTER);
                btnEditar.setStyle(baseStyle + "-fx-background-color: #51C68E");
                btnEliminar.setStyle(baseStyle + "-fx-background-color: #FF1436");
                btnAgregarCaja.setStyle(baseStyle + "-fx-background-color: #28a745");

                btnEditar.setOnAction(e -> abrirVentana(getTableView().getItems().get(getIndex())));
                btnEliminar.setOnAction(e -> eliminar(getTableView().getItems().get(getIndex())));
                btnAgregarCaja.setOnAction(e-> agregarCaja(getTableView().getItems().get(getIndex())) );
            }

            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : box);
            }
        });

        datosTabla = FXCollections.observableArrayList(gestor.obtenerTodos());
        tabla.setItems(datosTabla);
    }

    @FXML
    void agregaraTabla(ActionEvent event) {
        abrirVentana(null);
    }

    private void abrirVentana(Camion camion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fes/aragon/unam/administracion/camion-agregar.fxml"));
            Parent root = loader.load();
            AgregarCamionController ctrl = loader.getController();
            ctrl.setCamion(camion);
            ctrl.setMainViewController(this);

            Stage stage = new Stage();
            stage.setTitle(camion == null ? "Agregar Camión" : "Editar Camión");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error abriendo ventana: " + e.getMessage());
        }
    }

    public void refrescarTabla() {
        datosTabla.setAll(gestor.obtenerTodos());
    }

    private void eliminar(Camion c) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setContentText("¿Eliminar el camión \"" + c.getMatricula() + "\"?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            gestor.eliminarCamion(c.getId());
            refrescarTabla();
        }
    }
    private void agregarCaja(Camion camion)
    {

    }
}
