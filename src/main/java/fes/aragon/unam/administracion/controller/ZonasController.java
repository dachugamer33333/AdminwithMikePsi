package fes.aragon.unam.administracion.controller;

import fes.aragon.unam.administracion.model.GestorZonas;
import fes.aragon.unam.administracion.model.Zona;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ZonasController {

    @FXML private TableView<Zona>            tablaZonas;
    @FXML private TableColumn<Zona, Integer> colId;
    @FXML private TableColumn<Zona, String>  colDepartamento;
    @FXML private TableColumn<Zona, String>  colCp;
    @FXML private TableColumn<Zona, String>  colReferencia;
    @FXML private TableColumn<Zona, String>  colEstado;
    @FXML private TableColumn<Zona, Void>    colAcciones;

    private ObservableList<Zona> datosTabla;
    private GestorZonas gestor;

    @FXML
    public void initialize() {
        gestor = GestorZonas.getInstance();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        colReferencia.setCellFactory(col -> new TableCell<Zona, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item);
                    Tooltip tip = new Tooltip(item);
                    tip.setWrapText(true);
                    tip.setMaxWidth(300);
                    setTooltip(tip);
                }
            }
        });

        // Color de fila rojo claro si está BLOQUEADA
        tablaZonas.setRowFactory(tv -> new TableRow<Zona>() {
            @Override
            protected void updateItem(Zona zona, boolean empty) {
                super.updateItem(zona, empty);
                if (zona == null || empty) {
                    setStyle("");
                } else if (zona.getEstado().equals("BLOQUEADA")) {
                    setStyle("-fx-background-color: #ffe0e0;");
                } else {
                    setStyle("");
                }
            }
        });

        agregarBotonesAcciones();

        datosTabla = FXCollections.observableArrayList(gestor.obtenerTodos());
        tablaZonas.setItems(datosTabla);
    }

    @FXML
    void abrirAgregar(ActionEvent event) {
        abrirVentana(null);
    }

    private void agregarBotonesAcciones() {

        tablaZonas.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Zona zona = tablaZonas.getSelectionModel().getSelectedItem();
                if (zona != null) {
                    String nombreImagen = zona.getDepartamento()
                            .toLowerCase()
                            .replace(" ", "")
                            .replace("á", "a")
                            .replace("é", "e")
                            .replace("í", "i")
                            .replace("ó", "o")
                            .replace("ú", "u")
                            .replace("ñ", "n");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalle de la Zona");
                    alert.setHeaderText(zona.getDepartamento());
                    alert.setContentText(
                            "ID: "           + zona.getId()           + "\n" +
                                    "Departamento: " + zona.getDepartamento() + "\n" +
                                    "Cód. Postal: "  + zona.getCp()           + "\n" +
                                    "Referencia: "   + zona.getReferencia()   + "\n" +
                                    "Estado: "       + zona.getEstado()
                    );

                    try {
                        javafx.scene.image.Image img = new javafx.scene.image.Image(
                                getClass().getResourceAsStream(
                                        "/fes/aragon/unam/administracion/Imagenes/zonas/" + nombreImagen + ".png"
                                )
                        );
                        javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(img);
                        imgView.setFitWidth(200);
                        imgView.setFitHeight(150);
                        imgView.setPreserveRatio(true);
                        alert.setGraphic(imgView);
                    } catch (Exception e) {
                        System.err.println("No se encontró imagen para: " + nombreImagen);
                    }

                    alert.showAndWait();
                }
            }
        });

        colAcciones.setCellFactory(col -> new TableCell<>() {

            private final Button btnEditar = new Button("🖉");   // lápiz
            private final Button btnBloquear = new Button("⏸");   // bloquear
            private final Button btnEliminar = new Button("🗑");   // eliminar

            {
                String baseStyle =
                        "-fx-font-size: 12px; -fx-font-weight: bold;" +
                                "-fx-cursor: hand; -fx-text-fill: white;" +
                                "-fx-min-width: 30px; -fx-max-width: 30px;" +
                                "-fx-min-height: 24px; -fx-max-height: 24px;";

                btnEditar.setStyle(baseStyle + "-fx-background-color: #51C68E;");
                btnEliminar.setStyle(baseStyle + "-fx-background-color: #FF1436;");

                // Tooltip para que el usuario sepa qué hace cada botón
                btnEditar.setTooltip(new Tooltip("Editar zona"));
                btnEliminar.setTooltip(new Tooltip("Eliminar zona"));

                btnEditar.setOnAction(e -> {
                    Zona zona = getTableView().getItems().get(getIndex());
                    abrirVentana(zona);
                });

                btnBloquear.setOnAction(e -> {
                    Zona zona = getTableView().getItems().get(getIndex());
                    String accion = zona.getEstado().equals("ACTIVA") ? "bloquear" : "activar";
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirmar");
                    confirm.setHeaderText(null);
                    confirm.setContentText("¿Seguro que quieres " + accion
                            + " " + zona.getDepartamento() + "?");
                    confirm.showAndWait().ifPresent(r -> {
                        if (r == ButtonType.OK) {
                            gestor.toggleEstado(zona.getId());
                            refrescarTabla();
                        }
                    });
                });

                btnEliminar.setOnAction(e -> {
                    Zona zona = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Eliminar zona");
                    confirm.setHeaderText(null);
                    confirm.setContentText("¿Seguro que quieres eliminar "
                            + zona.getDepartamento() + "?");
                    confirm.showAndWait().ifPresent(r -> {
                        if (r == ButtonType.OK) {
                            gestor.eliminarZona(zona.getId());
                            refrescarTabla();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Zona zona = getTableView().getItems().get(getIndex());
                    String baseStyle =
                            "-fx-font-size: 12px; -fx-font-weight: bold;" +
                                    "-fx-cursor: hand; -fx-text-fill: white;" +
                                    "-fx-min-width: 28px; -fx-max-width: 28px;" +
                                    "-fx-min-height: 24px; -fx-max-height: 24px;";

                    // Botón bloquear cambia texto y color según estado
                    if (zona.getEstado().equals("ACTIVA")) {
                        btnBloquear.setText("⏸");
                        btnBloquear.setStyle(baseStyle +
                                "-fx-background-color: #e6a817;" +
                                "-fx-font-size: 12px;");
                        btnBloquear.setTooltip(new Tooltip("Bloquear zona"));
                    } else {
                        btnBloquear.setText("▶");
                        btnBloquear.setStyle(baseStyle + "-fx-background-color: #28a745;");
                        btnBloquear.setTooltip(new Tooltip("Activar zona"));
                    }

                    HBox hbox = new HBox(4, btnEditar, btnBloquear, btnEliminar);
                    hbox.setStyle("-fx-alignment: center;");
                    setGraphic(hbox);
                }
            }
        });
    }

    private void abrirVentana(Zona zona) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fes/aragon/unam/administracion/zonas-agregar.fxml"));
            Parent root = loader.load();

            ZonasAgregarController ctrl = loader.getController();
            ctrl.setZona(zona);
            ctrl.setZonasController(this);

            Stage stage = new Stage();
            stage.setTitle(zona == null ? "Agregar Zona" : "Editar Zona");
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
        tablaZonas.refresh();
    }
}