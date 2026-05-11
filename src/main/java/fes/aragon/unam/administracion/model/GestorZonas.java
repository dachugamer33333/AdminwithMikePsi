package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class GestorZonas {

    private static GestorZonas instancia;

    private ArrayList<Zona> listaZonas;

    private GestorZonas() {
        this.listaZonas = Zona.obtenerDepartamentos();
    }

    public static GestorZonas getInstance() {
        if (instancia == null) {
            instancia = new GestorZonas();
        }
        return instancia;
    }

    public ArrayList<Zona> obtenerTodos() {
        return this.listaZonas;
    }

}