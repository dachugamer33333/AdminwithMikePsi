package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.*;
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
    private TableColumn<Caja, String> colCajas;

    @FXML
    private TableColumn<Trabajador, String> colConductor;

    @FXML
    private TableColumn<Camion, String> colId;

    @FXML
    private TableColumn<Camion, String> colMatricula;

    @FXML
    private TableColumn<Zona, String> colZona;

    @FXML
    void agregaraTabla(ActionEvent event) {

    }
    private ObservableList<Camion> datosTabla;
    private GestorCamiones gestor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestor= GestorCamiones.getInstance();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colConductor.setCellValueFactory(new PropertyValueFactory<>("trabajador"));
        colZona.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

    }
}
