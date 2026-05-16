package fes.aragon.unam.administracion.dao;

import fes.aragon.unam.administracion.model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private static final String ARCHIVO = "productos.txt";

    // Leer todos los productos del archivo
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    String[] p = linea.split("\\|", -1);
                    if (p.length >= 6) {
                        Producto prod = new Producto(
                                Integer.parseInt(p[0]),
                                p[1], p[2],
                                Double.parseDouble(p[3]),
                                p[4], p[5]
                        );
                        lista.add(prod);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return lista;
    }

    // Guardar todos los productos (sobreescribe el archivo)
    private void guardarTodos(List<Producto> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Producto p : lista) {
                bw.write(p.getId() + "|" + p.getNombre() + "|" + p.getSabor() + "|"
                        + p.getPrecio() + "|" + p.getDescripcion() + "|" + p.getRutaImagen());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando archivo: " + e.getMessage());
        }
    }

    // Agregar producto nuevo
    public void agregar(Producto p) {
        List<Producto> lista = listar();
        int nuevoId = lista.isEmpty() ? 1 : lista.get(lista.size() - 1).getId() + 1;
        p.setId(nuevoId);
        lista.add(p);
        guardarTodos(lista);
    }

    // Editar producto existente
    public void editar(Producto editado) {
        List<Producto> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == editado.getId()) {
                lista.set(i, editado);
                break;
            }
        }
        guardarTodos(lista);
    }

    // Eliminar producto por id
    public void eliminar(int id) {
        List<Producto> lista = listar();
        lista.removeIf(p -> p.getId() == id);
        guardarTodos(lista);
    }
}