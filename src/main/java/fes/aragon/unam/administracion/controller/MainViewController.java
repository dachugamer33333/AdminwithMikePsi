package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private TableColumn<?, ?> acciones;

    @FXML
    private TextField bscBuscador;

    @FXML
    private Button btnAgregar;

    @FXML
    private TableColumn<?, ?> colAcciones;

    @FXML
    private TableColumn<Camion, String> colCajas;

    @FXML
    private TableColumn<Camion, String> colConductor;

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
        colConductor.setCellValueFactory(new PropertyValueFactory<>("trabajador"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

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

        datosTabla = FXCollections.observableArrayList(gestor.obtenerTodos());
        tabla.setItems(datosTabla);
    }

    @FXML
    void agregaraTabla(ActionEvent event) {

    }
}
