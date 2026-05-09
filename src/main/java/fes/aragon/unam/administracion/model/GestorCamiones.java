package fes.aragon.unam.administracion.model;

import java.util.ArrayList;
import java.util.List;

public class GestorCamiones {
    // 1. Atributo estático de la instancia única
    private static GestorCamiones instancia;

    // 2. La lista vive aquí, protegida
    private ArrayList<Camion> listaCamiones;

    // 3. El constructor inicializa la lista UNA SOLA VEZ
    private GestorCamiones() {
        this.listaCamiones = new ArrayList<>();
    }

    // 4. Método de acceso global
    public static GestorCamiones getInstance() {
        if (instancia == null) {
            instancia = new GestorCamiones();
        }
        return instancia;
    }

    // Métodos de control (La lógica de negocio)
    public void agregarCamion(Camion camion) {
        this.listaCamiones.add(camion);
    }

    public ArrayList<Camion> obtenerTodos() {
        return this.listaCamiones;
    }
}