package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.GestorZonas;
import fes.aragon.unam.administracion.model.Zona;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ZonasController {

    // Tabla y columnas — fx:id debe coincidir con el FXML
    @FXML private TableView<Zona>            tablaZonas;
    @FXML private TableColumn<Zona, Integer> colId;
    @FXML private TableColumn<Zona, String>  colCp;
    @FXML private TableColumn<Zona, String>  colDepartamento;

    @FXML
    public void initialize() {

        GestorZonas gestor = GestorZonas.getInstance();

        // Decirle a cada columna qué getter usar
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

        ObservableList<Zona> datos = FXCollections.observableArrayList(gestor.obtenerTodos());
        tablaZonas.setItems(datos);
    }
}
