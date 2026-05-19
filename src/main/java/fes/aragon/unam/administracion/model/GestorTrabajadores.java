package fes.aragon.unam.administracion.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class GestorTrabajadores {
    private static GestorTrabajadores instancia;
    private ObservableList<Trabajador> listaTrabajadores;

    private GestorTrabajadores() {
        listaTrabajadores = FXCollections.observableArrayList();
    }
    public static GestorTrabajadores getInstance() {
        if (instancia == null) {
            instancia = new GestorTrabajadores();
        }
        return instancia;
    }
    public ObservableList<Trabajador> getListaTrabajadores() {
        return listaTrabajadores;
    }
    public void agregarTrabajador (Trabajador trabajador) {
        listaTrabajadores.add(trabajador);
    }
    public void eliminarTrabajador(Trabajador trabajador) {
        listaTrabajadores.remove(trabajador);
    }

}
