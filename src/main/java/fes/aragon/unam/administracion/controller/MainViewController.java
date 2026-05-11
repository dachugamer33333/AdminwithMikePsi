package fes.aragon.unam.administracion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainViewController {

    @FXML
    private TableColumn<?, ?> acciones;

    @FXML
    private TextField bscBuscador;

    @FXML
    private Button btnAgregar;

    @FXML
    private TableColumn<?, ?> cajas;

    @FXML
    private TableColumn<?, ?> conductor;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> matricula;

    @FXML
    private TableView<?> tabla;

    @FXML
    private TableColumn<?, ?> zona;

    @FXML
    void agregaraTabla(ActionEvent event) {

    }

}
