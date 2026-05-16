package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class GestorProductos {
    // Singleton ALDITOOO
    private static GestorProductos instancia;


    private ArrayList<Producto> listaProductos;
    private GestorProductos() {
        this.listaProductos = new ArrayList<>();
    }

    public static GestorProductos getInstance() {
        if (instancia == null) {
            instancia = new GestorProductos();
        }
        return instancia;
    }


    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }


    public ArrayList<Producto> obtenerTodos() {
        return listaProductos;
    }


    public void eliminarProducto(int id) {
        listaProductos.removeIf(p -> p.getId() == id);
    }


    public Producto buscarProducto(int id) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
