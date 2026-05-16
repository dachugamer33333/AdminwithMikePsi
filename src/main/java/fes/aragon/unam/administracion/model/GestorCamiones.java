package fes.aragon.unam.administracion.model;

import java.io.*;
import java.util.ArrayList;

public class GestorCamiones {
    private static GestorCamiones instancia;
    private ArrayList<Camion> listaCamiones;

    private GestorCamiones() {
        this.listaCamiones = leerDeArchivo();
    }

    public static GestorCamiones getInstance() {
        if (instancia == null) {
            instancia = new GestorCamiones();
        }
        return instancia;
    }

    public void agregarCamion(Camion camion) {
        this.listaCamiones.add(camion);
        guardarEnArchivo(listaCamiones);
    }

    public ArrayList<Camion> obtenerTodos() {
        return this.listaCamiones;
    }

    public void eliminarCamion(int id) {
        listaCamiones.removeIf(c -> c.getId() == id);
        guardarEnArchivo(listaCamiones);
    }

    public Camion buscarPorId(int id) {
        for (Camion c : listaCamiones) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public int generarId() {
        int max = 0;
        for (Camion c : listaCamiones) {
            if (c.getId() > max) max = c.getId();
        }
        return max + 1;
    }

    private ArrayList<Camion> leerDeArchivo() {
        ArrayList<Camion> camiones = new ArrayList<>();
        File archivo = new File(System.getProperty("user.dir") + "/datos/camiones.txt");

        if (!archivo.exists()) {
            return camiones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",", -1);
                if (partes.length >= 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    String matricula = partes[1].trim();
                    camiones.add(new Camion(id, matricula));
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo camiones.txt: " + e.getMessage());
        }

        return camiones;
    }

    private void guardarEnArchivo(ArrayList<Camion> camiones) {
        File carpeta = new File(System.getProperty("user.dir") + "/datos");
        if (!carpeta.exists()) carpeta.mkdirs();

        File archivo = new File(carpeta, "camiones.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Camion c : camiones) {
                bw.write(c.getId() + "," + c.getMatricula());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando camiones.txt: " + e.getMessage());
        }
    }
}